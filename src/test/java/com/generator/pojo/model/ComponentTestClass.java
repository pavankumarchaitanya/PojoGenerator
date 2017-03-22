package com.generator.pojo.model;

public class ComponentTestClass {
	private String field11;
	private String field10;

	@Override
	public String toString() {
		return "ComponentTestClass [field11=" + field11 + ", field10=" + field10 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field10 == null) ? 0 : field10.hashCode());
		result = prime * result + ((field11 == null) ? 0 : field11.hashCode());
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
		ComponentTestClass other = (ComponentTestClass) obj;
		if (field10 == null) {
			if (other.field10 != null)
				return false;
		} else if (!field10.equals(other.field10))
			return false;
		if (field11 == null) {
			if (other.field11 != null)
				return false;
		} else if (!field11.equals(other.field11))
			return false;
		return true;
	}

	public String getField10() {
		return field10;
	}

	public void setField10(String field10) {
		this.field10 = field10;
	}

	public String getField11() {
		return field11;
	}

	public void setField11(String field11) {
		this.field11 = field11;
	}

}
