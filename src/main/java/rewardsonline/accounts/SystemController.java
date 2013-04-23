package rewardsonline.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A controller for obtaining login names. This is for demo purposes only - you
 * would never do this in a real application.
 */
@Controller
public class SystemController {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public SystemController(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Handles requests to list all accounts for currently logged in user.
	 */
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(Model model) {
		List<String> users = jdbcTemplate.query("SELECT username FRom T_Customer", new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("username");
			}
		});

		
		model.addAttribute("users", users);
		return "login";
	}
}