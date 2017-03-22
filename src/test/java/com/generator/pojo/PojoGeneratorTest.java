package com.generator.pojo;

import java.util.Set;

import org.junit.Test;

import com.generator.pojo.model.TestClass;

public class PojoGeneratorTest {

	@Test
	public void testPojoGenerator() throws ClassNotFoundException {
		PojoGenerator pojoGenerator = new PojoGenerator();

		pojoGenerator.buildMap(TestClass.class);
		Set<Object> objectSet = pojoGenerator.qualifiedClassNameAndObjectsMap.get(TestClass.class.getName());
		for (Object testClass : objectSet){
			TestClass tempTestClass = (TestClass)testClass;
			System.out.println("TestClass : " + tempTestClass);
		}
		System.out.println();
	}
}
