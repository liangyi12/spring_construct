package org.litespring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.Property;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.property.RunTimeReference;
import org.litespring.beans.factory.config.property.TypedStringObject;
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
				bean = initialBean(beanDefinition);
				this.registerSingleton(beanId, bean);
			}
			return bean;
		}
		return initialBean(beanDefinition);
	}
	
	public Object initialBean(BeanDefinition beanDefinition) {
		Object bean = createBean(beanDefinition);
		initialProperty(bean, beanDefinition);
		return bean;
	}

	private void initialProperty(Object bean, BeanDefinition beanDefinition) {
		bean.getClass().getDeclaredFields();
		try {
			BeanInfo bi = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				for(Property pro : beanDefinition.getProperties()) {
					if(pd.getName().equals(pro.getName())){
						if(pro.getActualValue() instanceof TypedStringObject){
							pd.getWriteMethod().invoke(bean, pro.getOriginalValue());
						}
					}
					if (pro.getActualValue() instanceof RunTimeReference) {
						System.out.println(pd.getWriteMethod().getName());
						pd.getWriteMethod().invoke(bean, getBean(pro.getOriginalValue().toString()));
					}
				}
				
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	public Object createBean(BeanDefinition beanDefinition) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = beanDefinition.getBeanClassName();
		try {
			Class c = cl.loadClass(beanClassName);
			return c.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + "failed" , e);
		}
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
	}

	

}
