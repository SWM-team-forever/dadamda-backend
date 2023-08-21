package com.forever.dadamda.filter;

import com.forever.dadamda.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String token = tokenService.resolveToken((HttpServletRequest) request);
        log.info("JwtAuthFilter token: {}", token);
        if (token != null && tokenService.validateToken(token)) {
            String email = tokenService.getEmail(token);
            log.info("JwtAuthFilter email: {}", email);

            Authentication auth = new UsernamePasswordAuthenticationToken(email, "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            log.info("JwtAuthFilter auth: {}", auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}