package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * 
 * 可配置bean工厂，可配置ClassLoader
 *
 */
public interface ConfigurableBeanFactory extends BeanFactory {
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();
}
