package org.litespring.test.v3;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.ConstructorResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.service.v3.PetStoreService;

public class ConstructorResolverTest {

	@Test
	public void testAutowireConstructor() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		reader.loadBeanDenifition(new ClassPathResource("petstore-v3.xml"));
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		ConstructorResolver resolver = new ConstructorResolver(factory);
		
		PetStoreService petStore = (PetStoreService) resolver.autowireConstructor(bd);
		
		assertEquals(1, petStore.getVersion());
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
		
	}

}
