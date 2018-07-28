package org.litespring.beans.factory;

/**
 * 
 * bean工厂，提供了获取bean的方法。
 *
 */
public interface BeanFactory {
	/**根据beanid得到bean的实例对象*/
	Object getBean(String beanId);

	Class<?> getType(String beanName) throws NoSuchBeanDefinitionException;

}
