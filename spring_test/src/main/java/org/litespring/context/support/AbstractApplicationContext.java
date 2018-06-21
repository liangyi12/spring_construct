package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeandefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
/**
 * 抽象出来的ApplicationContext实现，使用了模板模式，抽取了方法的重复内容，将不同实现的方法作为抽象方法，交由子类实现
 *  @author liyanli
 *
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
	private DefaultBeanFactory factory = null;
	private ClassLoader classLoader;
	
	/**
	 * 构造函数，load了BeanDefinition。
	 * @param configFile 配置文件路径
	 */
	public AbstractApplicationContext(String configFile) {
		factory = new DefaultBeanFactory();
		XmlBeandefinitionReader reader = new XmlBeandefinitionReader(factory);
		Resource resource = getResourceByPath(configFile);
		reader.loadBeanDenifition(resource);
		factory.setBeanClassLoader(classLoader);
	}
	
	/**根据路径得到resource，因相对于文件路径与项目classpath等有多种不同实现，故抽象出来，由相应的子类实现本方法*/
	public abstract Resource getResourceByPath(String configFile);

	public Object getBean(String beanId) {
		return factory.getBean(beanId);
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return this.classLoader;
	}
	
	
}
