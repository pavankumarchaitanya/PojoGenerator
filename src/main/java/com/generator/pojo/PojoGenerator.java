package com.generator.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PojoGenerator {
	Map<String, Set<Object>> qualifiedClassNameAndObjectsMap;

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
		Set<Object> nextFieldInclusiveObjectSet = includeFieldAndGenerateObjects(nextField,
				qualifiedClassNameAndObjectsMap, generatedObjectsSet, clazz);

		processedFieldsSet.add(nextField);

		return generateObjectSetFromMap(qualifiedClassNameAndObjectsMap, clazz, processedFieldsSet,
				nextFieldInclusiveObjectSet);
	}

	private Set<Object> includeFieldAndGenerateObjects(String currentField,
			Map<String, Set<Object>> classNameAndObjectsMap, Set<Object> generatedObjectsSet, Class clazz)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		Class fieldClass = getMethodParamClass(clazz, "set" + currentField);

		Set<Object> currentFieldValueSet = classNameAndObjectsMap.get(fieldClass.getName());

		Set<Object> responseSet = new HashSet<>();

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

		responseSet.addAll(generatedObjectsSet);
		return responseSet;
	}

	private Object deepClone(Object clazzInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	private Class getMethodParamClass(Class clazz, String string) {
		// TODO Auto-generated method stub
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
				fieldSet.add(method.getName().substring(2));
			}
		}
		return fieldSet;
	}

	private Set<String> getQualifiedClassNamesOfMemberVariables(Class clazz) {

		return null;
	}
}
