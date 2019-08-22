package com.ryanair.flights.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.model.Route;

public class RoutesController implements IRoutesController {
	private static final String RYANAIR = "RYANAIR";
	private static final String ROUTES_URL = "https://services-api.ryanair.com/locate/3/routes";

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public Set<Route> getRoutes() {
		Route[] routes = restTemplate.getForObject(ROUTES_URL, Route[].class);
		Predicate<Route> predicate = route -> route.getConnectingAirport() == null // only routes with connectingAirport set to null should be used
			&& route.getOperator().equals(RYANAIR); // only routes with operator set to RYANAIR should be used

		return Arrays.asList(routes).stream()
			.filter(predicate)
			.collect(Collectors.toSet());
	}
}
