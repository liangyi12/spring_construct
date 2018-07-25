package org.litespring.context.annotation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.util.StringUtils;

/**
 * 
 * 
 *A bean definition scanner that detects bean candidates on the classpath, 
 *registering corresponding bean definitions with a given registry (BeanFactory or ApplicationContext).
 *Candidate classes are detected through configurable type filters. The default filters include classes 
 *that are annotated with Spring's @Component, @Repository, @Service, or @Controller stereotype.
 *
 *bean定义扫描，检测classpath里通过注解注入的bean，将bean定义注册到给定的BeanDefinitionRegistry中
 *目前只实现了扫描 @Component 注解的类。
 */
public class ClassPathBeanDefinitionScanner {
	
	private final BeanDefinitionRegistry registry;
	
	private PackageResourceLoader resourceLoader = new PackageResourceLoader();
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BeanNameGenerator generator = new AnnotationBeanNameGenerator();
	
	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}
	//为什么返回set
	/**
	 *  Perform a scan within the specified base packages, returning the registered bean definitions.
	 *  在指定的包下进行扫描，返回已注册的bean的定义
	 * @param packageToScan
	 * @return
	 */
	public Set<BeanDefinition> doScan(String packageToScan){
		
		String[] bacePackages = StringUtils.tokenizeToStringArray(packageToScan, ",");
		
		Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
		
		for(String bacePackage : bacePackages) {
			Set<BeanDefinition> candidates = findCandidateComponents(bacePackage);
			for(BeanDefinition candidate : candidates) {
				beanDefinitions.add(candidate);
				registry.registerBeanDefinition(candidate.getID(), candidate);
			}
		}
		return beanDefinitions;
		
		 
	}
	
	/**
	 *  Scan the class path for candidate components.
	 * @param bacePackage the package to check for annotated classes
	 * @return  a corresponding Set of autodetected bean definitions
	 */
	public Set<BeanDefinition> findCandidateComponents(String bacePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		Resource[]  resources = this.resourceLoader.getResources(bacePackage);
		
		for(Resource resource : resources) {
			try {
				MetadataReader metadataReader = new SimpleMetadataReader(resource);
				if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
					ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
					String beanName = this.generator.generateBeanName(sbd, this.registry);
					sbd.setId(beanName);
					candidates.add(sbd);
				}
			} catch (Throwable  e) {
				throw new BeanDefinitionStoreException(
						"Failed to read candidate component class: " + resource, e);
			}
			
		}
		
		return candidates;
		
	}
	

}
