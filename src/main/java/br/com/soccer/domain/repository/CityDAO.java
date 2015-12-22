package br.com.soccer.domain.repository;

import java.util.List;
import java.util.Optional;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

import br.com.soccer.domain.entity.City;
import br.com.soccer.domain.infrastructure.MongoDBUseful;

public class CityDAO extends BasicDAO<City,String>{
	
	static final Logger LOG = LoggerFactory.getLogger(City.class);

	private CityDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
		super(mongoClient, morphia, dbName);
	}
	
	public CityDAO(){
		super(MongoDBUseful.getMongoClient(),MongoDBUseful.getMorphia(),MongoDBUseful.MONGODB_SOCCER_DATABASE);
		ensureIndexes();
	}

	public int deleteAllCities(){
		Query<City> query = this.createQuery().field("_id").exists();
		MongoDBUseful.showQueryExplain(query);
		
		return this.deleteByQuery(query).getN();
	}
	
	public List<City> findCities(String stateCode){
		Query<City> query = this.createQuery().filter("stateCode", stateCode);
		MongoDBUseful.showQueryExplain(query);
		
		return this.find(query).asList();
	}
	
	public Optional<City> findCity(String name){
		Query<City> query = this.createQuery().filter("name", name);
		MongoDBUseful.showQueryExplain(query);
		
		return Optional.ofNullable(this.findOne(query));
	}
	

}
