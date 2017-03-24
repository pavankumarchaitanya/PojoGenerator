package com.generator.pojo.model;

import java.util.Date;

public class TestClassWithDate {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componentTestClass == null) ? 0 : componentTestClass.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((field1 == null) ? 0 : field1.hashCode());
		result = prime * result + ((field2 == null) ? 0 : field2.hashCode());
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
		TestClassWithDate other = (TestClassWithDate) obj;
		if (componentTestClass == null) {
			if (other.componentTestClass != null)
				return false;
		} else if (!componentTestClass.equals(other.componentTestClass))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
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
		return true;
	}

	@Override
	public String toString() {
		return "TestClassWithDate [field2=" + field2 + ", field1=" + field1 + ", date=" + date + ", componentTestClass="
				+ componentTestClass + "]";
	}

	private String field2;
	private String field1;
	private Date date;
	private ComponentTestClass componentTestClass;

	public ComponentTestClass getComponentTestClass() {
		return componentTestClass;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
