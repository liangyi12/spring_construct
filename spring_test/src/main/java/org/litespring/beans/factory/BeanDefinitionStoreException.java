package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * 
 * 存储bean定义exception
 *
 */
public class BeanDefinitionStoreException extends BeansException{

	public BeanDefinitionStoreException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	

}
