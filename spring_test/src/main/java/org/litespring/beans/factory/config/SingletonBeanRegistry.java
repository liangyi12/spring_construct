package org.litespring.beans.factory.config;

/**
 * 
 * 单例对象注册接口
 *
 */
public interface SingletonBeanRegistry {
	/**注册单例对象*/
	public void registerSingleton(String beanName, Object singletonObject);
	/**根据beaname 获取单例对象*/
	public Object getSingleton(String beanName);
}
