package rewardsonline.accounts;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Account")   // 404
public class AccountNotFoundException extends RuntimeException {

	/**
	 * Serialisation Version Id
	 */
	private static final long serialVersionUID = -3891534644498426670L;

	public AccountNotFoundException(String accountId) {
		super("No such account with id: " + accountId);
	}
}
