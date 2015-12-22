
package br.com.soccer.domain.repository;

import static org.mongodb.morphia.aggregation.Group.grouping;
import static org.mongodb.morphia.aggregation.Sort.ascending;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.Sort;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

import br.com.soccer.domain.entity.Address;
import br.com.soccer.domain.entity.City;
import br.com.soccer.domain.entity.Honor;
import br.com.soccer.domain.entity.Team;
import br.com.soccer.domain.entity.TeamResult;
import br.com.soccer.domain.infrastructure.MongoDBUseful;

public class TeamDAO extends BasicDAO<Team,String>{
	
	static final Logger LOG = LoggerFactory.getLogger(TeamDAO.class);
	
	private AddressDAO addressDAO = new AddressDAO();
	private CityDAO cityDAO = new CityDAO();
	
	private TeamDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
		super(mongoClient, morphia, dbName);
	}
	
	public TeamDAO(){
		super(MongoDBUseful.getMongoClient(),MongoDBUseful.getMorphia(),MongoDBUseful.MONGODB_SOCCER_DATABASE);
		ensureIndexes();
	}
	
	public UpdateResults addNewHonor(String teamName,Honor honor){
		Query<Team> query = this.createQuery().filter("name", teamName);
		MongoDBUseful.showQueryExplain(query);
		
		UpdateOperations<Team> updateOperations = this.createUpdateOperations();
		updateOperations.add("honors",honor);
		
		return this.update(query, updateOperations);
	}
	
	public boolean addNewYearInTheHonor(String teamName, String championshipName, int year) {
		boolean registeUpdated = false;
		Query<Team> query = this.createQuery().filter("name", teamName)
											  .filter("honors.championshipName", championshipName);
		MongoDBUseful.showQueryExplain(query);
		
		Team team = this.findOne(query);
		
		if(team!=null){
			int index = team.getHonors().indexOf(new Honor(championshipName));
			team.getHonors().get(index).getYears().add(year);
			
			this.save(team);
			registeUpdated = true;
		}
		return registeUpdated;
		
	}
	
	public boolean deleteComplete(String teamName){
		int result = 0;
		Query<Team> query = this.createQuery().filter("name", teamName);
		MongoDBUseful.showQueryExplain(query);
		
		Team team = this.findOne(query);
		
		if(team!=null){
			if(team.getAddress()!=null){
				result = addressDAO.delete(team.getAddress()).getN();
			}
			result= result + this.delete(team).getN();
		}
		
		return (result==1 || result==2);
	}
	
	public int deleteTeamsComplete(String stateCode){
		Query<Team> query;
		int deletedRegistrationNumber=0;
		
		for(City city: cityDAO.findCities(stateCode)){
			for(Address address : addressDAO.findAddresses(city)){
				query = this.createQuery().filter("address", address);
				MongoDBUseful.showQueryExplain(query);
				 
				for(Team team : this.find(query)){
					this.delete(team);
					addressDAO.delete(address);
					deletedRegistrationNumber++;
				}
			}
		}
		
		return deletedRegistrationNumber;
	}
	
	public int deleteAllTeams(){
		Query<Team> query = this.createQuery().field("_id").exists();
		MongoDBUseful.showQueryExplain(query);
		
		return this.deleteByQuery(query).getN();
	}
	
	public List<Team> findTeams(String championshipName) {
		Query<Team> query = this.createQuery().filter("honors.championshipName", championshipName)
											  .order("name");
		MongoDBUseful.showQueryExplain(query);
		
		return this.find(query).asList();
	}
	
	public List<Team> findTeamsBetweenFoundationDate(LocalDate inicialDate, LocalDate finalDate){
		Query<Team> query = this.createQuery().field("found").greaterThan(Date.from(inicialDate.atStartOfDay().toInstant(ZoneOffset.UTC)))
											  .field("found").lessThan(Date.from(finalDate.atStartOfDay().toInstant(ZoneOffset.UTC)))
											  .order("found");
		MongoDBUseful.showQueryExplain(query);
		
		return this.find(query).asList();
	}
	
	public Optional<Team> find(String teamName){
		Query<Team> query = this.createQuery().filter("name", teamName);
		MongoDBUseful.showQueryExplain(query);

		return Optional.ofNullable(this.findOne(query));
	}

	public List<Team> findTeamsNamesHaveSpecifiedWord(String letter){
		
		Query<Team> query = this.createQuery().retrievedFields(true, "name")
											  .field("name").containsIgnoreCase(letter)
											  .order("name");
		MongoDBUseful.showQueryExplain(query);
		
		return this.find(query).asList();
	}
	
	public List<Team> findTeamsNearTheTeam(Team team, int maxDistanceInkilometers){
		List<Address> addresses = addressDAO.findAddressesNearTheAddress(team.getAddress(), maxDistanceInkilometers);
		
		Query<Team> query = this.createQuery().field("address")
											  .in(addresses)
											  .order("name");
		MongoDBUseful.showQueryExplain(query);
		
		return this.find(query).asList();
	}
	
	public Iterator<TeamResult> findTeamsWithAtLeastHonors(int numberOfHonors){
		Iterator<TeamResult> aggregate= this.getDatastore().createAggregation(Team.class)
						   .unwind("honors")
						   .unwind("honors.years")
						   .group("name", grouping("count", new Accumulator("$sum", 1)))
						   .match(this.getDatastore().createQuery(TeamResult.class).field("count").greaterThanOrEq(numberOfHonors))
						   .sort(new Sort("count",-1),ascending("name"))
						   .aggregate(TeamResult.class);
		return aggregate;
	}
	
	public Iterator<TeamResult> findTeamWithHonorsInTheChampionship(String championshipName){
		
		Iterator<TeamResult> aggregate = this.getDatastore().createAggregation(Team.class)
						   .unwind("honors")
						   .match(this.createQuery().field("honors.championshipName").equal(championshipName))
						   .unwind("honors.years")
						   .group("name", grouping("count", new Accumulator("$sum", 1)))
						   .sort(new Sort("count",-1),ascending("name"))
						   .aggregate(TeamResult.class);
						
		return aggregate;
		
	}
	
	
}
