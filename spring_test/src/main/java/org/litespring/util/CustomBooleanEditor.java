package org.litespring.util;

import java.beans.PropertyEditorSupport;

public class CustomBooleanEditor extends PropertyEditorSupport{
	/**是否允许空值*/
	private final boolean allowEmpty;
	
	public CustomBooleanEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		}else if(text.equals("true") || text.equals("on") || text.equals("yes")) {
			setValue(true);
		}else if(text.equals("false") || text.equals("off") || text.equals("no")) {
			setValue(false);
		}else{
			throw new IllegalArgumentException();
		}
	}

}
