package org.litespring.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
/**
 * 
 * bean定义实现类
 * 
 * 
 *
 */
public class GenericBeanDefinition implements BeanDefinition {
	/**id:对应xml配置中的<bean id>, bean的唯一标识符*/
	private String id;
	
	/**beanClassName:对应xml配置中的<bean class>, bean的class全名，用于通过反射创建类的实例*/
	private String beanClassName;
	
	/**scope：bean的作用域，对应xml配置中的<bean scope>，值为singleton或空时表示为单例模式，prototype时为原型模式 */
	private String scope = SCOPE_DEFAULT;
	
	/**property列表，在这里直接初始化，类中只提供了get方法，没有提供set方法*/
	private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();; 
	
	/**构造函数*/
	public GenericBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}
	
	/**得到bean的class全名*/
	public String getBeanClassName() {
		return this.beanClassName;
	}
	
	/**判断bean是否为单例模式*/
	public boolean isSingleton() {
		return this.scope.equals(SCOPE_SINGLETON) || this.scope.equals(SCOPE_DEFAULT);
	}
	
	/**判断bean是否为原型模式*/
	public boolean isPrototype() {
		return this.scope.equals(SCOPE_PROTPTYPE);
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public List<PropertyValue> getPropertyValues() {
		return this.propertyValues;
	}

}
