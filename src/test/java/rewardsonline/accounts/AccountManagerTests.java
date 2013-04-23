package rewardsonline.accounts;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for the AccountController implementation.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:system-test-config.xml" })
public class AccountManagerTests {

	private final static String[] accountNumber = { "123456788", "123456789",
			"123456799" };
	private final static String[] accountType = { "CHECK", "CREDIT", "SAVINGS" };

	private final int N_ACCOUNTS_EXPECTED = 3;

	@Autowired
	private AccountManager accountManager;

	@Test
	public void testLCustomer() throws Exception {
		Customer cust = accountManager
				.findCustomer(AccountTestConstants.TEST_USERNAME);
		assertNotNull("No such customer " + AccountTestConstants.TEST_USERNAME,
				cust);
		assertEquals(cust.getUsername(), AccountTestConstants.TEST_USERNAME);
		assertEquals(cust.getNumber(), AccountTestConstants.TEST_USER_NUMBER);
		assertEquals(cust.getName(), AccountTestConstants.TEST_USER);

		List<Account> accounts = cust.getAccounts();
		assertTrue("No such account", accounts != null && accounts.size() != 0);
		checkAccounts(accounts);
	}

	@Test
	public void testList() throws Exception {
		List<Account> accounts = accountManager
				.findAllAccounts(AccountTestConstants.TEST_USERNAME);
		assertTrue("No such account", accounts != null && accounts.size() != 0);
		checkAccounts(accounts);
	}

	@Test
	public void testShow() throws Exception {
		Account account = accountManager
				.findAccount(AccountTestConstants.TEST_ACCOUNT_NUMBER);

		System.out.println(account);
		assertNotNull("No such account", account);
		assertEquals(AccountTestConstants.TEST_ACCOUNT_NUMBER,
				account.getNumber());
	}

	private void checkAccounts(List<Account> accounts) {
		assertEquals(N_ACCOUNTS_EXPECTED, accounts.size());
		int i = 0;

		for (Account account : accounts) {
			System.out.println(account);
			assertEquals("Wrong account type for account #" + i,
					accountType[i], account.getType());
			assertEquals("Wrong account number for account #" + i,
					accountNumber[i], account.getNumber());
			i++;
		}
	}
}