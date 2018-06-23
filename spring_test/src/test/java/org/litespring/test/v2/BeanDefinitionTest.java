package org.litespring.test.v2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.Property;
import org.litespring.beans.factory.config.property.RunTimeReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;

public class BeanDefinitionTest {

	@Test
	public void test() {
		Resource resource = new ClassPathResource("petstore-v2.xml");
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		reader.loadBeanDenifition(resource);
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		assertEquals(2, bd.getProperties().size());
		
		List<Property> pros = bd.getProperties();
		Property pro_0 = (Property)pros.get(0);
		Property pro_1 = (Property)pros.get(1);
		
		assertEquals("accountDao", pro_0.getName());
		assertEquals("itemDao", pro_1.getName());
		
		assertTrue(pro_0.getActualValue() instanceof RunTimeReference);
		
		assertTrue(pro_1.getActualValue() instanceof RunTimeReference);
	}

}
