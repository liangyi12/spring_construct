package org.litespring.beans;

public class Property {
	private String name;
	private Object originalValue; //初始值，xml配置的value或ref值
	private Object actualValue;

	public Property(String name, Object originalValue, Object actualValue) {
		this.name = name;
		this.originalValue = originalValue;
		this.actualValue = actualValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Object getActualValue() {
		return actualValue;
	}
	public void setActualValue(Object actualValue) {
		this.actualValue = actualValue;
	}
	public Object getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(Object value) {
		this.originalValue = value;
	}
	
	
}
