package org.litespring.beans.factory.support;

import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.ConstructorArgument.ValueHolder;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;

/**
 * 解析构造器注入，并根据对应的构造器新建实例
 *
 */
public class ConstructorResolver {
	
	Log logger = LogFactory.getLog(ConstructorResolver.class);
	
	private final ConfigurableBeanFactory factory;
	
	//使用ConfigurableBeanFactory 类型 的参数，是为了得到classloader。
	//不使用defaultBeanFactory --面向接口编程，而不是具体实现
	public ConstructorResolver(ConfigurableBeanFactory factory) {
		this.factory = factory;
	}
	/**
	 * 自动装配构造器，并根据构造器和参数新建实例
	 * @param bd bean定义，使用final，不能将bd指向别的BeanDefinition
	 * @return
	 */
	public Object autowireConstructor(final BeanDefinition bd){
		Constructor<?> constructorToUse =  null;
		Object[] argsToUse = null;
		Class beanClass = null;
		
		try {
			//效率不高，高效率方法：在beanDefinition中缓存，之后从缓存里拿
			beanClass = this.factory.getBeanClassLoader().loadClass(bd.getBeanClassName());
		} catch (ClassNotFoundException e) {
			throw new BeanCreationException(bd.getID() + " Instantiation of bean failed, can't resolve class", e);
		}
		
		
		Constructor<?>[] candidates = beanClass.getConstructors();
		BeanDefinitioValueResolver resolver = new BeanDefinitioValueResolver(this.factory);
		TypeConverter convert = new SimpleTypeConverter();
		ConstructorArgument  cargs = bd.getConstructorArgument();
		
		for(int i=0; i < candidates.length; i++) {
			Class[] parameterTypes = candidates[i].getParameterTypes();
			//如果参数个数和构造函数形参个数不一致，则进入下一次循环
			if(parameterTypes.length != cargs.getArgumentCount()){
				continue;
			}
			//每次都新建数组，如果找到合适的构造函数，会把参数值加到数组里
			argsToUse = new Object[parameterTypes.length];
			
			boolean result = this.isSuitableConstructor(parameterTypes, cargs.getArgumentValues(), argsToUse, resolver, convert);
			if (result) {
				constructorToUse = candidates[i];
				break;
			}
		}
		if (constructorToUse == null){
			throw new BeanCreationException(bd.getID() + "  cann't find a appropriate constructor");
		}
		
		try {
			return constructorToUse.newInstance(argsToUse);
		} catch (Exception e) {
			throw new BeanCreationException(bd.getID() + "  cann't find a create instance using " +  constructorToUse);
		}
	}
	
	/**
	 * 判断是否为合适的构造函数
	 * @param parameterTypes  构造函数形参类型
	 * @param valueHolders	参数值
	 * @param argsToUse	用到的参数值，转换之后的
	 * @param resolver	解析类型
	 * @param convert	类型转换
	 * @return
	 */
	public boolean isSuitableConstructor(Class[] parameterTypes,
			List<ConstructorArgument.ValueHolder> valueHolders,
			Object[] argsToUse, 
			BeanDefinitioValueResolver resolver,
			TypeConverter convert) {
		
		for (int i=0; i < parameterTypes.length; i++) {
			ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
			////获取参数的值，可能是TypedStringValue, 也可能是RuntimeBeanReference
			Object originalValue = valueHolder.getValue();
			try{
				//获得真正的值
				Object resolverValue = resolver.resolveValueIfNecessary(originalValue);
				//如果参数类型是 int, 但是值是字符串,例如"3",还需要转型
				//如果转型失败，则抛出异常。说明这个构造函数不可用
				Object convertedValue = convert.convertIfNecessary(resolverValue, parameterTypes[i]);
				//转型成功，记录下来
				argsToUse[i] = convertedValue;
			}catch(Exception e) {
				logger.error(e);
				return false;
			}
		}
		return true;
	}

}
