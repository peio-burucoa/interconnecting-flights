package com.ryanair.flights;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.model.Route;
import com.ryanair.flights.model.Schedule;
import com.ryanair.flights.utils.DateUtils;

@SpringBootApplication
@RestController
public class MainApplication {
	private static final String RYANAIR = "RYANAIR";
	private static final String SCHEDULES_URL = "https://services-api.ryanair.com/timtbl/3/schedules/{departure}/{arrival}/years/{year}/months/{month}";
	private static final String ROUTES_URL = "https://services-api.ryanair.com/locate/3/routes";

	// private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@GetMapping(value = "/ryanair/interconnections")
	public String getInterconnections(
			@RequestParam("departure") String departure,
			@RequestParam("arrival") String arrival,
			@RequestParam("departureDateTime") String departureDateTime,
			@RequestParam("arrivalDateTime") String arrivalDateTime) throws ParseException {
		RestTemplate restTemplate = new RestTemplate();

		// available routes based on the airport's IATA codes
		Set<Route> routes = getRoutes(restTemplate, departure, arrival);
		if (routes.isEmpty()) {
			// TODO
		}

		// available flights for given departure/arrival airport IATA codes, a year and a month
		int year = DateUtils.getYear(departureDateTime);
		int month = DateUtils.getMonth(departureDateTime);
		Schedule schedule = getSchedule(restTemplate, departure, arrival, year, month);

		// TODO: search for possible interconnecting flights

		return "Departure: " + departure + ", Arrival: " + arrival + ", Number of routes: " + routes.size();
	}
	
	private Set<Route> getRoutes(RestTemplate restTemplate, String departure, String arrival) {
		Route[] routes = restTemplate.getForObject(ROUTES_URL, Route[].class);
		
		Predicate<Route> predicate = route -> route.getConnectingAirport() == null // only routes with connectingAirport set to null should be used
			&& route.getOperator().equals(RYANAIR) // only routes with operator set to RYANAIR should be used
			&& route.getAirportFrom().equals(departure)
			&& route.getAirportTo().equals(arrival);

		return Arrays.asList(routes).stream()
			.filter(predicate)
			.collect(Collectors.toSet());
	}

	private Schedule getSchedule(RestTemplate restTemplate, String departure, String arrival, int year, int month) {
		Map<String, Object> uriVariables = new HashMap<>();
		uriVariables.put("departure", departure);
		uriVariables.put("arrival", arrival);
		uriVariables.put("year", year);
		uriVariables.put("month", month);
		return restTemplate.getForObject(SCHEDULES_URL, Schedule.class, uriVariables);
	}
}