package com.saman.jsf;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Saman on 4/4/2015.
 */
@WebFilter(filterName = "MobileFilter", urlPatterns = {"*.xhtml"})
public class MobileFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            try {

                //   HttpServletRequest   req =(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

                //    doGet(((HttpServletRequest) request), ((HttpServletResponse) response));
                if(((HttpServletRequest)request).getHeader("User-Agent").indexOf("Mobile") != -1) {
                    String contextPath = ((HttpServletRequest)request).getContextPath();
                    ((HttpServletResponse)response).sendRedirect(contextPath + "/home/loginMobile.xhtml");

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


