package br.com.soccer.domain.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

public abstract class BaseEntity {
	
	@Id()
	private ObjectId id;
	
	@Version("ver")
	private Long version;

	public ObjectId getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}


	
}
