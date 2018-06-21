package org.litespring.context.support;

import org.litespring.core.io.Resource;
import org.litespring.core.io.support.FileSystemResource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {
	
	public FileSystemXmlApplicationContext(String path) {
		super(path);
	}

	public Resource getResourceByPath(String configFile) {
		return new FileSystemResource(configFile);
	}

}
