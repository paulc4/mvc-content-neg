package rewardsonline.accounts;

import java.util.List;

/**
 * Static stub used to support unit testing objects that depend on the
 * AccountManager interface.
 */
public class StubAccountManager implements AccountManager, AccountTestConstants {

	@Override
	public Account findAccount(String number) {
		Customer c = new Customer(TEST_USER_NUMBER, TEST_USER);
		
		return TEST_ACCOUNT_NUMBER.equals(number) ? new Account(c,
				TEST_ACCOUNT_NUMBER, TEST_ACCOUNT_TYPE) : null;
	}

	@Override
	public List<Account> findAllAccounts(String owner) {
		Customer c = findCustomer(owner);
		return c.getAccounts();
	}

	@Override
	public Customer findCustomer(String owner) {
		if (!TEST_USER.equals(owner))
			throw new IllegalArgumentException("Unknwon user " + owner);
		
		Customer c = new Customer(TEST_USER_NUMBER, TEST_USER);
		c.addAccount(new Account(c, TEST_ACCOUNT_NUMBER, TEST_USER));
		return c;
	}

}
