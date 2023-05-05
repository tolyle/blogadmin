package org.lyle;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lyle.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdServiceTest {

	@Autowired
	private IdService idService;

	@Test
	public void genId() {

		System.out.println(idService.getId() + "");
	}
}
