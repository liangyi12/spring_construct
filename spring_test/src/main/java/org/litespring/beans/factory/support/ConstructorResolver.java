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
 * @author liyanli
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
	 * @param bd bean定义
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
			e.printStackTrace();
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
	public boolean isSuitableConstructor(Class[] parameterTypes, List<ValueHolder> valueHolders,Object[] argsToUse, 
			BeanDefinitioValueResolver resolver,TypeConverter convert) {
		for (int i=0; i < parameterTypes.length; i++) {
			Object value = valueHolders.get(i).getValue();
			try{
				Object resolver_object = resolver.resolveValueIfNecessary(value);
				Object convert_object = convert.convertIfNecessary(resolver_object, parameterTypes[i]);
				argsToUse[i] = convert_object;
			}catch(Exception e) {
				logger.error(e);
				return false;
			}
		}
		return true;
	}

}
