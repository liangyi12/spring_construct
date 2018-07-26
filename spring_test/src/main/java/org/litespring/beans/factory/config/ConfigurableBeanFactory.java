package org.litespring.beans.factory.config;

import java.util.List;

/**
 * 
 * 可配置bean工厂，可配置ClassLoader
 *
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();
	
	void addBeanPostProcessor(BeanPostProcessor postProcessor);
	List<BeanPostProcessor> getBeanPostProcessors();
}
