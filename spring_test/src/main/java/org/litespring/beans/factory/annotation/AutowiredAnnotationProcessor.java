package org.litespring.beans.factory.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.util.ReflectionUtils;

public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor{
	
	private AutowireCapableBeanFactory factory;
	
	private Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<Class<? extends Annotation>>();

	private String requiredParameterName = "required";

	private boolean requiredParameterValue = true;

	public AutowiredAnnotationProcessor(){
		autowiredAnnotationTypes.add(Autowired.class);
	}
	
	public void setBeanFactory(AutowireCapableBeanFactory factory) {
		this.factory = factory;
		
	}

	public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
		
		LinkedList<InjectedElement> elements = new LinkedList<InjectedElement>();
		
		Class<?> targetClass = clazz;
		
		do{
			LinkedList<InjectedElement> currElements = new LinkedList<InjectedElement>();
			for (Field field : targetClass.getDeclaredFields()) {
				Annotation anno = findAutowiredAnnotation(field);
				if (anno != null) {
					if (Modifier.isStatic(field.getModifiers())) {
						continue;
					}
					boolean required = determineRequiredStatus(anno);
					AutowiredFieldElement ele = new AutowiredFieldElement(field, required, factory);
					currElements.add(ele);
				}
			}
			
			for (Method method : targetClass.getDeclaredMethods()) {
				//TODO 处理方法注入
			}
			elements.addAll(0, currElements);
			targetClass = targetClass.getSuperclass(); //父类注入的属性
		}while (targetClass != null && targetClass == Object.class);
		
		return new InjectionMetadata(clazz, elements);
	}
	
	
	private Annotation findAutowiredAnnotation(AccessibleObject  ao) {
		for (Class<? extends Annotation> type : autowiredAnnotationTypes) {
			Annotation ann = AnnotationUtils.getAnnotation(ao, type);
			if (ann != null) {
				return ann;
			}
		}
		return null;
		
	}
	
	private boolean determineRequiredStatus(Annotation ann) {
		try {
			Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName );
			if (method == null){
				// Annotations like @Inject and @Value don't have a method (attribute) named "required"
				// -> default to required status
				return true;
			}
			return (this.requiredParameterValue  == (Boolean) ReflectionUtils.invokeMethod(method, ann));
		}catch (Exception e) {
			// An exception was thrown during reflective invocation of the required attribute
			// -> default to required status
			return true;
		}
		
	}

	public Object beforeInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object afterInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object beforeInstantiation(Class<?> beanClass, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean afterInstantiation(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return false;
	}

	public void postProcessPropertyValues(Object bean, String beanName)
			throws BeansException {
		InjectionMetadata injectionMetadata = this.buildAutowiringMetadata(bean.getClass());
		try {
			injectionMetadata.inject(bean);
		}catch (Throwable ex){
			throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
		}
		
		
	}
	
	
}
