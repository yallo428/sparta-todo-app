package org.example.spartatodoapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spartatodoapp.jwt.JwtUtil;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class AuthFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(JwtUtil.AUTHORIZATION_HEADER);
        String userName = jwtUtil.getTokenSubject(token);

        log.info("필터 진입");

        request.setAttribute("userName", userName);
        chain.doFilter(request, response);
    }
}
