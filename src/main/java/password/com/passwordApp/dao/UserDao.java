package password.com.passwordApp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {
	private JdbcTemplate _jdbcTemplate;

	public UserDao(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated constructor stub
		_jdbcTemplate = jdbcTemplate;
	}

	public User getUser(String userId) {
		return _jdbcTemplate.queryForObject(
				"SELECT TOP 1  * FROM LOGIN WHERE USERID = ?",
				new Object[] { userId }, new RowMapper<User>() {
					public User mapRow(ResultSet arg0, int arg1) {
						User user = new User();
						try {
							user.setId(arg0.getString("USERID"));
							user.setPassword(arg0.getString("PASSWORD"));
							return user;
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();

							// EmptyResultDataAccessException
						} catch (EmptyResultDataAccessException e1) {
							// TODO Auto-generated catch block
							return null;

							// EmptyResultDataAccessException
						}
						return null;
					}
				});
	}
}
