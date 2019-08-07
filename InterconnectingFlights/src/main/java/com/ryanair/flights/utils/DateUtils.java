package com.ryanair.flights.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	// regex: "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}"
	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);

	public static int getYear(String dateTime) throws ParseException {
		Date date = DATE_FORMAT.parse(dateTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth(String dateTime) throws ParseException {
		Date date = DATE_FORMAT.parse(dateTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
}
