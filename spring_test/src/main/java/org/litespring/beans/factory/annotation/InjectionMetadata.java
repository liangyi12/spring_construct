package org.litespring.beans.factory.annotation;

import java.util.List;

/**
 * Internal class for managing injection metadata. Not intended for direct use in applications.
 * 用于管理元数据的内部类，不推荐在应用中直接使用
 *
 */
public class InjectionMetadata {
	
	private Class targetClass;
	
	private  List<InjectedElement>  injectedElements;
	
	public InjectionMetadata(Class targetClass, List<InjectedElement> injectedElements) {
		this.targetClass = targetClass;
		this.injectedElements = injectedElements;
	}
	
	/**
	 * 注入
	 * @param target
	 */
	public void inject(Object target) {
		
		if (injectedElements == null || injectedElements.isEmpty()) {
			return;
		}
		
		for (InjectedElement injectedElement : injectedElements) {
			injectedElement.inject(target);
		}
	}
}
