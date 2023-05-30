package org.lyle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IdServiceTest {

	@Autowired
	private IdService idService;

	@Test
	public void genId() {

		System.out.println(idService.getId() + "");
	}
}
