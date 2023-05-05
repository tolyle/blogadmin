package org.lyle;

import cn.hutool.http.HttpRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lyle.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadServiceTest {

	@Autowired
	private IdService idService;

	@Test
	public void bodySubmitData() {

		String url = "/upload";
		Map<String, Object> param = new HashMap<>();
		param.put("uuid", "jklasdfkjljasdfasdfasdf");


		String result = HttpRequest.post(url).body("asdfasdfasdfasdfasdaf").execute().body();

		System.out.println("==========" + result);
	}
}
