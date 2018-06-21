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
		reader.loadBeanDenifition("petstore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
		assertNotNull(petStoreService);
	}
	
	@Test
	public void testInvalidBean() {
		reader.loadBeanDenifition("petstore-v1.xml");
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
			reader.loadBeanDenifition("petstore-v2.xml");
		}catch(BeanDefinitionStoreException e) {
			return;
		}
		Assert.fail("expect BeanDefinitionStoreException");
	}

}
