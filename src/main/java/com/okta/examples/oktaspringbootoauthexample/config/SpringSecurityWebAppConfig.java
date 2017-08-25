package com.okta.examples.oktaspringbootoauthexample.config;


import com.okta.examples.oktaspringbootoauthexample.jwt.JWTFilter;
import com.okta.examples.oktaspringbootoauthexample.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Configuration
@EnableOAuth2Sso
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenProvider tokenProvider;

    @Value("#{ @environment['okta.moreSecure.authorities'] }")
    String[] moreSecureAuthorities;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/more-secure").hasAnyAuthority(moreSecureAuthorities)
            .anyRequest().authenticated()
            .and()
            .logout().logoutSuccessUrl("/")
            .and()
            .addFilterBefore(new JWTFilter(tokenProvider), BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthoritiesExtractor authoritiesExtractor(OAuth2RestOperations template) {
        return map -> {
            List<String> groups = (List<String>) map.get("groups");
            return AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", groups));
        };
    }

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        return new OAuth2RestTemplate(resource, context);
    }
}
