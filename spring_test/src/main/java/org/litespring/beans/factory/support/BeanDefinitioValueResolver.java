package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
/**
 * 实现propertyValue中value变量的解析，
 * ref引用会解析为一个对应的bean实例。
 * String类型还是原来的值
 *
 */
public class BeanDefinitioValueResolver {
	//factory用来getBean
	private final BeanFactory factory;
	
	public BeanDefinitioValueResolver(BeanFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * 如果有必要则对value进行解析
	 * value 为 RuntimeBeanReference 类型，则解析为一个bean
	 * value 为TypedStringValue 类型，则解析为它的value值。
	 * 
	 * @param value 要解析的对象
	 * @return
	 */
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
