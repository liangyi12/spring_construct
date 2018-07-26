package org.litespring.beans.factory.annotation;

import java.lang.reflect.Field;

import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.util.ReflectionUtils;

/**
 * 
 * 属性注入
 */
public class AutowiredFieldElement extends InjectedElement {
	
	private boolean required;

	public AutowiredFieldElement(Field f, boolean required, DefaultBeanFactory factory) {
		super(f,factory);
		this.required = required;
	}
	
	public Field getField(){
		return (Field)this.member;
	}

	@Override
	public void inject(Object target) {
		
		Field field = this.getField();
		
		try {
			DependencyDescriptor descriptor = new DependencyDescriptor(field, this.required);
			
			Object value = this.factory.resolveDependency(descriptor);
			
			if(value != null) {
				ReflectionUtils.makeAccessible(field);
				field.set(target, value);
			}
			
			
		} catch (Throwable ex) {
			
			throw new BeanCreationException("Could not autowire field: " + field, ex);
		}
	}

}
