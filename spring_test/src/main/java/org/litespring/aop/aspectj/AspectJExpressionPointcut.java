package org.litespring.aop.aspectj;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.litespring.aop.MethodMatcher;
import org.litespring.aop.Pointcut;
import org.litespring.util.ClassUtils;
import org.litespring.util.StringUtils;
import org.aspectj.weaver.reflect.ReflectionWorld.ReflectionWorldException;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParameter;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

/**
 * Spring Pointcut implementation that uses the AspectJ weaver to evaluate 
 * a pointcut expression.
 * spring 切点实现，使用AspectJ weaver 来评估切入点表达式
 *
 */
public class AspectJExpressionPointcut implements Pointcut, MethodMatcher{
	
	private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();
	
	static{
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);	
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);

	}
	
	private String expression;
	
	private PointcutExpression pointcutExpression;
	
	private ClassLoader pointcutClassLoader;
	
	public AspectJExpressionPointcut() {
		
	}
	
	public MethodMatcher getMethodMatcher() {
		return this;
	}
	
	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
		
	}

	public boolean matches(Method method) {
		
		checkReadyToMatch();
		
		ShadowMatch shadowMatch = getShadowMatch(method);
		
		if (shadowMatch.alwaysMatches()) {
			return true;
		}
		
		return false;
	}

	private ShadowMatch getShadowMatch(Method method) {
		ShadowMatch shadowMatch = null;
		
		try {
			shadowMatch = this.pointcutExpression.matchesMethodExecution(method);
		}catch (ReflectionWorldException ex) {
			
			throw new RuntimeException("not implemented yet");
			/*try {
				fallbackExpression = getFallbackPointcutExpression(methodToMatch.getDeclaringClass());
				if (fallbackExpression != null) {
					shadowMatch = fallbackExpression.matchesMethodExecution(methodToMatch);
				}
			}
			catch (ReflectionWorldException ex2) {
				fallbackExpression = null;
			}*/
		}
		
		return shadowMatch;
	}
	
	/**
	 * Check whether this pointcut is ready to match,
	 * lazily building the underlying AspectJ pointcut expression.
	 */
	private void checkReadyToMatch() {
		if (getExpression() == null) {
			throw new IllegalStateException("Must set property 'expression' before attempting to match");
		}
		if (this.pointcutExpression == null) {
			this.pointcutClassLoader = ClassUtils.getDefaultClassLoader();
			this.pointcutExpression = buildPointcutExpression(this.pointcutClassLoader);
		}
		
	}

	/**
	 * Build the underlying AspectJ pointcut expression.
	 */
	private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {
		PointcutParser  parser = PointcutParser.
				getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution
				(SUPPORTED_PRIMITIVES, classLoader);
				
		return parser.parsePointcutExpression(replaceBooleanOperators(getExpression()), null,  new PointcutParameter[0]);
	}

	/**
	 * If a pointcut expression has been specified in XML, the user cannot
	 * write {@code and} as "&&" (though &amp;&amp; will work).
	 * We also allow {@code and} between two pointcut sub-expressions.
	 * <p>This method converts back to {@code &&} for the AspectJ pointcut parser.
	 */
	private String replaceBooleanOperators(String pcExpr) {
		String result = StringUtils.replace(pcExpr, " and ", " && ");
		result = StringUtils.replace(result, " or ", " || ");
		result = StringUtils.replace(result, " not ", " ! ");
		return result;
	}

	

	

}
