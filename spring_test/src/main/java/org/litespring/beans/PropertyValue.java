package org.litespring.beans;

public class PropertyValue {
	//为什么用final--是因为name和value 只能在xml或是注解中设定，然后由构造函数初始化值，这之后不能再修改？
	//假设场景：在spring框架把所有的类都注入之后，程序使用之前，有人从beandefinition中得到了某个bean的属性列表，修改了其中一个，如果修改的是name的话，
	//使用时会找不到想要的属性，如果修改的是value的话，会导致拿到的初始值错误
	private final String name;
	/**值，根据属性名的不同对应不同的对象，例如ref对应的是RuntimeBeanReference*/
	private final Object value; 
	/**是否转换了*/
	private boolean converted = false;
	/**转换之后的真实的bena对象，或其他java类型*/
	private Object convertedValue;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return value;
	}
	
	/**查看是否转换
	 * 加锁可以理解为：为了防止一个线程a准备进行线程转换，拿了下converted发现是false，然后去做转换了，结果另一个线程b在这之前已经拿过了也是false，
	 * 并在a做转换的时候已经转换过了，并对转换对象convertedValue进行赋值。而a转换完之后，也做了给convertedValue赋值的操作，导致a的值覆盖了
	 * b的值。
	 * 问题来了：ioc这个过程是多线程的吗，不懂加锁。。。
	 * 
	 * */
	public synchronized boolean isConverted() {
		return this.converted;
	}
	
	/**给convertedValue赋值，并将converted置为true*/
	public synchronized void setConvertedValue(Object value) {
		this.converted = true;
		this.convertedValue = value;
	}
	
	/**得到convertedValue*/
	public synchronized Object getConvertedValue() {
		return this.convertedValue;
	}
	
}
