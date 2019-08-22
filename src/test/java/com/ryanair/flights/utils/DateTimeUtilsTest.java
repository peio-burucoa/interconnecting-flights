package com.ryanair.flights.utils;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class DateTimeUtilsTest {

	@Test
	public void testGetYear() throws ParseException {
		Assert.assertEquals(2019, DateTimeUtils.getYear("2019-03-14T23:50"));
	}

	@Test(expected = ParseException.class)
	public void testGetYear_ParseException() throws ParseException {
		DateTimeUtils.getYear("14-03-2019");
	}

	@Test
	public void testGetMonth() throws ParseException {
		Assert.assertEquals(3, DateTimeUtils.getMonth("2019-03-14T23:50"));
	}

	@Test(expected = ParseException.class)
	public void testGetMonth_ParseException() throws ParseException {
		DateTimeUtils.getMonth("14-03-2019");
	}

	@Test
	public void testGetDayOfMonth() throws ParseException {
		Assert.assertEquals(14, DateTimeUtils.getDayOfMonth("2019-03-14T23:50"));
	}

	@Test(expected = ParseException.class)
	public void testGetDayOfMonth_ParseException() throws ParseException {
		DateTimeUtils.getDayOfMonth("14-03-2019");
	}

	@Test
	public void testGetHour() throws ParseException {
		Assert.assertEquals(23, DateTimeUtils.getHour("2019-03-14T23:50"));
	}

	@Test(expected = ParseException.class)
	public void testGetHour_ParseException() throws ParseException {
		DateTimeUtils.getHour("23:50");
	}

	@Test
	public void testGetMinute() throws ParseException {
		Assert.assertEquals(50, DateTimeUtils.getMinute("2019-03-14T23:50"));
	}

	@Test(expected = ParseException.class)
	public void testGetMinute_ParseException() throws ParseException {
		DateTimeUtils.getMinute("23:50");
	}
}
