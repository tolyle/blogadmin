package org.lyle.blogadmin.tools;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 七牛文件上传下载
 */
@Slf4j
public class QiNiu {
	private static final String ACCESS_KEY = "g770FVcH8NEKN2Ifvs_7-PXtTVJ521fJktVvPkBC";
	private static final String SECRET_KEY = "KvxXNjvD-BXJ1OR7Z81WPTkHYb2h6wmu5QBEAs7O";
	private static final String BUCKET = "lyle-blog";

	public static String token = "";
	public static Auth auth = null;
	public static Long LAST_TOKEN_TIME = 0L;

	public QiNiu() {

		boolean timeOut = System.currentTimeMillis() - LAST_TOKEN_TIME >= 3600 * 1000;
		log.info("QiNiu Token timeOut:{}", System.currentTimeMillis() - LAST_TOKEN_TIME);
		log.info("QiNiu Token timeOut:{}", timeOut);

		if ((StringUtils.isBlank(token) || auth == null) || timeOut) {


			log.info("qiqiu get token start");
			auth = Auth.create(ACCESS_KEY, SECRET_KEY);
			token = auth.uploadToken(BUCKET);

			LAST_TOKEN_TIME = System.currentTimeMillis();
			log.info("qiqiu get token end");

		}
	}

	public Response delete(String key, String bucket) throws QiniuException {
		Configuration cfg = new Configuration(Region.region2());
		cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
		BucketManager bucketManager = new BucketManager(auth, cfg);

		Response response = bucketManager.delete(bucket, key);

		log.info("qiqiu delete file response:{},{},{}", bucket, key, response);

		return response;
	}

	public void upload(InputStream inputStream, String fileName) throws QiniuException {
//构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(Region.region2());
		cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
		UploadManager uploadManager = new UploadManager(cfg);

		Response response = uploadManager.put(inputStream, fileName, token, null, null);
		log.info("qiqiu upload file response:{}", response);

	}


	/**
	 * 获取图片缩略图
	 *
	 * @param fileName  在七牛中的文件名
	 * @param styleName 图片样式
	 * @throws QiniuException
	 */
	public String getPublicUrl(String fileName, String styleName) throws QiniuException {
		try {

			String domainOfBucket = "https://qiniu.bobjoy.com";
			String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
			String publicUrl = "";
			if (StringUtils.isNotBlank(styleName)) {
				publicUrl = String.format("%s/%s-%s", domainOfBucket, encodedFileName, styleName);
				log.info("获取七牛缩略图:{}", publicUrl);
			} else {
				publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
				log.info("获取七牛原图:{}", publicUrl);
			}

			long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
			String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
			return finalUrl;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}


	public static void main(String[] args) {
		try {
			//InputStream inputStream = new FileInputStream(new File("d:\\Users\\tolyl\\Pictures\\2023\\2023-04-06\\DSCF1557.JPG"));
			//new QiNiu().upload(inputStream, "test.jpg");

			//new QiNiu().getPublicUrl("29dbf0e7-25f4-4463-91cc-adf5aa83e9a9.JPG", "thumbnail");
			new QiNiu().delete("04abf62048f34e49a762772009b97d61.JPG", "lyle-blog");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
