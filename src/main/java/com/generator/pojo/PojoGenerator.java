package com.generator.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
		return generateObjectSetFromMap(qualifiedClassNameAndObjectsMap, clazz, new HashSet<String>(),
				new HashSet<Object>());
	}

	private Set<Object> generateObjectSetFromMap(Map<String, Set<Object>> qualifiedClassNameAndObjectsMap, Class clazz,
			HashSet<String> processedFieldsSet, Set<Object> generatedObjectsSet) {
		Set<String> fieldSet = getCompleteFieldSet(clazz);

		String nextField = getNextFieldToProcess(fieldSet, processedFieldsSet);

		if (nextField == null) {
			return generatedObjectsSet;
		}
		Set<Object> nextFieldInclusiveObjectSet = includeFieldAndGeneraterObjects(nextField, generatedObjectsSet);

		processedFieldsSet.add(nextField);

		return generateObjectSetFromMap(qualifiedClassNameAndObjectsMap, clazz, processedFieldsSet,
				nextFieldInclusiveObjectSet);
	}

	private Set<Object> includeFieldAndGeneraterObjects(String nextField, Set<Object> generatedObjectsSet) {

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
