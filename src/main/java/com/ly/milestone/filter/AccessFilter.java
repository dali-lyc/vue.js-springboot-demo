package com.ly.milestone.filter;

/**
 * Created by lyc28724 on 2017/3/30.
 */

import com.ly.milestone.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ly.milestone.utils.Constants.ACCESS_TOKEN;

public class AccessFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 访问系统页面，检查cookie中是否有token信息
        String accessToken = WebUtils.getCookieValueByName(request, ACCESS_TOKEN);
        if (StringUtils.isNotEmpty(accessToken)) {
//            request.setAttribute(ACCESS_TOKEN, accessToken);
            chain.doFilter(req, resp);
        } else {
            String returnUri = request.getRequestURL().toString();
            request.setAttribute("returnUri", returnUri);
            request.getRequestDispatcher("/oauth/applyfortoken").forward(req,resp);
        }
    }

    @Override
    public void destroy() {

    }
}
