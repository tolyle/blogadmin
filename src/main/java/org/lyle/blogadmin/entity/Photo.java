package org.lyle.blogadmin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Photo {
	private Long id;
	//照片标题
	private String title;
	//标签
	private String tags;
	//缩略图地址
	private String thumbnailUrl;
	//缩略图宽度
	private Integer thumbnailWidth;
	//缩略图高度
	private Integer thumbnailHeight;
	//拍摄时间
	private Date photoTime;
	//拍摄国家
	private String photoCountry;
	//拍摄城市
	private String photoCity;
	//拍摄景点
	private String photoTouristSpot;
	//拍摄经度
	private Double photoLatitude;
	//拍摄维度
	private Double photoLongitude;
	//原图地址
	private String srcUrl;
	//拍摄分辨率宽
	private Integer srcWidth;
	//拍摄分辨率高
	private Integer srcHeight;
	//拍摄相机型号
	private String srcCameraModel;
	//拍摄相机镜头型号
	private String srcLensModel;
	//原图大小
	private Long srcSize;
	//原图拍摄相机品牌
	private String srcCameraBrand;
	//原图光圈值
	private Double srcAValue;
	//原图ISO
	private Integer srcIsoValue;
	//原图曝光时间
	private String srcSValue;
	//原图曝光补偿
	private String srcEvValue;
	//原图焦距
	private Integer srcFValue;
	//原图测光模式
	private String srcMeteringMode;
	//原图闪光模式
	private String srcFlashMode;
	private Date createTime;
	private Integer clickTimes;


}
