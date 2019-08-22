package com.ryanair.flights;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class LegFinderTest {

	private LegFinder legFinder = new LegFinder("DUB", "WRO", "2019-09-02T12:30", "2019-09-02T20:30");

	@Test
	public void testIsFlightDepartureTimeValid() throws ParseException {
		Assert.assertTrue(legFinder.isFlightDepartureTimeValid("13:00"));

		Assert.assertTrue(legFinder.isFlightDepartureTimeValid("12:45"));

		Assert.assertFalse(legFinder.isFlightDepartureTimeValid("12:15"));

		Assert.assertFalse(legFinder.isFlightDepartureTimeValid("12:00"));
	}

	@Test
	public void testIsFlightArrivalTimeValid() throws ParseException {
		Assert.assertTrue(legFinder.isFlightArrivalTimeValid("20:00"));

		Assert.assertTrue(legFinder.isFlightArrivalTimeValid("20:15"));

		Assert.assertFalse(legFinder.isFlightArrivalTimeValid("20:45"));

		Assert.assertFalse(legFinder.isFlightArrivalTimeValid("21:00"));
	}
}
