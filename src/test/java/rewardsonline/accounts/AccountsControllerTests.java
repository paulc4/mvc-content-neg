package rewardsonline.accounts;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;

import com.sun.net.httpserver.HttpPrincipal;

/**
 * Unit test for the AccountController implementation.
 */
public class AccountsControllerTests {

	private AccountsController controller;

	private StubAccountManager accountManager;

	@Before
	public void setUp() throws Exception {
		accountManager = new StubAccountManager();
		controller = new AccountsController(accountManager);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testList() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		String view = controller.list(new HttpPrincipal(
				StubAccountManager.TEST_USER, StubAccountManager.TEST_USER),
				model);
		assertEquals("accounts/list", view);
		assertEquals(1, ((List<Account>) model.get("accountList")).size());
	}

	@Test
	public void testShow() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		String view = controller.show(StubAccountManager.TEST_ACCOUNT_NUMBER,
				model);
		assertEquals("accounts/show", view);
		assertEquals(StubAccountManager.TEST_ACCOUNT_NUMBER,
				((Account) model.get("account")).getNumber());
	}

}