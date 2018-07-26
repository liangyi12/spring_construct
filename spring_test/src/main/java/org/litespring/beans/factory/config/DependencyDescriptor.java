package org.litespring.beans.factory.config;

import java.lang.reflect.Field;

import org.litespring.util.Assert;

/**
* 
 * Descriptor for a specific dependency that is about to be injected. Wraps a constructor parameter,
 * a method parameter or a field, allowing unified access to their metadata
 * 
 * 要被注入的依赖的描述符，包括构造函数参数，方法参数或自动，允许统一访问其元数据

 */
public class DependencyDescriptor {
	
	private Field field;
	
	private boolean required;
	
	/**
	 * Create a new descriptor for a field.
	 * @param field
	 * @param required
	 */
	public DependencyDescriptor(Field field, boolean required) {
		Assert.notNull(field, "field must not be null");
		this.field = field;
		this.required = required;
	}
	
	/**
	 * Determine the declared (non-generic) type of the wrapped parameter/field.
	 * 得到参数或属性声明的类型
	 * @return the declared type (never null)
	 */
	public Class<?> getDependencyType() {
		if (this.field != null) {
			//返回一个 Class 对象，它标识了此 Field 对象所表示字段的声明类型。
			return this.field.getType(); 
		}
		throw new RuntimeException("only support field dependency");
		
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
	
	
	
}
