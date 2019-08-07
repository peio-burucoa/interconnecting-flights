package com.ryanair.flights.utils;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testGetYear() throws ParseException {
		Assert.assertEquals(2019, DateUtils.getYear("2019-03-14T21:00"));
	}

	@Test(expected = ParseException.class)
	public void testGetYear_ParseException() throws ParseException {
		DateUtils.getYear("14-03-2019");
	}

	@Test
	public void testGetMonth() throws ParseException {
		Assert.assertEquals(3, DateUtils.getMonth("2019-03-14T21:00"));
	}

	@Test(expected = ParseException.class)
	public void testGetMonth_ParseException() throws ParseException {
		DateUtils.getMonth("14-03-2019");
	}
}
