package com.ryanair.flights;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanair.flights.controller.IRoutesController;
import com.ryanair.flights.model.Route;

public class ItinerariesFinderTest {
	private ItinerariesFinder itinerariesFinder;

	public ItinerariesFinderTest() {
		itinerariesFinder = new ItinerariesFinder(new IRoutesController() {
			@Override
			public Set<Route> getRoutes() {
				InputStream routesFile = getClass().getClassLoader().getResourceAsStream("routes.json");
				try {
					Route[] routes = new ObjectMapper().readValue(routesFile, Route[].class);
					return Arrays.asList(routes).stream().collect(Collectors.toSet());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Test
	public void testGetItineraries() {
		Set<List<String>> itineraries = itinerariesFinder.getItineraries("DUB", "WRO");
		Assert.assertEquals(31, itineraries.size());
	}
}
