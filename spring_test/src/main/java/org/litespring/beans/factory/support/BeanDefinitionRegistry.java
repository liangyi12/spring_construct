package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * 
 * beandefinition获取、注册接口
 *
 */
public interface BeanDefinitionRegistry {
	BeanDefinition getBeanDefinition(String beanId);
	void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);
}
