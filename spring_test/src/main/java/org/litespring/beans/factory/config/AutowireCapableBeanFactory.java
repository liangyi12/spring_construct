package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
	/**
	 * Resolve the specified dependency against the beans defined in this factory.
	 * @param descriptor
	 * @return
	 */
	public Object resolveDependency(DependencyDescriptor descriptor);
}
