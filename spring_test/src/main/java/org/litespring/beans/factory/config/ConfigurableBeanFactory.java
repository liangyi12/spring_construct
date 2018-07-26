package org.litespring.beans.factory.config;

/**
 * 
 * 可配置bean工厂，可配置ClassLoader
 *
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();
}
