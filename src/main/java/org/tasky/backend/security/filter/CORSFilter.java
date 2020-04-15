package org.tasky.backend.security.filter;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {


    public static final String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String MAX_AGE = "Access-Control-Max-Age";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader(ALLOW_ORIGIN, "*");
        response.setHeader(ALLOW_METHODS, "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader(MAX_AGE, "3600");
        response.setHeader(ALLOW_HEADERS, "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
