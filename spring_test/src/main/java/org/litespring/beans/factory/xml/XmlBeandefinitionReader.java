package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;
import org.litespring.util.StringUtils;

/**
 * 
 * 读取、解析xml
 *
 */
public class XmlBeandefinitionReader {
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	public static final String SCOPE_ATTRIBUTE = "scope";
	public static final String PROPERTY_ELEMENT = "property";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String VALUE_ATTRIBUTE = "value";
	public static final String REF_ATTRIBUTE = "ref";
	
	//持有一个BeanDefinitionRegistry对象
	BeanDefinitionRegistry registry;
	protected final Log logger = LogFactory.getLog(getClass());
	
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
				ParsePropertyElement(ele,bd);
				
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
	
	public void ParsePropertyElement(Element beanEle, BeanDefinition bd) {
		Iterator iter = beanEle.elementIterator();
		while(iter.hasNext()) {
			Element e = (Element) iter.next();
			String propertyName = e.attributeValue(NAME_ATTRIBUTE);
			if(!StringUtils.hasLength(propertyName)) {
				//FATAL level指出每个严重的错误事件将会导致应用程序的退出。
				logger.fatal("Tag property must hava a 'name' attribute");
				return;
			}
			Object val = parsePropertyValue(e, bd, propertyName);
			PropertyValue pv = new PropertyValue(propertyName, val);
			/*为什么不用setPropertyValues(list)? 因为如果使用这个方法，需要返回一个propertyValue的list，这样用代码的实现的话，会把解析
			property元素的name与解析value掺杂在一起，而value要处理很多情况，之后也有可能会出现其他情况，再次修改的话会修改整个方法，不如把方法
			分成两个来实现，一个专门用来处理value，并返回value的值。*/
			bd.getPropertyValues().add(pv);
		}
	}
	
	
	private Object parsePropertyValue(Element e, BeanDefinition bd,	String propertyName) {
		String elementName = (propertyName != null ?
				"<property> element for property '" + propertyName + "'" :
				"<constructor-arg> element");
		boolean hasRefAttribute = (e.attribute(REF_ATTRIBUTE) != null);
		boolean hasValueAttribute = (e.attribute(VALUE_ATTRIBUTE) != null);
		if(hasRefAttribute) {
			String refname = e.attributeValue(REF_ATTRIBUTE);
			if(!StringUtils.hasText(refname)){
				logger.error(elementName + " contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refname);
			return ref;
		}else if(hasValueAttribute) {
			TypedStringValue valueBolder = new TypedStringValue(e.attributeValue(VALUE_ATTRIBUTE));
			return valueBolder;
		}else{
			throw new RuntimeException(elementName + " must specify a ref or value");
		}
	}
	
	//我写代码喜欢都揉搓在一起写，之后修改的时候会很乱。而spring的方法做到了单一职责，每个方法只处理一个事
	private List<PropertyValue> handleProperty(List<Element> pro_eles) {
		List<PropertyValue> properties = new ArrayList<PropertyValue>();
		if(!pro_eles.isEmpty()){
			properties = new ArrayList<PropertyValue>();
			for(Element e : pro_eles){
				String pro_name = e.attributeValue(NAME_ATTRIBUTE);
				String pro_value = e.attributeValue(VALUE_ATTRIBUTE);
				if(pro_value != null){
					PropertyValue p = new PropertyValue(pro_name, pro_value);
					properties.add(p);
				}
				String pro_ref = e.attributeValue(REF_ATTRIBUTE);
				if(pro_ref != null) {
					PropertyValue p = new PropertyValue(pro_name, pro_ref);
					properties.add(p);
				}
			}
		}
		return properties;
	}

}
