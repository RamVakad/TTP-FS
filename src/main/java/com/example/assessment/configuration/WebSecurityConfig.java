package com.example.assessment.configuration;

import com.example.assessment.security.jwt.JWTFilterConfig;
import com.example.assessment.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // Disable CSRF (cross site request forgery)
        httpSecurity.csrf().disable();

        //H2 Database Frame
        httpSecurity.headers().frameOptions().disable();

        // No session will be created or used by spring security
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        httpSecurity.authorizeRequests()//
                .antMatchers("/api/users/signIn").permitAll()//
                .antMatchers("/api/users/signIn").permitAll()//
                .antMatchers("/api").authenticated()// All API access needs to be authenticated except signIn & signUp
                // Allow everything else..
                .anyRequest().permitAll();

        // If a user tries to access a resource without having enough permissions
        httpSecurity.exceptionHandling().accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(httpServletResponse.SC_UNAUTHORIZED, "Authentication failed: " + e.getMessage()));

        // Apply JWT
        httpSecurity.apply(new JWTFilterConfig(jwtProvider));

        // Optional, if you want to test the API from a browser
        httpSecurity.httpBasic();
        // httpSecurity.requiresChannel().anyRequest().requiresSecure(); //HTTPS only
    }

    @Override
    public void configure(WebSecurity web) {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/public")
                .antMatchers("/h2");
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}

