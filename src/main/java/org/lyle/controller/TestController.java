package org.lyle.controller;


import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.io.resource.MultiResource;
import jakarta.annotation.Resource;
import org.lyle.entity.Photo;
import org.lyle.exception.BusinessException;
import org.lyle.mapper.BaseMapper;
import org.lyle.mapper.Dal;
import org.lyle.mapper.PhotoMapper;
import org.lyle.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.lyle.service.IdService;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public  class TestController {
	@Autowired
	private PhotoService photoService;
	@Autowired
	PhotoMapper photoMapper;

	@Resource
	private IdService idService;


	@Resource
	BaseMapper<Photo> baseMapper;

	@RequestMapping("/test")
	String show(){

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
	String addPhoto(){
		return "addPhoto";
	}
	@RequestMapping("/savePhoto")
	@ResponseBody
	Map<String,Object> savePhoto(MultipartFile[] files,   String title, String tags){
		Map<String,Object> result = new HashMap<String,Object>();

		if(null == files||files.length==0){
			throw  new BusinessException("未选择文件");
		}

		log.info("上传照片,title:{},tags:{}",title,tags);

		MultiResource multiResource = new MultiResource(
			Arrays.stream(files)
				.map(multipartFile -> {
					try {
						log.info("上传文件名,{},文件大小,{}M",multipartFile.getOriginalFilename(),multipartFile.getSize()/1024/1024);

						photoService.savePhoto(multipartFile,title,tags);
						return new InputStreamResource(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
					} catch (IOException e) {
						throw new BusinessException("输入流打开失败", e);
					}
				}).collect(Collectors.toList())
		);


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
