package org.lyle.blogadmin.controller;


import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.io.resource.MultiResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lyle.blogadmin.controller.response.RR;
import org.lyle.blogadmin.entity.Photo;
import org.lyle.blogadmin.exception.BusinessException;
import org.lyle.blogadmin.mapper.PhotoMapper;
import org.lyle.blogadmin.service.AdminPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/admin")
public class ApiAdminController {
	@Autowired
	private AdminPhotoService adminPhotoService;

	@Autowired
	private PhotoMapper photoMapper;

	@GetMapping(value = "/photoList")
	public RR findPhoto(Long currentPage, String searchKey) {
		Page<Photo> papge = new Page<>();
		papge.setCurrent(currentPage);
		papge.setSize(10);
		Page<Photo> page = photoMapper.selectPage(papge, null);

		return RR.success(page);
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
