package org.litespring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
	implements  ConfigurableBeanFactory, BeanDefinitionRegistry{
	
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	private ClassLoader classLoader;

	public DefaultBeanFactory() {
	}

	public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanId, beanDefinition);
	}
	
	public BeanDefinition getBeanDefinition(String beanId) {
		return beanDefinitionMap.get(beanId);
	}

	public Object getBean(String beanId) {
		BeanDefinition beanDefinition = getBeanDefinition(beanId);
		if(beanDefinition == null) {
			throw new BeanCreationException(beanId, "beanDefinition does not exist");
		}
		
		if(beanDefinition.isSingleton()){
			Object bean = this.getSingleton(beanId);
			if(bean == null){
				bean = createBean(beanDefinition);
				this.registerSingleton(beanId, bean);
			}
			return bean;
		}
		return createBean(beanDefinition);
	}

	public Object createBean(BeanDefinition beanDefinition) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = beanDefinition.getBeanClassName();
		try {
			Class c = cl.loadClass(beanClassName);
			return c.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + "failed" , e);
		}
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
	}

	

}
