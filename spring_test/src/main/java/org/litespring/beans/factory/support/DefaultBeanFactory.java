package org.litespring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
	implements  ConfigurableBeanFactory, BeanDefinitionRegistry{
	
	/**保存beanDefinition实例的map对象，key为beanname，value为实例*/
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	/**类加载器*/
	private ClassLoader classLoader;

	public DefaultBeanFactory() {
	}
	
	/**注册beanDefinition*/
	public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanId, beanDefinition);
	}
	
	/**获取beanDefinition*/
	public BeanDefinition getBeanDefinition(String beanId) {
		return beanDefinitionMap.get(beanId);
	}
	
	/**获取bean对象*/
	public Object getBean(String beanId) {
		BeanDefinition beanDefinition = getBeanDefinition(beanId);
		if(beanDefinition == null) {
			throw new BeanCreationException(beanId, "beanDefinition does not exist");
		}
		
		//判断是否为单例对象
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
	
	/**创建bean对象，并为属性等赋值*/
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
		if (beanDefinition.hasConstructorArgumentValues()) {
			ConstructorResolver resolver = new ConstructorResolver(this);
			return resolver.autowireConstructor(beanDefinition);
		}else{
			ClassLoader cl = this.getBeanClassLoader();
			String beanClassName = beanDefinition.getBeanClassName();
			try {
				Class c = cl.loadClass(beanClassName);
				return c.newInstance();
			} catch (Exception e) {
				throw new BeanCreationException("create bean for " + beanClassName + "failed" , e);
			}
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
		TypeConverter convert = new SimpleTypeConverter();
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
						Object convertValue = convert.convertIfNecessary(resolveValue, pd.getPropertyType());
						//pd.getWriteMethod() 得到属性的set方法
						pd.getWriteMethod().invoke(bean, convertValue);
						break;
					}
				}
			}
		} catch (Exception ex) {
			//还是不习惯处理exception，得改
			throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", ex);
		}
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
	}
	
	
	public Object resolveDependency(DependencyDescriptor descriptor){
		Collection<BeanDefinition>  beanDefinitionList = this.beanDefinitionMap.values();
		Class typeToMatch  =  descriptor.getDependencyType();
		for (BeanDefinition bd : beanDefinitionList) {
			//确保BeanDefinition 有Class对象
			this.resolveBeanClass(bd);
			//isAssignableFrom(Class<?> cls)  判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
			if (typeToMatch.isAssignableFrom(bd.getBeanClass())) {
				return this.getBean(bd.getID());
			}
			
		}
		return null;
	}
	
	public void resolveBeanClass(BeanDefinition bd) {
		if (bd.hasBeanClass()){
			return;
		}else{
			try {
				bd.resolveBeanClass(this.getBeanClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	

}
