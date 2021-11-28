package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.2);
		EUR = new Currency("EUR", 1.5);
		NOK = new Currency("NOK", 1.3);
	}

	@Test
	public void testGetName() {
		// checking getName() method

		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		// checking getRate() method

		assertEquals(Double.valueOf(0.15), SEK.getRate());
		assertEquals(Double.valueOf(0.20), DKK.getRate());
		assertEquals(Double.valueOf(1.5), EUR.getRate());
	}
	
	@Test
	public void testSetRate() {
		// checking setRate() method

		assertEquals(Double.valueOf(0.15), SEK.getRate());
		SEK.setRate(0.20);
		assertEquals(Double.valueOf(0.20), SEK.getRate());

		assertEquals(Double.valueOf(0.20), DKK.getRate());
		DKK.setRate(0.25);
		assertEquals(Double.valueOf(0.25), DKK.getRate());

		assertEquals(Double.valueOf(1.5), EUR.getRate());
		EUR.setRate(1.4);
		assertEquals(Double.valueOf(1.4), EUR.getRate());
	}
	
	@Test
	public void testGlobalValue() {
		// checking universalValue() method

		SEK.setRate(0.013);
		assertEquals(Integer.valueOf(0), SEK.universalValue(0));
		assertEquals(Integer.valueOf(130), SEK.universalValue(100_00));
		DKK.setRate(0.0000165);
		assertEquals(Integer.valueOf(0), DKK.universalValue(0));
		assertEquals(Integer.valueOf(495), DKK.universalValue(300000_00));
		EUR.setRate(1.3);
		assertEquals(Integer.valueOf(0), EUR.universalValue(0));
		assertEquals(Integer.valueOf(26390), EUR.universalValue(203_00));
	}
	
	@Test
	public void testValueInThisCurrency() {
		// checking valueInThisCurrency() method

		// 100 NOK == 86.66 EUR
		assertEquals(8666, EUR.valueInThisCurrency(100_00, NOK), 1);
		// 2005 DKK == 2673.33 SEK
		assertEquals(267333, SEK.valueInThisCurrency(2005_00, DKK), 1);
		// 15 EUR == 112.50 DKK
		assertEquals(11250, DKK.valueInThisCurrency(15_00, EUR), 1);
	}

}
