package org.lyle.controller;


import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.io.resource.MultiResource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lyle.exception.BusinessException;
import org.lyle.mapper.Page;
import org.lyle.response.RR;
import org.lyle.service.AdminPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/admin")
public class ApiAdminController {
	@Autowired
	private AdminPhotoService adminPhotoService;


	@GetMapping(value = "/photoList")
	public RR findPhoto(Long currentPage, String searchKey) {

		Page page = adminPhotoService.findPhoto(searchKey, currentPage);
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);

		return RR.success(map);
	}


	@PostMapping(value = "/addPhoto")
	public RR savePhoto(HttpServletRequest request,
			    String title,
			    String tags,
			    MultipartFile[] files) {

		log.info("receive data:{}", title);
		log.info("receive data:{}", files.length);

		MultiResource multiResource = new MultiResource(Arrays.stream(files).map(multipartFile -> {
			try {
				log.info("上传文件名,{},文件大小,{}M", multipartFile.getOriginalFilename(), multipartFile.getSize() / 1024 / 1024);
				adminPhotoService.savePhoto(multipartFile, title, tags);
				return new InputStreamResource(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
			} catch (IOException e) {
				throw new BusinessException("输入流打开失败", e);
			}
		}).collect(Collectors.toList()));


		return RR.success();
	}


}