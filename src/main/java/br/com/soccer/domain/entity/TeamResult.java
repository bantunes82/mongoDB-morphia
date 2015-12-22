package br.com.soccer.domain.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="teamsResult",noClassnameStored=true)
public class TeamResult {
	
	@Id
	private String name;
	
	private int count;
	
	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}
	
	

}
