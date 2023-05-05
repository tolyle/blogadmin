package org.lyle.tools;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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

	public QiNiu() {
		if (StringUtils.isBlank(token) || auth == null) {
			log.info("qiqiu get token start");
			auth = Auth.create(ACCESS_KEY, SECRET_KEY);
			token = auth.uploadToken(BUCKET);
			log.info("qiqiu get token end");
		}
	}

	public void upload(InputStream inputStream, String fileName) throws QiniuException {
//构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(Region.region2());
		cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
		UploadManager uploadManager = new UploadManager(cfg);

		Response response = uploadManager.put(inputStream, fileName, token, null, null);

		log.info("qiqiu upload file response:{}", response);

	}

	public static void main(String[] args) {
		try {
			InputStream inputStream = new FileInputStream(new File("d:\\Users\\tolyl\\Pictures\\2023\\2023-04-06\\DSCF1557.JPG"));
			new QiNiu().upload(inputStream, "test.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
