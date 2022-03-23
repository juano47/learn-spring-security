package com.baeldung.lsso.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter;
        http.authorizeRequests()
              .anyRequest()
                .authenticated()
            .and()
              .oauth2ResourceServer()
                .jwt();
        /*
        Here, we've used 2 different objects: a JwtDecoder which is in charge of decoding String tokens into validated
        instances of the Jwt class, and a Converter that maps this Jwt instance to an Authentication object.
            .decoder(customDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter);
        //Now, Spring Security supplies a default converter JwtGrantedAuthoritiesConverter that maps the scopes to
        authorities prefixed with SCOPE_. So, normally, we don’t need to configure the converter, unless we have special
        requirements on how to extract the authorities from the JWTs.
         */
    }//@formatter:on

   /* @Bean
    public JwtDecoder customDecoder() {
        return new JwtDecoder() {
            @Override
            public Jwt decode(String s) throws JwtException {
                return null;
            }
        };
    }*/
}