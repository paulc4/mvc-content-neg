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

	/**
	 * Handles requests to list all accounts for currently logged in user.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Principal prinicpal, Model model) {
		if (prinicpal == null)
			throw new AccessDeniedException("Not authenitcated");

		model.addAttribute("customer", accountManager.findCustomer(prinicpal.getName()));
		assert(model.asMap().get("customer") != null);
		logger.info("Customer = " + model.asMap().get("customer") );
		return "accounts/list";
	}

	/**
	 * Handles requests to shows detail about one account.
	 */
	@RequestMapping(value = "/{number}", method = RequestMethod.GET)
	public String show(@PathVariable String number, Model model) {
		model.addAttribute(accountManager.findAccount(number));
		return "accounts/show";
	}

}