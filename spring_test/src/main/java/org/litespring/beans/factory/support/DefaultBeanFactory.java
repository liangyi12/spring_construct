package org.litespring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
	implements  ConfigurableBeanFactory, BeanDefinitionRegistry{
	
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	private ClassLoader classLoader;

	public DefaultBeanFactory() {
	}

	public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanId, beanDefinition);
	}
	
	public BeanDefinition getBeanDefinition(String beanId) {
		return beanDefinitionMap.get(beanId);
	}

	public Object getBean(String beanId) {
		BeanDefinition beanDefinition = getBeanDefinition(beanId);
		if(beanDefinition == null) {
			throw new BeanCreationException(beanId, "beanDefinition does not exist");
		}
		
		if(beanDefinition.isSingleton()){
			Object bean = this.getSingleton(beanId);
			if(bean == null){
				bean = createBean(beanDefinition);
				this.registerSingleton(beanId, bean);
			}
			return bean;
		}
		return createBean(beanDefinition);
	}

	public Object createBean(BeanDefinition beanDefinition) {
		//创建bean实例
		Object bean = instantiateBean(beanDefinition);
		//设置属性
		populateBean(beanDefinition,bean);
		return bean;
	}
	
	
	/**
	 * 创建bean实例
	 * @param beanDefinition
	 * @return
	 */
	public Object instantiateBean(BeanDefinition beanDefinition) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = beanDefinition.getBeanClassName();
		try {
			Class c = cl.loadClass(beanClassName);
			return c.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + "failed" , e);
		}
	}
	
	/**
	 * 设置bean属性
	 */
	public void populateBean(BeanDefinition beanDefinition ,Object bean) {
		List<PropertyValue> pvs = beanDefinition.getPropertyValues();
		if(pvs == null || pvs.isEmpty()) {
			return;
		}
		BeanDefinitioValueResolver resolver = new BeanDefinitioValueResolver(this);
		try {
			BeanInfo bi = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			/*
			 * 在嵌套For循环中，将循环次数多的循环放在内侧，循环次数少的循环放在外侧，其性能会更好。如果在循环次数较少的情况下，其运行效果区别不大，
			 * 但在循环次数较多的情况下，其效果就比较明显了
			 * */
			for(PropertyValue pro : pvs) {
				String propertyName = pro.getName();
				Object originialValue = pro.getValue();
				Object resolveValue = resolver.resolveValueIfNecessary(originialValue);
				for(PropertyDescriptor pd : pds) {
					if(pd.getName().equals(propertyName)) {
						pd.getWriteMethod().invoke(bean, resolveValue);
					}
				}
			}
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
	}

	

}
