package org.litespring.test.v1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTest {

	@Test
	public void testGetBean() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
		PetStoreService petStoreService = (PetStoreService)ctx.getBean("petStore");
		assertNotNull(petStoreService);
	}
	
	@Test
	public void testFileGetBean() {
		//相对路径：相对的是本项目的路径
		ApplicationContext ctx = new FileSystemXmlApplicationContext("./src/test/resources/petstore-v1.xml");
		PetStoreService petStoreService = (PetStoreService)ctx.getBean("petStore");
		assertNotNull(petStoreService);
	}
	 

}
