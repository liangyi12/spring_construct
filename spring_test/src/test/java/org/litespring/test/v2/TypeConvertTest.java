package org.litespring.test.v2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.TypeMismacthException;

public class TypeConvertTest {

	@Test
	public void testConverStringToInt() {
		TypeConverter convert  = new SimpleTypeConverter();
		Integer i = convert.convertIfNecessary("3",Integer.class);
		assertEquals(3, i.intValue());
		
		try{
			convert.convertIfNecessary("3.1",Integer.class);
		}catch(TypeMismacthException e){
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void testConvertStringToBoolean() {
		TypeConverter convert  = new SimpleTypeConverter();
		boolean b = convert.convertIfNecessary("1", boolean.class);
		assertEquals(true, b);
		try{
			convert.convertIfNecessary("11", boolean.class);
		}catch(TypeMismacthException e) {
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void testConvertStringToString() {
		TypeConverter convert  = new SimpleTypeConverter();
		String s = convert.convertIfNecessary("test", String.class);
		assertEquals("test",s);
	}
	
	@Test
	public void testInvalidConvert() {
		TypeConverter convert  = new SimpleTypeConverter();
		try{
			convert.convertIfNecessary("123", List.class);
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
		Assert.fail();
	}

}
