package br.com.soccer.domain.repository;

import java.util.List;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

import br.com.soccer.domain.entity.Address;
import br.com.soccer.domain.entity.City;
import br.com.soccer.domain.infrastructure.MongoDBUseful;

public class AddressDAO extends BasicDAO<Address,String>{
	
	static final Logger LOG = LoggerFactory.getLogger(Address.class);

	private AddressDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
		super(mongoClient, morphia, dbName);
	}
	
	public AddressDAO(){
		super(MongoDBUseful.getMongoClient(),MongoDBUseful.getMorphia(),MongoDBUseful.MONGODB_SOCCER_DATABASE);
		ensureIndexes();
	}
	
	public int deleteAllAddresses(){
		Query<Address> query = this.createQuery().field("_id").exists();
		MongoDBUseful.showQueryExplain(query);
		
		return this.deleteByQuery(query).getN();
	}
	
	public List<Address> findAddresses(City city){
		Query<Address> query = this.createQuery().filter("city", city);
		MongoDBUseful.showQueryExplain(query);
		
		return this.find(query).asList();
	}
	
	public List<Address> findAddressesNearTheAddress(Address address, int maxDistanceInkilometers){
		Query<Address> query = this.createQuery().field("location").near(address.getLocation(), maxDistanceInkilometers*1000);
		MongoDBUseful.showQueryExplain(query);
		
		return this.find(query).asList();
	}
	
}
