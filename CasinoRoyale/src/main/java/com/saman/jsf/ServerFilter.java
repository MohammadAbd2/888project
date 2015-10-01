package com.saman.jsf;



import org.primefaces.json.JSONException;

import javax.faces.context.FacesContext;
import javax.servlet.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Saman on 2/4/2015.
 */
@WebFilter(filterName = "ServerFilter", urlPatterns = {"*.xhtml"})
public class ServerFilter implements Filter {
   LoginBean srv;

    public ServerFilter() throws IOException,  org.json.JSONException, JSONException {
        srv = new LoginBean();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String contextPath = ((HttpServletRequest)request).getContextPath();
         //   HttpServletRequest   req =(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (srv.sereverCheck()==false){

                ((HttpServletResponse)response).sendRedirect(contextPath + "/updating.xhtml");
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
