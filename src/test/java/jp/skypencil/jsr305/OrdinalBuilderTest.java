package jp.skypencil.jsr305;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrdinalBuilderTest {

	@Test
	public void testOrdinal() {
		assertEquals("0th", OrdinalBuilder.valueOf(0));
		assertEquals("1st", OrdinalBuilder.valueOf(1));
		assertEquals("2nd", OrdinalBuilder.valueOf(2));
		assertEquals("3rd", OrdinalBuilder.valueOf(3));
		assertEquals("4th", OrdinalBuilder.valueOf(4));
		assertEquals("11th", OrdinalBuilder.valueOf(11));
		assertEquals("12th", OrdinalBuilder.valueOf(12));
	}

}
