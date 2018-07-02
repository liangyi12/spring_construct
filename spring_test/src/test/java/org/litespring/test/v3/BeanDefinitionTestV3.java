package org.litespring.test.v3;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.ConstructorArgument.ValueHolder;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.support.ClassPathResource;

public class BeanDefinitionTestV3 {

	@Test
	public void testConstructArgument() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		reader.loadBeanDenifition(new ClassPathResource("petstore-v3.xml"));
		
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		assertEquals("org.litespring.service.v3.PetStoreService", bd.getBeanClassName());
		
		ConstructorArgument args = bd.getConstructorArgument();
		List<ValueHolder> valueHolders = args.getArgumentValues();
		
		assertEquals(3, valueHolders.size());
		
		RuntimeBeanReference ref1 = (RuntimeBeanReference)valueHolders.get(0).getValue();
		assertEquals("accountDao", ref1.getBeanName());
		
		RuntimeBeanReference ref2 = (RuntimeBeanReference)valueHolders.get(1).getValue();
		assertEquals("itemDao", ref2.getBeanName());
		
		TypedStringValue value = (TypedStringValue)valueHolders.get(2).getValue();
		assertEquals("1", value.getValue());
	}

}
