package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;
/**
 * 资源接口，提供了getInputStream()方法得到一个InputStream，得到的InputStream可以传给dom4j的SAXReader
 * 来解析。实现有：ClassPathResource、FileSystemResource
 * @author liyanli
 *
 */
public interface Resource {
	/**得到资源的输入流*/
	public InputStream getInputStream() throws IOException;
	/**得到资源的描述*/
	public String getDescription();

}
