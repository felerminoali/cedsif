package com.cedsif.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cedsif.config.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource datasource;
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
        csrf().disable()
        .authorizeRequests()
                .antMatchers("/bootstrap/**", "/dist/**", "/plugins/**", "/img/**").permitAll()
                .antMatchers("/employee/**/details", "/relative/**", "/consutant-area").hasAnyAuthority("ADMIN", "MANAGER","CONSULTANT")
                .antMatchers("/departament/**", "/project/**").hasAnyAuthority("ADMIN")
                .antMatchers(
                		"/employee/all", 
                		"/employee/add-consultant/**", 
                		"/employee/add-admin/**", 
                		"/employee/add-manager/**",
                		"/employee/save-consultant/**", 
                		"/employee/save-admin/**", 
                		"/employee/save-manager/**"
                		).hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .failureUrl("/login?error")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                ;
        
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // Use Spring Boots User
																						// detailsMAnager
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();

		// Set our Datasource to use the one defined in application.properties
		userDetailsService.setDataSource(datasource);

		// Create BCryptPassword encoder 
		PasswordEncoder encoder = new
		BCryptPasswordEncoder();

		// add components
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
		auth.jdbcAuthentication().dataSource(datasource);

		// add new user "user" with password "password" - password will be encrypted
		if (!userDetailsService.userExists("felermino")) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
			User userDetails = new User("felermino", encoder.encode("1234"), authorities);
			userDetailsService.createUser(userDetails);
		}
		
		
	}
	
	 
}
