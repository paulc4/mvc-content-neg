package common.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlValue;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A representation of money.
 * 
 * A value object. Immutable.
 */
@SuppressWarnings("serial")
public class MonetaryAmount implements Serializable {

	private BigDecimal value;

	/**
	 * Create a new monetary amount from the specified value.
	 * @param value the value of the amount; for example, in $USD "10.00" would be ten dollars, ".29" would be 29 cents
	 */
	@JsonCreator
	public MonetaryAmount(BigDecimal value) {
		initValue(value);
	}

	/**
	 * Create a new monetary amount from the specified value.
	 * @param value the monetary amount as a double
	 */
	public MonetaryAmount(double value) {
		initValue(BigDecimal.valueOf(value));
	}

	@SuppressWarnings("unused")
	private MonetaryAmount() {
	}

	private void initValue(BigDecimal value) {
		this.value = value.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Convert the string representation of a monetary amount (e.g. $5 or 5) to a MonetaryAmount object.
	 * @param string the monetary amount string
	 * @return the monetary amount object
	 */
	public static MonetaryAmount valueOf(String string) {
		if (string == null || string.length() == 0) {
			throw new IllegalArgumentException("The monetary amount value is required");
		}
		if (string.startsWith("$")) {
			int index = string.indexOf('$');
			string = string.substring(index + 1);
		}
		BigDecimal value = new BigDecimal(string);
		return new MonetaryAmount(value);
	}

	/**
	 * Returns the zero (0.00) monetary amount.
	 */
	public static MonetaryAmount zero() {
		return new MonetaryAmount(0);
	}

	/**
	 * Add to this monetary amount, returning the sum as a new monetary amount.
	 * @param amount the amount to add
	 * @return the sum
	 */
	public MonetaryAmount add(MonetaryAmount amount) {
		return new MonetaryAmount(value.add(amount.value));
	}

	/**
	 * Subtract from this monetary amount, returning the difference as a new monetary amount.
	 * @param amount the amount to subtract
	 * @return the difference
	 */
	public MonetaryAmount subtract(MonetaryAmount amount) {
		return new MonetaryAmount(value.subtract(amount.value));
	}

	/**
	 * Multiply this monetary amount, returning the product as a new monetary amount.
	 * @param amount the amount to multiply
	 * @return the product
	 */
	public MonetaryAmount multiplyBy(BigDecimal amount) {
		return new MonetaryAmount(value.multiply(amount));
	}

	/**
	 * Divide this monetary amount, returning the quotient as a decimal.
	 * @param amount the amount to divide by
	 * @return the quotient
	 */
	public BigDecimal divide(MonetaryAmount amount) {
		return value.divide(amount.value);
	}

	/**
	 * Divide this monetary amount, returning the quotient as a new monetary amount.
	 * @param amount the amount to divide by
	 * @return the quotient
	 */
	public MonetaryAmount divideBy(BigDecimal amount) {
		return new MonetaryAmount(value.divide(amount));
	}

	/**
	 * Returns true if this amount is greater than the amount.
	 * @param amount the monetary amount
	 * @return true or false
	 */
	public boolean greaterThan(MonetaryAmount amount) {
		return value.compareTo(amount.value) > 0;
	}

	/**
	 * Get this amount as a double. Useful for when a double type is needed by an external API or system.
	 * @return this amount as a double
	 */
	public double asDouble() {
		return value.doubleValue();
	}

	/**
	 * Get this amount as a big decimal. Useful for when a BigDecimal type is needed by an external API or system.
	 * @return this amount as a big decimal
	 */
	@JsonProperty
	@XmlValue
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Get this amount as a big decimal. Useful for when a BigDecimal type is needed by an external API or system.
	 * @return this amount as a big decimal
	 */
	public BigDecimal asBigDecimal() {
		return value;
	}

	public boolean equals(Object o) {
		if (!(o instanceof MonetaryAmount)) {
			return false;
		}
		return value.equals(((MonetaryAmount) o).value);
	}

	public int hashCode() {
		return value.hashCode();
	}

	public String toString() {
		return "$" + value.toString();
	}

}