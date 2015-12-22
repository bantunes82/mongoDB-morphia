package br.com.soccer.domain.repository;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.soccer.domain.entity.Team;
import br.com.soccer.domain.entity.TeamResult;
import br.com.soccer.domain.useful.TestCaseSetup;

public class TeamFindAggegationTest extends TestCaseSetup {

	private Team corinthians;
	private Team saoPaulo;
	private Team santos;
	private Team palmeiras;
	private Team atleticoMineiro;
	private Team cruzeiro;

	@Before
	public void initializeTest() throws ParseException {
		super.initializeTest();

		corinthians = teamBuilder.getCorinthiansTeam();
		saoPaulo = teamBuilder.getSaoPauloTeam();
		santos = teamBuilder.getSantosTeam();
		palmeiras = teamBuilder.getPalmeirasTeam();
		atleticoMineiro = teamBuilder.getAtleticoMineiroTeam();
		cruzeiro = teamBuilder.getCruzeiroTeam();

	}

	@Test
	public void findTeamsByChampionshipName() {
		List<Team> teams = teamDAO.findTeams("Copa do Mundo de Clubes da FIFA");

		Assert.assertEquals(2, teams.size());
		Assert.assertEquals(corinthians.getName(), teams.get(0).getName());
		Assert.assertEquals(saoPaulo.getName(), teams.get(1).getName());
	}

	@Test
	public void findTeamsByFoundationDate() throws ParseException {
		List<Team> teams = teamDAO.findTeamsBetweenFoundationDate(LocalDate.of(1910, Month.JANUARY, 01),LocalDate.of(1920, Month.DECEMBER, 31));

		Assert.assertEquals(3, teams.size());
		Assert.assertEquals(corinthians.getName(), teams.get(0).getName());
		Assert.assertEquals(santos.getName(), teams.get(1).getName());
		Assert.assertEquals(palmeiras.getName(), teams.get(2).getName());
	}

	@Test
	public void findTeamByName() {
		Team teamActual = teamDAO.find(corinthians.getName()).get();

		Assert.assertEquals(corinthians.getName(), teamActual.getName());
	}

	@Test
	public void findTeamsNamesHaveSpecifiedWord() {
		List<Team> teams = teamDAO.findTeamsNamesHaveSpecifiedWord("thi");

		Assert.assertEquals(corinthians.getName(), teams.get(0).getName());
		Assert.assertNull(teams.get(0).getAddress());
		Assert.assertNull(teams.get(0).getFoundation());
		Assert.assertNull(teams.get(0).getHonors());
		Assert.assertNull(teams.get(0).getNicknames());
		Assert.assertNotNull(teams.get(0).getId());
		Assert.assertNull(teams.get(0).getVersion());
	}

	@Test
	public void findTeamsNearTheTeam() {
		List<Team> teams = teamDAO.findTeamsNearTheTeam(corinthians, 50);

		Assert.assertEquals(3, teams.size());
		Assert.assertEquals(palmeiras, teams.get(0));
		Assert.assertEquals(corinthians, teams.get(1));
		Assert.assertEquals(saoPaulo, teams.get(2));

	}

	@Test
	public void findTeamsWithAtLeastHonors() {
		Iterator<TeamResult> iterator = teamDAO.findTeamsWithAtLeastHonors(5);

		int count = 0;
		while (iterator.hasNext()) {
			TeamResult teamResult = iterator.next();
			switch (count) {
			case 0:
				Assert.assertEquals(palmeiras.getName(), teamResult.getName());
				Assert.assertEquals(11,teamResult.getCount());
				break;
			case 1:
				Assert.assertEquals(cruzeiro.getName(), teamResult.getName());
				Assert.assertEquals(10,teamResult.getCount());
				break;
			case 2:
				Assert.assertEquals(santos.getName(), teamResult.getName());
				Assert.assertEquals(10,teamResult.getCount());
				break;
			case 3:
				Assert.assertEquals(saoPaulo.getName(), teamResult.getName());
				Assert.assertEquals(10,teamResult.getCount());
				break;
			case 4:
				Assert.assertEquals(corinthians.getName(), teamResult.getName());
				Assert.assertEquals(8,teamResult.getCount());
				break;
			default:
				Assert.fail("Should only find 5 elements");
			}

			count++;
		}

	}
	
	@Test
	public void findTeamWithHonorsInTheChampionship(){
		String honor = "Copa do Mundo de Clubes da FIFA";
		
		Iterator<TeamResult> iterator = teamDAO.findTeamWithHonorsInTheChampionship(honor);
		int count = 0;
		while (iterator.hasNext()) {
			TeamResult teamResult = iterator.next();
			switch (count) {
			case 0:
				Assert.assertEquals(corinthians.getName(), teamResult.getName());
				Assert.assertEquals(2,teamResult.getCount());
				break;
			case 1:
				Assert.assertEquals(saoPaulo.getName(), teamResult.getName());
				Assert.assertEquals(1,teamResult.getCount());
				break;
			default:
				Assert.fail("Should only find 2 elements");
			}

			count++;
		}
	}

}
