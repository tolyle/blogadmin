package org.lyle.blogadmin.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
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


	public Page<Photo> getPhoto(Integer currentPage) {
		Page<Photo> papge = new Page<>();
		papge.setCurrent(currentPage);
		papge.setSize(10);
		Page<Photo> page = page(papge, null);
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

	public void savePhoto(MultipartFile multipartFile, String title, String tags) throws QiNiuException, IOException {

		String uuid = IdUtil.simpleUUID();
		//上传文件
		String fileName = multipartFile.getOriginalFilename();
		String fileExt = FileUtil.extName(fileName);
		String newFileName = uuid + "." + fileExt;
		long size = multipartFile.getSize() / 1024;
		log.info("save photo,file name:{},file size:{}", newFileName, size);

		//上传到七牛存储里去
		new QiNiu().upload(multipartFile.getInputStream(), newFileName);

		//生产缩略图
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		Thumbnails.of(multipartFile.getInputStream())
			//设置缩略图大小，按等比缩放
			.size(Constants.THUMBNAIL_WIDTH, Constants.THUMBNAIL_HEIGHT).toOutputStream(os);

		ByteArrayInputStream fileInputStream = new ByteArrayInputStream(os.toByteArray());
		ByteArrayInputStream fileInputStream2 = new ByteArrayInputStream(os.toByteArray());

		BufferedImage img = ImageIO.read(fileInputStream);

		String thumbFileName = uuid + Constants.THUMBNAIL_SUFFIX + "." + fileExt;
		new QiNiu().upload(fileInputStream2, thumbFileName);

		Photo photo = new Photo();
		photo.setCreateTime(new Date());
		photo.setTitle(title);
		photo.setTags(tags);
		photo.setSrcSize(size);

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
					photo.setSrcAValue(directory.getDouble(ExifSubIFDDirectory.TAG_FNUMBER));
					photo.setSrcSValue(directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME));
					photo.setSrcIsoValue(directory.getInteger(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
					photo.setSrcFValue(directory.getInteger(ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
					photo.setSrcLensModel(directory.getString(ExifSubIFDDirectory.TAG_LENS_MODEL));
					photo.setSrcMeteringMode(directory.getString(ExifSubIFDDirectory.TAG_METERING_MODE));
					photo.setSrcFlashMode(directory.getString(ExifSubIFDDirectory.TAG_FLASH));
					photo.setSrcMeteringMode(directory.getDescription(ExifSubIFDDirectory.TAG_METERING_MODE));
					photo.setSrcFlashMode(directory.getDescription(ExifSubIFDDirectory.TAG_FLASH));
					photo.setSrcEvValue(directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_BIAS));
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
