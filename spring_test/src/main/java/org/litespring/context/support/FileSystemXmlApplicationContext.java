package org.litespring.context.support;

import org.litespring.core.io.Resource;
import org.litespring.core.io.support.FileSystemResource;

/**
 * 
 * 根据文件路径下的配置文件得到的上下文环境
 *
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {
	
	public FileSystemXmlApplicationContext(String path) {
		super(path);
	}

	public Resource getResourceByPath(String configFile) {
		return new FileSystemResource(configFile);
	}

}
