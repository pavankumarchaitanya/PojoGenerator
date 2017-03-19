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

	private Set<Object> generateObjectSetFromMap(Map<String, Set<Object>> qualifiedClassNameAndObjectsMap2, Class clazz,
			HashSet<String> processedFieldSet, HashSet<Object> generatedObjectsSet) {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<String> getQualifiedClassNamesOfMemberVariables(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}
}
