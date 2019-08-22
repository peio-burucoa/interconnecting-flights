package com.ryanair.flights.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
	private static final String TIME_PATTERN = "HH:mm";
	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern(TIME_PATTERN);

	public static int getHour(String time) {
		LocalTime localTime = LocalTime.parse(time, TIME_FORMAT);
		return localTime.getHour();
	}

	public static int getMinute(String time) {
		LocalTime localTime = LocalTime.parse(time, TIME_FORMAT);
		return localTime.getMinute();
	}
}
