package org.litespring.test.v2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitioValueResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.service.v2.AccountDao;

public class BeanDefinitioValueResolverTest {
	DefaultBeanFactory factory;
	
	@Before
	public void setUp(){
		factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v2.xml");
		reader.loadBeanDenifition(resource);
	}
	
	
	@Test
	public void testResolveRuntimeBeanReference() {
		BeanDefinitioValueResolver resolver = new BeanDefinitioValueResolver(factory);
		RuntimeBeanReference ref = new RuntimeBeanReference("accountDao");
		Object value = resolver.resolveValueIfNecessary(ref);
		assertNotNull(value);
		assertTrue(value instanceof AccountDao);
	}
	
	@Test
	public void testResolveTypeStringValue() {
		BeanDefinitioValueResolver resolver = new BeanDefinitioValueResolver(factory);
		TypedStringValue stringvalue = new TypedStringValue("test");
		Object value = resolver.resolveValueIfNecessary(stringvalue);
		assertNotNull(value);
		assertEquals("test", value);
	}

}
