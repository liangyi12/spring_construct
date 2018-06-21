package org.litespring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;
/*问题，为什么接口BeanDenifitionRegistry没有DefaultBeanDenifitionRegistry的实现，而是由DefaultBeanFactory来实现这个接口
 * 这样不会违背单一职责吗，DefaultBeanFactory的职责是什么？？
 **/
public class DefaultBeanFactory implements  BeanFactory, BeanDefinitionRegistry{
	
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

	public DefaultBeanFactory() {
	}

	public void registerBeanDefinition(String beanId,
			BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanId, beanDefinition);
		
	}
	
	public BeanDefinition getBeanDefinition(String beanId) {
		return beanDefinitionMap.get(beanId);
	}

	public Object getBean(String beanId) {
		BeanDefinition beanDefinition = getBeanDefinition(beanId);
		if(beanDefinition == null) {
			throw new BeanCreationException("beanDefinition does not exist");
		}
		ClassLoader cl = ClassUtils.getDefaultClassLoader();
		String beanClassName = beanDefinition.getBeanClassName();
		try {
			Class c = cl.loadClass(beanClassName);
			return c.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + "failed" , e);
		}
	}

	

}
