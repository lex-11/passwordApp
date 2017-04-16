package password.com.passwordApp.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.context.annotation.Bean;

import password.com.passwordApp.dao.PasswordDao;
import password.com.passwordApp.dao.UserDao;


@Configurable
public class DataSourceConfig {
	
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("dbschema.sql").addScript("data.sql").build();
		return db;
	}
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource());
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
