package rewardsonline.accounts;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A controller handling requests for showing and updating an Account.
 */
@Controller
@RequestMapping("/accounts")
public class AccountsController {

	private Logger logger = Logger.getLogger(AccountsController.class);

	private AccountManager accountManager;

	@Autowired
	public AccountsController(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	// REST using Message Converters
	/**
	 * Handles requests to fetch customer account details for the currently
	 * logged in user.
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Customer getCustomerAccount(Principal principal) {
		if (principal == null)
			throw new AccessDeniedException("Not authenitcated");

		return accountManager.findCustomer(principal.getName());
	}

	/**
	 * Handles requests to fetch detail about one account.
	 */
	@RequestMapping(value = "/{number}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Account getAccountDetails(@PathVariable String number, Principal principal) {
		if (principal == null)
			throw new AccessDeniedException("Not authenitcated");

		Account account = accountManager.findAccount(number);

		if (account.getOwner().getName().equals(principal.getName()))
			return account;

		throw new AccessDeniedException("Not authorized");
	}

	// HTML via Tiles views
	/**
	 * Handles requests to list all accounts for currently logged in user.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Principal principal, Model model) {
		if (principal == null)
			throw new AccessDeniedException("Not authenitcated");

		model.addAttribute("customer", getCustomerAccount(principal));
		logger.info("Customer = " + model.asMap().get("customer"));
		return "accounts/list";
	}

	/**
	 * Handles requests to shows detail about one account.
	 */
	@RequestMapping(value = "/{number}", method = RequestMethod.GET)
	public String show(@PathVariable String number, Principal principal,
			Model model) {
		model.addAttribute(getAccountDetails(number, principal));
		return "accounts/show";
	}

}