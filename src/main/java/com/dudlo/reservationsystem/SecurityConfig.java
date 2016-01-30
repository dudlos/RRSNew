package com.dudlo.reservationsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.dudlo.reservationsystem.webapp.MyCustomLoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * The SecurityConfig will: Require authentication to every URL in your
	 * application Generate a login form for you Allow the user with the
	 * Username user and the Password password to authenticate with form based
	 * authentication Allow the user to logout CSRF attack prevention Session
	 * Fixation protection Security Header integration HTTP Strict Transport
	 * Security for secure requests X-Content-Type-Options integration Cache
	 * Control (can be overridden later by your application to allow caching of
	 * your static resources) X-XSS-Protection integration X-Frame-Options
	 * integration to help prevent Clickjacking Integrate with the following
	 * Servlet API methods HttpServletRequest#getRemoteUser()
	 * HttpServletRequest.html#getUserPrincipal()
	 * HttpServletRequest.html#isUserInRole(java.lang.String)
	 * HttpServletRequest.html#login(java.lang.String, java.lang.String)
	 * HttpServletRequest.html#logout()
	 */
@Autowired
private UserDetailsService userDetailsService;
	
	@Autowired 
	  public void configureGlobal(AuthenticationManagerBuilder auth)
	  throws Exception { auth.userDetailsService(userDetailsService) ;}
	 

	 @Override
	    public void configure(final WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/resources/**");
	    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
	.antMatchers( "/api/home", "/api/getAllRestaurants", "/api/checkAvailibilityDate", "/api/checkAvailibilityTime", /* "/api/makeReservation", "/api/tableBooking", 
				"/api/getReservation", "/api/findAllMenusPerReservation", */ "/api/getAllMenusForRestaurant", "/api/getAllMenusForMenuType", 
				"/createUser").permitAll()
			.anyRequest().access("hasRole('ROLE_USER')")
			.and().formLogin().loginPage("/login")
			.successHandler(successHandler())
			.permitAll();
		
			
		
		
		/*.loginPage("/login").
			.usernameParameter("email").passwordParameter("password");*/
			

	}
	@Bean
	public AuthenticationSuccessHandler successHandler() {
	    return new MyCustomLoginSuccessHandler();
	}
}
