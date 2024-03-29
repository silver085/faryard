package com.faryard.api.configurators;

import com.faryard.api.domain.Roles;
import com.faryard.api.services.impl.JwtTokenProvider;
import com.faryard.api.services.impl.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    MongoUserService mongoUserService;
    @Autowired
    MessageSource msgSource;
    @Autowired
    UserAndPassAuthenticationProvider userAndPassAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(mongoUserService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(userAndPassAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String msg = msgSource.getMessage("auth.invalidToken", null, Locale.forLanguageTag(LocaleContextHolder.getLocale().getLanguage()));
        http.httpBasic().disable().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/panel").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/register").permitAll()
                .antMatchers("/nodeapi/**").permitAll()
                .antMatchers("/api/v1/test").permitAll()
                .antMatchers("/api/v1/**").hasAuthority(Roles.USER.getRole())
                .antMatchers("/api/v1/**").hasAuthority(Roles.ADMIN.getRole())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf()
                .disable().exceptionHandling()
                .authenticationEntryPoint(new AuthExceptionEntryPoint(msg)).and()
                .apply(new JwtConfigurer(jwtTokenProvider));

    }

    private AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized");
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}