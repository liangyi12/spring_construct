package org.litespring.beans.factory.annotation;

import org.litespring.core.type.AnnotationMetadata;

/**
 * Extended BeanDefinition interface that exposes AnnotationMetadata about its bean class 
 * - without requiring the class to be loaded yet.
 *
 */
public interface AnnotatedBeanDefinition {
	AnnotationMetadata getMetadata();
}
