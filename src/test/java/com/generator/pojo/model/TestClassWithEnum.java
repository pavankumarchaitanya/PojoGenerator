package com.generator.pojo.model;

public class TestClassWithEnum {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componentTestClass == null) ? 0 : componentTestClass.hashCode());
		result = prime * result + ((field1 == null) ? 0 : field1.hashCode());
		result = prime * result + ((field2 == null) ? 0 : field2.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestClassWithEnum other = (TestClassWithEnum) obj;
		if (componentTestClass == null) {
			if (other.componentTestClass != null)
				return false;
		} else if (!componentTestClass.equals(other.componentTestClass))
			return false;
		if (field1 == null) {
			if (other.field1 != null)
				return false;
		} else if (!field1.equals(other.field1))
			return false;
		if (field2 == null) {
			if (other.field2 != null)
				return false;
		} else if (!field2.equals(other.field2))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestClassWithEnum [field2=" + field2 + ", field1=" + field1 + ", componentTestClass="
				+ componentTestClass + ", number=" + number + "]";
	}

	private String field2;
	private String field1;
	private ComponentTestClass componentTestClass;
	private Number number;

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}

	public ComponentTestClass getComponentTestClass() {
		return componentTestClass;
	}

	public void setComponentTestClass(ComponentTestClass componentTestClass) {
		this.componentTestClass = componentTestClass;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

}
