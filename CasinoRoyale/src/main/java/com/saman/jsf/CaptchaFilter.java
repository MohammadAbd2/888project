package com.saman.jsf;

import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Saman on 2/4/2015.
 */

@WebFilter(filterName = "CaptchaFilter", urlPatterns = {"*.xhtml"})
public class CaptchaFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  {
            try {
                String ip = ((HttpServletRequest) request).getHeader("X-FORWARDED-FOR");
                if (ip == null) {
                    ip = ((HttpServletRequest) request).getRemoteAddr();
                }
                LoginBean loginBean = (LoginBean) ((HttpServletRequest) request).getSession().getAttribute("loginBean");
if (loginBean != null) {
  //  if (loginBean.session.getAttribute("captcha") != null) {
        if (loginBean.isCaptcha() && ip.equals(loginBean.getIpAddress())) {
            if (((HttpServletRequest) request).getHeader("User-Agent").indexOf("Mobile") != -1) {
                String contextPath = ((HttpServletRequest) request).getContextPath();
                ((HttpServletResponse) response).sendRedirect(contextPath + "/AccessDenied.xhtml");
            } else {
                String contextPath = ((HttpServletRequest) request).getContextPath();
                ((HttpServletResponse) response).sendRedirect(contextPath + "/home/loginCaptcha.xhtml");
            }
        }
   // }
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


