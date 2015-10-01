package com.saman.jsf.com.saman.jsf.info;

import com.saman.jsf.Api;
import com.saman.jsf.NavigationBean;
import com.saman.jsf.com.saman.jsf.email.MailServer;
import com.saman.jsf.com.saman.jsf.email.Sendmail;
import org.apache.commons.mail.EmailException;
import org.primefaces.json.JSONException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Saman on 3/16/2015.
 */
@ManagedBean
@SessionScoped
public class PasswordRecovery implements Serializable {
    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    @ManagedProperty(value="#{navigationBean}")
    private NavigationBean navigationBean;
    Api api;

    public PasswordRecovery()  {

        try {
            api = new Api();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //  String url = "http://188.138.41.216:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
   // String url = "http://localhost:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    // String url = "http://87.247.179.233:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    FacesContext ctx = FacesContext.getCurrentInstance();
    String server = ctx.getExternalContext().getInitParameter("ipPort");
    String myapi = ctx.getExternalContext().getInitParameter("mavenPassword")+"&JSON=Yes";
    String url = server + myapi;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private String username;
    private  String email;

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(50, random).toString(32);
    }
    public void sendPass(){
        if(getUsername().indexOf("&") != -1){
           setUsername(getUsername().replaceAll("&", ""));
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Access Denied!",
                            ""));
            return;
        }
        else  if(getEmail().indexOf("&") != -1){
              setEmail(getEmail().replaceAll("&", ""));
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Access Denied!",
                            ""));
            return;
        }
        String account = "&Command=AccountsEdit";
     String   player = getUsername();
        String pl = "&Player=" + player;
        String accountParam =account + pl +"&PW=" +getPassword();
        api.sendPost(url, accountParam);
    }
    public void changePass() throws JSONException, EmailException {

        try {
            accountGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (api.jsobj.getString("Result").equals("Ok") && api.jsobj.getString("Email").equals(getEmail())) {
            password = nextSessionId();
            sendPass();
            String subject = "New Password Recovery";
            String Msg = "<h2 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 18px; font-weight: normal;" + "\"" + ">Dear Friend<br/>Your Password has been Recovery.</h2>" +
                    "<h3 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">Your account information is:</h3><br/>" +
                    "<h4 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.1); padding: 15px 20px; margin-top:0;" + "\"" + ">Username: <b>" + getUsername() + "</b><br/>Password: <b>" + getPassword() + "</b></h4>";

            FacesContext ctx = FacesContext.getCurrentInstance();
            String mailServer = ctx.getExternalContext().getInitParameter("mailServer");
            if (mailServer.equals("gmail")) {
                Sendmail mail = new Sendmail();
                mail.sendEmail(getEmail(), subject, Msg);
                if (mail.isMailResult()) {
                    //   navigationBean.ToPasswordRecovery();
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "New Password Sent to your Email",
                                    ""));
                } else {

                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "unSuccess",
                                    "Mail Server Error!"));
                    //   setBtndisable(true);
                }
            }
            else {
                MailServer mail = new MailServer();
                mail.sendEmail(getEmail(), subject, Msg);
                if (mail.isMailResult()) {
                    //   navigationBean.ToPasswordRecovery();
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "New Password Sent to your Email",
                                    ""));
                } else {

                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "unSuccess",
                                    "Mail Server Error!"));
                    //   setBtndisable(true);
                }
            }
        } else {
        //    navigationBean.ToPasswordRecovery();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Username or Email is Wrong!",
                            ""));
        }
    }

    public void accountGet() throws Exception {
        if(getUsername().indexOf("&") != -1){
            setUsername(getUsername().replaceAll("&", ""));
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Access Denied!",
                            ""));
         //   return;
        }
        String account = "&Command=AccountsGet";
       String user = "&Player=" + getUsername();

        String accountParam = account + user;
        api.sendPost(url, accountParam);

    }

}
