package org.litespring.test.v4;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v4.PetStoreService;

public class ApplicationContextTestV4 {

	@Test
	public void testAutoInjection() {
		ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v4.xml");
		PetStoreService petStore = (PetStoreService)context.getBean("petStore");
		
		assertNotNull(petStore);
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
	}

}
