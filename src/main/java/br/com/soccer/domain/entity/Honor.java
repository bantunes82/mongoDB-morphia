package br.com.soccer.domain.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;


@Embedded
public class Honor {
	
	@Property("name")
	private String championshipName;
	
	private List<Integer> years;

	public Honor(String championshipName, List<Integer> years) {
		super();
		this.championshipName = championshipName;
		this.years = years;
	}
	
	public Honor() {
		super();
	}

	public Honor(String championshipName) {
		super();
		this.championshipName = championshipName;
	}

	public List<Integer> getYears() {
		return years;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((championshipName == null) ? 0 : championshipName.hashCode());
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
		Honor other = (Honor) obj;
		if (championshipName == null) {
			if (other.championshipName != null)
				return false;
		} else if (!championshipName.equals(other.championshipName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Honor [championshipName=" + championshipName + ", years="
				+ years + "]";
	}

	
	
}
