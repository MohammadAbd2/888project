package com.saman.jsf.com.saman.jsf.messages;

import com.saman.jsf.Api;
import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import com.saman.jsf.com.saman.jsf.info.AccountInformationBean;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Saman on 2015-07-05.
 */
@ManagedBean
@ViewScoped
public class Messages implements Serializable {
    Api api;
UserDAO dao;
    FacesContext ctx = FacesContext.getCurrentInstance();
    String server = ctx.getExternalContext().getInitParameter("ipPort");
    String myapi = ctx.getExternalContext().getInitParameter("mavenPassword")+"&JSON=Yes";

    public void initmsg(){
        dao = new UserDAO();
        dao.setMessageDescription("Content...");
        offlineMsg = dao.getMsgForUser(loginBean.getUserN());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    private String statusColor;
    public ArrayList<Messages> getOfflineMsg() {
        return offlineMsg;
    }

    public void setOfflineMsg(ArrayList<Messages> offlineMsg) {
        this.offlineMsg = offlineMsg;
    }

    private ArrayList<Messages> offlineMsg;

    public void saveOfflineMsg() {
        if (getUserOfflineMessage() == null || StringUtils.isBlank(getUserOfflineMessage()) || getSubject() == null || StringUtils.isBlank(getSubject())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Warning",
                            "Message field and subject can't empty."));
            RequestContext.getCurrentInstance().update("FormMenu:messages");
        } else {
            dao = new UserDAO();
            dao.setOfflineMessages(accountInformationBean.getUsername(), getSubject(), getUserOfflineMessage());
        }
    }
    public String getUserOfflineMessage() {
        return userOfflineMessage;
    }

    public void setUserOfflineMessage(String userOfflineMessage) {
        this.userOfflineMessage = userOfflineMessage;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public String getNumberColor() {
        return numberColor;
    }

    public void setNumberColor(String numberColor) {
        this.numberColor = numberColor;
    }

    private static String numberColor;
    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberUnreadMsg() {
        return numberUnreadMsg;
    }

    public void setNumberUnreadMsg(int numberUnreadMsg) {
        this.numberUnreadMsg = numberUnreadMsg;
    }


    private String userOfflineMessage;
    private String subject;
    private Date date;
    private String status;
    private static int numberUnreadMsg;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url = server + myapi;

private String news;

    public String getUserOnlineMessage() {
        return userOnlineMessage;
    }

    public void setUserOnlineMessage(String userOnlineMessage) {
        this.userOnlineMessage = userOnlineMessage;
    }

    private String userOnlineMessage;
    public String getOnlineMessages() {
        return onlineMessages;
    }

    public void setOnlineMessages(String onlineMessages) {
        this.onlineMessages = onlineMessages;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    private String onlineMessages;


    public AccountInformationBean getAccountInformationBean() {
        return accountInformationBean;
    }

    public void setAccountInformationBean(AccountInformationBean accountInformationBean) {
        this.accountInformationBean = accountInformationBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    @ManagedProperty(value = "#{accountInformationBean}")
private AccountInformationBean accountInformationBean;
    public Messages() {
        try {
            api = new Api();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setSiteNews() throws JSONException {
        String cmdNews = "&Command=SystemSet&property=SiteNews&Value=";
        api.sendPost(getUrl(),cmdNews+getNews());

        if (api.jsobj.getString("Result").equals("Ok")) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Site news has been saved."));
        }else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Warning",
                            "Site news can't saved!"));

        }
        RequestContext.getCurrentInstance().update("FormMenu:messages");
    }

    public void setOnlineMessage() throws JSONException {
        if (getOnlineMessages() == null || StringUtils.isBlank(getOnlineMessages())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Warning",
                            "Message field can't empty!"));
        } else {
            String cmdConList = "&Command=ConnectionsList&Fields=SessionID";
            api.sendPost(getUrl(), cmdConList);
            if (api.jsobj.getString("Result").equals("Ok")) {
                JSONArray arrSID = api.jsobj.getJSONArray("SessionID");
                for (int i = 0; i < arrSID.length(); i++) {
                    String sessionId = arrSID.getString(i);
                    String cmdMessage = "&Command=ConnectionsMessage&Message=" + getOnlineMessages() + "&SessionID=" + sessionId;
                    api.sendPost(getUrl(), cmdMessage);
                }
                //   if (api.jsobj.getString("Result").equals("Ok")) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Message to online user has been sent."));
                //    }
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Warning",
                                "Message to online user can't be sent!"));
            }
        }
        RequestContext.getCurrentInstance().update("FormMenu:messages");
    }
    public void sendMessageOnlineUser() throws Exception {
        if(getUserOnlineMessage() == null || StringUtils.isBlank(getUserOnlineMessage())){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Warning",
                            "Message field can't empty!"));
        }else {
            accountGet(accountInformationBean.getUsername());
            String sessionId = api.jsobj.getString("SessionID");

            String cmdMessage = "&Command=ConnectionsMessage&Message=" + getUserOnlineMessage() + "&SessionID=" + sessionId;
            api.sendPost(getUrl(), cmdMessage);
            //  System.out.println(getUrl()+cmdMessage);
            if (api.jsobj.getString("Result").equals("Ok")) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Message to " + accountInformationBean.getUsername() + " has been sent."));

            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                accountInformationBean.getUsername() + " is offline! Try offline messages feature."));
            }
        }
        RequestContext.getCurrentInstance().update("FormMenu:messages");
    }

    public void accountGet(String userNm) throws Exception {
        String account = "&Command=AccountsGet&Player=" + userNm;
        api.sendPost(url, account);
    }

}
