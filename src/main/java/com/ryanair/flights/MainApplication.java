package com.ryanair.flights;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanair.flights.controller.RoutesController;
import com.ryanair.flights.controller.ScheduleController;
import com.ryanair.flights.model.Interconnection;
import com.ryanair.flights.model.Leg;
import com.ryanair.flights.model.Schedule;
import com.ryanair.flights.utils.DateTimeUtils;

@SpringBootApplication
@RestController
public class MainApplication {
	private RoutesController routesController = new RoutesController();
	private ScheduleController scheduleController = new ScheduleController();
	private ItinerariesFinder itinerariesFinder = new ItinerariesFinder(routesController);

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@GetMapping(value = "/ryanair/interconnections")
	public String getInterconnections(
			@RequestParam("departure") String departure,
			@RequestParam("arrival") String arrival,
			@RequestParam("departureDateTime") String departureDateTime,
			@RequestParam("arrivalDateTime") String arrivalDateTime) throws ParseException, JsonProcessingException {

		// possible itineraries based on the airport's IATA codes
		Set<List<String>> itineraries = itinerariesFinder.getItineraries(departure, arrival);

		if (itineraries.isEmpty()) {
			return ""; // no direct nor interconnected flights
		}

		Set<Interconnection> interconnections = new HashSet<>();
		int year = DateTimeUtils.getYear(departureDateTime);
		int month = DateTimeUtils.getMonth(departureDateTime);

		for (List<String> itinerary : itineraries) {
			switch (itinerary.size()) {
			case 2: // direct flights
				Schedule schedule = scheduleController.getSchedule(departure, arrival, year, month);
				if (schedule == null) {
					continue;
				}

				LegFinder directLegFinder = new LegFinder(departure, arrival, departureDateTime, arrivalDateTime);
				Set<Leg> legs = directLegFinder.getLegs(schedule);
				legs.forEach(leg -> interconnections.add(new Interconnection(0, Collections.singletonList(leg))));
				break;
			case 3: // interconnecting flights
				String intermediate = itinerary.get(1);
				Schedule schedule1 = scheduleController.getSchedule(departure, intermediate, year, month);
				Schedule schedule2 = scheduleController.getSchedule(intermediate, arrival, year, month);
				if (schedule1 == null || schedule2 == null) {
					continue;
				}

				LegFinder legFinder = new LegFinder(departure, intermediate, departureDateTime, arrivalDateTime);
				Set<Leg> legs1 = legFinder.getLegs(schedule1);

				// search for possible interconnecting flights
				for (Leg leg1 : legs1) {
					String intermediateDateTime = DateTimeUtils.addTwoHours(leg1.getArrivalDateTime());
					legFinder = new LegFinder(intermediate, arrival, intermediateDateTime, arrivalDateTime);
					Set<Leg> legs2 = legFinder.getLegs(schedule2);
					legs2.forEach(leg2 -> interconnections.add(new Interconnection(1, Arrays.asList(leg1, leg2))));
				}
				break;
			}
		}

		// convert interconnecting flights to JSON
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(interconnections);
	}
}
