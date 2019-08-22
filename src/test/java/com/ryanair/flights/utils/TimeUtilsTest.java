package com.ryanair.flights.utils;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class TimeUtilsTest {

	@Test
	public void testGetHour() throws ParseException {
		Assert.assertEquals(23, TimeUtils.getHour("23:50"));
	}

	@Test
	public void testGetMinute() throws ParseException {
		Assert.assertEquals(50, TimeUtils.getMinute("23:50"));
	}
}
