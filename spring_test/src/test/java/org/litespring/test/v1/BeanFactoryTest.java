package org.litespring.test.v1;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {
	DefaultBeanFactory factory = null;
	XmlBeandefinitionReader reader = null;
	
	@Before
	public void setup() {
		factory = new DefaultBeanFactory();
		reader = new XmlBeandefinitionReader(factory);
	}

	@Test
	public void testGetBean() {
		Resource resource = new ClassPathResource("petstore-v1.xml");
		reader.loadBeanDenifition(resource);
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		assertTrue(bd.isSingleton());
		assertFalse(bd.isPrototype());
		assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
		assertNotNull(petStoreService);
		PetStoreService petStoreService2 = (PetStoreService)factory.getBean("petStore");
		assertTrue(petStoreService.equals(petStoreService2));
	}
	
	@Test
	public void testInvalidBean() {
		Resource resource = new ClassPathResource("petstore-v1.xml");
		reader.loadBeanDenifition(resource);
		try{
			factory.getBean("testbean");
		}catch(BeanCreationException e) {
			return;
		}
		Assert.fail("expect BeanCreationException ");
		
	}
	
	@Test
	public void testInvalidXml() {
		try{
			Resource resource = new ClassPathResource("petstore-v3.xml");
			reader.loadBeanDenifition(resource);
		}catch(BeanDefinitionStoreException e) {
			return;
		}
		Assert.fail("expect BeanDefinitionStoreException");
	}

}
