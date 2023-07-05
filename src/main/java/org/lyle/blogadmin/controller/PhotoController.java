package org.lyle.blogadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.lyle.blogadmin.controller.response.RR;
import org.lyle.blogadmin.controller.vo.PhotoVo;
import org.lyle.blogadmin.entity.Photo;
import org.lyle.blogadmin.entity.Tag;
import org.lyle.blogadmin.service.AdminPhotoService;
import org.lyle.blogadmin.service.TagService;
import org.lyle.blogadmin.utils.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PhotoController {

	@Autowired
	private AdminPhotoService adminPhotoService;
	@Autowired
	private TagService tagService;
	@GetMapping(value = "/tags")
	public RR tags() {
		List<Tag> list = tagService.list();
		return RR.success(list);
	}

	@GetMapping(value = "/echo")
	public RR echo() {
		return RR.success();
	}

	@GetMapping(value = "/index")
	public RR findPhoto(Integer currentPage,String tag,String key) {
		log.info("index,currentPage:{},tag:{},key:{}",currentPage,tag,key);

		Page<Photo> page = adminPhotoService.getPhoto(currentPage,tag,key);

		List<PhotoVo> list = new ArrayList<>();
		page.getRecords().forEach(item -> {
			PhotoVo photoVo = new PhotoVo();

			photoVo.setId(item.getId());
			photoVo.setCity(item.getPhotoCity());
			photoVo.setCameraBrand(item.getSrcCameraBrand());
			if(item.getPhotoTime()!=null){
				photoVo.setPhotoTime(DateUtil.dateAndTime(item.getPhotoTime()));
			}else{
				photoVo.setPhotoTime(DateUtil.dateAndTime(item.getCreateTime()));
			}
			photoVo.setPhotoTouristSpotEn(item.getPhotoTouristSpotEn());
			photoVo.setCityEn(item.getPhotoCityEn());
			photoVo.setEvValue(item.getSrcEvValue());
			photoVo.setAValue(item.getSrcAValue());
			photoVo.setIsoValue(item.getSrcIsoValue());
			photoVo.setFValue(item.getSrcFValue());
			photoVo.setSValue(item.getSrcSValue());
			photoVo.setHeight(item.getThumbnailHeight());
			photoVo.setWidth(item.getThumbnailWidth());
			photoVo.setRating("1");
			photoVo.setFlashMode(item.getSrcFlashMode());
			photoVo.setSize(item.getSrcSize() / 1024);
			photoVo.setUrl(item.getThumbnailUrl());
			photoVo.setSrcImgURL(item.getSrcUrl());
			photoVo.setTitle(item.getTitle());
			photoVo.setResolution(item.getSrcWidth() + "*" + item.getSrcHeight());
			photoVo.setLens(item.getSrcLensModel());
			photoVo.setPhotoTouristSpot(item.getPhotoTouristSpot());
			list.add(photoVo);

		});

		Map<String, Object> retDate = new HashMap<>();
		retDate.put("data", list);
		retDate.put("pages", page.getPages());
		retDate.put("total", page.getTotal());
		retDate.put("hasNext", page.hasNext());
		retDate.put("hashasPrevious", page.hasPrevious());

		return RR.success(retDate);
	}

	@GetMapping(value = "/index2")
	public RR findPhoto2(Integer currentPage) {
		List l = new ArrayList<>();


		Map map = new HashMap();
		map.put("id", "1");
		map.put("city", "深圳");
		map.put("url", "https://images.unsplash.com/photo-1679855315528-106bb5cea34e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=600&q=60");
		map.put("width", "900");
		map.put("height", "1358");
		map.put("title", "可爱的猫咪");
		map.put("photoTime", "2020-12-23 11:12:33");
		map.put("resolution", "5152*7728");
		map.put("size", "28");
		map.put("rating", "1");
		map.put("cameraBrand", "XT3");
		map.put("aValue", "2.2");
		map.put("isoValue", "400");
		map.put("sValue", "1/125");
		map.put("evValue", "+1");
		map.put("fValue", "35");
		map.put("meteringMode", "片中心平局");
		map.put("flashMode", "无");
		map.put("srcImgURL", "http://www.qq.com");
		l.add(map);

		return RR.success(l);
	}
}
