package org.lyle.blogadmin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AddPhotoTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	public void addPhoto() throws Exception {
		String url = "/api/admin/addPhoto";

		String path = "d:\\Users\\tolyl\\Pictures\\test";

		File[] files = new File(path).listFiles();

		for (File f : files) {
			FileInputStream fileInputStream = new FileInputStream(f);

			MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "DSCF1556.JPG", "image/jpeg", fileInputStream);

			mockMvc.perform(MockMvcRequestBuilders.multipart(url)
					.file(mockMultipartFile)
					.param("title", "测试" + new Date())
					.param("tags", "旅拍")
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		}


	}

}
