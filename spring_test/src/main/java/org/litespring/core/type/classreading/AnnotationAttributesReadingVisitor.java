package org.litespring.core.type.classreading;

import java.util.Map;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

public final class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
	
	private final String annotationType;
	
	private final Map<String, AnnotationAttributes> attributesMap;
	
	AnnotationAttributes attributes = new AnnotationAttributes();


	public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributeMap) {
		super(SpringAsmInfo.ASM_VERSION);
		this.attributesMap = attributeMap;
		this.annotationType = annotationType;
	}

	/**
	 * Visits a primitive value of the annotation.
	 */
	public void visit(String attributeName, Object attributeValue) {
		this.attributes.put(attributeName, attributeValue);
	}

	/**
	 * Visits the end of the class.
	 */
	public void visitEnd() {
		this.attributesMap.put(annotationType, attributes);
	}
	
	

}
