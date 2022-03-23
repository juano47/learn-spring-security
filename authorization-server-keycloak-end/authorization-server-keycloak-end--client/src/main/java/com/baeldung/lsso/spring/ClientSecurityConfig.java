package com.baeldung.lsso.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@EnableWebSecurity
public class ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    //Configuramos el login con OAuth2
    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
                //si usamos solo  .oauth2Client() (solo da funcionalidad de autorizacion) tendremos un cliente OAuth2 pero no lo
                //usamos para autenticar nuestra aplicacion, necesitariamos una forma extra de loguearse
            .oauth2Login()
            .and()
            .logout().logoutSuccessUrl("/");
    }// @formatter:on


    //INTERCEPTOR QUE AGREGA EL TOKEN AL RESTTEMPLATE/WEBCLIENT
    /*
    We need to add a filter to the WebClient configuration to include the token we obtain when logging in as a Bearer token in the requests we make.

    - ClientRegistrationRepository: a repository of the registered OAuth Clients
    - OAuth2AuthorizedClientRepository: stores the information of Clients that have already been authorized, like for example, the Access Token issued to it
     */
    @Bean
    WebClient webClient(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, authorizedClientRepository);

        /*
        we first need to be authenticated using OAuth2 to discover the client configuration to use
        the Bearer token will be included automatically in every request we make with the WebClient
        */
        oauth2.setDefaultOAuth2AuthorizedClient(true);

        return WebClient.builder()
            .apply(oauth2.oauth2Configuration())
            .build();
    }

}