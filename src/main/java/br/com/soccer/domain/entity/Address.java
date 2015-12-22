package br.com.soccer.domain.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.geo.Point;
import org.mongodb.morphia.utils.IndexType;

@Entity(value="addresses", noClassnameStored=true)
@Indexes({@Index(fields=@Field(value="city",type=IndexType.ASC)),
		  @Index(fields=@Field(value="location", type=IndexType.GEO2DSPHERE))})
public class Address extends BaseEntity {
	
	@Property("loc")
	private Point location;

	@Property("zip")
	private String zipCode;

	@Property("num")
	private int number;
	
	private String street;
	
	@Reference(lazy=true)
	private City city;
	
	public Address() {
		super();
	}

	public Address(Point location,
			String zipCode, int number, String street, City city) {
		super();
		this.location = location;
		this.zipCode = zipCode;
		this.number = number;
		this.street = street;
		this.city = city;
	}

	public Point getLocation() {
		return location;
	}

	public City getCity() {
		return city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		Address other = (Address) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [location=" + location + ", zipCode=" + zipCode
				+ ", number=" + number + ", street=" + street + ", city="
				+ city + "]";
	}

	
}
