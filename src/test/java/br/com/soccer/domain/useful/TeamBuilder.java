package br.com.soccer.domain.useful;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mongodb.morphia.geo.GeoJson;

import br.com.soccer.domain.entity.Address;
import br.com.soccer.domain.entity.City;
import br.com.soccer.domain.entity.Honor;
import br.com.soccer.domain.entity.Team;
import br.com.soccer.domain.repository.AddressDAO;
import br.com.soccer.domain.repository.CityDAO;
import br.com.soccer.domain.repository.TeamDAO;

public class TeamBuilder {
	
	private CityDAO cityDAO = new CityDAO();
	private AddressDAO addressDAO = new AddressDAO();
	private TeamDAO teamDAO = new TeamDAO();
	
	private City getSaoPaulo() {
		Optional<City> saoPauloOptional = cityDAO.findCity("São Paulo");
		City saoPaulo = new City("São Paulo","SP","BR");
		if(!saoPauloOptional.isPresent()){
			cityDAO.save(saoPaulo);
		}
		
		return saoPauloOptional.orElse(saoPaulo);
	}

	private City getSantos() {
		Optional<City> santosOptional = cityDAO.findCity("Santos");
		City santos =  new City("Santos","SP","BR");
		if(!santosOptional.isPresent()){
			cityDAO.save(santos);
		}
		
		return santosOptional.orElse(santos);
	}

	private City getBeloHorizonte() {
		Optional<City> beloHorizonteOptional = cityDAO.findCity("Belo Horizonte");
		City beloHorizonte = new City("Belo Horizonte","MG","BR");
		if(!beloHorizonteOptional.isPresent()){
			cityDAO.save(beloHorizonte);
		}
		
		return beloHorizonteOptional.orElse(beloHorizonte);
	}

	public Team getCorinthiansTeam() throws ParseException{
		/**address**/
		Address corinthiansAddress = new Address(GeoJson.point(-23.5215729,-46.5707998),"03087-000",777,"R. São Jorge",getSaoPaulo());
		addressDAO.save(corinthiansAddress);
		
		/**Champion List**/
		Honor mundial = new Honor("Copa do Mundo de Clubes da FIFA", Arrays.asList(2000,2012));
		Honor libertadores = new Honor("Copa Libertadores da América", Arrays.asList(2012));
		Honor brasileiro = new Honor("Campeonato Brasileiro", Arrays.asList(1990, 1998, 1999, 2005, 2011));
		
		List<Honor> listaChampion = Arrays.asList(mundial,libertadores,brasileiro);
		
		/**Team**/
		Team corinthians = new Team("Sport Club Corinthians Paulista",LocalDate.of(1910, Month.SEPTEMBER, 01),Arrays.asList("Timão", "Todo Poderoso", "Time do Povo"),corinthiansAddress,listaChampion);
		teamDAO.save(corinthians);
		
		return corinthians;
	}
	
	public Team getSaoPauloTeam() throws ParseException{
		/**address**/
		Address saoPauloAddress = new Address(GeoJson.point(-23.6,-46.719722),"05653-070",1,"Praça Roberto Gomes",getSaoPaulo());
		addressDAO.save(saoPauloAddress);
		
		/**Champion List**/
		Honor mundial = new Honor("Copa do Mundo de Clubes da FIFA", Arrays.asList(2005));
		Honor libertadores = new Honor("Copa Libertadores da América", Arrays.asList(1992, 1993, 2005));
		Honor brasileiro = new Honor("Campeonato Brasileiro", Arrays.asList(1977, 1986, 1991, 2006, 2007, 2008));
		
		List<Honor> listaChampion = Arrays.asList(mundial,libertadores,brasileiro);
		
		/**Team**/
		Team saoPauloTeam = new Team("São Paulo Futebol Clube",LocalDate.of(1930, Month.JANUARY, 25),Arrays.asList("Tricolor do Morumbi", "Tricolor Paulista"),saoPauloAddress,listaChampion);
		teamDAO.save(saoPauloTeam);
		
		return saoPauloTeam;
	}
	
