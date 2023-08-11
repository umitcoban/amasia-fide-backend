package com.umityasincoban.amasia_fide.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String ipAddress = httpRequest.getRemoteAddr();
        String language = httpRequest.getHeader("Accept-Language");

        logger.info("IP Address: {}", ipAddress);
        logger.info("Language: {}", language);

        chain.doFilter(request, response);  //
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
