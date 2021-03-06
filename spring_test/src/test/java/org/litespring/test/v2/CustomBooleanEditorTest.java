package org.litespring.test.v2;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyEditors.CustomBooleanEditor;

public class CustomBooleanEditorTest{

	@Test
	public void testConvertStringToBoolean() {
		CustomBooleanEditor editor = new CustomBooleanEditor(true);
		
		editor.setAsText("true");
		assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
		
		editor.setAsText("false");
		assertEquals(false, ((Boolean)editor.getValue()).booleanValue());
		
		editor.setAsText("on");
		assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
		
		editor.setAsText("off");
		assertEquals(false, ((Boolean)editor.getValue()).booleanValue());
		
		editor.setAsText("yes");
		assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
		
		editor.setAsText("no");
		assertEquals(false, ((Boolean)editor.getValue()).booleanValue());
	}
	
	@Test
	public void testConvertStringToBooleanException() {
		CustomBooleanEditor editor = new CustomBooleanEditor(true);
		try{
			editor.setAsText("aaaa");
		}catch(IllegalArgumentException e) {
			return;
		}
		Assert.fail();
	}

}
