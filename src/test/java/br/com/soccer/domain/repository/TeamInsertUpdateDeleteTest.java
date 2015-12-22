package br.com.soccer.domain.repository;

import java.text.ParseException;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.query.UpdateResults;

import br.com.soccer.domain.entity.Address;
import br.com.soccer.domain.entity.City;
import br.com.soccer.domain.entity.Honor;
import br.com.soccer.domain.entity.Team;
import br.com.soccer.domain.useful.TestCaseSetup;

public class TeamInsertUpdateDeleteTest extends TestCaseSetup {

	private Team corinthians;
	
	@Before
	public void initializeTest() throws ParseException {
		super.initializeTest();

		corinthians = teamBuilder.getCorinthiansTeam();
	}

	@Test
	public void testInsert(){

		Team teamActual = teamDAO.find().get();
		Address addressActual = addressDAO.find().get();
		City cityActual = cityDAO.find().get();

		Assert.assertEquals(corinthians, teamActual);
		Assert.assertEquals(corinthians.getNicknames().size(), teamActual
				.getNicknames().size());
		Assert.assertEquals(corinthians.getHonors().size(), teamActual
				.getHonors().size());

		Assert.assertEquals(corinthians.getAddress(), addressActual);

		Assert.assertEquals(corinthians.getAddress().getCity(), cityActual);

	}

	@Test()
	public void testUpdate(){
		corinthians = teamDAO.find().get();
		corinthians.getNicknames().add("Timao EOOO");

		teamDAO.save(corinthians);

		Team teamActual = teamDAO.find().get();

		Assert.assertEquals(corinthians.getNicknames().size(), teamActual
				.getNicknames().size());

	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testConcurrentUpdate(){
		corinthians = teamDAO.find().get();
		corinthians.getNicknames().add("Timao EOOO");
		
		Team corinthians2 = teamDAO.find().get();
		corinthians2.getNicknames().add("Vai Corinthians");

		teamDAO.save(corinthians);
		teamDAO.save(corinthians2);
	}

	@Test()
	public void testUpdateAddNewHonor(){
		UpdateResults results = teamDAO.addNewHonor(corinthians.getName(),
				new Honor("Recopa Sul-Americana", Arrays.asList(2013)));

		Team teamActual = teamDAO.find().get();

		Assert.assertEquals(1, results.getUpdatedCount());
		Assert.assertTrue(results.getUpdatedExisting());
		Assert.assertEquals(4, teamActual.getHonors().size());

	}

	@Test()
	public void testUpdateAddNewYearInTheHonor(){
		boolean result = teamDAO.addNewYearInTheHonor(corinthians.getName(),
				"Campeonato Brasileiro", 2015);

		Team teamActual = teamDAO.find().get();
		int index = teamActual.getHonors().indexOf(
				new Honor("Campeonato Brasileiro"));

		Assert.assertTrue(result);
		Assert.assertEquals(6, teamActual.getHonors().get(index).getYears()
				.size());

	}

	@Test
	public void testDelete() {
		teamDAO.delete(corinthians);
		
		Team teamActual = teamDAO.find().get();
		
		Assert.assertNull(teamActual);
	}
	
	@Test
	public void testDeleteCompleteByTeamName() {
		boolean result = teamDAO.deleteComplete(corinthians.getName());
		
		Team teamActual = teamDAO.find().get();
		Address addressActual = addressDAO.find().get();
		
		Assert.assertTrue(result);
		Assert.assertNull(teamActual);
		Assert.assertNull(addressActual);
	}
	
	@Test
	public void testDeleteTeamsCompleteByStateCode() {
		int result = teamDAO.deleteTeamsComplete(corinthians.getAddress().getCity().getStateCode());
		
		Team teamActual = teamDAO.find().get();
		Address addressActual = addressDAO.find().get();
		
		Assert.assertNull(teamActual);
		Assert.assertNull(addressActual);
		Assert.assertEquals(1, result);
	}

}
