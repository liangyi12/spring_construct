package org.litespring.beans.factory.support;

import java.util.HashMap;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.util.Assert;
/**
 * 
 * 默认单例注册实现
 *
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	/**存放单例对象的hashmap，key值为beanname，value为bean实例对象*/
	private final HashMap<String,Object> singleObjects = new HashMap<String, Object>();
	
	public void registerSingleton(String beanName, Object singletonObject) {
		//前置条件，注册beaname必须非空
		Assert.notNull(beanName, "'beanName' must not be null");
		
		Object oldObject = this.singleObjects.get(beanName);
		if(oldObject != null){
			throw new IllegalStateException("Could not register object [" + singletonObject +
					"] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
		}
		this.singleObjects.put(beanName, singletonObject);
	}

	public Object getSingleton(String beanName) {
		return this.singleObjects.get(beanName);

	}

}
