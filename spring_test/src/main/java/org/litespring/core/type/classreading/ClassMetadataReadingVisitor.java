package org.litespring.core.type.classreading;

import org.litespring.core.type.ClassMetadata;
import org.litespring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;

/**
 * A visitor to visit a Java class. to get the class Metadata
 * 访问类的元数据：类名、父类类名等
 *
 */
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata{

	private boolean isAbstract;
	
	private boolean isInterface;
	
	private boolean isFinal;
	
	private String className;
	
	private String superClassName;
	
	private String[] interfaceNames;

	public ClassMetadataReadingVisitor() {
		super(SpringAsmInfo.ASM_VERSION);
	}
	
	/**
	 * Visits the header of the class.
	 * 覆盖asm提供的visit方法，得到类的类名，是否为抽象类等
	 */
	public void visit(int version, int access, String name,	String signature, String superName, String[] interfaces) {
		this.className = ClassUtils.convertResourcePathToClassName(name);
		this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0); 
		this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0); 
		this.isFinal = ((access & Opcodes.ACC_FINAL) != 0); 
		if (superName != null) {
			this.superClassName = ClassUtils.convertResourcePathToClassName(superName);
		}
		this.interfaceNames = new String[interfaces.length];
		for (int i=0; i < interfaces.length; i++){
			this.interfaceNames[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
		}
	}
	
	public boolean isAbstract() {
		return this.isAbstract;
	}

	public boolean isInterface() {
		return this.isInterface;
	}

	public boolean isFinal() {
		return this.isFinal;
	}

	public String getClassName() {
		return this.className;
	}

	public String getSuperClassName() {
		return this.superClassName;
	}

	public String[] getInterfaceNames() {
		return this.interfaceNames;
	}


	public boolean hasSuperClass() {
		return !(this.superClassName == null);
	}
	
	

}
