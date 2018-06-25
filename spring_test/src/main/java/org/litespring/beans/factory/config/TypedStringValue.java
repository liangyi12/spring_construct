package org.litespring.beans.factory.config;

/**
 * 
 * 对应于spring 配置中property下的value属性
 *
 */
public class TypedStringValue {
	private String value;

	public TypedStringValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}	
	
}
