package com.saman.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Saman on 2/4/2015.
 */

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

    private static final long serialVersionUID = 1520318172495977648L;

    /**
     * Redirect to login page.
     * @return Login page name.
     */
    public String redirectToLogin() throws IOException {
       return "/home/login.xhtml?faces-redirect=true";

      //  return "/home/loginMobile.xhtml?faces-redirect=true";
       //     return "pm:first?transition=flip";

    }
    public String redirectToLoginCaptcha() {
       return "/home/loginCaptcha.xhtml?faces-redirect=true";
   // return "pm:second?transition=flip";
    }
    public String redirectToLoginMbCaptcha() {
     //   return "/home/loginCaptcha.xhtml?faces-redirect=true";
        return "pm:second?transition=flip";
    }
    public String ToLoginCaptcha() {
        return "/home/loginCaptcha.xhtml";
      //  return "pm:second";
    }
    public String ToPasswordRecovery() {
          return "/home/loginCaptcha.xhtml";
        // return "pm:forget";
    }
    public String toLogin() {
        return "/home/login.xhtml";
     //   return "pm:first";
    }
    public String toLoginMobile() {
        //  return "/home/login.xhtml";
        return "pm:first";
    }


    public String redirectToInfo() {
        return "/updating.xhtml?faces-redirect=true";
    }

    /**
     * Go to info page.
     * @return Info page name.
     */
    public String toInfo() {
        return "/updating.xhtml";
    }

    /**
     * Redirect to welcome page.
     * @return Welcome page name.
     */
    public String redirectToGame() {
        return "/secured/game.xhtml?faces-redirect=true";
    }
    public String toMobileGame() {
        return "/secured/mobileGame.xhtml?faces-redirect=true";
    }
    /**
     * Go to welcome page.
     * @return Welcome page name.
     */
    public String toGame() {
        return "/secured/game.xhtml";
    }
    public String redirecttoPoker() {
        return "/secured/poker.xhtml?faces-redirect=true";
    }
}