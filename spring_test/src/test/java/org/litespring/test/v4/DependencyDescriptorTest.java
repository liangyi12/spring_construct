package org.litespring.test.v4;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.dao.v4.AccountDao;
import org.litespring.service.v4.PetStoreService;

public class DependencyDescriptorTest {

	@Test
	public void testResolveDependency() throws NoSuchFieldException, SecurityException {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v4.xml");
		
		reader.loadBeanDenifition(resource);
		
		Field f = PetStoreService.class.getDeclaredField("accountDao");
		
		DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
		
		Object o = factory.resolveDependency(descriptor);
		
		assertTrue(o instanceof AccountDao);
	}

}
