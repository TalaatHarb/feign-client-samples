package net.talaatharb.samples.securedcrud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http // HTTP Security
				.cors().and() // CORS policy
				.csrf().disable() // CSRF disable
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // disable
																				// session
																				// creation

				.authorizeRequests() // Configuration for authorization of
										// requests
				.antMatchers("/api/**").authenticated().and()
				.oauth2ResourceServer().jwt();
	}
}
