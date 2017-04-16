package password.com.passwordApp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PasswordDao {

	private JdbcTemplate _jdbcTemplate;

	public PasswordDao(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated constructor stub
		_jdbcTemplate = jdbcTemplate;
	}

	public Password getPasswordContainingKey(String key) {

		Password password = this._jdbcTemplate.queryForObject(
				"SELECT TOP 1 * FROM MYPASSWORD WHERE DESCRIPTION  LIKE ? ",
				new Object[] { key }, new RowMapper<Password>() {

					public Password mapRow(ResultSet arg0, int arg1) {
						Password password = new Password();
						try {
							password.setPassword(arg0.getString("PASSWORD"));
							return password;
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						catch (EmptyResultDataAccessException e1) {
							// TODO Auto-generated catch block
							return null;

							// EmptyResultDataAccessException
						}

						return null;
					}
				});

		return password;
	}

	public void savePassword(Password password) {
		this._jdbcTemplate.update(
				"INSERT INTO MYPASSWORD (PASSWORD , DESCRIPTION) values (?,?)",
				password.getPassword(), password.getPassword_description());
	}
}
