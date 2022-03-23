package com.baeldung.lsso.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserInfoController {

    //Se puede acceder a la info del token con este endpoint
    @GetMapping("/user/info")
    public Map<String, Object> getUserInfo(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        return Collections.singletonMap("token", jwt);
    }

    @GetMapping("/user/info/direct")
    //También podemos obtener una referencia al Principal directamente usando la anotación @AuthenticationPrincipal
    //mejor usar esto para evitar la conversion a Jwt que sucede en la funcion anterior
    public Map<String, Object> getDirectUserInfo(@AuthenticationPrincipal Jwt principal) {
        String username = (String) principal.getClaims().get("preferred_username");

        return Collections.singletonMap("username", username);
    }

}
