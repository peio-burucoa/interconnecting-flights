package com.ryanair.flights;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ryanair.flights.controller.IRoutesController;
import com.ryanair.flights.model.Route;

public class ItinerariesFinder {
	private IRoutesController routesController;

	public ItinerariesFinder(IRoutesController routesController) {
		this.routesController = routesController;
	}

	public Set<List<String>> getItineraries(String departure, String arrival) {
		Set<Route> routes = routesController.getRoutes();
		Set<List<String>> itineraries = new HashSet<>();
		for (Route route : routes) {
			if (route.getAirportFrom().equals(departure)) {
				if (route.getAirportTo().equals(arrival)) {
					itineraries.add(Arrays.asList(departure, arrival));
				} else {
					String intermediate = route.getAirportTo();
					for (Route route2 : routes) {
						if (route2.getAirportFrom().equals(intermediate) && route2.getAirportTo().equals(arrival)) {
							itineraries.add(Arrays.asList(departure, intermediate, arrival));
						}
					}
				}
			}
		}
		return itineraries;
	}
}
