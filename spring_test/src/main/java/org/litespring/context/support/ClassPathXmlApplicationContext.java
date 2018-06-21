package org.litespring.context.support;

import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
	
	public ClassPathXmlApplicationContext(String configFile) {
		super(configFile);
	}

	public Resource getResourceByPath(String path) {
		return new ClassPathResource(path, this.getBeanClassLoader());
	}


}
