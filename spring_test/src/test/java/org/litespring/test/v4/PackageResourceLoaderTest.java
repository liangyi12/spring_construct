package org.litespring.test.v4;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;

public class PackageResourceLoaderTest {

	@Test
	public void testLoadResource() {
		PackageResourceLoader packageLoader = new PackageResourceLoader();
		Resource[] resources = packageLoader.getResources("org.litespring.dao.v4");
		assertEquals(2, resources.length);
	}

}
