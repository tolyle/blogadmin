package org.lyle.blogadmin.controller.vo;

import lombok.Data;

@Data
public class PhotoVo {
	private Long id;
	private String city;
	private String cityEn;
	private String url;
	private Integer width;
	private Integer height;
	private String title;
	private String photoTime;
	private String resolution;
	private Long size;
	private String rating;
	private String cameraBrand;
	private Double aValue;
	private Integer isoValue;
	private String sValue;
	private String evValue;
	private Integer fValue;
	private String meteringMode;
	private String flashMode;
	private String srcImgURL;
	private String lens;
	private String photoTouristSpotEn;

	private String photoTouristSpot;

}
