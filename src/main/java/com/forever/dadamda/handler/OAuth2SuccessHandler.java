package com.forever.dadamda.handler;

import com.forever.dadamda.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${login.redirect.url}")
    private String LOGIN_REDIRECT_URL;

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("onAuthenticationSuccess oAuth2User: {}", oAuth2User.getAttributes());
        String email = oAuth2User.getAttribute("email");

        String token = tokenService.generateToken(email, "USER");
        log.info("onAuthenticationSuccess token: {}", token);
        log.info("onAuthenticationSuccess redirect url: {}", LOGIN_REDIRECT_URL+token);
        response.sendRedirect(LOGIN_REDIRECT_URL+token);
    }
}
