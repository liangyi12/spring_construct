package org.litespring.test.v4;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.litespring.beans.factory.annotation.AutowiredFieldElement;
import org.litespring.beans.factory.annotation.InjectedElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.service.v4.PetStoreService;

public class AutowiredAnnotationProcessorTest {
	
	AccountDao accountDao = new AccountDao();
	ItemDao itemDao = new ItemDao();
	
	DefaultBeanFactory factory = new DefaultBeanFactory(){
		public Object resolveDependency(DependencyDescriptor descriptor){
			if (descriptor.getDependencyType().equals(AccountDao.class)) {
				return accountDao;
			}
			
			if (descriptor.getDependencyType().equals(ItemDao.class)) {
				return itemDao;
			}
			throw new RuntimeException("can't support types except AccountDao and ItemDao");
		}
	};

	@Test
	public void testGetInjectionMetadata() {
		
		AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
		processor.setBeanFactory(factory);
		
		InjectionMetadata injectMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
		List<InjectedElement> injectedElements = injectMetadata.getInjectedElements();
		
		assertEquals(2, injectedElements.size());
		
		assertFieldExists(injectedElements,"accountDao");
		assertFieldExists(injectedElements,"itemDao");
		
		PetStoreService petStore = new PetStoreService();
		
		injectMetadata.inject(petStore);
		
		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		
		assertTrue(petStore.getItemDao() instanceof ItemDao);
	}

	private void assertFieldExists(List<InjectedElement> injectedElements,String fieldName) {
		for (InjectedElement injectedElement : injectedElements){
			AutowiredFieldElement fieldEle = (AutowiredFieldElement)injectedElement;
			Field f = fieldEle.getField();
			if(f.getName().equals(fieldName)) {
				return;
			}
		}
		Assert.fail(fieldName + "does not exist!");
		
	}

}
