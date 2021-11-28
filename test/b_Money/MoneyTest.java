package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, EUR20and43, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(100_00, SEK);
		EUR10 = new Money(10_00, EUR);
		EUR20and43 = new Money(20_43, EUR);
		SEK200 = new Money(200_00, SEK);
		EUR20 = new Money(20_00, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-100_00, SEK);
	}

	@Test
	public void testGetAmount() {
		// checking method getAmount()

		assertEquals(100_00, SEK100.getAmount(), 0);
		assertEquals(10_00, EUR10.getAmount(), 0);
		assertEquals(200_00, SEK200.getAmount(), 0);
		assertEquals(20_00, EUR20.getAmount(), 0);
	}

	@Test
	public void testGetCurrency() {
		// checking names of corresponding currencies to ensure that getCurrency() method
		// works as intended

		assertEquals(SEK.getName(), SEK100.getCurrency().getName());
		assertEquals(SEK.getName(), SEK200.getCurrency().getName());
		assertEquals(SEK.getName(), SEK0.getCurrency().getName());
		assertNotEquals(DKK.getName(), SEK100.getCurrency().getName());
		assertEquals(EUR.getName(), EUR20.getCurrency().getName());
	}

	@Test
	public void testToString() {
		// checking corresponding toString() method

		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("20.0 EUR", EUR20.toString());
		assertEquals("20.43 EUR", EUR20and43.toString());
		assertEquals("0.0 SEK", SEK0.toString());
	}

	@Test
	public void testGlobalValue() {
		// checking universalValue() method

		// UV - universal value
		// 100 SEK == 15 UV (rate 0.15)
		assertEquals(15_00,SEK100.universalValue(), 1);
		// 20.43 EUR == 30.64 UV (rate 1.5)
		assertEquals(30_64,EUR20and43.universalValue(),1);
		// 200 SEK == 30 UV (rate 0.15)
		assertEquals(30_00,SEK200.universalValue(),1);
	}

	@Test
	public void testEqualsMoney() {
		// checking equals() method

		// 10 EUR == 10 EUR
		assertTrue(EUR10.equals(EUR10));
		// 20 EUR != 10 EUR
		assertFalse(EUR20.equals(EUR10));
		// 100 SEK == 10 EUR
		assertTrue(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		// checking add() method

		// 10 EUR + 20 EUR == 30 EUR
		assertEquals(30_00,EUR10.add(EUR20).getAmount(),0);
		// 100 SEK + 0 EUR == 100 SEK
		assertEquals(100_00,SEK100.add(EUR0).getAmount(),0);
		// 100 SEK + 10 EUR == 200 SEK
		assertEquals(200_00,SEK100.add(EUR10).getAmount(),0);
	}

	@Test
	public void testSub() {
		// checking sub() method

		// 20 EUR - 10 EUR == 10 EUR
		assertEquals(10_00,EUR20.sub(EUR10).getAmount(),0);
		// 10 EUR - 20 EUR == -10 EUR
		assertEquals(-10_00,EUR10.sub(EUR20).getAmount(),0);
		// 100 SEK - 0 EUR == 100 SEK
		assertEquals(100_00,SEK100.sub(EUR0).getAmount(),0);
		// 100 SEK - 10 EUR == 0 SEK
		assertEquals(0,SEK100.sub(EUR10).getAmount(),0);
		// checking currency name after calling function sub()
		assertEquals("SEK",SEK100.sub(EUR10).getCurrency().getName());
	}

	@Test
	public void testIsZero() {
		// checking isZero() method

		assertTrue(EUR0.isZero());
		assertTrue(SEK0.isZero());
		assertFalse(SEK200.isZero());
		assertFalse(SEK100.isZero());
		assertFalse(EUR20and43.isZero());
	}

	@Test
	public void testNegate() {
		// checking negate() method

		// negate 100 SEK == -100 SEK
		assertEquals(-100_00, SEK100.negate().getAmount(), 0);
		// negate 200 SEK == -200 SEK
		assertEquals(-200_00, SEK200.negate().getAmount(), 0);
		// negate 20.43 EUR == -20.43 EUR
		assertEquals(-20_43, EUR20and43.negate().getAmount(), 0);
	}

	@Test
	public void testCompareTo() {
		// checking compareTo() method

		// 100 SEK == EUR 10
		assertEquals(0, SEK100.compareTo(EUR10));
		// 100 SEK < EUR 20
		assertEquals(-1, SEK100.compareTo(EUR20));
		// 100 SEK > EUR 0
		assertEquals(1, SEK100.compareTo(EUR0));
		// 20.43 SEK > EUR 20
		assertEquals(1, EUR20and43.compareTo(EUR20));
	}
}
