package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

/**
 * 
 * 读取、解析xml
 *
 */
public class XmlBeandefinitionReader {
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	public static final String SCOPE_ATTRIBUTE = "scope";
	
	//持有一个BeanDefinitionRegistry对象
	BeanDefinitionRegistry registry;
	
	public XmlBeandefinitionReader(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}
	
	/**
	 * 解析xml并注册BeanDenifition
	 * @param resource ：资源类对象
	 */
	public void loadBeanDenifition(Resource resource) {
		InputStream is = null;
		
		try {
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			Iterator iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = (Element) iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
				if(ele.attributeValue(SCOPE_ATTRIBUTE) != null) {
					bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
				}
				this.registry.registerBeanDefinition(id, bd);	
			}
		} catch (Exception e) {
			throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
		}finally{
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
