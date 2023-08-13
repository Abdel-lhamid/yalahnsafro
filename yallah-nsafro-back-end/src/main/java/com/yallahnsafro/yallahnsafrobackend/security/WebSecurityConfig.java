package com.yallahnsafro.yallahnsafrobackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/index").permitAll();
        http.authorizeRequests().antMatchers("/index/").permitAll();
        http.authorizeRequests().antMatchers("/.js", "/.css", "/.html", "/.txt", "/.png", "/.ico", "/.woff", "/.woff2", "/*.ttf").permitAll();
        http.authorizeRequests().antMatchers("/users/forgetPassword").permitAll();
        http.authorizeRequests().antMatchers("/users/resetPassword").permitAll();
        http.authorizeRequests().antMatchers("/users/registration").permitAll();
        http.authorizeRequests().antMatchers("/users/verifyEmail").permitAll();
        http.authorizeRequests().antMatchers("/users/{userId}").hasAnyAuthority(SecurityConstants.USER_ROLE_ADMIN);
        http.authorizeRequests().antMatchers("/users/allUsers").hasAnyAuthority(SecurityConstants.USER_ROLE_ADMIN);
        http.authorizeRequests().antMatchers("/users/").hasAnyAuthority(SecurityConstants.USER_ROLE_ADMIN);





        //http.authorizeRequests().antMatchers("/users").hasAnyAuthority(SecurityConstants.USER_ROLE_ADMIN);
        http.authorizeRequests().anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(getAuthenticationFilter());
        http.addFilter(new AuthorizationFilter(authenticationManager()));
    }
    protected AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return super.authenticationManagerBean();
    }
}
