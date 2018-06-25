package org.litespring.beans;

/**
 * 
 * TypeMismacthException:值和类型不匹配异常
 *
 */
public class TypeMismacthException extends BeansException {
	//为什么用transient--transient表示不能被序列化，为什么，exception什么时候序列化
	private transient Object value; 
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
