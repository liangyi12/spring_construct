package org.litespring.core.type.classreading;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

public class SimpleMetadataReader implements MetadataReader {
	
	private final  ClassMetadata classMetadata;
	private final AnnotationMetadata annotationMetadata;

	public SimpleMetadataReader(Resource resource) throws IOException {
		InputStream is = new BufferedInputStream(resource.getInputStream());
		ClassReader reader;
		try {
			reader = new ClassReader(is);
		}finally{
			is.close();
		}
		
		AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
		reader.accept(visitor, ClassReader.SKIP_DEBUG);
		
		this.classMetadata = visitor;
		this.annotationMetadata = visitor;
	}

	public ClassMetadata getClassMetadata() {
		return this.classMetadata;
	}

	public AnnotationMetadata getAnnotationMetadata() {
		return this.annotationMetadata;
	}

}
