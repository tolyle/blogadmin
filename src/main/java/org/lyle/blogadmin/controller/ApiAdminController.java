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
import org.springframework.web.bind.annotation.*;
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
	public RR findPhoto(Integer currentPage, String searchKey) {

		Page<Photo> page = adminPhotoService.getPhoto(currentPage,"","");

		return RR.success(page);
	}


	@DeleteMapping(value = "/deleteById")
	public RR deleteById(@RequestBody Photo photo) {

		try {
			log.info("删除照片:{}", photo.getId());
			adminPhotoService.deleteById(photo.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RR.success();
	}

	@PostMapping(value = "/click")
	public RR click(@RequestBody Photo photo) {

		log.info("photoId{}:", photo.getId());
		adminPhotoService.updateClickTimes(photo.getId());
		return RR.success();
	}

	@PostMapping(value = "/addPhoto")
	public RR savePhoto(HttpServletRequest request,
			    String title,
			    String spot,
						String spotEn,
			    String city,
						String cityEn,
			    String tags,
			    MultipartFile[] files) {

		log.info("receive data title:{}", title);
		log.info("receive data: files{}", files.length);

		MultiResource multiResource = new MultiResource(Arrays.stream(files).map(multipartFile -> {
			try {
				log.info("上传文件名,{},文件大小,{}M", multipartFile.getOriginalFilename(), multipartFile.getSize() / 1024 / 1024);


				adminPhotoService.savePhoto(multipartFile, title, tags, spot, city,spotEn,cityEn);
				return new InputStreamResource(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
			} catch (IOException e) {
				throw new BusinessException("输入流打开失败", e);
			}
		}).collect(Collectors.toList()));


		return RR.success();
	}


}
