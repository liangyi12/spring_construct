package org.litespring.beans;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

import org.litespring.beans.propertyEditors.CustomNumberEditor;
import org.litespring.util.ClassUtils;
import org.litespring.util.CustomBooleanEditor;
/**
 * 
 * 类型转换实现类
 *
 */
public class SimpleTypeConvert implements TypeConvert {
	/**存放给定类型编辑器的map，key为class*/
	Map<Class<?>, PropertyEditor> defaultEditors;
	
	public <T> T convertIfNecessary(Object value, Class<T> requiredType)
			throws TypeMismacthException {
		if(ClassUtils.isAssignableValue(requiredType, value)) {
			return (T)value;
		}else if(value instanceof String) {
			PropertyEditor editor = findDefalutEditor(requiredType);
			try{
				editor.setAsText((String)value);
			}catch(IllegalArgumentException e){
				throw new TypeMismacthException(value, requiredType);
			}
			return (T)editor.getValue();
			
		}else{
			throw new RuntimeException("Todo : can't convert value for " + value + "  class: " + requiredType);
		}
	}
	
	/***
	 * 查找对应class的编辑器
	 * @param requiredType
	 * @return
	 */
	public PropertyEditor findDefalutEditor(Class<?> requiredType) {
		PropertyEditor editor = getDefalutEditor(requiredType);
		if(editor == null) {
			throw new RuntimeException("editor for " + requiredType + " has not been implemented");
		}
		return editor;
	}

	public PropertyEditor getDefalutEditor(Class<?> requiredType) {
		if(this.defaultEditors == null) {
			createDefalutEditors();
		}
		return this.defaultEditors.get(requiredType);
	}
	
	/**
	 * 创建类型编辑器，并放到map中
	 */
	private void createDefalutEditors() {
		defaultEditors = new HashMap<Class<?>, PropertyEditor>(64);
		this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
		this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
		this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
		this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(false));
		
	}

}
