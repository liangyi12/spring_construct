package org.litespring.beans;

/**
 * 
 * 类型转换接口
 *
 */
public interface TypeConverter {
	
	/***类型转换方法
	 * 如果需要则进行类型转换，当value的类型就是requiredType的时候不需要转换，直接返回。
	 * @param value 需要进行转换的值
	 * @param requiredType 转换的类型，如果value不能转换成这种类型则抛出TypeMismacthException异常
	 * @return 返回转换后的对象
	 * @throws TypeMismacthException
	 */
	<T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismacthException;

}
