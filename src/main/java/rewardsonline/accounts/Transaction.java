package rewardsonline.accounts;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import common.money.MonetaryAmount;

/**
 * A single beneficiary allocated to an account. Each beneficiary has a name
 * (e.g. Annabelle) and a savings balance tracking how much money has been saved
 * for he or she to date (e.g. $1000). Scoped by the Account aggregate.
 */
@Entity
@Table(name = "T_ACCOUNT_TRANSACTION")
@XmlRootElement
public class Transaction {

	@Id
	@Column(name = "ID")
	private Integer entityId;

	@Column(name = "NAME")
	private String accountName;

	@Column(name = "CODE")
	private String bankCode;

	@Column(name = "NUMBER")
	private String accountNumber;

	@Embedded
	@AttributeOverride(name="value", column=@Column(name="AMOUNT"))
	private MonetaryAmount amount;

	@SuppressWarnings("unused")
	private Transaction() {
	}

	Transaction(String accountName, String accountNumber, String bankCode, MonetaryAmount amount) {
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.bankCode = bankCode;
		this.amount = amount;
	}


	/**
	 * Returns the name of the beneficiary.
	 * 
	 * @return the name
	 */
	@XmlAttribute
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Returns the name of the beneficiary.
	 * 
	 * @return the name
	 */
	@XmlAttribute
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Returns the name of the beneficiary.
	 * 
	 * @return the name
	 */
	@XmlAttribute
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * Returns the total savings accrued by this beneficiary.
	 * 
	 * @return the total savings
	 */
	@XmlAttribute
	public MonetaryAmount getAmount() {
		return amount;
	}

	public String toString() {
		return "[Transfer " + amount + " to " + accountName + " (" + bankCode + "/" + accountNumber + ")]";
	}
}