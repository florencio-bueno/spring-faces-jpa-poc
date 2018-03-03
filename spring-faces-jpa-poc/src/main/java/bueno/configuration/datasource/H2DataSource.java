/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bueno.configuration.datasource;

import java.io.PrintWriter;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.Driver;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.tool.schema.Action;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import bueno.configuration.server.ProfileEnum;

@Configuration
@EnableTransactionManagement
public class H2DataSource {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Bean
	DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(Driver.class.getName());
		//hikariConfig.setDataSourceClassName(H2DataSource.class.getName());

		config.setJdbcUrl("jdbc:h2:mem:test");
		config.setUsername("sa");
		config.setConnectionTimeout(30000);
		config.setIdleTimeout(30000);
		config.setMinimumIdle(3);
		config.setMaximumPoolSize(10);
		config.setConnectionTestQuery("SELECT 1");

		config.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		config.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
		config.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
		config.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		config.addDataSourceProperty("dataSource.logWriter", new PrintWriter(System.out));

		return new HikariDataSource(config);
	}

	@Bean(name = "entityManagerFactory")
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("bueno.common.jpa", "bueno.poc.mvc.model.entity");
		entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());

		return entityManagerFactoryBean;
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	private Properties jpaHibernateProperties() {
		Properties prop = new Properties();

		prop.put("datasource.tomcat.max-wait", 20000);
		prop.put("datasource.tomcat.max-active", 50);
		prop.put("datasource.tomcat.max-idle", 200);
		prop.put("datasource.tomcat.min-idle", 15);

		prop.put(Environment.MAX_FETCH_DEPTH, 5);
		prop.put(Environment.STATEMENT_FETCH_SIZE, 500);
		prop.put(Environment.STATEMENT_BATCH_SIZE, 500);
		prop.put(Environment.DEFAULT_BATCH_FETCH_SIZE, 50);

		// Configures the used database dialect. This allows Hibernate to create SQL that is optimized for the used database.
		prop.put(Environment.DIALECT, H2Dialect.class.getName());

		// Specifies the action that is invoked to the database when the Hibernate SessionFactory is created or closed.
		prop.put(Environment.HBM2DDL_AUTO, "update");

		if (ProfileEnum.DEV.getProfile().equals(activeProfile)) {
			// If the value of this property is true, Hibernate writes all SQL statements to the console.
			prop.put(Environment.SHOW_SQL, true);

			// If the value of this property is true, Hibernate will use prettyprint when it writes SQL to the console.
			prop.put(Environment.FORMAT_SQL, true);
		}

		// Disable feature detection by this undocumented parameter. Check the org.hibernate.engine.jdbc.internal.JdbcServiceImpl.configure method for more details.
		prop.put("hibernate.temp.use_jdbc_metadata_defaults", false);

		return prop;
	}

}
