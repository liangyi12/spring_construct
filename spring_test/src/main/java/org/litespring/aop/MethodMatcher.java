package org.litespring.aop;

import java.lang.reflect.Method;

/**
 * Part of a Pointcut: Checks whether the target method is eligible for advice.
 *	切入点的组成，判断目标方法是否适合通知
 */
public interface MethodMatcher {
	
	/**
	 * Perform static checking whether the given method matches.
	 * 判断给定的方法是否匹配
	 * @param method
	 * @return
	 */
	boolean matches(Method method);
}
