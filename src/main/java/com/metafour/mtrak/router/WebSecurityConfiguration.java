package com.metafour.mtrak.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.metafour.mtrak.router.config.MrouterConfig;
import com.metafour.mtrak.router.config.MrouterConfig.User;

/**
 * @author noor
 *
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	MrouterConfig mConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and()
			.authorizeRequests()
				.antMatchers("/*.html").hasRole(mConfig.getUserRoleAdmin())
				.antMatchers("/refresh").hasRole(mConfig.getUserRoleSuperAdmin())
				.anyRequest().fullyAuthenticated().and()
			.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> abc = auth.inMemoryAuthentication();
		@SuppressWarnings("rawtypes")
		UserDetailsBuilder udb = null;
		for (int i = 0; i < mConfig.getUsers().size(); i++) {
			User user = mConfig.getUsers().get(i);
			if (i == 0) {
				udb = abc.withUser(user.getCode()).password(user.getPassword()).roles(user.getRoles());
			} else {
				udb = udb.and().withUser(user.getCode()).password(user.getPassword()).roles(user.getRoles());
			}
		}
	}
}
