package br.com.soccer.domain.infrastructure;
import java.util.Map;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;


public class MongoDBUseful {
	
	static final Logger LOG = LoggerFactory.getLogger(MongoDBUseful.class);
	
	private static final MongoClient mongoClient = new MongoClient();
	
	private static Morphia morphia;
	
	public static String MONGODB_SOCCER_DATABASE = "soccer";
	
	public static MongoClient getMongoClient(){
		return mongoClient;
	}
	
	public static Morphia getMorphia(){
		if(morphia==null){
			morphia = new Morphia();
			morphia.mapPackage(" br.com.soccer.domain.repository");
		}
		return morphia;
	}
	
	public static void showQueryExplain(Query<?> query) {
		if(LOG.isDebugEnabled()){
			Map<String, Object> map =  query.explain();
			map.keySet().stream()
						.forEach(key ->{
								LOG.debug("key: "+key);
								LOG.debug("value: "+map.get(key));});
		}
		
	}
		
}
