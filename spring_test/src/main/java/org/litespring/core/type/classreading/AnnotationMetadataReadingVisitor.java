package org.litespring.core.type.classreading;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

/**
 * 
 * a visitor to visit the annotation of the class
 * 访问类的注解的visitor，继承自ClassMetadataReadingVisitor
 *
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata{
	
	private final Set<String> annotationSet = new LinkedHashSet<String>(4);
	private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<String, AnnotationAttributes>(4);
	
	public AnnotationMetadataReadingVisitor() {
		
	}
	/**
	 * Visits an annotation of the class.
	 */
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		String className = Type.getType(desc).getClassName();
		this.annotationSet.add(className);
		return new AnnotationAttributesReadingVisitor(className, this.attributeMap);
	}
	
	public Set getAnnotationSet() {
		return this.annotationSet;
	}
	
	public boolean hasAnnotation(String annotationType) {
		return this.annotationSet.contains(annotationType);
	}
	
	public AnnotationAttributes getAnnotationAttributes(String annotationType) {
		return this.attributeMap.get(annotationType);
	}
	
}
