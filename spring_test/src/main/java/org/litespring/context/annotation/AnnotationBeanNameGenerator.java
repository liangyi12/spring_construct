package org.litespring.context.annotation;

import java.beans.Introspector;
import java.util.Set;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.util.ClassUtils;
import org.litespring.util.StringUtils;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {

	public String generateBeanName(BeanDefinition definition,BeanDefinitionRegistry registry) {
		if (definition instanceof AnnotatedBeanDefinition) {
			String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
			if (StringUtils.hasText(beanName)) {
				return beanName;
			}
		}
		return buildDefaultBeanName(definition, registry);
	}


	public String determineBeanNameFromAnnotation(AnnotatedBeanDefinition definition) {
		AnnotationMetadata  amd = definition.getMetadata();
		Set<String> types = amd.getAnnotationTypes();
		String beanName = null;
		for(String type : types) {//如果多个注解都有value怎么赋值？---直接抛出异常
			AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
			if (attributes.get("value") != null) {
				Object value = attributes.get("value");
				if (value instanceof String) {
					String strVal = (String) value;
					if (StringUtils.hasLength(strVal)) {
						if (beanName != null && !strVal.equals(beanName)) {
							throw new IllegalStateException("Stereotype annotations suggest inconsistent " +
									"component names: '" + beanName + "' versus '" + strVal + "'");
						}
						beanName = strVal;
						
					}
				}
			}
		}
		
		return beanName;
	}
	
	public String buildDefaultBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		return buildDefaultBeanName(definition);
	}
	
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
		return Introspector.decapitalize(shortClassName);
	}



}
