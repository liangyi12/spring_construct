package org.litespring.test.v4;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.context.annotation.ScannedGenericBeanDefinition;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;

public class ClassPathBeanDefinitionScannerTest {

	@Test
	public void testParseScanedBean() throws IOException {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		String basePackage = "org.litespring.service.v4,org.litespring.dao.v4";
		
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
		scanner.doScan(basePackage);
		
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
