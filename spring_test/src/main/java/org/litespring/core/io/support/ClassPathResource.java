package org.litespring.core.io.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

/**
 * 用于得到项目路径下的resource
 *
 */
public class ClassPathResource implements Resource {
	String path;
	ClassLoader classLoader;
	public ClassPathResource(String path) {
		this(path, (ClassLoader)null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		this.path = path;
		this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
	}

	public InputStream getInputStream() throws IOException{
		InputStream is = this.classLoader.getResourceAsStream(this.path);
		
		if(is == null){
			throw new FileNotFoundException(this.path + "cannot opened");
		}
		return is;
	}

	public String getDescription() {
		return this.path;
	}

}
