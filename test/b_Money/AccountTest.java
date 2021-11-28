package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	Account testAccount2;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		//testAccount2 = new Account("Alice", SEK);
		testAccount.deposit(new Money(100000_00, SEK));
		//testAccount2.deposit(new Money(100000_00, SEK));
		SweBank.deposit("Alice", new Money(10000_00, SEK));
	}
	

	@Test
	public void testAddRemoveTimedPayment() {
		// checking addTimedPayment() and removeTimedPayment() methods

		testAccount.addTimedPayment("1", 4, 2, new Money(10_00, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("1"));
		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// checking mechanics of time payments for an Account and a Bank

		assertEquals(100000_00, testAccount.getBalance().getAmount(), 0);
		assertEquals(10000_00, SweBank.getBalance("Alice"), 0);
		testAccount.addTimedPayment("1", 4, 2, new Money(100_00, SEK), SweBank, "Alice");
		for (int i = 0; i < 4; i++) {
			testAccount.tick();
		}
		assertEquals(99900_00, testAccount.getBalance().getAmount(), 0);
		assertEquals(10100_00, SweBank.getBalance("Alice"), 0);


	}

	@Test
	public void testAddWithdraw() throws AccountDoesNotExistException {
		// checking deposit() and withdraw() methods

		assertEquals(100000_00, testAccount.getBalance().getAmount(), 0);
		testAccount.withdraw(new Money(100_00, SEK));
		assertEquals(99900_00, testAccount.getBalance().getAmount(), 0);
		testAccount.deposit(new Money(200_00, SEK));
		assertEquals(100100_00, testAccount.getBalance().getAmount(), 0);
	}
	
	@Test
	public void testGetBalance() {
		// checking getBalance() methods

		assertEquals(100000_00, testAccount.getBalance().getAmount(), 0);
		testAccount.deposit(new Money(200_34, SEK));
		assertEquals(100200_34, testAccount.getBalance().getAmount(), 0);
	}
}
