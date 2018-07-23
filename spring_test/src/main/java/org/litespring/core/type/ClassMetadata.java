package org.litespring.core.type;

public interface ClassMetadata {
	
	String getClassName();
	
	boolean isAbstract();
	
	boolean isInterface();
	
	boolean isFinal();
	
	boolean hasSuperClass();
	
	String getSuperClassName();
	
	String[] getInterfaceNames();
}
