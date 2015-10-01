package com.saman.jsf.com.saman.jsf.moneymaking;

import com.saman.jsf.Api;
import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import com.saman.jsf.com.saman.jsf.autocomplete.JqueryAutoComplete;
import com.saman.jsf.com.saman.jsf.email.MailServer;
import com.saman.jsf.com.saman.jsf.email.Sendmail;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
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
 * Created by Saman on 3/10/2015.
 */
@ManagedBean
@SessionScoped
public class AddUser implements Serializable {
    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    private Api api;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    public AddUser()  {

        try {

            api = new Api();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String username;
    private String email;
    private String realname;
    private String password;

    public boolean isCheckUN() {
        return checkUN;
    }

    public void setCheckUN(boolean checkUN) {
        this.checkUN = checkUN;
    }

    private boolean checkUN;

    //  String url = "http://188.138.41.216:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
   // String url = "http://localhost:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    // String url = "http://87.247.179.233:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    FacesContext ctx = FacesContext.getCurrentInstance();
    String server = ctx.getExternalContext().getInitParameter("ipPort");
    String myapi = ctx.getExternalContext().getInitParameter("mavenPassword")+"&JSON=Yes";
    String url = server + myapi;
    public String getUrl() {
        return url;
    }


    String msgContent;
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(50, random).toString(32);
    }
public void adduserController(){
    password = nextSessionId();
  //  System.out.println(password);
    String account = "&Command=AccountsAdd&Log=no";

        String pl = "&Player=" + getUsername();
        String accountParam = account + pl + "&RealName=" + getRealname() + "&Location=teh" + "&Email=" + getEmail() + "&PW=" + getPassword() + "&Chat=9999-99-99 99:99";
        // String test = url+accountParam;
        api.sendPost(url, accountParam);

}
    public void addChip(int chip,String user){
String addchip = "&Command=AccountsIncBalance";
        String pl = "&Player=" + user;
        String amount = "&Amount="+chip;
        String accountParam = addchip +pl +amount;
        api.sendPost(url, accountParam);
    }
    public void decChip(int chip,String user){
        String decchip = "&Command=AccountsDecBalance";
        String pl = "&Player=" + user;
        String amount = "&Amount="+chip;
        String accountParam = decchip +pl +amount;
        api.sendPost(url, accountParam);
    }

    public void showDialogMmaking(){

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgMmaking').show()");

    }


public boolean checkUsername(String user)  {
    //    if(!FacesContext.getCurrentInstance().isPostback()) {
            JqueryAutoComplete userList = new JqueryAutoComplete();
            userList.checkUserList();
            //  System.out.println(userList.getArrayP());
            String[] listP = userList.getArrayP().split(",");
            // System.out.println(getUsername());
            for (int i = 0; i < listP.length; i++) {
                if (listP[i].equalsIgnoreCase(user)) {
                    checkUN = false;
                    break;
                } else {
                    checkUN = true;
                }

            }

         //   System.out.println(checkUN);
            return checkUN;

}
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public void inviteUser() throws Exception {
    //    StringUtils su = new StringUtils();
        if (getUsername() == null || StringUtils.isBlank(getUsername()) || getEmail() == null || StringUtils.isBlank(getEmail()) || getRealname() == null || StringUtils.isBlank(getRealname())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "All fields is required!"));
        } else if (getUsername().length() < 3 || getUsername().length()>12 ){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Username must between 3-12 character!"));
        } else  if (!isValidEmailAddress(getEmail())){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Invalid Email Format!"));
        }

        else {

            checkUsername(getUsername());
            //   System.out.println(checkUN);
            if (!isCheckUN()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "",
                                getUsername() + " has been Exist!"));
            } else {
                if(getEmail().indexOf("&") != -1){
                    setEmail(getEmail().replaceAll("&",""));
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "Access Denied!"));
                    return;
                }
                else  if(getRealname().indexOf("&") != -1){
                    setRealname(getRealname().replaceAll("&",""));
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "Access Denied!"));
                    return;
                }
                else  if(getUsername().indexOf("&") != -1){
                    setUsername(getUsername().replaceAll("&", ""));
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "Access Denied!"));
                    return;
                }
                adduserController();
                if (api.jsobj.getString("Result").equals("Ok")) {
                    UserDAO dao = new UserDAO();
                    dao.addUserDB(getUsername(), loginBean.getUserN());
                    dao.addNewUserRequest(getUsername(), loginBean.getUserN());
                    String subject = "888 Account Information";
                    String Msg = "<h2 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 18px; font-weight: normal;" + "\"" + ">Dear Friend<br/>You are invited to 888Casino.</h2>" +
                            "<h3 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">Your account information is:</h3><br/>" +
                            "<h4 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.1); padding: 15px 20px; margin-top:0;" + "\"" + ">Username: <b>" + getUsername() + "</b><br/>Password: <b>" + getPassword() + "</b></h4>";

                    FacesContext ctx = FacesContext.getCurrentInstance();
                    String mailServer = ctx.getExternalContext().getInitParameter("mailServer");
                    if (mailServer.equals("gmail")) {
                        Sendmail mail = new Sendmail();
                        mail.sendEmail(getEmail(), subject, Msg);
                    if (mail.isMailResult()) {

                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "Success",
                                        "Account created successfully and information send to " + getEmail()));
                        //    setBtndisable(true);
                    } else {

                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "unSuccess",
                                        "Mail Server Error!"));
                        //   setBtndisable(true);
                    }
                }else{
                        MailServer mail = new MailServer();
                        mail.sendEmail(getEmail(), subject, Msg);
                        if (mail.isMailResult()) {

                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Success",
                                            "Account created successfully and information send to " + getEmail()));
                            //    setBtndisable(true);
                        } else {

                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "unSuccess",
                                            "Mail Server Error!"));
                            //   setBtndisable(true);
                        }

                    }
                }
                    else {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "unSuccess",
                                    "Can't connect to poker server!"));
                }

            }
        }
    }

}
