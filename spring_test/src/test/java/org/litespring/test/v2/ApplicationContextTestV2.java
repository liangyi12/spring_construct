package org.litespring.test.v2;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v2.AccountDao;
import org.litespring.service.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;
//测试spring setter注入
public class ApplicationContextTestV2 {

	@Test
	public void testGetBean() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
		PetStoreService ps = (PetStoreService)ctx.getBean("petStore");
		
		assertNotNull(ps.getAccountDao());
		
		assertNotNull(ps.getItemDao());
		assertTrue(ps.getAccountDao() instanceof AccountDao);
		assertTrue(ps.getItemDao() instanceof ItemDao);
		//assertNotNull(ps.getTest());
		}

}
