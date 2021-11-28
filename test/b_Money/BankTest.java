package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		SweBank.openAccount("Ann");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");

		SweBank.deposit("Ulrika", new Money(10000_00, SEK));
		SweBank.deposit("Ann", new Money(10000_00, SEK));
		Nordea.deposit("Bob", new Money(10000_00, SEK));
	}

	@Test
	public void testGetName() {
		// checking getName() method

		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		// checking getCurrency() method

		assertEquals("SEK", SweBank.getCurrency().getName());
		assertEquals("SEK", Nordea.getCurrency().getName());
		assertEquals("DKK", DanskeBank.getCurrency().getName());
	}

	@Test(expected=AccountExistsException.class)
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// checking openAccount() method providing already existing IDs

		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
	}

	@Test(expected=AccountDoesNotExistException.class)
	public void testDeposit() throws AccountDoesNotExistException {
		// checking deposit() method providing unexisting id

		SweBank.deposit("Alice", new Money(10000_00, SEK));
		Nordea.deposit("Philip", new Money(10_00, DKK));
	}

	@Test
	public void testDeposit2() throws AccountDoesNotExistException {
		// checking deposit() method providing existing id

		SweBank.deposit("Ulrika", new Money(10000_00, SEK));
		Nordea.deposit("Bob", new Money(10_00, DKK));
	}

	@Test(expected=AccountDoesNotExistException.class)
	public void testWithdraw() throws AccountDoesNotExistException {
		// checking withdraw() method providing unexisting id

		Nordea.withdraw("Michael", new Money(10, DKK));
		Nordea.withdraw("ALEX", new Money(10, DKK));
	}

	@Test
	public void testWithdraw2() throws AccountDoesNotExistException {
		// checking withdraw() method providing existing id

		SweBank.withdraw("Ulrika", new Money(10_00, SEK));
		assertEquals(9990_00, SweBank.getBalance("Ulrika"), 0);
		Nordea.withdraw("Bob", new Money(10, DKK));
		assertEquals(9999_87, Nordea.getBalance("Bob"), 0);
	}

	@Test(expected=AccountDoesNotExistException.class)
	public void testGetBalance() throws AccountDoesNotExistException {
		// checking getBalance() method providing unexisting id

		Nordea.getBalance("Michael");
	}

	@Test
	public void testGetBalance2() throws AccountDoesNotExistException {
		// checking getBalance() method providing existing id

		assertEquals(10000_00, SweBank.getBalance("Ulrika"), 0);
	}

	@Test(expected=AccountDoesNotExistException.class)
	public void testTransfer() throws AccountDoesNotExistException {
		// checking transfer() method providing unexisting id

		Nordea.transfer("Eric", "Alex", new Money(100_00, SEK));
	}

	@Test
	public void testTransfer2() throws AccountDoesNotExistException {
		// checking transfer() method providing existing id

		assertEquals(10000_00,SweBank.getBalance("Ulrika"),0);
		assertEquals(10000_00,SweBank.getBalance("Ann"),0);
		SweBank.transfer("Ulrika", "Ann", new Money(100_00, SEK));
		assertEquals(9900_00,SweBank.getBalance("Ulrika"),0);
		assertEquals(10100_00,SweBank.getBalance("Ann"),0);
	}



	@Test
	public void testTimedPaymen() throws AccountDoesNotExistException {
		// checking addTimedPayment() for the same bank

		// initial balance checking
		assertEquals(10000_00,SweBank.getBalance("Ulrika"),0);
		assertEquals(10000_00,SweBank.getBalance("Ann"),0);
		// initializing time payment
		SweBank.addTimedPayment("Ulrika", "21", 1, 1, new Money(100_00, SEK), SweBank, "Ann");
		// waiting for 5 ticks, 3 payments had to happen
		for (int i = 0; i < 6; i++) {
			SweBank.tick();
		}
		// checking balances after the ticks
		assertEquals(970000,SweBank.getBalance("Ulrika"),0);
		assertEquals(10300_00,SweBank.getBalance("Ann"),0);
	}

	@Test
	public void testTimedPayment2() throws AccountDoesNotExistException {
		// checking addTimedPayment() for two different banks

		// initial balance checking
		assertEquals(10000_00,SweBank.getBalance("Ulrika"),0);
		assertEquals(10000_00,Nordea.getBalance("Bob"),0);
		// initializing time payment
		SweBank.addTimedPayment("Ulrika", "1", 1, 2, new Money(100_00, SEK), Nordea, "Bob");
		// waiting for 5 ticks, 2 payments had to happen
		for (int i = 0; i < 5; i++) {
			SweBank.tick();
		}
		// checking balances after the ticks
		assertEquals(980000,SweBank.getBalance("Ulrika"),0);
		assertEquals(10200_00,Nordea.getBalance("Bob"),0);
	}


}
