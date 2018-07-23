package org.litespring.core.io.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.core.io.Resource;
import org.litespring.util.Assert;

/**
 * 
 * 用于得到文件目录下的resource
 *
 */
public class FileSystemResource implements Resource {
	String path;//好像没啥用
	File file; //为了在使用相对路径的时候可以返回给人绝对路径
	
	public FileSystemResource(String path) {
		Assert.notNull(path,"path must not null");
		this.path = path;
		this.file = new File(path);
	}

	public FileSystemResource(File file) {
		this.file = file;
		this.path = file.getPath();
	}

	public InputStream getInputStream() throws IOException{
		return new FileInputStream(this.file);
	}
	
	/**
	 * 资源说明，返回的是资源的真实路径
	 */
	public String getDescription() {
		String description = null;
		try {
			description = "file(" + this.file.getCanonicalPath() + ")";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return description;
	}

}
