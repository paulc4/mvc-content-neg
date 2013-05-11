package rewardsonline.accounts;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.springframework.util.StringUtils;

import common.money.MonetaryAmount;

/**
 * Represents an account for a member of a financial institution. An account has
 * zero or more {@link Transaction}s and belongs to a {@link Customer}. An aggregate entity.
 */
@Entity
@Table(name = "T_ACCOUNT")
@XmlRootElement
public class Account {

	private static final String NO_CREDIT_CARD = "";

	// For simplicity account-types represented as Strings
	public static final String CHECKING = "CHECK";
	public static final String SAVINGS = "SAVINGS";
	public static final String CREDIT = "CREDIT";

	@Id
	@Column(name = "ID")
	private Integer entityId;

	@Column(name = "NUMBER")
	private String number;

	@Column(name = "ACC_TYPE")
	private String type;

	@Column(name = "CREDIT_CARD")
	private String creditCardNumber;

	@Embedded
	@AttributeOverride(name="value", column=@Column(name="BALANCE"))
	private MonetaryAmount balance;

	@ManyToOne
	@JoinColumn(name = "CUST_ID")
	@JsonBackReference
	private Customer owner;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ACCOUNT_ID")
	private Set<Transaction> transactions = new LinkedHashSet<Transaction>();

	/**
	 * Required by JPA.
	 */
	protected Account() {
	}

	/**
	 * Create a new account.
	 * 
	 * @param number
	 *            the account number
	 * @param name
	 *            the name on the account
	 */
	public Account(Customer owner, String number, String type) {
		this.owner = owner;
		this.number = number;
		this.type = type;
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
	 * Returns the number used to uniquely identify this account.
	 */
	public Customer getOwner() {
		return owner;
	}

	/**
	 * Returns the number used to uniquely identify this account.
	 */
	@XmlAttribute
	public String getNumber() {
		return number;
	}

	/**
	 * Get the account type.
	 * 
	 * @return One of "CREDIT", "SAVINGS", "CHECK".
	 */
	@XmlAttribute
	public String getType() {
		return type;
	}

	/**
	 * Get the credit-card, if any, associated with this account.
	 * 
	 * @return The credit-card number or null if there isn't one.
	 */
	@XmlAttribute
	public String getCreditCardNumber() {
		return StringUtils.hasText(creditCardNumber) ? creditCardNumber : null;
	}

	/**
	 * Set the credit-card to associate with this account.
	 * 
	 * @param creditCardNumber
	 *            The credit card number. Specify null or the empty string for
	 *            no card.
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = StringUtils.hasText(creditCardNumber) ? creditCardNumber
				: NO_CREDIT_CARD;
	}

	/**
	 * Get the balance of this account in local currency.
	 * 
	 * @return Current account balance.
	 */
	@XmlAttribute
	public MonetaryAmount getBalance() {
		return balance;
	}

	/**
	 * Set the balance of this account in local currency.
	 * 
	 * @return New account balance.
	 */
	public void setBalance(MonetaryAmount balance) {
		this.balance = balance;
	}

	/**
	 * Add a single beneficiary with the specified allocation percentage.
	 * 
	 * @param beneficiaryName
	 *            the name of the beneficiary (should be unique)
	 * @param allocationPercentage
	 *            the beneficiary's allocation percentage within this account
	 */
	public void addTransaction(String accountName, String accountNumber,
			String bankCode, MonetaryAmount amount) {
		transactions.add(new Transaction(accountName, accountNumber, bankCode,
				amount));
	}

	/**
	 * Returns a single account transaction. Callers should not attempt to hold
	 * on or modify the returned object. This method should only be used
	 * transitively; for example, called to facilitate reporting or testing.
	 * 
	 * @param name
	 *            the name of the transaction account e.g "Fred Smith"
	 * @return the beneficiary object
	 */
	@XmlElement
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Returns a single account transaction. Callers should not attempt to hold
	 * on or modify the returned object. This method should only be used
	 * transitively; for example, called to facilitate reporting or testing.
	 * 
	 * @param name
	 *            the name of the transaction account e.g "Fred Smith"
	 * @return the beneficiary object
	 */
	public Transaction getTransaction(String name) {
		for (Transaction trns : transactions) {
			if (trns.getAccountName().equals(name)) {
				return trns;
			}
		}
		throw new IllegalArgumentException("No such beneficiary with name '"
				+ name + "'");
	}

	public String toString() {
		return "[Account number = '" + number + "', type = '" + type
				+ "', balance = '" + balance + "'. cc = '" + creditCardNumber
				+ "']"; // + transactions = " + transactions;
	}

}