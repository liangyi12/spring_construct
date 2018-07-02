package org.litespring.beans;

import java.util.List;

//接口变量默认为public static final， 方法默认为public abstract
//bean定义接口
public interface BeanDefinition {

	public static final String SCOPE_DEFAULT = "";
	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTPTYPE = "prototype";
	
	/**
	 * 得到bean id
	 * @return
	 */
	public String getID();
	
	/**
	 * 得到bean的class全名
	 * @return
	 */
	String getBeanClassName();
	
	/**
	 * 判断bean是否为单例模式
	 * @return
	 */
	public boolean isSingleton();
	
	/**
	 * 判断bean是否为原型模式
	 * @return
	 */
	public boolean isPrototype();
	
	/**
	 * 得到bean作用域
	 * @return
	 */
	String getScope();
	
	/**
	 * 设置bean的作用域：单例、原型等
	 * @param scope
	 */
	void setScope(String scope);
	
	/**
	 * 得到bean的属性列表
	 * @return
	 */
	List<PropertyValue> getPropertyValues();
	
	/**
	 * 得到bean的构造器注入值
	 * @return
	 */
	public ConstructorArgument getConstructorArgument();
	
	/**
	 * 判断bean配置中是否有构造器注入配置
	 * @return
	 */
	public boolean hasConstructorArgumentValues();
	
	
}