	public Team getPalmeirasTeam() throws ParseException{
		/**address**/
		Address palmeirasAddress = new Address(GeoJson.point(-23.5282885,-46.6795043),"05005-000",1840,"Rua Turiaçu",getSaoPaulo());
		addressDAO.save(palmeirasAddress);
		
		/**Champion List**/
		Honor copaBrasil = new Honor("Copa do Brasil", Arrays.asList(1998,2012));
		Honor libertadores = new Honor("Copa Libertadores da América", Arrays.asList(1999));
		Honor brasileiro = new Honor("Campeonato Brasileiro", Arrays.asList(1960,1967, 1967, 1969, 1972, 1973, 1993, 1994));
		
		List<Honor> listaChampion = Arrays.asList(copaBrasil,libertadores,brasileiro);
		
		/**Team**/
		Team palmeirasTeam = new Team("Sociedade Esportiva Palmeiras",LocalDate.of(1914, Month.AUGUST, 26),Arrays.asList("Alviverde", "Palestra"),palmeirasAddress,listaChampion);
		teamDAO.save(palmeirasTeam);
		
		return palmeirasTeam;
	}
	
	public Team getSantosTeam() throws ParseException{
		/**address**/
		Address santosAddress = new Address(GeoJson.point(-23.9426996,-46.3390097),"11075-110",1,"Rua Francisco Manoel",getSantos());
		addressDAO.save(santosAddress);
		
		/**Champion List**/
		Honor copaBrasil = new Honor("Copa do Brasil", Arrays.asList(2010));
		Honor libertadores = new Honor("Copa Libertadores da América", Arrays.asList(1962, 1963,2011));
		Honor brasileiro = new Honor("Campeonato Brasileiro", Arrays.asList(1961, 1962, 1963, 1968, 2002, 2004));
		
		List<Honor> listaChampion = Arrays.asList(copaBrasil,libertadores,brasileiro);
		
		/**Team**/
		Team santosTeam = new Team("Santos Futebol Clube",LocalDate.of(1912, Month.APRIL, 14),Arrays.asList("Peixe","Alvinegro Praiano","Alvinegro da Vila"),santosAddress,listaChampion);
		teamDAO.save(santosTeam);
		
		return santosTeam;
	}
	
	public Team getAtleticoMineiroTeam() throws ParseException{
		/**address**/
		Address atleticoMineiroAddress = new Address(GeoJson.point(-19.927336,-43.946708),"30180-112",1516,"Av. Olegário Maciel",getBeloHorizonte());
		addressDAO.save(atleticoMineiroAddress);
		
		/**Champion List**/
		Honor copaBrasil = new Honor("Copa do Brasil", Arrays.asList(2014));
		Honor libertadores = new Honor("Copa Libertadores da América", Arrays.asList(2013));
		Honor brasileiro = new Honor("Campeonato Brasileiro", Arrays.asList(1971));
		
		List<Honor> listaChampion = Arrays.asList(copaBrasil,libertadores,brasileiro);
		
		/**Team**/
		Team atleticoMineiroTeam = new Team("Clube Atlético Mineiro",LocalDate.of(1908, Month.MARCH, 25),Arrays.asList("Galo","Galão da Massa","Galo Doido"),atleticoMineiroAddress,listaChampion);
		teamDAO.save(atleticoMineiroTeam);
		
		return atleticoMineiroTeam;
	}
	
	public Team getCruzeiroTeam() throws ParseException{
		/**address**/
		Address cruzeiroAddress = new Address(GeoJson.point(-19.8343266,-44.0041976),"31545-260",250,"Rua Adolfo Lippi Fonseca",getBeloHorizonte());
		addressDAO.save(cruzeiroAddress);
		
		/**Champion List**/
		Honor copaBrasil = new Honor("Copa do Brasil", Arrays.asList(1993, 1996, 2000, 2003));
		Honor libertadores = new Honor("Copa Libertadores da América", Arrays.asList(1976,1997));
		Honor brasileiro = new Honor("Campeonato Brasileiro", Arrays.asList(1966, 2003, 2013, 2014));
		
		List<Honor> listaChampion = Arrays.asList(copaBrasil,libertadores,brasileiro);
		
		/**Team**/
		Team cruzeiroTeam = new Team("Cruzeiro Esporte Clube",LocalDate.of(1921, Month.JANUARY, 2),Arrays.asList("Celeste","La Bestia Negra","Raposa"),cruzeiroAddress,listaChampion);
		teamDAO.save(cruzeiroTeam);
		
		return cruzeiroTeam;
	}
	
	
	

}
