package com.saman.jsf.com.saman.jsf.info;

import com.saman.jsf.Api;
import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import com.saman.jsf.com.saman.jsf.cashier.RequestList;
import com.saman.jsf.com.saman.jsf.email.MailServer;
import com.saman.jsf.com.saman.jsf.email.Sendmail;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Saman on 2/13/2015.
 */
@ManagedBean
@SessionScoped
public class AccountInformationBean implements Serializable  {
    private static final long serialVersionUID = 1L;
    Api api;
    UserDAO dao;
    public AccountInformationBean()  {

        try {
            api = new Api();
            dao = new UserDAO();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public int getChipOnTable() {
        return ChipOnTable;
    }

    public void setChipOnTable(int chipOnTable) {
        ChipOnTable = chipOnTable;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    private String username;
    private  String Logins;
    private int TotalChip;
    private  String FirstLogin;
    private  String LastLogin;

    public String getBtnBlaclList() {
        return btnBlaclList;
    }

    public void setBtnBlaclList(String btnBlaclList) {
        this.btnBlaclList = btnBlaclList;
    }

    private String btnBlaclList;

    public String getBtnBlockStyle() {
        return btnBlockStyle;
    }

    public void setBtnBlockStyle(String btnBlockStyle) {
        this.btnBlockStyle = btnBlockStyle;
    }

    private String btnBlockStyle;
    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    private  String Location;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    private String rePassword;
    private String RealName;
    private int ChipOnTable;
    private String Email;
    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getFirstLogin() {
        return FirstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        FirstLogin = firstLogin;
    }

    public String getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
    }




    public String getLogins() {
        return Logins;
    }

    public void setLogins(String logins) {
        Logins = logins;
    }


    public int getTotalChip() {
        return TotalChip;
    }

    public void setTotalChip(int totalChip) {
        TotalChip = totalChip;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }







    String  user;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    String player;
    //  String url = "http://188.138.41.216:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
  //  String url = "http://localhost:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    // String url = "http://87.247.179.233:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    FacesContext ctx = FacesContext.getCurrentInstance();
    String folderAvatar = ctx.getExternalContext().getInitParameter("avatar");
    String server = ctx.getExternalContext().getInitParameter("ipPort");
    String myapi = ctx.getExternalContext().getInitParameter("mavenPassword")+"&JSON=Yes";
    String url = server + myapi;
    public void showDialogAccInfo(){
      /*  Map<String,Object> options = new HashMap<String, Object>();

        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
       // options.put("contentHeight", 460);

       RequestContext.getCurrentInstance().openDialog("dialog/accountInfo", options,null);*/

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgAccInfo').show()");

    }
    private SecureRandom random = new SecureRandom();
    public String nextSessionId() {
        return new BigInteger(50, random).toString(32);
    }
    public void adduserController(){
        password = nextSessionId();
        String account = "&Command=AccountsEdit&Log=no";
        String pl = "&Player=" + getUsername();
        String accountParam =account + pl+"&Email="+getEmail()+ "&PW=" +getPassword();
        api.sendPost(url, accountParam);

    }
    public void resetPassword() throws EmailException, JSONException {
        adduserController();
        if (api.jsobj.getString("Result").equals("Ok")) {
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
                                    "Password reset successfully and information sent to Mailbox of " + getUsername()));
                    //    setBtndisable(true);
                } else {

                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "unSuccess",
                                    "Mail Server Error!"));
                    //   setBtndisable(true);
                }
            } else {
                MailServer mail = new MailServer();
                mail.sendEmail(getEmail(), subject, Msg);
                mail.sendEmail(getEmail(), subject, Msg);
                if (mail.isMailResult()) {

                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    "Password reset successfully and information sent to Mailbox of " + getUsername()));
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

    public int getSlotBank() {
        return slotBank;
    }

    public void setSlotBank(int slotBank) {
        this.slotBank = slotBank;
    }

    public int getBankChip() {
        return bankChip;
    }

    public void setBankChip(int bankChip) {
        this.bankChip = bankChip;
    }

    private  int slotBank;
    private int bankChip;
    public void setuserInfo() throws JSONException {


String userN = api.jsobj.getString("Player");
        RealName = api.jsobj.getString("RealName");
        ChipOnTable = api.jsobj.getInt("RingChips") / 1000 ;
        Email = api.jsobj.getString("Email");
        bankChip = api.jsobj.getInt("Balance") / 1000 ;
        slotBank = dao.slotMachineBank(userN,-1)/1000;
TotalChip = slotBank + bankChip + ChipOnTable;
        FirstLogin = api.jsobj.getString("FirstLogin");
        LastLogin = api.jsobj.getString("LastLogin");
        Logins = api.jsobj.getString("Logins");
        Location = api.jsobj.getString("Location");
    }
    public void changeUserInfo() throws Exception {


        accountEdit();

        if (api.jsobj.getString("Result").equals("Ok"))  {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Information has been changed"));

  /*  FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "Information Changed");
    RequestContext.getCurrentInstance().showMessageInDialog(message);*/
            //  RequestContext context = RequestContext.getCurrentInstance();
            //  context.execute("PF('dlgAccInfo').hide()");
            //  UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            //  UIComponent component = viewRoot.findComponent("someId");
            //  RequestContext.getCurrentInstance().closeDialog("dialog/accountInfo");

        }


    }


    public void accountEdit()throws Exception{
        String account = "&Command=AccountsEdit&Log=no";
        player = getUsername();
        String pl = "&Player=" + player;
        String accountParam =account + pl +"&RealName=" +getRealName()+"&Location="+getLocation()+"&Email="+getEmail();
        // String test = url+accountParam;
        api.sendPost(url, accountParam);
   /* if(api.getsrv==false){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "unSuccess", "Can't Connect to Server");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }*/

    }

    public String getBtnBlockIcon() {
        return btnBlockIcon;
    }

    public void setBtnBlockIcon(String btnBlockIcon) {
        this.btnBlockIcon = btnBlockIcon;
    }

    private String btnBlockIcon;
    public void info() throws JSONException {
        //  String test = "";
        try {

            accountGet(getUsername());
            //  System.out.println(username);
            if (api.jsobj.getString("Result").equals("Ok")) {
                setuserInfo();
                if(!api.jsobj.getString("Title").equals("Blocked")) {
                    btnBlaclList = "Block";
                    btnBlockStyle ="color:green";
                    btnBlockIcon = "fa fa-cut";
                }else {
                    btnBlaclList = "unBlock";
                    btnBlockStyle ="color:red";
                    btnBlockIcon = "fa fa-ban";
                }
                showDialogAccInfo();
                //   test = "/secured/test.xhtml";
            } else {
                ErrorMessageBean err =new ErrorMessageBean();
                err.errinvaliduser();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void lastActionInfo(RequestList dl4) throws JSONException {
        //  String test = "";
        try {
username = dl4.getUsername();

            accountGet(username);
            //  System.out.println(username);
            if (api.jsobj.getString("Result").equals("Ok")) {
                setuserInfo();
                if(!api.jsobj.getString("Title").equals("Blocked")) {
                    btnBlaclList = "Block";
                    btnBlockStyle ="color:green";
                    btnBlockIcon = "fa fa-cut";
                }else {
                    btnBlaclList = "unBlock";
                    btnBlockStyle ="color:red";
                    btnBlockIcon = "fa fa-ban";
                }
                showDialogAccInfo();
                //   test = "/secured/test.xhtml";
            } else {
                ErrorMessageBean err =new ErrorMessageBean();
                err.errinvaliduser();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void accountGet(String userNm) throws Exception {
        //  doPost();
        String account = "&Command=AccountsGet&Log=no";
        user = "&Player=" + userNm;

        String accountParam = account + user;
        api.sendPost(url, accountParam);

    }

    public void terminate() throws Exception {
        try {
            accountGet(getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sessionId = api.jsobj.getString("SessionID");
        String cmdtrm = "&Command=ConnectionsTerminate&Log=no";
        String trm = "&SessionID=" + sessionId + cmdtrm;
        api.sendPost(url,trm);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success",
                        username+" has been Terminated!"));
    }
    public void blackList(){
        try {
            accountGet(getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String account = "&Command=AccountsEdit&Log=no";
       // player = getUsername();
        String pl = "&Player=" + getUsername();
        try {

                if (api.jsobj.getString("Title").equals("Blocked")){

                    String accountParam = account + pl + "&RealName=" + getRealName() + "&Title=";

                    api.sendPost(url, accountParam);
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    username+" has been unBlock!"));
                    btnBlaclList = "Block";
                    btnBlockStyle ="color:green";
                    btnBlockIcon = "fa fa-cut";

                }else {
                    String accountParam = account + pl + "&RealName=" + getRealName() + "&Title=Blocked";

                    api.sendPost(url, accountParam);
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    username+" has been Blocked!"));
                    btnBlaclList = "unBlock";
                    btnBlockStyle ="color:red";
                    btnBlockIcon = "fa fa-ban";
                }



        } catch (JSONException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "unSuccess",
                            "Can't Connect to Server!"));
        }
    }



    public ArrayList<AvatarName> getAname(){
        File dir = new File(folderAvatar);
        File[] listOfFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });
        ArrayList<AvatarName>  avatarList = new ArrayList<AvatarName>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
             AvatarName avatarName = new AvatarName();
                avatarName.setAvatarName(listOfFiles[i].getName());
             //   System.out.println(avatarName.getAvatarName());
               avatarList.add(avatarName);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return avatarList;
    }
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    public void getUserAccountinfo() throws Exception {
        accountGet(loginBean.getUserN());
        if (api.jsobj.getString("Result").equals("Ok")) {
            setuserInfo();
        }
    }
    public void userChangeInfo() throws Exception {

        if (getEmail() == null || StringUtils.isBlank(getEmail()) || getLocation() == null || StringUtils.isBlank(getLocation())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Information can't empty"));
        } else if (!isValidEmailAddress(getEmail())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Invalid Email Format!"));
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
            else if(getLocation().indexOf("&Balance=") != -1){
                setLocation(getLocation().replaceAll("&Balance=",""));
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "Access Denied!"));
return;
            }
            else if(getLocation().indexOf("&") != -1){
                setLocation(getLocation().replaceAll("&", ""));
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "Access Denied!"));
                return;
            }

            String account = "&Command=AccountsEdit&Log=no";
            player = loginBean.getUserN();
            String pl = "&Player=" + player;
            String accountParam = account + pl + "&RealName=" + loginBean.getRealname() + "&Location=" + getLocation() + "&Email=" + getEmail();
            // String test = url+accountParam;
            api.sendPost(url, accountParam);
            if (api.jsobj.getString("Result").equals("Ok")) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Information has been changed"));
            }
        }
    }
    public void changePassword() throws JSONException {
        if (getPassword() == null || StringUtils.isBlank(getPassword()) || getRePassword() == null || StringUtils.isBlank(getRePassword())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Information can't empty"));
        } else if (!getPassword().equals(getRePassword())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "The passwords do not match!"));
        }else if (getPassword().length() < 8 ){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Password must over 8 character!"));
        }
        else {
             if(getPassword().indexOf("&") != -1){
                setPassword(getPassword().replaceAll("&", ""));
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "Password must contain letters a-z, A-Z and digit 0-9"));
                return;
            }
            String account = "&Command=AccountsEdit&Log=no";
            player = loginBean.getUserN();
            String pl = "&Player=" + player;
            String accountParam = account + pl + "&PW=" + getPassword();
            // String test = url+accountParam;
            api.sendPost(url, accountParam);
            if (api.jsobj.getString("Result").equals("Ok")) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Password has been changed"));
            }
        }
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
public void changeAvatar(AvatarName avatarName) throws JSONException {
    String account = "&Command=AccountsEdit&Log=no";
    player = loginBean.getUserN();
    String pl = "&Player=" + player;
    String accountParam =account + pl +"&AvatarFile="+folderAvatar+avatarName.getAvatarName()+"&Avatar=0" ;
    api.sendPost(url, accountParam);
   if(api.jsobj.getString("Result").equals("Ok")){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success",
                        "Avatar has been Changed"));
        // System.out.println("Click");
        RequestContext.getCurrentInstance().update("FormMenu:messages");
    }
}

}
