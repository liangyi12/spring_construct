package org.litespring.test.v2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.SimpleTypeConvert;
import org.litespring.beans.TypeConvert;
import org.litespring.beans.TypeMismacthException;

public class TypeConvertTest {

	@Test
	public void testConverStringToInt() {
		TypeConvert convert  = new SimpleTypeConvert();
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
		TypeConvert convert  = new SimpleTypeConvert();
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
		TypeConvert convert  = new SimpleTypeConvert();
		String s = convert.convertIfNecessary("test", String.class);
		assertEquals("test",s);
	}
	
	@Test
	public void testInvalidConvert() {
		TypeConvert convert  = new SimpleTypeConvert();
		try{
			convert.convertIfNecessary("123", List.class);
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
		Assert.fail();
	}

}
