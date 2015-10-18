package com.saman.jsf;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Saman on 2015-07-06.
 */
@WebFilter(filterName = "BrowserFilter", urlPatterns = {"*.xhtml"})
public class BrowserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String browser = "on";

            try {
                if (browser.equals("on")) {
                    boolean firefox = false;
                    boolean chrome = false;
                    boolean safari = false;
                    String contextPath = ((HttpServletRequest) request).getContextPath();
                 //   String winAppUserAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:29.0) Gecko/20100101 /29.0";
                  //  Mozilla/5.0 (Windows NT 5.1; rv:29.0) Gecko/20100101 /29.0
                    if (((HttpServletRequest)request).getHeader("User-Agent").indexOf("Mobile") != -1 ) {

                    }
                    else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Safari") != -1 ) {
                        safari = true;
                        ((HttpServletResponse) response).sendRedirect("http://google.com");
                    } else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Firefox") != -1 ) {
                        firefox = true;
                        ((HttpServletResponse) response).sendRedirect("http://google.com");
                    } else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Chrome") != -1 ) {
                        chrome = true;
                        ((HttpServletResponse) response).sendRedirect("http://google.com");
                    }
                    else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("rv:29.0) Gecko/20100101 /29.0") != -1 && firefox == false && safari == false && chrome == false) {
                     //   ((HttpServletResponse)response).sendRedirect(contextPath + "/updating.xhtml");
                    }
                    // ((HttpServletRequest)request).getHeader("User-Agent").indexOf("Mobile") != -1 ||

                    else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Safari") != -1 && ((HttpServletRequest) request).getHeader("User-Agent").indexOf("Macintosh") != -1) {
                        safari = true;
                        ((HttpServletResponse) response).sendRedirect("http://google.com");
                    } else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Firefox") != -1 && ((HttpServletRequest) request).getHeader("User-Agent").indexOf("Macintosh") != -1) {
                        firefox = true;
                        ((HttpServletResponse) response).sendRedirect("http://google.com");
                    } else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Chrome") != -1 && ((HttpServletRequest) request).getHeader("User-Agent").indexOf("Macintosh") != -1) {
                        chrome = true;
                        ((HttpServletResponse) response).sendRedirect("http://google.com");
                    } else if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Macintosh") != -1 && firefox == false && safari == false && chrome == false) {

                    } else {

                        //   ((HttpServletResponse)response).sendRedirect(contextPath + "/AccessDenied.xhtml");
                        ((HttpServletResponse) response).sendRedirect("http://google.com");
                    }
                }
                chain.doFilter(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public void destroy() {

    }
}

