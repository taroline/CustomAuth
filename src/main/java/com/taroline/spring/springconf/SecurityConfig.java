package com.taroline.spring.springconf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/webjars/**", "/images/**", "/css/**", "/signup").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin().loginPage("/login").failureUrl("/login-error").usernameParameter("username")
                .passwordParameter("password").permitAll()
                .and();

        http.logout().logoutUrl("/logout").permitAll();

        // TODO:At de ques
        // h2-consoleを使うために一時的にCSRF対策とフレーム対策を切る
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }
}
