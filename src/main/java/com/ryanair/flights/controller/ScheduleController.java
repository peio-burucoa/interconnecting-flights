package com.ryanair.flights.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.model.Schedule;

public class ScheduleController implements IScheduleController {
	private static final String SCHEDULES_URL = "https://services-api.ryanair.com/timtbl/3/schedules/{departure}/{arrival}/years/{year}/months/{month}";

	private RestTemplate restTemplate = new RestTemplate();

	public Schedule getSchedule(String departure, String arrival, int year, int month) {
		Map<String, Object> uriVariables = new HashMap<>();
		uriVariables.put("departure", departure);
		uriVariables.put("arrival", arrival);
		uriVariables.put("year", year);
		uriVariables.put("month", month);
		try {
			return restTemplate.getForObject(SCHEDULES_URL, Schedule.class, uriVariables);
		} catch (RestClientException e) {
			return null;
		}
	}
}
