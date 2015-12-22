package br.com.soccer.domain.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.utils.IndexType;

@Entity(value="cities",noClassnameStored=true)
@Indexes({@Index(fields=@Field(value="stateCode",type=IndexType.ASC))})
public class City extends BaseEntity {
		
	private String name;

	@Property("state")
	private String stateCode;
	
	@Property("country")
	private String countryCode;
	

	public City() {
		super();
	}

	public City(String name, String stateCode, String countryCode) {
		super();
		this.name = name;
		this.stateCode = stateCode;
		this.countryCode = countryCode;
	}
	
	public String getStateCode() {
		return stateCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((stateCode == null) ? 0 : stateCode.hashCode());
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
		City other = (City) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", stateCode=" + stateCode
				+ ", countryCode=" + countryCode + "]";
	}
	
}
