package br.com.soccer.domain.entity;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.utils.IndexType;

@Entity(value="teams",noClassnameStored=true)
@Indexes({@Index(fields=@Field(value="name",type=IndexType.ASC),options=@IndexOptions(unique=true)),
	      @Index(fields=@Field(value="address",type=IndexType.ASC)),
	      @Index(fields=@Field(value="found",type=IndexType.ASC))})
public class Team extends BaseEntity {
	
	private String name;
	
	@Property("found")
	private Date foundation;
	
	@Property("nicks")
	private List<String> nicknames;
	
	@Reference(value="add",lazy=true)
	private Address address;
	
	@Embedded
	private List<Honor> honors;
	
	public Team() {
		super();
	}

	public Team(String name, LocalDate foundation, List<String> nicknames,
			Address address, List<Honor> champions) {
		super();
		this.name = name;
		this.foundation = Date.from(foundation.atStartOfDay().toInstant(ZoneOffset.UTC));
		this.nicknames = nicknames;
		this.address = address;
		this.honors = champions;
	}

	public String getName() {
		return name;
	}

	public LocalDate getFoundation() {
		return foundation==null?null:LocalDate.from(foundation.toInstant());
	}

	public List<String> getNicknames() {
		return nicknames;
	}

	public Address getAddress() {
		return address;
	}

	public List<Honor> getHonors() {
		return honors;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((foundation == null) ? 0 : foundation.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Team other = (Team) obj;
		if (foundation == null) {
			if (other.foundation != null)
				return false;
		} else if (!foundation.equals(other.foundation))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + ", foundation=" + getFoundation()
				+ ", nicknames=" + nicknames + ", address=" + address
				+ ", honors=" + honors + "]";
	}

}
