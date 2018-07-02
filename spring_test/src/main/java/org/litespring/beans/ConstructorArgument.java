package org.litespring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * ConstructorArgument：通过构造器注入的参数
 *
 */
public class ConstructorArgument {
	/**持有参数的list*/
	private final List<ValueHolder> argumentValues = new LinkedList<ValueHolder>();
	
	public ConstructorArgument(){
		
	}
	
	/**
	 * 添加参数
	 * @param valueHolder
	 */
	public void addArgumentValue(String value, String type){
		this.argumentValues.add(new ValueHolder(value, type));
	}
	
	/**
	 * 添加参数
	 * @param valueHolder
	 */
	public void addArgumentValue(ValueHolder valueHolder){
		this.argumentValues.add(valueHolder);
	}
	
	/**
	 * 得到通过构造器注入的参数
	 * @return
	 */
	public List<ValueHolder> getArgumentValues() {
		// ?? 线程安全
		return Collections.unmodifiableList(this.argumentValues);
	}
	
	/**
	 * Clear this holder, removing all argument values.
	 */
	public void clear() {
		this.argumentValues.clear();
	}
	
	/**
	 * 得到注入的参数个数
	 * @return
	 */
	public int getArgumentCount(){
		return this.argumentValues.size();
	}
	
	/**
	 * 判断是否有参数
	 * @return
	 */
	public boolean isEmpty(){
		return this.argumentValues.isEmpty();
	}
	
	
	/**
	 * Holder为构造函数参数值，带有一个可选的类型属性，指示实际构造函数参数的目标类型。<br/>
	 * 构造器参数类<br/>
	 * 使用静态内部类：高内聚，只在ConstructorArgument中使用
	 * @author liyanli
	 *
	 */
	public static class ValueHolder {
		/**
		 * 构造函数参数值
		 */
		private Object value;
		/**
		 * 类属性名:为使构造器注入中的name起作用，需要在debug模式使用，或者在构造函数上面添加
		 * @ConstructorProperties({"years", "ultimateAnswer"})注解。
		 */
		private String name;
		/**
		 * 类属性类型
		 */
		private String type;
		public ValueHolder(Object value) {
			super();
			this.value = value;
		}
		public ValueHolder(Object value, String type) {
			super();
			this.value = value;
			this.type = type;
		}
		public ValueHolder(Object value, String name, String type) {
			super();
			this.value = value;
			this.name = name;
			this.type = type;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	
	
}
