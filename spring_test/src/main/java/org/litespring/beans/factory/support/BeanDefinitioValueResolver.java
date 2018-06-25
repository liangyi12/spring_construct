package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
/**
 * 实现propertyValue中value变量的转换，
 * ref引用会转换为一个对应的bean实例。
 * String类型还是原来的值
 * @author liyanli
 *
 */
public class BeanDefinitioValueResolver {
	private final DefaultBeanFactory factory;
	
	public BeanDefinitioValueResolver(DefaultBeanFactory factory) {
		this.factory = factory;
	}
	
	public Object resolveValueIfNecessary(Object value) {
		if(value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;
			String beanName = ref.getBeanName();
			Object bean = this.factory.getBean(beanName);
			return bean;
		}else if(value instanceof TypedStringValue) {
			return ((TypedStringValue) value).getValue();
		}else{
			//todo 意思是还没有做完，之后可能有新的类型的resolve
			throw new RuntimeException("the value " + value + " has not implemented");
		}
	}

}
