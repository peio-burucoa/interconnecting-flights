package com.ryanair.flights.controller;

import com.ryanair.flights.model.Schedule;

public interface IScheduleController {

	public Schedule getSchedule(String departure, String arrival, int year, int month);

}
