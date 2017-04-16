package password.com.passwordApp.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import password.com.passwordApp.dao.PasswordDao;
import password.com.passwordApp.dao.UserDao;

@Configurable
@ImportResource("classpath:application-datasource.xml")
public class DataSourceConfig {

	@Autowired
	DataSource dataSource;

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public PasswordDao passwordDao() {
		return new PasswordDao(getJdbcTemplate());
	}

	@Bean
	public UserDao userDao() {
		return new UserDao(getJdbcTemplate());
	}

}
