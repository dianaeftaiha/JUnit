package com.example.demo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DemoApplicationTests {

	Calculator calculator = new Calculator();

	@Test
	void testItAddsNumbers() {
		int a = 20;
		int b = 30;

		int sum = calculator.add(a, b);

		int expected = 50;
		Assert.assertEquals(sum, expected);
	}

	class Calculator {
		public int add (int a, int b) {
			return a + b;
		}
	}
}
