package org.litespring.aop.config;

import java.lang.reflect.Method;

import org.litespring.beans.BeanUtils;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.StringUtils;

/**
 *  locates a Method on a specified bean.
 *
 */
public class MethodLocatingFactory {
	
	private String targetBeanName;
	private String methodName;
	private Method method;
	
	/**
	 * Set the name of the bean to locate the Method on.
	 * @param targetBeanName
	 */
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;	
	}
	
	/**
	 * Set the name of the Method to locate.
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * Callback that supplies the owning factory to a bean instance.
	 * @param factory
	 */
	public void setBeanFactory(BeanFactory factory) {
		if (!StringUtils.hasText(this.targetBeanName)) {
			throw new IllegalArgumentException("Property 'targetBeanName' is required");
		}
		
		if (!StringUtils.hasText(this.methodName)) {
			throw new IllegalArgumentException("Property 'methodName' is required");
		}
		
		Class<?> beanClass = factory.getType(this.targetBeanName);	
		if (beanClass == null) {
			throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
		}
		
		this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
		if(this.method == null){
			throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
					"] on bean [" + this.targetBeanName + "]");
		}
		
	}
	
	/**
	 * Return an instance (possibly shared or independent) of the object managed by this factory.
	 * @return
	 */
	public Method getObject() {
		return this.method;
	}

}
