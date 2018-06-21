package org.litespring.beans;
//接口变量默认为public static final， 方法默认为public abstract
//bean定义接口
public interface BeanDefinition {

	public static final String SCOPE_DEFAULT = "";
	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTPTYPE = "prototype";
	
	public boolean isSingleton();
	public boolean isPrototype();
	String getScope();
	void setScope(String scope);

	String getBeanClassName();

	
}
