package com.saman.jsf;



import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by Saman on 3/30/2015.
 */
@WebFilter(filterName = "ViewFilter", urlPatterns = {"*.xhtml"})
public class ViewFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String userAgent = request.getHeader( "user-agent" );
        String accept = request.getHeader( "Accept" );

        if (userAgent != null && accept != null) {
         /*   UserAgentInfo agent = new UserAgentInfo( userAgent, accept );
            if (agent.isMobileDevice() || DetectMobileBrowser.isMobile( userAgent )) {
                response.sendRedirect(request.getContextPath() + "/indexMobile.xhtml");
            }*/
        }
        chain.doFilter(req, res); // Logged-in user found, so just continue request.
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}

