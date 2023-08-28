package com.yallahnsafro.yallahnsafrobackend.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.SpringApplicationContext;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    UserService userService;
    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserDto creds = null;
        try {
            creds = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //  we used it //we can request info in the body as a jason request anass method https://youtu.be/VVn9OG9nfH0?t=4083 will do later
        //String email = request.getParameter("email");
        //String password = request.getParameter("password");
       // UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password,new ArrayList<>());

        //return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String email = ((User) authentication.getPrincipal()).getUsername();
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDtoLogged = userService.getUserByEmail(email);
        User springUser = ((User) authentication.getPrincipal());
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));



        String access_token = Jwts.builder()
                .setSubject(springUser.getUsername())
                .claim("login", springUser.getUsername())
                .claim("droits", userDtoLogged.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET).compact();

        String refresh_token = Jwts.builder()
                .setSubject(springUser.getUsername())
                .claim("login", springUser.getUsername())
                .claim("droits", userDtoLogged.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();


        response.setHeader("access_token",access_token);
        response.setHeader("refresh_token",refresh_token);


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Le email ou le mot de passe est incorrect. Veuillez réessayer.";
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "Votre compte est désactivé. Veuillez contacter votre agent. ";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "Votre mot de passe a expiré et doit être changé.";
        } else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
            errorMessage = "Votre compte est Bloqué temporairement pour 10 minutes.";
        } else if (exception.getMessage().equalsIgnoreCase("User credentials have expired")) {
            errorMessage = "Votre mot de passe a expiré et doit être changé.";
        }
        logger.debug("Authentification fail: " + errorMessage);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(errorMessage);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
