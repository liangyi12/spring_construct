package org.litespring.test.v5;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v5.PetStoreService;
import org.litespring.util.MessageTracker;

public class ApplicationContextTest {
	
	@Before
	public void setup() {
		MessageTracker.clearMsg();
	}

	@Test
	public void testPlaceOrder() {
		ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v5.xml");
		PetStoreService petStore = (PetStoreService) context.getBean("petStore");
		
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
		
		petStore.placeOrder();
		
		List<String> message = MessageTracker.getMsg();
		assertEquals("start", message.get(0));
		assertEquals("place order", message.get(0));
		assertEquals("commit", message.get(0));
	}
	
	public void testPlaceOrderWithException() {
		
	}

}
