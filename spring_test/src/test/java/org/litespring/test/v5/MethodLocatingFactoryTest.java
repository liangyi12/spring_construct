package org.litespring.test.v5;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;
import org.litespring.aop.config.MethodLocatingFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.tx.TransactionManager;

public class MethodLocatingFactoryTest {

	@Test
	public void testGetMethod() throws NoSuchMethodException, SecurityException {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v5.xml");
		reader.loadBeanDenifition(resource);
		
		MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
		methodLocatingFactory.setTargetBeanName("tx");
		methodLocatingFactory.setMethodName("start");
		methodLocatingFactory.setBeanFactory(factory);
		
		Method method = methodLocatingFactory.getObject();
		
		assertTrue(TransactionManager.class.equals(method.getDeclaringClass()));
		assertTrue(method.equals(TransactionManager.class.getMethod("start")));
	}

}
