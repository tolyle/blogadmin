package org.lyle.blogadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.lyle.blogadmin.controller.response.RR;
import org.lyle.blogadmin.entity.Photo;
import org.lyle.blogadmin.service.AdminPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PhotoController {

	@Autowired
	private AdminPhotoService adminPhotoService;

	@GetMapping(value = "/index")
	public RR findPhoto(Integer currentPage) {
		Page<Photo> page = adminPhotoService.getPhoto(currentPage);

		return RR.success(page);
	}
}
