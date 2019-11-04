package com.example.demo.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class WebSecurityConfig {

    @Configuration
    @Order(1)
    public static class HttpBasicSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/api/**").authorizeRequests()
                    //
                    // .antMatchers("/api/member/**").hasRole("USER")
                    //
                    .anyRequest().permitAll();
            http.httpBasic().realmName("SMKOR");
            http.csrf().disable();

        }
    }

    @Configuration
    @Order(5)
    public static class HttpFormSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/app/**").authorizeRequests()
                    //
                    .antMatchers("/app/login", "/app/**/*.json").permitAll()
                    //
                    .antMatchers("/app/dashbord/**").hasRole("USER")
                    //
                    .anyRequest().authenticated();
            http.csrf().disable();
            http.formLogin()
                    //
                    .usernameParameter("username").passwordParameter("password")
                    //
                    .loginPage("/app/login").loginProcessingUrl("/app/login-process");
            http.rememberMe().rememberMeParameter("remember-me");
            http.logout().logoutUrl("/app/logout").logoutSuccessUrl("/");
            http.headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
                    .frameOptions().disable();

        }
    }

    @Order(0)
    @Configuration
    public static class GlobalConfig {

        // @Bean
        // public UserDetailsService userDetailsService() {
        // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // manager.createUser(User.withUsername("user").password("user").roles("USER").build());
        // return manager;
        // }

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired(required = true)
        private PasswordEncoder passwordEncoder;

        @Autowired
        public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        // @Bean("persistentTokenBasedRememberMeServices")
        // public PersistentTokenBasedRememberMeServices
        // persistentTokenBasedRememberMeServices() {
        // PersistentTokenBasedRememberMeServices tokenBasedservice = new
        // PersistentTokenBasedRememberMeServices(
        // "remember-me", userDetailsService, tokenRepository);
        // return tokenBasedservice;
        // }

    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // return new BCryptPasswordEncoder();
    }

}