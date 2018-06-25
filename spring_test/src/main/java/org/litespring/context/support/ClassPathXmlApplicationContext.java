package org.litespring.context.support;

import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;

/**
 * 根据classpath下的配置文件得到的上下文环境
 *
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
	
	public ClassPathXmlApplicationContext(String configFile) {
		super(configFile);
	}

	public Resource getResourceByPath(String path) {
		return new ClassPathResource(path, this.getBeanClassLoader());
	}


}
