package com.ryanair.flights;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ryanair.flights.model.Day;
import com.ryanair.flights.model.Flight;
import com.ryanair.flights.model.Interconnection;
import com.ryanair.flights.model.Leg;
import com.ryanair.flights.model.Schedule;
import com.ryanair.flights.utils.DateTimeUtils;
import com.ryanair.flights.utils.TimeUtils;

public class DirectFlightSearcher {

	private String departure;
	private String arrival;
	private String departureDateTime;
	private String arrivalDateTime;

	public DirectFlightSearcher(String departure, String arrival, String departureDateTime, String arrivalDateTime) {
		this.departure = departure;
		this.arrival = arrival;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	public List<Interconnection> search(Schedule schedule) throws ParseException {
		List<Interconnection> interconnections = new ArrayList<>();

		// assuming departure day = arrival day
		int dayOfMonth = DateTimeUtils.getDayOfMonth(departureDateTime);

		for (Day day : schedule.getDays()) {
			if (day.getDay() == dayOfMonth) {
				for (Flight flight : day.getFlights()) {
					if (isFlightDepartureTimeValid(flight.getDepartureTime()) && isFlightArrivalTimeValid(flight.getArrivalTime())) {
						String flightDepartureDateTime = DateTimeUtils.getDate(departureDateTime, flight.getDepartureTime());
						String flighArrivalDateTime = DateTimeUtils.getDate(arrivalDateTime, flight.getArrivalTime());
						Leg leg = new Leg(departure, arrival, flightDepartureDateTime, flighArrivalDateTime);
						interconnections.add(new Interconnection(0, Collections.singletonList(leg)));
					}
				}
			}
		}

		return interconnections;
	}

	public boolean isFlightDepartureTimeValid(String flightDepartureTime) throws ParseException {
		int flightDepartureHour = TimeUtils.getHour(flightDepartureTime);
		int departureHour = DateTimeUtils.getHour(departureDateTime);
		if (flightDepartureHour > departureHour) {
			return true;
		}
		if (flightDepartureHour == departureHour) {
			return TimeUtils.getMinute(flightDepartureTime) >= DateTimeUtils.getMinute(departureDateTime);
		}
		return false;
	}

	public boolean isFlightArrivalTimeValid(String flightArrivalTime) throws ParseException {
		int flightArrivalHour = TimeUtils.getHour(flightArrivalTime);
		int arrivalHour = DateTimeUtils.getHour(arrivalDateTime);
		if (flightArrivalHour < arrivalHour) {
			return true;
		}
		if (flightArrivalHour == arrivalHour) {
			return TimeUtils.getMinute(flightArrivalTime) <= DateTimeUtils.getMinute(arrivalDateTime);
		}
		return false;
	}
}
