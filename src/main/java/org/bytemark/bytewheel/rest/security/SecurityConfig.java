package org.bytemark.bytewheel.rest.security;

/*import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;*/

//@Configuration
public class SecurityConfig{ 
//extends WebSecurityConfigurerAdapter {

	/**
	 * 	Authentication : User --> Roles
	 * 
	 *  The Front UI will have 2 seperate pages for admin and customer: 
	 *  
	 *  The Admin page will raise REST calls using the basic auth used 
	 *  for ADMIN role, i.e, user name: admin, password: admin_secret
	 *  
	 *  The customer page will make rest calls with basic auth with role as USER
	 *  i.e, user name: user1, password: secret
	 */
	
	/*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
				.passwordEncoder(NoOpPasswordEncoder.getInstance())
				.withUser("user1").password("secret").roles("USER").and()
				.withUser("admin").password("admin_secret").roles("ADMIN");
	}

	*//**
	 * provide the authority to access the REST endpoints for the roles - ADMIN and USER 
	 *//*
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic().and().authorizeRequests()
		
			.antMatchers("/**").hasRole("ADMIN")
			.antMatchers("/**").hasRole("USER")
			.and().csrf().disable().headers().frameOptions().disable();
	}*/

}