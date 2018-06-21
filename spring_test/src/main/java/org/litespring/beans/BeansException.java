package org.litespring.beans;
/**
 * 自定义bean的顶级exception，继承RuntimeException，不强制捕获
 * */
public class BeansException extends RuntimeException{
	public BeansException(String msg) {
		super(msg);
	}
	
	public BeansException(String msg, Throwable cause){
		super(msg,cause);
	}
}
