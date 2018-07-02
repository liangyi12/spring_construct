package org.litespring.test.v3;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v3.AccountDao;
import org.litespring.service.v3.ItemDao;
import org.litespring.service.v3.PetStoreService;

public class ApplicationContextTestV3 {

	@Test
	public void testBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
		PetStoreService ps = (PetStoreService)ctx.getBean("petStore");
		
		assertNotNull(ps.getAccountDao());
		
		assertNotNull(ps.getItemDao());
		assertTrue(ps.getAccountDao() instanceof AccountDao);
		assertTrue(ps.getItemDao() instanceof ItemDao);
	}

}
