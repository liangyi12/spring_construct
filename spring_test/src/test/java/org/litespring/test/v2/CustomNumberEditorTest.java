package org.litespring.test.v2;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyEditors.CustomNumberEditor;

public class CustomNumberEditorTest {

	@Test
	public void testConvertString() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
		
		editor.setAsText("3");
		Object value = editor.getValue();
		assertTrue(value instanceof Integer);
		assertEquals(3, ((Integer) value).intValue());
	}
	
	@Test
	public void testConverStringNull(){
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
		editor.setAsText("");
		assertTrue(editor.getValue() == null);		
	}
	
	@Test
	public void testConvertStringException() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
		try{
			editor.setAsText("3.1");
		}catch(IllegalArgumentException e){
			return;
		}
		Assert.fail();
		
	}
	
	@Test
	public void testConvertStringNotAllowEmpty() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, false);
		try{
			editor.setAsText("");
		}catch(IllegalArgumentException e){
			return;
		}
		Assert.fail();
		
	}

}
