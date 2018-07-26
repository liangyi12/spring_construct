package org.litespring.test.v4;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.LinkedList;

import org.junit.Test;
import org.litespring.beans.factory.annotation.AutowiredFieldElement;
import org.litespring.beans.factory.annotation.InjectedElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.service.v4.PetStoreService;

public class InjectionMetadataTest {

	@Test
	public void testInjection() throws NoSuchFieldException, SecurityException {
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v4.xml");
		reader.loadBeanDenifition(resource);
		
		Class targetClass = PetStoreService.class;
		LinkedList<InjectedElement> elementList = new LinkedList<InjectedElement>();
		
		{
			Field f = targetClass.getDeclaredField("accountDao");
			InjectedElement element = new AutowiredFieldElement(f, true, factory);
			elementList.add(element);
		}
		
		{
			Field f = targetClass.getDeclaredField("itemDao");
			InjectedElement element = new AutowiredFieldElement(f, true, factory);
			elementList.add(element);
		}
		
		InjectionMetadata metadata = new InjectionMetadata(targetClass, elementList);
		
		PetStoreService petStore = new PetStoreService();
		
		metadata.inject(petStore);
		
		assertNotNull(petStore.getAccountDao());
		
		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		
		assertNotNull(petStore.getItemDao());
		
		assertTrue(petStore.getItemDao() instanceof ItemDao);
	}

}
