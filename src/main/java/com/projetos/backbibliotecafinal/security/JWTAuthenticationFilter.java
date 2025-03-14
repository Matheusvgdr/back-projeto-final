package com.projetos.backbibliotecafinal.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetos.backbibliotecafinal.dto.LoginUsuarioDto;
import com.projetos.backbibliotecafinal.dto.response.TokenResponse;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            var requestBody = request.getInputStream();
            var loginDto = new ObjectMapper().readValue(requestBody, LoginUsuarioDto.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.senha())
            );

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        var userDetails = ((UserDetails)authResult.getPrincipal());
        var email = userDetails.getUsername();
        var perfil = userDetails.getAuthorities()
                .stream().map(grantedAuthority -> grantedAuthority.getAuthority())
                .toList();

        var token = new TokenResponse(JwtUtils.generateToken(email, perfil));
        Json.mapper().writeValue(response.getWriter(), token);
        response.getWriter().flush();


    }

}