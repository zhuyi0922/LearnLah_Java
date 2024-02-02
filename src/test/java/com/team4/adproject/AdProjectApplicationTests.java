package com.team4.adproject;

import com.team4.adproject.Service.DictionaryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdProjectApplicationTests {
	@Autowired
	private DictionaryServiceImpl dictionaryService;
	@Test
	void TestLoadJson() {
		dictionaryService.getDicFromJson("IELTSluan_2");
	}

}
