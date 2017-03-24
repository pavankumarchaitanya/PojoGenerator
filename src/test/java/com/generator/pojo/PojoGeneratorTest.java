package com.generator.pojo;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.generator.pojo.model.Number;
import com.generator.pojo.model.TestClass;
import com.generator.pojo.model.TestClassWithDate;
import com.generator.pojo.model.TestClassWithEnum;


public class PojoGeneratorTest {

	@Test
	public void testPojoGenerator() throws ClassNotFoundException {
		PojoGenerator pojoGenerator = new PojoGenerator();

		pojoGenerator.buildMap(TestClass.class);
		Set<Object> objectSet = pojoGenerator.qualifiedClassNameAndObjectsMap.get(TestClass.class.getName());
		for (Object testClass : objectSet) {
			TestClass tempTestClass = (TestClass) testClass;
		//	System.out.println("TestClass : " + tempTestClass);
		}
		Assert.assertEquals(40,objectSet.size());
	}

	@Test
	public void testDatePOJOGeneration() throws ClassNotFoundException {
		PojoGenerator pojoGenerator = new PojoGenerator();

		pojoGenerator.buildMap(TestClassWithDate.class);
		Set<Object> objectSet = pojoGenerator.qualifiedClassNameAndObjectsMap.get(TestClassWithDate.class.getName());
		for (Object testClassWithDate : objectSet) {
			TestClassWithDate tempTestClassWithDate = (TestClassWithDate) testClassWithDate;
		//	System.out.println("testClassWithDate : " + tempTestClassWithDate);
		}
		Assert.assertEquals(40,objectSet.size());
	}

	@Test
	public void testEnumPOJOGeneration() throws ClassNotFoundException {
		PojoGenerator pojoGenerator = new PojoGenerator();
		Set<Number> numberSet = new HashSet<>();
		numberSet.add(Number.One);
		numberSet.add(Number.Two);
		numberSet.add(Number.Three);
		pojoGenerator.setObjectCombinationsForClass(Number.class, numberSet);
		pojoGenerator.buildMap(TestClassWithEnum.class);
		Set<Object> objectSet = pojoGenerator.qualifiedClassNameAndObjectsMap.get(TestClassWithEnum.class.getName());
		for (Object obj : objectSet) {
			TestClassWithEnum testClassWithEnum = (TestClassWithEnum) obj;
	//		System.out.println("testClassWithEnum : " + testClassWithEnum);
		}
		Assert.assertEquals(80,objectSet.size());
	}
}
