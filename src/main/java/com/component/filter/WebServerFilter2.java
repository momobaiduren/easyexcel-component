package com.component.filter;

import com.component.utils.WebUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * created by zhanglong and since  2019/11/26  5:33 下午
 *
 * @description: 描述
 */
@Order(2)
@Component
@WebFilter(filterName = "webServerFilter2", urlPatterns = "/")
public class WebServerFilter2 implements Filter {

    @Override
    public void init( FilterConfig filterConfig ) {

    }

    @Override
    public void doFilter( ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
