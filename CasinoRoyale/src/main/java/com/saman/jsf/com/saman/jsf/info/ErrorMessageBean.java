package com.saman.jsf.com.saman.jsf.info;

import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by Saman on 2/24/2015.
 */
@ManagedBean
@SessionScoped
public class ErrorMessageBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getInvalidUsr() {
        return invalidUsr;
    }

    public void setInvalidUsr(String invalidUsr) {
        this.invalidUsr = invalidUsr;
    }

    public String getPta() {
        return pta;
    }

    public void setPta(String pta) {
        this.pta = pta;
    }

    private String invalidUsr = "Invalid Username!";
    private String pta = "Please Try Again";
    public void errinvaliduser(){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        invalidUsr,
                        pta));
      //  FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, invalidUsr, pta);
      //  RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
}
