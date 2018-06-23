package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.Property;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.property.RunTimeReference;
import org.litespring.beans.factory.config.property.TypedStringObject;
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
	public static final String PROPERTY_ATTRIBUTE = "property";
	public static final String PROPERTY_NAME_ATTRIBUTE = "name";
	public static final String PROPERTY_VALUE_ATTRIBUTE = "value";
	public static final String PROPERTY_REF_ATTRIBUTE = "ref";
	
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
				List<Element> pro_eles = ele.elements(PROPERTY_ATTRIBUTE);
				List<Property> properties = handleProperty(pro_eles);
				bd.setProperties(properties);
				
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
	
	private List<Property> handleProperty(List<Element> pro_eles) {
		List<Property> properties = new ArrayList<Property>();
		if(!pro_eles.isEmpty()){
			properties = new ArrayList<Property>();
			for(Element e : pro_eles){
				String pro_name = e.attributeValue(PROPERTY_NAME_ATTRIBUTE);
				String pro_value = e.attributeValue(PROPERTY_VALUE_ATTRIBUTE);
				if(pro_value != null){
					Property p = new Property(pro_name, pro_value, new TypedStringObject(pro_value));
					properties.add(p);
				}
				String pro_ref = e.attributeValue(PROPERTY_REF_ATTRIBUTE);
				if(pro_ref != null) {
					Property p = new Property(pro_name, pro_ref, new RunTimeReference(pro_ref));
					properties.add(p);
				}
			}
		}
		return properties;
	}

}
