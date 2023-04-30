package org.lyle.controller;


import org.lyle.mapper.PhotoMapper;
import org.lyle.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public  class TestController {
	@Autowired
	private PhotoService photoService;
	@Autowired
	PhotoMapper photoMapper;
	Logger logger = LoggerFactory.getLogger(TestController.class);


	@RequestMapping("/test")
	@ResponseBody
	String show(){
		//photoService.login("adsfasdf");
		logger.info("进入到系统里来了....................");
		logger.info(photoMapper.getPhoto(2).getId().toString());
		return "hello";
	}
}
