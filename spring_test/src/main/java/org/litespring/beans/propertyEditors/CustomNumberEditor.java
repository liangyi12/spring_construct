package org.litespring.beans.propertyEditors;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

import org.litespring.util.NumberUtils;
import org.litespring.util.StringUtils;

public class CustomNumberEditor extends PropertyEditorSupport{
	
	private final Class<? extends Number> numberClass;
	
	private final NumberFormat numberFormat;
	/**是否允许空值*/
	private final boolean allowEmpty;
	
	public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException{
		this(numberClass, null, allowEmpty);
	}
	
	public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat,boolean allowEmpty) 
			throws IllegalArgumentException{
		// public boolean isAssignableFrom(Class<?> cls) 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，
		//或是否是其超类或超接口。如果是则返回 true；否则返回 false
		if(numberClass == null || !Number.class.isAssignableFrom(numberClass)) {
			throw new IllegalArgumentException("Property class must be a subclass of Number");
		}
		this.numberClass = numberClass;
		this.numberFormat = numberFormat;
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		}else if(this.numberFormat != null) {
			setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
		}else{
			setValue(NumberUtils.parseNumber(text, this.numberClass));
		}
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof Number) {
			super.setValue(NumberUtils.convertNumberToTargetClass((Number)value, this.numberClass));
		}else{
			super.setValue(value);
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		if(value == null){
			return "";
		}else if(this.numberFormat != null){
			return this.numberFormat.format(value);
		}else{
			return value.toString();
		}
	}

}
