package org.litespring.beans.factory.annotation;

import java.lang.reflect.Member;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;

/**
 * 注入的元素：字段、方法或构造函数
 *
 */
public abstract class InjectedElement {
	
	protected Member member;
	
	protected AutowireCapableBeanFactory factory;
	
	public InjectedElement(Member member, AutowireCapableBeanFactory factory) {
		this.member = member;
		this.factory = factory;
	}
	
	/**
	 * 注入值到target中
	 * @param target
	 */
	public abstract void inject(Object target);
}
