package org.litespring.test.v1;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.ClassPathResource;
import org.litespring.core.io.support.FileSystemResource;

public class ResourceTest {

	@Test
	public void testClassPathResource() {
		Resource resource = new ClassPathResource("petstore-v1.xml");
		InputStream is = null;
		try{
			is = resource.getInputStream();
			//断言并不准确，应该得到输入流并比较
			assertNotNull(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void testFileSystemResource() {
		Resource resource = new FileSystemResource(this.getClass().getResource("/petstore-v1.xml").getPath());
		//Resource resource = new FileSystemResource("D:\\workspace\\eclipseWorkSpace\\spring_test\\src\\test\\resources\\petstore-v1.xml");
		InputStream is = null;
		try{
			is = resource.getInputStream();
			//System.out.println(resource.getDescription());
			assertNotNull(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
