package com.example;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.service.ValidatationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	ValidatationUtil validatationUtil;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Test
	public void xmlFileValidation() throws Exception {
		
		Resource resource = resourceLoader.getResource("classpath:records.xml");
		File file = resource.getFile();
		validatationUtil.readFile(file);
		
	}
	
	@Test
	public void csvFileValidation() throws Exception {
		
		Resource resource = resourceLoader.getResource("classpath:records.csv");
		File file = resource.getFile();
		validatationUtil.readFile(file);
		
	}

}
