package org.litespring.core.io.support;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.io.Resource;
import org.litespring.util.Assert;
import org.litespring.util.ClassUtils;
/**
 * load package 中的class为resource
 *
 */
public class PackageResourceLoader {
	private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);
	private final ClassLoader classLoader;

	public PackageResourceLoader() {
		this.classLoader = ClassUtils.getDefaultClassLoader();
	}
	
	public PackageResourceLoader(ClassLoader classLoader) {
		Assert.notNull(classLoader, "ResourceLoader must not be null");
		this.classLoader = classLoader;
	}
	
	
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}

	/**
	 * 把一个package下的class变成resource
	 * @param basePackage 包名
	 * @return resource数组
	 */
	public Resource[] getResources(String basePackage) {
		Assert.notNull(basePackage, "baskPackage must not null");
		String location = ClassUtils.convertClassNameToResourcePath(basePackage);
		ClassLoader classLoader = getClassLoader();
		URL url = classLoader.getResource(location);
		File rootDir = new File(url.getFile());
		
		Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
		Resource[] result = new Resource[matchingFiles.size()];
		int i=0;
		for(File file : matchingFiles) {
			result[i++] = new FileSystemResource(file);
		}
		return result;
	}
	
	/**
	 * 递归得到目录下的文件集合
	 * @param rootDir 目录
	 * @return 文件集合
	 */
	protected   Set<File> retrieveMatchingFiles(File rootDir) {
		if(!rootDir.exists()) {
			if(logger.isDebugEnabled()) {
				logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
			}
			return Collections.emptySet();
		}
		if(!rootDir.isDirectory()) {
			if(logger.isWarnEnabled()) {
				logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
			}
			return Collections.emptySet();
		}
		if(!rootDir.canRead()) {
			if(logger.isWarnEnabled()) {
				logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
						"] because the application is not allowed to read the directory");
			}
			return Collections.emptySet();
		}
		
		Set<File> result = new LinkedHashSet<File>(8); //8？？
		doRetrieveMatchingFiles(rootDir, result);
		return result;
		
	}
	
	/**
	 * 递归得到目录下的文件集合
	 * @param dir 目录
	 * @param result 文件集合
	 */
	protected  void doRetrieveMatchingFiles(File dir, Set<File> result) {
		File[] dirContents = dir.listFiles();
		if(dirContents == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
			}
			return;
		}
		for (File content  : dirContents) {
			if(content.isDirectory()){
				if (!content.canRead()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
								"] because the application is not allowed to read the directory");
					}
				}else{
					doRetrieveMatchingFiles(content, result);
				}
			}else{
				result.add(content);
			}
		}
		
	}
}
