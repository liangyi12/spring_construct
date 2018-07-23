package org.litespring.test.v4;

import  static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;

public class MetadataReaderTest {

	@Test
	public void testGetMetadata() throws IOException {
		ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
		MetadataReader reader = new SimpleMetadataReader(resource);
		
		AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
		
		String annotationType = "org.litespring.stereotype.Component";
		assertTrue(annotationMetadata.hasAnnotation(annotationType));
		
		AnnotationAttributes attributess = annotationMetadata.getAnnotationAttributes(annotationType);
		
		assertEquals("petStore", attributess.getString("value"));
		
		assertFalse(annotationMetadata.isAbstract());
		assertFalse(annotationMetadata.isInterface());
		assertFalse(annotationMetadata.isFinal());
		assertEquals("org.litespring.service.v4.PetStoreService", annotationMetadata.getClassName());
	}

}
