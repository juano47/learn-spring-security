package com.baeldung.lsso.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//anotacion para activar el procesamiento de las anotaciones @Pre y @Post
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.authorizeRequests()
                /*
                El método AuthorizeRequests() permite especificar las reglas de acceso para nuestros puntos finales.

                Así que aquí, por ejemplo, permitimos el acceso al punto final de los proyectos GET solo si la instancia de autenticación contiene la autoridad SCOPE_read .

                Esto será cierto si al Cliente se le otorgó el alcance de lectura al solicitar el token de acceso.
                 */
              .antMatchers(HttpMethod.GET, "/api/projects/**")
                .hasAuthority("SCOPE_read")
              .antMatchers(HttpMethod.POST, "/api/projects")
                .hasAuthority("SCOPE_write")
              .anyRequest()
                .authenticated()
            .and()
              .oauth2ResourceServer()
                .jwt();
    }//@formatter:on

}