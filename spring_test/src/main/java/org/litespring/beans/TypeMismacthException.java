package org.litespring.beans;

/**
 * 
 * TypeMismacthException:值和类型不匹配异常
 *
 */
public class TypeMismacthException extends BeansException {
	//为什么用transient--transient表示不能被序列化，Object没有实现serializable接口，是不能被序列化的
	//为什么有的变量不能被实例化：1.设计者没有实现serializable。 2，动态数据只可以在执行时求出而不能或不必存储
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
