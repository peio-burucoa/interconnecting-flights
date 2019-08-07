package com.ryanair.flights.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {
	private String airportFrom; // departure airport IATA code
	private String airportTo; // arrival airport IATA code
	private String connectingAirport; // connecting airport IATA code
	private boolean newRoute;
	private boolean seasonalRoute;
	private String operator;
	private String group;

	public String getAirportFrom() {
		return airportFrom;
	}
	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}
	public String getAirportTo() {
		return airportTo;
	}
	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}
	public String getConnectingAirport() {
		return connectingAirport;
	}
	public void setConnectingAirport(String connectingAirport) {
		this.connectingAirport = connectingAirport;
	}
	public boolean isNewRoute() {
		return newRoute;
	}
	public void setNewRoute(boolean newRoute) {
		this.newRoute = newRoute;
	}
	public boolean isSeasonalRoute() {
		return seasonalRoute;
	}
	public void setSeasonalRoute(boolean seasonalRoute) {
		this.seasonalRoute = seasonalRoute;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
}
