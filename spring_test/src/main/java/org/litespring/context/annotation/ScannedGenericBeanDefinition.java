package org.litespring.context.annotation;

import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

/**
 * 
 * Extension of the GenericBeanDefinition class, based on an ASM ClassReader, with support for annotation metadata exposed 
 * through the AnnotatedBeanDefinition interface.
 * This class does not load the bean Class early. It rather retrieves all relevant metadata from the ".class" file itself, 
 * parsed with the ASM ClassReader.
 *
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition  implements AnnotatedBeanDefinition{
	private final AnnotationMetadata metadata;
	
	public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
		super();
		this.metadata = metadata;
		setBeanClassName(this.metadata.getClassName());
	}

	public AnnotationMetadata getMetadata() {
		return this.metadata;
	}
	

}
