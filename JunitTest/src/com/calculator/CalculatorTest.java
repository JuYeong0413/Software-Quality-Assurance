package com.calculator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.calculator.Calculator;

public class CalculatorTest {

	@Test
	public void testMultiple() {
//		fail("Not yet implemented");
		Calculator cal = new Calculator();
		assertEquals(30, cal.multiple (6, 5));
	}

}
