package org.litespring.beans.factory.config.property;

public class RunTimeReference {
	private String ref_beanid;
	
	public RunTimeReference(String ref_beanid) {
		super();
		this.ref_beanid = ref_beanid;
	}

	public String getRef_beanid() {
		return ref_beanid;
	}

	public void setRef_beanid(String ref_beanid) {
		this.ref_beanid = ref_beanid;
	}
}
