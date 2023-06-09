package org.lyle.blogadmin.controller;


import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.io.resource.MultiResource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.lyle.blogadmin.exception.BusinessException;
import org.lyle.blogadmin.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class TestController {
	@Autowired
	private PhotoService photoService;


	@RequestMapping("/test")
	String show() {

		//	Dal.with(Photo.class).select(map);


		log.info("hello @Sfl4j + logback......");

		log.info("进入到dasdfddddd......进入到dasdfddddd......进入到dasdfddddd......进入到dasdfddddd......进入到dasdfddddd......进入到dasdfddddd......进入到dasdfddddd......进入到dasdfddddd......");
//		System.out.print(userBaseMapper.select(map).get(0));
//
//
//
//		//photoService.login("adsfasdf");
//		logger.info("进入到dasdfddddd......");
//		logger.info(photoMapper.getPhoto(2).getId().toString());
		return "test";
	}

	@RequestMapping("/addPhoto")
	String addPhoto() {
		return "addPhoto";
	}

	@RequestMapping("/addPhoto2")
	String addPhoto2() {
		return "addPhoto2";
	}


	@PostMapping("/api/upload")
	String savePhoto2(HttpServletRequest request,
			  String title,
			  String tags,
			  MultipartFile[] files) {

		log.info("receive data:{}", title);
		log.info("receive data:{}", files.length);


		MultiResource multiResource = new MultiResource(Arrays.stream(files).map(multipartFile -> {
			try {
				log.info("上传文件名,{},文件大小,{}M", multipartFile.getOriginalFilename(), multipartFile.getSize() / 1024 / 1024);
				photoService.savePhoto(multipartFile, title, tags);
				return new InputStreamResource(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
			} catch (IOException e) {
				throw new BusinessException("输入流打开失败", e);
			}
		}).collect(Collectors.toList()));


		return "ok";
	}


	@RequestMapping("/api/savePhoto")
	@ResponseBody
	Map<String, Object> savePhoto(MultipartFile[] files, String title, String tags) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (StringUtils.isBlank(files[0].getOriginalFilename())) {
			throw new BusinessException("未选择文件");
		}

		MultiResource multiResource = new MultiResource(Arrays.stream(files).map(multipartFile -> {
			try {
				log.info("上传文件名,{},文件大小,{}M", multipartFile.getOriginalFilename(), multipartFile.getSize() / 1024 / 1024);
				photoService.savePhoto(multipartFile, title, tags);
				return new InputStreamResource(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
			} catch (IOException e) {
				throw new BusinessException("输入流打开失败", e);
			}
		}).collect(Collectors.toList()));


//		Iterator<String> fileNames = request.getFileNames();
//		while (fileNames.hasNext()){
//			String fileName = fileNames.next();
//			logger.info("fileName:{}"+fileName,fileName);
//			List<MultipartFile> fileList = request.getFiles(fileName);
//			if(fileList.size()>0){
//				Iterator<MultipartFile> fileIte = fileList.iterator();
//				while (fileIte.hasNext()){
//					//获取每一个文件
//					MultipartFile multipartFile = fileIte.next();
//					logger.info("multipartFile:{},{}",multipartFile.getName(),multipartFile.getSize());
//
//				}
//			}
//		}
		return result;
	}

}
