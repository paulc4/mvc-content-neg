package rewardsonline.accounts.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rewardsonline.accounts.Account;
import rewardsonline.accounts.AccountManager;
import rewardsonline.accounts.AccountNotFoundException;
import rewardsonline.accounts.Customer;

/**
 * An AccountManager that uses JPA to work with accounts.
 */
@Repository("accountManager")
public class JpaAccountManager implements AccountManager {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public Customer findCustomer(String owner) {
		try {
			String query = "select c from Customer c left join fetch c.accounts a "
					+ "where username = :name order by a.number";

			Customer c = (Customer) entityManager.createQuery(query)
					.setParameter("name", owner).getSingleResult();
			return c;
		} catch (NoResultException e) {
			return null;
		}

	}

	@Transactional(readOnly = true)
	public List<Account> findAllAccounts(String owner) {
		Customer c = findCustomer(owner);
		return new ArrayList<Account>(c.getAccounts());
	}

	@Transactional(readOnly = true)
	public Account findAccount(String number) {
		try {
			return (Account) entityManager
					.createQuery(
							"select a from Account a left join fetch a.transactions where a.number = :number")
					.setParameter("number", number).getSingleResult();
		} catch (NoResultException e) {
			throw new AccountNotFoundException(number);
		}
	}

}