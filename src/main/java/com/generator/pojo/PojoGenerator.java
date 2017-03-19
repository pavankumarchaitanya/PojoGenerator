package com.generator.pojo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		// TODO Auto-generated method stub
		return null;
	}

	private String getNextFieldToProcess(Set<String> fieldSet, HashSet<String> processedFieldSet) {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<String> getCompleteFieldSet(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<String> getQualifiedClassNamesOfMemberVariables(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}
}
