package com.example.demo.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(3)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        // protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests().antMatchers("/home", "/vendor/**", "/image/**",
        // "/home/**").permitAll()
        // .antMatchers("/admin/**").hasRole("EDMIN_ROLE").anyRequest().authenticated().and().formLogin()
        // .loginPage("/login").permitAll().defaultSuccessUrl("/home").and().logout()
        // .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        // }
    }

    @Configuration
    @Order(5)
    public static class HttpFormSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            // http.csrf().disable()
            // .antMatcher("/app/**")
            // .authorizeRequests()
            // .antMatchers("/app/login").access("permitAll")
            // .antMatchers("/app/so/**").hasRole("OWNER")
            // // .antMatchers("/app", "/app/", "/login",
            // // "/logout").access("permitAll")
            // // .antMatchers("/images/**", "/css/**", "/js/**",
            // // "/lib/**").access("permitAll")
            // .antMatchers("/app/users/**").hasAuthority("Administrators")
            // // .antMatchers("/users/**").hasRole("ADMINISTRATORS")
            // // .antMatchers("/notices/**").hasAuthority("Administrators")
            // // .antMatchers("/joblauncher/**").hasAnyAuthority("Administrators",
            // // "Managers")
            // .anyRequest().authenticated()
            // .and().formLogin()
            // .usernameParameter("loginEmail")
            // .passwordParameter("loginPassword")
            // .loginPage("/app/login")
            // .loginProcessingUrl("/app/login-process")
            // .and().rememberMe().rememberMeParameter("remember-me")
            // .tokenRepository(tokenRepository).tokenValiditySeconds(86400)
            // .and().logout().logoutUrl("/app/logout").logoutSuccessUrl("/app/")
            // .and().headers()
            // .addHeaderWriter(new
            // StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"))
            // .frameOptions().disable()
            // ;

        }
    }

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

        @Autowired
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