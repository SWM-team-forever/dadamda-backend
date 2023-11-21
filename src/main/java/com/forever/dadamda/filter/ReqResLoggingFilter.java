package com.forever.dadamda.filter;

import static java.lang.System.currentTimeMillis;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class ReqResLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long staratTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long endTime = System.currentTimeMillis();

        try {
            log.info("Request URI: {}, Method: {}, Request Body: {}, Response Body: {}, Response Status: {}, Response Time: {}ms",
                    requestWrapper.getRequestURI(),
                    requestWrapper.getMethod(),
                    new String(requestWrapper.getContentAsByteArray()),
                    new String(responseWrapper.getContentAsByteArray()),
                    responseWrapper.getStatus(),
                    endTime - staratTime);

            responseWrapper.copyBodyToResponse();
        } catch (Exception e) {
            log.error("Error occurred while logging request and response", e);
        }
    }
}
