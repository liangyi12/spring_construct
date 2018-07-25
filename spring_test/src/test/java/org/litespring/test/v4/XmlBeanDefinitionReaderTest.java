package org.litespring.test.v4;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.context.annotation.ScannedGenericBeanDefinition;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;

public class XmlBeanDefinitionReaderTest {

	@Test
	public void testXmlBeanDefinitionReader() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		ClassPathResource resource = new ClassPathResource("petstore-v4.xml");
		reader.loadBeanDenifition(resource);
		
		String annotationType = Component.class.getName();
		{
			BeanDefinition bd = factory.getBeanDefinition("petStore");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			
			assertTrue(amd.hasAnnotation(annotationType));
			AnnotationAttributes attributeds = amd.getAnnotationAttributes(annotationType);
			assertEquals("petStore", attributeds.getString("value"));
		}
		
		{
			BeanDefinition bd = factory.getBeanDefinition("accountDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			
			assertTrue(amd.hasAnnotation(annotationType));
		}
		
		{
			BeanDefinition bd = factory.getBeanDefinition("itemDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			
			assertTrue(amd.hasAnnotation(annotationType));
		}
	}

}
