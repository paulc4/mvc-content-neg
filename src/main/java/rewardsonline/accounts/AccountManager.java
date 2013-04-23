package rewardsonline.accounts;

import java.util.List;

/**
 * Manages access to member account information.
 */
public interface AccountManager {

	/**
	 * Find the customer with the provided account number.
	 * 
	 * @param owner
	 *            Name of the owner of the accounts.
	 * @return The customer or <code>null</code> if no customer exists with that
	 *         name.
	 */
	Customer findCustomer(String owner);

	/**
	 * Return a listing of all accounts for a given customer.
	 * 
	 * @param owner
	 *            Name of the owner of the accounts.
	 * @return the account listing
	 */
	List<Account> findAllAccounts(String owner);

	/**
	 * Find the account with the provided account number.
	 * 
	 * @param number
	 *            The account number
	 * @return The account
	 * @throws AccountNotFoundException
	 *             If no such account exists.
	 */
	Account findAccount(String number);

}