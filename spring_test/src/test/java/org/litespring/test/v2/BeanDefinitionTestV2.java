package org.litespring.test.v2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;

public class BeanDefinitionTestV2 {

	@Test
	public void testGetBeanDefinition() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v2.xml");
		reader.loadBeanDenifition(resource);
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		List<PropertyValue> pvs = bd.getPropertyValues();
		assertEquals(5, pvs.size());
		//为什么加了两个局部代码块， 是为了省空间吗？
		{
			PropertyValue pv = this.getPropertyValue("accountDao", pvs);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
			
		}
		
		{
			PropertyValue pv = this.getPropertyValue("itemDao", pvs);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
			
		}
	}
	
	//不希望有顺序，因为xml中配置是无序的，可能会改变，不能假设谁在前谁在后，测试用例应该做成通用的，而不是xml配置改变了，测试用例也要变
	//我写代码是在假设某种情况，然后根据那种情况去写，没有考虑通用性
	private static PropertyValue getPropertyValue(String name, List<PropertyValue> pvs) {
		for(PropertyValue pv : pvs) {
			if(name.equals(pv.getName())) {
				return pv;
			}
		}
		return null;
	}

}
