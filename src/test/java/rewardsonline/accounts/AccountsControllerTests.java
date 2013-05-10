package rewardsonline.accounts;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.ui.ExtendedModelMap;

import com.sun.net.httpserver.HttpPrincipal;

/**
 * Unit test for the AccountController implementation.
 */
public class AccountsControllerTests {

	private AccountsController controller;

	private StubAccountManager accountManager;

	private Principal testPrincipal = new HttpPrincipal(
			StubAccountManager.TEST_USER, StubAccountManager.TEST_USER);

	@Before
	public void setUp() throws Exception {
		accountManager = new StubAccountManager();
		controller = new AccountsController(accountManager);
	}

	@Test
	public void testList() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		String view = controller.list(testPrincipal, model);
		assertEquals("accounts/list", view);
		Customer customer = (Customer) model.get("customer");

		assertNotNull(customer);
		assertEquals(1, customer.getAccounts().size());
	}

	@Test
	@DirtiesContext
	public void testShow() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		String view = controller.show(StubAccountManager.TEST_ACCOUNT_NUMBER,
				testPrincipal, model);
		assertEquals("accounts/show", view);
		assertEquals(StubAccountManager.TEST_ACCOUNT_NUMBER,
				((Account) model.get("account")).getNumber());
	}

}