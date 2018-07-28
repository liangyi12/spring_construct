package org.litespring.aop;

/**
 * 切入点 a predicate that matches join points
 * 
 *
 */
public interface Pointcut {
	
	/**
	 * Return the MethodMatcher for this pointcut.
	 */
	public MethodMatcher getMethodMatcher();
	
	String getExpression();
}
