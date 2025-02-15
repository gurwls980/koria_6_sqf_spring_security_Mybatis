package com.study.SpringSecurityMybatis.config;

import com.study.SpringSecurityMybatis.security.filter.JwtAccessTokenFilter;
import com.study.SpringSecurityMybatis.security.handler.AuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAccessTokenFilter jwtAccessTokenFilter;

    @Autowired
    private AuthenticationHandler authenticationHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override   // 메서드를 오버라이딩 했을때 매개변수나 오버로딩 여부 확인
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.httpBasic().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
        http.exceptionHandling().authenticationEntryPoint(authenticationHandler);
        http.authorizeRequests()
                .antMatchers("/auth/**", "/h2-console/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
