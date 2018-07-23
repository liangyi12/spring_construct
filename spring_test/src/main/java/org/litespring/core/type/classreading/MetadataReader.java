package org.litespring.core.type.classreading;

import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;
/**
 * Simple facade for accessing class metadata,
 * as read by an ASM {@link org.springframework.asm.ClassReader}.
 */
public interface MetadataReader {
	
	/**
	 * Read basic class metadata for the underlying class.
	 */
	ClassMetadata getClassMetadata();
	
	/**
	 * Read full annotation metadata for the underlying class,
	 * including metadata for annotated methods.
	 */
	AnnotationMetadata getAnnotationMetadata();

	

}
