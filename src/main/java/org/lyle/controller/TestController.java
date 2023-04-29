package org.lyle.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public  class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);


	@RequestMapping("/test")
	@ResponseBody
	String show(){
		logger.info("进入到系统里来了....................");
		return "hello";
	}
}
