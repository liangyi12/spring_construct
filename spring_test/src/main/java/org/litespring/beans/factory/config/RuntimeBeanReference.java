package org.litespring.beans.factory.config;

/**
 * 
 * 对应于spring 配置中property下的ref对象
 *
 */
public class RuntimeBeanReference {
	//同样是为了防止修改beandefinition。不给set入口，只在构造函数中实现，之后写代码可以考虑是否有这种需求
	private final String beanName;
	
	public RuntimeBeanReference(String beanName) {
		super();
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}
	
}

