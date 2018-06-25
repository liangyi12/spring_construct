package org.litespring.beans;

/**
 * 
 * TypeMismacthException:值和类型不匹配异常
 *
 */
public class TypeMismacthException extends BeansException {
	private transient Object value; //为什么用transient
	private Class<?> requiredType;
	
	public TypeMismacthException(Object value, Class<?> requiredType) {
		super("Failed convert value :" + value + " to type " + requiredType);
		this.value = value;
		this.requiredType = requiredType;
	}

	public Object getValue() {
		return value;
	}

	public Class<?> getRequiredType() {
		return requiredType;
	}

}
