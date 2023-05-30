package org.lyle.service;

import cn.hutool.core.io.FileUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import org.lyle.entity.Photo;
import org.lyle.exception.QiNiuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Service
@Transactional
public class PhotoService {
	Logger logger = LoggerFactory.getLogger(PhotoService.class);


	public void savePhoto(MultipartFile multipartFile, String title, String tags) {

		long id = 2;
		//上传文件
		try {
			String fileName = multipartFile.getOriginalFilename();
			String fileExt = FileUtil.extName(fileName);
			String newFileName = id + "." + fileExt;
			long size = multipartFile.getSize() / 1024;
			logger.info("save photo,file name:{},file size:{}", newFileName, size);
			FileUtil.writeFromStream(multipartFile.getInputStream(), "d:\\" + newFileName);

			Photo photo = new Photo();
			photo.setId(id);
			photo.setCreateTime(new Date());
			photo.setTitle(title);
			photo.setTags(tags);
			photo.setSrcSize(size);


			Metadata metadata = ImageMetadataReader.readMetadata(multipartFile.getInputStream());


			for (Directory directory : metadata.getDirectories()) {
				if (directory == null) {
					continue;
				}
				System.out.println("=========================" + directory.getClass().getSimpleName());


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

			System.out.println("===============" + photo);


		} catch (Exception e) {
			throw new QiNiuException(e);
		}

		//
	}
}
