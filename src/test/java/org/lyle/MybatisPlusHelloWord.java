package org.lyle;

import org.junit.jupiter.api.Test;
import org.lyle.entity.Photo;
import org.lyle.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusHelloWord {


	@Value("${spring.profiles.active}")
	String activeProfiles;

	@Autowired
	private PhotoMapper photoMapper;

	@Test
	public void testSelectList() {
		List<Photo> photoList = photoMapper.selectList(null);
		photoList.forEach(System.out::println);

		System.out.printf("activeProfiles:" + activeProfiles);
	}
}
