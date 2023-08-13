package com.yallahnsafro.yallahnsafrobackend.security;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.SpringApplicationContext;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Map;


public class AuthorizationFilter extends BasicAuthenticationFilter {;
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);
            if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstants.TOKEN_SECRET)
                        .parseClaimsJws(header.replace(SecurityConstants.TOKEN_PREFIX, ""))
                        .getBody();
                String username = claims.getSubject();
                String role = (String) claims.get("droits");
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                authorities.add(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(request, response);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                logger.error("Token expired :" + e.getMessage());

            } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IOException | IllegalArgumentException | ServletException e) {
                logger.error("Some other exception in JWT parsing :" + e.getMessage());
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (user != null) {
                UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
                UserDto userDto = userService.getUserForLogin(user);
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                authorities.add(new SimpleGrantedAuthority(userDto.getRole().name()));

                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }

        return null;
    }



}
