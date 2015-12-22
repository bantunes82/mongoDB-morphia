package br.com.soccer.domain.useful;

import java.text.ParseException;

import org.junit.Before;
import org.junit.BeforeClass;

import br.com.soccer.domain.infrastructure.MongoDBUseful;
import br.com.soccer.domain.repository.AddressDAO;
import br.com.soccer.domain.repository.CityDAO;
import br.com.soccer.domain.repository.TeamDAO;

public abstract class TestCaseSetup {
	
	protected static CityDAO cityDAO;
	protected static AddressDAO addressDAO;
	protected static TeamDAO teamDAO;
	protected static TeamBuilder teamBuilder;
	
	@BeforeClass
	public static void initialSetup(){
		MongoDBUseful.MONGODB_SOCCER_DATABASE = "soccerTest";
		MongoDBUseful.getMongoClient().dropDatabase(MongoDBUseful.MONGODB_SOCCER_DATABASE);
		
		cityDAO = new CityDAO();
		addressDAO = new AddressDAO();
		teamDAO = new TeamDAO();
		teamBuilder = new TeamBuilder();
	}
	
	@Before
	public void initializeTest() throws ParseException{
		dropCollections();
	}
	
	private static void dropCollections(){
		cityDAO.deleteAllCities();
		addressDAO.deleteAllAddresses();
		teamDAO.deleteAllTeams();
	}

}
