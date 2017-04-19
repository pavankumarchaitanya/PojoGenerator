package com.generator.pojo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;

public class PojoGenerator {
	Map<String, Set<Object>> qualifiedClassNameAndObjectsMap;
	public static Logger logger = org.slf4j.LoggerFactory.getLogger(PojoGenerator.class);
test
	public PojoGenerator() {
		init();
	}

	public void init() {
		Set<Object> stringCombinationObjectSet = new HashSet<Object>();
		stringCombinationObjectSet.add("test");
		stringCombinationObjectSet.add(null);

		Set<Object> dateCombinationObjectSet = new HashSet<Object>();
		dateCombinationObjectSet.add(null);
		dateCombinationObjectSet.add(new Date());
		qualifiedClassNameAndObjectsMap = new HashMap<>();
		qualifiedClassNameAndObjectsMap.put(String.class.getName(), stringCombinationObjectSet);
		qualifiedClassNameAndObjectsMap.put(Date.class.getName(), dateCombinationObjectSet);
	}

	public <T> void setObjectCombinationsForClass(Class<T> clazz, Set<T> objectSet) {
		qualifiedClassNameAndObjectsMap.put(clazz.getName(), (Set<Object>) objectSet);
	}

	public void buildMap(Class clazz) throws ClassNotFoundException {

		Set<Object> objectSet = qualifiedClassNameAndObjectsMap.get(clazz.getName());

		if (objectSet != null) {
			return;
		}

		Set<String> qualifiedClassNamesSet = getQualifiedClassNamesOfMemberVariables(clazz);

		for (String className : qualifiedClassNamesSet) {

			if (qualifiedClassNameAndObjectsMap.keySet().contains(className)) {
				continue;
			} else {
				buildMap(Class.forName(className));
			}
		}

		objectSet = generateObjectSetFromMap(qualifiedClassNameAndObjectsMap, clazz);
		qualifiedClassNameAndObjectsMap.put(clazz.getName(), objectSet);
	}

	private Set<Object> generateObjectSetFromMap(Map<String, Set<Object>> qualifiedClassNameAndObjectsMap,
			Class clazz) {
		Set<Object> objectSet = null;
		try {
			objectSet = generateObjectSetFromMap(qualifiedClassNameAndObjectsMap, clazz, new HashSet<String>(),
					new HashSet<Object>());
		} catch (Exception e) {
			// TODO : Fix Exception handling
		}

		return objectSet;
	}

	private Set<Object> generateObjectSetFromMap(Map<String, Set<Object>> qualifiedClassNameAndObjectsMap, Class clazz,
			HashSet<String> processedFieldsSet, Set<Object> generatedObjectsSet)
			throws InstantiationException, IllegalAccessException {
		Set<String> fieldSet = getCompleteFieldSet(clazz);

		String nextField = getNextFieldToProcess(fieldSet, processedFieldsSet);

		if (nextField == null) {
			return generatedObjectsSet;
		}
		Set<Object> nextFieldInclusiveObjectSet;

		nextFieldInclusiveObjectSet = includeFieldAndGenerateObjects(nextField, qualifiedClassNameAndObjectsMap,
				generatedObjectsSet, clazz);

		processedFieldsSet.add(nextField);

		return generateObjectSetFromMap(qualifiedClassNameAndObjectsMap, clazz, processedFieldsSet,
				nextFieldInclusiveObjectSet);
	}

	private Set<Object> includeFieldAndGenerateObjects(String currentField,
			Map<String, Set<Object>> classNameAndObjectsMap, Set<Object> generatedObjectsSet, Class clazz) {
		Class fieldClass = getMethodParamClass(clazz, "set" + currentField);

		Set<Object> currentFieldValueSet = classNameAndObjectsMap.get(fieldClass.getName());

		Set<Object> responseSet = new HashSet<>();
		try {
			if (generatedObjectsSet.size() == 0) {
				generatedObjectsSet.add(clazz.newInstance());
			}

			for (Object fieldValue : currentFieldValueSet) {
				for (Object clazzInstance : generatedObjectsSet) {
					Object tempClazzInstance = deepClone(clazzInstance);

					Method method = clazz.getMethod("set" + currentField, fieldClass);
					method.invoke(tempClazzInstance, fieldValue);
					responseSet.add(tempClazzInstance);

				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred while generating objects", e);
		}
		responseSet.addAll(generatedObjectsSet);
		return responseSet;
	}

	private Object deepClone(Object originalObj) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {

		Class clazz = originalObj.getClass();
		Object newObject = clazz.newInstance();
		for (Method m : clazz.getMethods()) {
			if (m.getName().startsWith("set")) {
				Method getterMethod = clazz.getDeclaredMethod("get" + (m.getName().substring(3)));
				Object fieldValueObject = getterMethod.invoke(originalObj);
				if (fieldValueObject == null)
					continue;

				if (Serializable.class.isAssignableFrom(fieldValueObject.getClass())) {
					m.invoke(newObject, serializableDeepClone(fieldValueObject));
				} else {
					m.invoke(newObject, deepClone(fieldValueObject));
				}
			}
		}

		return newObject;
	}

	private Object serializableDeepClone(Object originalObj) {
		Object obj = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(originalObj);
			out.flush();
			out.close();
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} catch (Exception e) {
			logger.error("Exception Occurred while cloning [{}]", e);
		}
		return obj;
	}

	private Class getMethodParamClass(Class clazz, String currentFieldMethodName) {
		for (Method m : clazz.getDeclaredMethods()) {
			if (m.getName().equals(currentFieldMethodName)) {
				return m.getParameterTypes()[0];
			}
		}
		return null;
	}

	private String getNextFieldToProcess(Set<String> fieldSet, HashSet<String> processedFieldSet) {
		if (fieldSet != null && fieldSet.size() > 0) {
			List<String> nextList = fieldSet.stream().filter(n -> !processedFieldSet.contains(n))
					.collect(Collectors.toList());
			if (nextList != null && nextList.size() > 0) {
				return nextList.get(0);
			}
		}

		return null;
	}

	private Set<String> getCompleteFieldSet(Class clazz) {
		Set<String> fieldSet = new HashSet<>();
		for (Method method : clazz.getMethods()) {
			if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
				fieldSet.add(method.getName().substring(3));
			}
		}
		return fieldSet;
	}

	private Set<String> getQualifiedClassNamesOfMemberVariables(Class clazz) {
		Set<String> classNamesSet = new HashSet<>();
		for (Method method : clazz.getMethods()) {
			if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
				classNamesSet.add(method.getParameterTypes()[0].getName());

			}
		}
		return classNamesSet;
	}
}
