package org.lyle.blogadmin.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.lyle.blogadmin.config.Constants;
import org.lyle.blogadmin.entity.Photo;
import org.lyle.blogadmin.exception.BusinessException;
import org.lyle.blogadmin.exception.QiNiuException;
import org.lyle.blogadmin.mapper.PhotoMapper;
import org.lyle.blogadmin.tools.QiNiu;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;


@Slf4j
@Service
public class AdminPhotoService extends ServiceImpl<PhotoMapper, Photo> {


	public void deleteById(Long id) throws QiniuException {
		Photo photo = getById(id);
		if (removeById(id)) {
			new QiNiu().delete(photo.getSrcUrl(), "lyle-blog");
			new QiNiu().delete(photo.getThumbnailUrl(), "lyle-blog");
		}
	}

	public Page<Photo> getPhoto(Integer currentPage,String tag,String key) {
		Page<Photo> papge = new Page<>();
		papge.setCurrent(currentPage);
		papge.setSize(10);

		LambdaQueryWrapper<Photo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.orderByDesc(Photo::getId);
		lambdaQueryWrapper.like(StringUtils.isNotEmpty(tag), Photo::getTags, tag);

		if (StringUtils.isNotEmpty(key)) {

		lambdaQueryWrapper.and(wq -> wq.
				like(StringUtils.isNotEmpty(key), Photo::getPhotoCity, key).or().
				like(StringUtils.isNotEmpty(key), Photo::getPhotoTouristSpot, key).or().
				like(StringUtils.isNotEmpty(key), Photo::getTitle, key).or().
				like(StringUtils.isNotEmpty(key), Photo::getSrcCameraBrand, key).or().
				like(StringUtils.isNotEmpty(key), Photo::getSrcCameraModel, key).or().
				like(StringUtils.isNotEmpty(key), Photo::getSrcLensModel, key).or().
				like(StringUtils.isNotEmpty(key), Photo::getCreateTime, key)
		);
	}


		Page<Photo> page = page(papge, lambdaQueryWrapper);
		page.getRecords().forEach(item -> {
			//获取七牛图片原图
			try {
				item.setSrcUrl(new QiNiu().getPublicUrl(item.getSrcUrl(), ""));
				item.setThumbnailUrl(new QiNiu().getPublicUrl(item.getThumbnailUrl(), ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return page;
	}

	public void updateClickTimes(Long id) {
		Photo photo = getById(id);
		photo.setClickTimes(photo.getClickTimes() + 1);
		saveOrUpdate(photo);
	}

	public void savePhoto(MultipartFile multipartFile, String title, String tags, String spot, String city) throws QiNiuException, IOException {

		String uuid = IdUtil.simpleUUID();
		//上传文件
		String fileName = multipartFile.getOriginalFilename();
		String fileExt = FileUtil.extName(fileName);
		String newFileName = uuid + "." + fileExt;
		long size = multipartFile.getSize() / 1024;
		log.info("save photo,file name:{},file size:{}", newFileName, size);


		long l1 = System.currentTimeMillis();
		//上传到七牛存储里去
		log.info("开始文件上传到七牛.....");
		new QiNiu().upload(multipartFile.getInputStream(), newFileName);
		log.info("结束文件上传到七牛....." + (System.currentTimeMillis() - l1) / 1000);
		//生产缩略图
		ByteArrayOutputStream os = new ByteArrayOutputStream();


		Thumbnails.of(multipartFile.getInputStream())
			//设置缩略图大小，按等比缩放
			.size(Constants.THUMBNAIL_WIDTH, Constants.THUMBNAIL_HEIGHT).toOutputStream(os);

		ByteArrayInputStream fileInputStream = new ByteArrayInputStream(os.toByteArray());
		ByteArrayInputStream fileInputStream2 = new ByteArrayInputStream(os.toByteArray());

		BufferedImage img = ImageIO.read(fileInputStream);

		String thumbFileName = uuid + Constants.THUMBNAIL_SUFFIX + "." + fileExt;
		l1 = System.currentTimeMillis();
		log.info("开始缩略图文件上传到七牛.....");
		new QiNiu().upload(fileInputStream2, thumbFileName);
		log.info("结束缩略图文件上传到七牛....." + (System.currentTimeMillis() - l1) / 1000);
		Photo photo = new Photo();
		photo.setCreateTime(new Date());
		photo.setTitle(title);
		photo.setTags(tags);
		photo.setSrcSize(size);
		photo.setPhotoCity(city);
		photo.setPhotoTouristSpot(spot);
		photo.setThumbnailUrl(thumbFileName);
		photo.setThumbnailWidth(img.getWidth());
		photo.setThumbnailHeight(img.getHeight());

		photo.setSrcUrl(newFileName);

		try {


			Metadata metadata = ImageMetadataReader.readMetadata(multipartFile.getInputStream());

			for (Directory directory : metadata.getDirectories()) {
				if (directory == null) {
					continue;
				}

				if ("ExifSubIFDDirectory".equalsIgnoreCase(directory.getClass().getSimpleName())) {
					if (directory.containsTag(ExifSubIFDDirectory.TAG_FNUMBER)) {
						photo.setSrcAValue(directory.getDouble(ExifSubIFDDirectory.TAG_FNUMBER));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_EXPOSURE_TIME)) {
						photo.setSrcSValue(directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT)) {
						photo.setSrcIsoValue(directory.getInteger(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_FOCAL_LENGTH)) {
						photo.setSrcFValue(directory.getInteger(ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_LENS_MODEL)) {
						photo.setSrcLensModel(directory.getString(ExifSubIFDDirectory.TAG_LENS_MODEL));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_METERING_MODE)) {
						photo.setSrcMeteringMode(directory.getString(ExifSubIFDDirectory.TAG_METERING_MODE));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_FLASH)) {
						photo.setSrcFlashMode(directory.getString(ExifSubIFDDirectory.TAG_FLASH));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_FLASH)) {
						photo.setSrcFlashMode(directory.getString(ExifSubIFDDirectory.TAG_FLASH));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_METERING_MODE)) {
						photo.setSrcMeteringMode(directory.getDescription(ExifSubIFDDirectory.TAG_METERING_MODE));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_FLASH)) {
						photo.setSrcFlashMode(directory.getDescription(ExifSubIFDDirectory.TAG_FLASH));
					}
					if (directory.containsTag(ExifSubIFDDirectory.TAG_EXPOSURE_BIAS)) {
						photo.setSrcEvValue(directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_BIAS));
					}
//
//					for (Tag tag : directory.getTags()) {
//						String tagName = tag.getTagName(); // 标签名
//						String desc = tag.getDescription(); // 标签信息
//						String directName = tag.getDirectoryName();//文档名字
//						//	System.out.println("==================================================");
//
//						System.out.println(tagName + "===" + desc + "===" + directName);//照片信息
//
//					}


				} else if ("ExifIFD0Directory".equalsIgnoreCase(directory.getClass().getSimpleName())) {
					photo.setSrcCameraBrand(directory.getString(ExifIFD0Directory.TAG_MAKE));
					photo.setSrcCameraModel(directory.getString(ExifIFD0Directory.TAG_MODEL));
					photo.setPhotoTime(directory.getDate(ExifIFD0Directory.TAG_DATETIME));
				} else if ("JpegDirectory".equalsIgnoreCase(directory.getClass().getSimpleName())) {
					photo.setSrcWidth(directory.getInteger(JpegDirectory.TAG_IMAGE_WIDTH));
					photo.setSrcHeight(directory.getInteger(JpegDirectory.TAG_IMAGE_HEIGHT));
					photo.setPhotoTime(directory.getDate(ExifIFD0Directory.TAG_DATETIME));
				}


			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}


		save(photo);
		log.info("保存照片成功:{}", photo);


	}
}
