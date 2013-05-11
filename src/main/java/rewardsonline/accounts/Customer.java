package rewardsonline.accounts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A customer of a financial institution. A customer has zero or more accounts.
 * An aggregate entity.
 */
@Entity
@Table(name = "T_CUSTOMER")
@XmlRootElement
public class Customer {

	@Id
	@Column(name = "ID")
	private Integer entityId;

	@NotNull
	@Column(name = "NUMBER")
	private String number;

	@NotNull
	private String name;

	@NotNull
	private String username;

	@Column(name = "DATE_OF_BIRTH")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	private String email;

	@Column(name = "REWARDS_NEWSLETTER")
	private boolean receiveNewsletter;

	@Column(name = "MONTHLY_EMAIL_UPDATE")
	private boolean receiveMonthlyEmailUpdate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
	private List<Account> accounts;

	/**
	 * Required by JPA.
	 */
	protected Customer() {
	}

	/**
	 * Create a new account.
	 * 
	 * @param number
	 *            the account number
	 * @param name
	 *            the name on the account
	 */
	public Customer(String number, String name) {
		this.number = number;
		this.name = name;
		accounts = new ArrayList<Account>();
	}

	/**
	 * Returns the entity identifier used to internally distinguish this entity
	 * among other entities of the same type in the system. Should typically
	 * only be called by privileged data access infrastructure code such as an
	 * Object Relational Mapper (ORM) and not by application code.
	 * 
	 * @return the internal entity identifier
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * Sets the internal entity identifier - should only be called by privileged
	 * data access code (repositories that work with an Object Relational Mapper
	 * (ORM)). Should never be set by application code explicitly.
	 * 
	 * @param entityId
	 *            the internal entity identifier
	 */
	protected void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * Returns the number used to uniquely identify this account.
	 */
	@XmlAttribute
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number used to uniquely identify this account.
	 * 
	 * @param number
	 *            The number for this account
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Returns the name on file for this account.
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * Sets the name on file for this account.
	 * 
	 * @param name
	 *            The name for this account
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the username for this account - this is the name the user logs in
	 * with.
	 * 
	 * @return
	 */
	@XmlAttribute
	public String getUsername() {
		return username;
	}

	@XmlAttribute
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@XmlAttribute
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlAttribute
	public boolean isReceiveNewsletter() {
		return receiveNewsletter;
	}

	public void setReceiveNewsletter(boolean receiveNewsletter) {
		this.receiveNewsletter = receiveNewsletter;
	}

	@XmlAttribute
	public boolean isReceiveMonthlyEmailUpdate() {
		return receiveMonthlyEmailUpdate;
	}

	public void setReceiveMonthlyEmailUpdate(boolean receiveMonthlyEmailUpdate) {
		this.receiveMonthlyEmailUpdate = receiveMonthlyEmailUpdate;
	}

	/**
	 * Add a new account.
	 * 
	 * @param account
	 *            the account (should be unique)
	 * @throws DuplicateAccountException
	 *             If the account number already exists for this customer.
	 */
	public void addAccount(Account account) {
		try {
			if (getAccount(account.getNumber()) != null)
				throw new DuplicateAccountException("Account "
						+ account.getNumber()
						+ " already exists for customeer " + number);
		} catch (AccountNotFoundException e) {
			accounts.add(account);
		}
	}

	/**
	 * Returns all the accounts. Callers should not attempt to hold on to or
	 * modify the returned object or its contents. This method should only be
	 * used transitively. For example, to perform reporting or testing.
	 * 
	 * @param name
	 *            the name of the account account e.g "Fred Smith"
	 * @return the account object
	 */
	@XmlElement
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Returns a single account account. Callers should not attempt to hold on
	 * or modify the returned object. This method should only be used
	 * transitively; for example, called to facilitate reporting or testing.
	 * 
	 * @param number
	 *            The number of the account to find
	 * @return the account object
	 */
	public Account getAccount(String number) {
		if (accounts == null)
			throw new IllegalStateException("No accounts collection");

		if (accounts != null) {
			// Simple sequential search - OK as most people don't have many
			// accounts
			for (Account account : accounts) {
				if (account.getNumber().equals(name)) {
					return account;
				}
			}
		}

		throw new AccountNotFoundException("No such account with number '"
				+ number + "'");
	}

	public String toString() {
		return "Customer [number = '" + number + "', name = '" + name
				+ "', user-id = '" + username + "'] - Accounts = " + accounts;
	}

}