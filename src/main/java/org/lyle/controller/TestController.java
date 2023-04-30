package org.lyle.controller;


import org.lyle.mapper.PhotoMapper;
import org.lyle.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public  class TestController {
	@Autowired
	private PhotoService photoService;
	@Autowired
	PhotoMapper photoMapper;
	Logger logger = LoggerFactory.getLogger(TestController.class);


	@RequestMapping("/test")
	String show(){

		//photoService.login("adsfasdf");
		logger.info("进入到dasdf 了....................");
		logger.info(photoMapper.getPhoto(2).getId().toString());
		return "test";
	}

	@RequestMapping("/addPhoto")
	String addPhoto(){
		return "addPhoto";
	}
	@RequestMapping("/savePhoto")
	@ResponseBody
	Map<String,Object> savePhoto(  MultipartHttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		Iterator<String> fileNames = request.getFileNames();
		while (fileNames.hasNext()){
			String fileName = fileNames.next();
			logger.info("fileName:{}"+fileName,fileName);
			List<MultipartFile> fileList = request.getFiles(fileName);
			if(fileList.size()>0){
				Iterator<MultipartFile> fileIte = fileList.iterator();
				while (fileIte.hasNext()){
					//获取每一个文件
					MultipartFile multipartFile = fileIte.next();
					logger.info("multipartFile:{},{}",multipartFile.getName(),multipartFile.getSize());

				}
			}
		}
		return result;
	}

}
