package com.saman.jsf.com.saman.jsf.moneymaking;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import org.primefaces.json.JSONException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Saman on 5/19/2015.
 */
@ManagedBean(name = "downlines")
@ViewScoped
public class Downlines implements Serializable {

    private ArrayList<Downlines> messages;

   /* public void init(){
        messages = getMessages();
    }*/

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
private UserDAO dao;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;


    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }

    private String payout;

    public String getRakeColor() {
        return rakeColor;
    }

    public void setRakeColor(String rakeColor) {
        this.rakeColor = rakeColor;
    }

    private String rakeColor = "white";

   @PostConstruct
    public void init() {
       dao = new UserDAO();
       messages = dao.getDownlines(loginBean.getUserN());

    }

        public ArrayList<Downlines> getMessages() throws IOException, JSONException {

               return messages;
          }

    }
/*public class Bean {

    private SomeObject someProperty;

    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        someProperty = loadSomeProperty();
    }

    public void preRender(ComponentSystemEvent event) {
        // Or in some SystemEvent method (e.g. <f:event type="preRenderView">).
        someProperty = loadSomeProperty();
    }

    public void change(ValueChangeEvent event) {
        // Or in some FacesEvent method (e.g. <h:inputXxx valueChangeListener>).
        someProperty = loadSomeProperty();
    }

    public void ajaxListener(AjaxBehaviorEvent event) {
        // Or in some BehaviorEvent method (e.g. <f:ajax listener>).
        someProperty = loadSomeProperty();
    }

    public void actionListener(ActionEvent event) {
        // Or in some ActionEvent method (e.g. <h:commandXxx actionListener>).
        someProperty = loadSomeProperty();
    }

    public String submit() {
        // Or in Action method (e.g. <h:commandXxx action>).
        someProperty = loadSomeProperty();
        return "outcome";
    }

    public SomeObject getSomeProperty() {
        // Just keep getter untouched. It isn't intented to do business logic!
        return someProperty;
    }

}*/

