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
		ApplicationContext ctx = new FileSystemXmlApplicationContext("..\\..\\..\\petstore-v2.xml");
		PetStoreService petStoreService = (PetStoreService)ctx.getBean("petStore");
		assertNotNull(petStoreService);
	}
	 

}
