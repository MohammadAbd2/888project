package com.saman.jsf;

/**
 * Created by Saman on 2/13/2015.
 */

import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

@ManagedBean(name = "loginBean")
@SessionScoped

public class LoginBean implements Serializable {
    @ManagedProperty(value="#{navigationBean}")
    private NavigationBean navigationBean;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }
    UserDAO dao;
    Api api;

    private static final long serialVersionUID = 1L;
    private String message, uname;
    private String password;

    public String getIconpMenu() {
        return iconpMenu;
    }

    public void setIconpMenu(String iconpMenu) {
        this.iconpMenu = iconpMenu;
    }

    private String iconpMenu= "fa fa-angle-double-up";
public void changeIcon(){
    if(iconpMenu.equals("fa fa-angle-double-up")){
        setIconpMenu("fa fa-angle-double-down");
    }else {
        setIconpMenu("fa fa-angle-double-up");
    }
 //   RequestContext.getCurrentInstance().update("Form:slidePanel");
}
    public String getUserN() {
        return userN;
    }

    public void setUserN(String userN) {
        this.userN = userN;
    }
    FacesContext ctx = FacesContext.getCurrentInstance();
    String server = ctx.getExternalContext().getInitParameter("ipPort");
    String myapi = ctx.getExternalContext().getInitParameter("mavenPassword")+"&JSON=Yes";
    String url = server + myapi;
    String custom = ctx.getExternalContext().getInitParameter("getCustomFields");
    String root1 = ctx.getExternalContext().getInitParameter("root1");
    String root2 = ctx.getExternalContext().getInitParameter("root2");
    private  String userN;
    String key;
    String sessionParam ;
    String accountParam;
    String sessionId;

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    boolean root;
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    boolean admin;
    boolean manager;

    public boolean isPubl() {
        return publ;
    }

    public void setPubl(boolean publ) {
        this.publ = publ;
    }

    boolean publ;
    // String url = "http://87.247.179.233:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    String param;
    private boolean loggedIn;
    String user;
    private String src2;
    // String server = "http://188.138.41.216:4210";
    // String server = "http://87.247.179.233:4210";
   // String server = "http://localhost:4210";
    HttpSession session = Util.getSession();
    boolean checksrv;


    public String getuAgent() {
        return uAgent;
    }

    public void setuAgent(String uAgent) {
        this.uAgent = uAgent;
    }

    private String uAgent;
    public void getBrowserName() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String userAgent2 = externalContext.getRequestHeaderMap().get("User-Agent");
      //  System.out.println(userAgent2);

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        userAgent2,
                        (userAgent2)));

        RequestContext.getCurrentInstance().update("loginForm:msgs");

        final HttpServletRequest request =(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        final String userAgent = request.getHeader("user-agent");
        if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
            System.out.println("Mobile");
        } else {
            System.out.println("Desktop");
        }
     /*   if(userAgent.contains("MSIE")){
            uAgent = "Internet Explorer";

        }
        if(userAgent.contains("Firefox")){
            uAgent = "Firefox";

        }
        if(userAgent.contains("Chrome")){
            uAgent = "Chrome";

        }
        if(userAgent.contains("Opera")){
            uAgent = "Opera";

        }
        if(userAgent.contains("Safari")){
            uAgent = "Safari";

        }*/


    }


    public String getChipBank() {
        return chipBank;
    }

    public void setChipBank(String chipBank) {
        this.chipBank = chipBank;
    }

    private String chipBank;

    public String getChipTable() {
        return chipTable;
    }

    public void setChipTable(String chipTable) {
        this.chipTable = chipTable;
    }

    public String getChipTotal() {
        return chipTotal;
    }

    public void setChipTotal(String chipTotal) {
        this.chipTotal = chipTotal;
    }

    private String chipTable;
    private String chipTotal;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }




    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    String redirect ;








    public LoginBean() throws IOException,  JSONException {
        api = new Api();
        dao = new UserDAO();
    }

    public String getSrc2() throws Exception {

        return src2;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public boolean getBtnBlock() {

        return boolBlock;
    }

    public void setBtnBlock(boolean btnBlock) {
        this.boolBlock = btnBlock;
    }

    private boolean boolBlock;
    public boolean checkBlacklist()  {

        String blackList = "";
        try {
            accountGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            blackList = api.jsobj.getString("Title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (blackList.equals("Blocked")) {
            boolBlock = true;
            // return btnBlock;

        } else {
            boolBlock = false;
        }
        return boolBlock;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public  boolean isCaptcha() {
        return captcha;
    }

    public void setCaptcha(boolean captcha) {
        this.captcha = captcha;
    }

    private  boolean captcha ;
    private int count = 0;
    public void findMacAddress() throws UnknownHostException, SocketException {
       InetAddress ip = InetAddress.getLocalHost();
    //   InetAddress ip = InetAddress.getByName("46.143.71.40");

            /*
             * Get NetworkInterface for the current host and then read the
             * hardware address.
             */
        System.out.println("Current IP address : " + ip.getHostAddress());
        NetworkInterface ni = NetworkInterface.getByInetAddress(ip);
        if (ni != null) {
            byte[] mac = ni.getHardwareAddress();
            if (mac != null) {
                    /*
                     * Extract each array of mac address and convert it to hexa with the
                     * following format 08-00-27-DC-4A-9E.
                     */
                for (int i = 0; i < mac.length; i++) {
                    System.out.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
                }
            } else {
                System.out.println("Address doesn't exist or is not accessible.");
            }
        } else {
            System.out.println("Network Interface for the specified address is not found.");
        }
    }

    public String loginAsUser() throws Exception {
        loggedIn = false;
       session.removeAttribute("username");
        terminate();
        loggedIn = true;
        user = "&Player=" + getUname();
        session.setAttribute("username", getUname());
        root = true;
        admin = true;
        publ = true;
        manager = true;
        return navigationBean.redirectToGame();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

   static String ipAddress;
    public String Logincheck() throws Exception {
   //  getBrowserName();
      /*  RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('login1').disable()");*/
     //   findMacAddress();
    /*    if(custom.equals("yes")) {
               UserDAO dao = new UserDAO();
               dao.getCustomFields();
        }*/
        String rd = "";
        HttpServletRequest   request =(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
    //    System.out.println("ipAddress:" + ipAddress);
        //    session.setAttribute("ip",ipAddress);
        if (count > 3 ) {
         /*   if(((HttpServletRequest)request).getHeader("User-Agent").indexOf("Mobile") != -1) {
                count = 0;
                setCaptcha(false);
            }else {*/
                setCaptcha(true);
            //    session.setAttribute("captcha",isCaptcha());
                rd = navigationBean.redirectToLoginCaptcha();
       //     }
        } else {
            try {
                user = "&Player=" + getUname();
                String pass = "&PW=" + getPassword();
                String cmdap = "&Command=AccountsPassword";
                param = cmdap + pass + user;


                api.sendPost(url, param);
                //  boolean result = UserDAO.login(uname, password);
                // if (result) {
                if ((api.jsobj.getString("Result").equals("Ok")) && (api.jsobj.getString("Verified").equals("Yes"))) {

                    checkBlacklist();
                    if (boolBlock == false) {


                        //  FacesContext.getCurrentInstance().getExternalContext()
                        //        .redirect("/game.xhtml");
                        loggedIn = true;

                        session.setAttribute("username", getUname());
                        //     JqueryAutoComplete jqac = new JqueryAutoComplete();
                        //   jqac.searchjson();
                        //   searchPlayer();
                        //   System.out.println();
                    //    count = 0;
                    //    captcha = false;


accountGet();
                        dao = new UserDAO();
                        dao.addDeleteUserToDB(api.jsobj.getString("Player"));
                        if (api.jsobj.getString("Note").equals("Admin")){
                            if(uname.equalsIgnoreCase(root1) || uname.equalsIgnoreCase(root2)){
                               root = true;
                                admin = true;
                                publ = true;
                                manager = true;
                            }else {
                                admin = true;
                                publ = true;
                                manager = true;
                            }
                        }
                        else if (api.jsobj.getString("Note").equals("Manager")) {
                            // admin = false;
                            manager = true;
                            publ = true;
                        } else if (api.jsobj.getString("Note").equals("Support")) {
                            // admin = false;
                         //   manager = true;
                            publ = true;
                        }
                        else {
                            root = false;
                            admin = false;
                            manager = false;
                            publ = false;
                        }
                       /* if(((HttpServletRequest)request).getHeader("User-Agent").indexOf("Mobile") != -1) {
                            rd = navigationBean.toMobileGame();
                        }else {*/
                            rd = navigationBean.redirectToGame();
                      //  }
                    }
                    if (boolBlock == true) {
                        rd = navigationBean.toLogin();
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        getUname() + " has been Blocked!",
                                        "Don't Try Again!"));
                    }

                } else {
                    //   msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    //         "Invalid UserName or Password");
                    loggedIn = false;
                    count++;

                   /* if(((HttpServletRequest)request).getHeader("User-Agent").indexOf("Mobile") != -1) {
                        rd = navigationBean.toLoginMobile();
                    }else {
                        rd = navigationBean.toLogin();
                    }*/

                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Username or Password is wrong!",
                                    "Please Try Again!"));


                    //    FacesContext.getCurrentInstance().addMessage(null, msg);
                    //  return "login";

                }

            } catch (Exception ioe) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Can't Connect To Server!",
                                "Please Try Again!"));
                rd = navigationBean.toLogin();
            }
        }
            return rd;
        }
    public String LogincheckCaptcha() throws Exception {
        String rd = "";
        HttpServletRequest   request =(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        //    System.out.println("ipAddress:" + ipAddress);
     //   session.setAttribute("ip",ipAddress);
            try {
                user = "&Player=" + getUname();
                String pass = "&PW=" + getPassword();
                String cmdap = "&Command=AccountsPassword";
                param = cmdap + pass + user;


                api.sendPost(url, param);
                //  boolean result = UserDAO.login(uname, password);
                // if (result) {
                if ((api.jsobj.getString("Result").equals("Ok")) && (api.jsobj.getString("Verified").equals("Yes"))) {

                    checkBlacklist();
                    if (boolBlock == false) {


                        //  FacesContext.getCurrentInstance().getExternalContext()
                        //        .redirect("/game.xhtml");
                        loggedIn = true;

                        session.setAttribute("username", getUname());
                        //     JqueryAutoComplete jqac = new JqueryAutoComplete();
                        //   jqac.searchjson();
                        //   searchPlayer();
                        //   System.out.println();
                        count = 0;
                        setCaptcha(false);
                     //   session.setAttribute("captcha",isCaptcha());
                        accountGet();
                        if (api.jsobj.getString("Note").equals("Admin")){
                            admin = true;
                            publ = true;
                            manager = true;
                        }
                        else if (api.jsobj.getString("Note").equals("Manager")) {
                            // admin = false;
                            manager = true;
                            publ = true;
                        }else {
                            admin = false;
                            manager = false;
                            publ = false;
                        }
                        rd = navigationBean.redirectToGame();
                    }
                    if (boolBlock == true) {
                        rd = navigationBean.ToLoginCaptcha();
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        getUname() + " has been Blocked!",
                                        "Don't Try Again!"));
                    }

                } else {
                    //   msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    //         "Invalid UserName or Password");
                    loggedIn = false;
                 //   count++;
                  //  rd = navigationBean.ToLoginCaptcha();
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Username or Password is wrong!",
                                    "Please Try Again!"));


                    //    FacesContext.getCurrentInstance().addMessage(null, msg);
                    //  return "login";

                }

            } catch (Exception ioe) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Can't Connect To Server!",
                                "Please Try Again!"));
                rd = navigationBean.redirectToLoginCaptcha();
            }

        return rd;
    }
public void updateComponent(String id) {

        RequestContext.getCurrentInstance().update(id);
 //   System.out.println("update");
}
    public String createSession() throws Exception {
        if(!FacesContext.getCurrentInstance().isPostback()) {

        String cmdsk = "&Command=AccountsSessionKey";
        sessionParam = cmdsk + user;

        if((loggedIn)&&(session.getAttribute("username").equals(uname))) {
            //System.out.println(verified);
            //System.out.println(session.getAttribute("username"));
            api.sendPost(url, sessionParam);

            if ((api.jsobj.getString("Result").equals("Ok"))) {
                key = api.jsobj.getString("SessionKey");
                //  System.out.println(key);
                userN = api.jsobj.getString("Player");
           //     UserDAO udao = new UserDAO();
           //     udao.firstlastpayoutTime(userN);
                src2 = server + "/?LoginName=" + getUname() + "&SessionKey=" + key;
                chipBalance();
              //  System.out.println("createSession");
            }
           }
            return navigationBean.redirectToGame();
        }else{
            return navigationBean.redirectToLogin();
        }

    }

    public boolean isMultiAccount() {
        return multiAccount;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    private String pc;
    public void setMultiAccount(boolean multiAccount) {
        this.multiAccount = multiAccount;
    }

    private boolean multiAccount ;

    public boolean isMsgCount() {
        return msgCount;
    }

    public void setMsgCount(boolean msgCount) {
        this.msgCount = msgCount;
    }

    private boolean msgCount;

    public void checkMessages() throws Exception {

        /*    if (msgCount) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('pollCheckMessage').stop()");
            } else {*/
                UserDAO dao = new UserDAO();
                    dao.checkMessages(userN);
                //    setMsgCount(true);
         //   }
        }


    public void checkMultiAccount() throws Exception {
        if (publ != true) {
         /*   if (multiAccount) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('pollMultiAccount').stop()");
            } else {*/
                UserDAO dao = new UserDAO();
                connectionGet();

                if ((api.jsobj.getString("Result").equals("Ok"))) {
                    pc = api.jsobj.getString("PC");
                    //   System.out.println(userN + "----->" + pc);
                    dao.checkMultiAccount(userN, pc);
                 //   setMultiAccount(true);

                }
          //  }
        }
        }

    public String changePc() throws Exception {

        String page = "";
        if(getEmailCheck().equals(email)){
            pcc = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgChangePc').hide()");

        }else{
            pcc = false;
            loggedIn=false;
            session.invalidate();

            terminate();
            page = navigationBean.redirectToLogin();

        }
        if (pcc) {
            UserDAO dao = new UserDAO();
            dao.changePc(userN, pc);
        }
        return page;
    }

    public void connectionGet() throws Exception {

            accountGet();

        String sessionId = api.jsobj.getString("SessionID");
        String cmdpc = "&Command=ConnectionsGet&SessionID="+sessionId;
        api.sendPost(url,cmdpc);
    }

    public String getEmailCheck() {
        return emailCheck;
    }

    public void setEmailCheck(String emailCheck) {
        this.emailCheck = emailCheck;
    }

    private String emailCheck;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    public String checkChangePc() throws Exception {
        String page = "";
        if(getEmailCheck().equals(email)){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgChangePc').hide()");

        }else{
            loggedIn=false;
            session.invalidate();

            terminate();
          page = navigationBean.redirectToLogin();

        }
        return page;
    }

    public boolean isPcc() {
        return pcc;
    }

    public void setPcc(boolean pcc) {
        this.pcc = pcc;
    }

    private boolean pcc;
    public String reLoad() throws Exception {
        String page = "";
        String cmdsk = "&Command=AccountsSessionKey";
        sessionParam = cmdsk + user;

        if((loggedIn)&&(session.getAttribute("username").equals(uname))) {
            //System.out.println(verified);
            //System.out.println(session.getAttribute("username"));
            api.sendPost(url, sessionParam);

            if ((api.jsobj.getString("Result").equals("Ok"))) {
                key = api.jsobj.getString("SessionKey");
                //  System.out.println(key);
            //    userN = api.jsobj.getString("Player");
                //    System.out.println(userN);
                src2 = server + "/?LoginName=" + getUname() + "&SessionKey=" + key;
                //  chipBalance();
            }
            //   }
       //     page = navigationBean.toGame();
        }else{
            page = navigationBean.redirectToLogin();

        }
return page;
    }


    public boolean isChipOnTable() {
        return chipOnTable;
    }

    public void setChipOnTable(boolean chipOnTable) {
        this.chipOnTable = chipOnTable;
    }

    private  boolean chipOnTable;

    public boolean isChipOnSlotMachine() {
        return chipOnSlotMachine;
    }

    public void setChipOnSlotMachine(boolean chipOnSlotMachine) {
        this.chipOnSlotMachine = chipOnSlotMachine;
    }

    private boolean chipOnSlotMachine;
    public int getChipB() {
        return chipB;
    }

    public void setChipB(int chipB) {
        this.chipB = chipB;
    }

    public int getSlotBank() {
        return slotBank;
    }

    public void setSlotBank(int slotBank) {
        this.slotBank = slotBank;
    }

    private int slotBank;
    private int chipB;
    public void chipBalance() throws Exception {
        try {
            accountGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
         chipB = api.jsobj.getInt("Balance");
        chipBank = chipB/1000+"";
        int chipT = api.jsobj.getInt("RingChips");
        slotBank = dao.slotMachineBank(userN,-1)/1000;
      chipTable = chipT/1000+"";
        chipTotal = (chipB+chipT)/1000 +slotBank+"";


        if(chipT > 0){
            chipOnTable = true;
        }else {
            chipOnTable = false;
        }
        if (slotBank > 0){
            chipOnSlotMachine = true;
        }else {
            chipOnSlotMachine = false;
        }

    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    private String realname;
    public void accountGet() throws Exception {
        String account = "&Command=AccountsGet&Log=no";
    //  System.out.println("run in HTML5");
        accountParam = account + user;
        api.sendPost(url, accountParam);
     setRealname( api.jsobj.getString("RealName"));
        setEmail(api.jsobj.getString("Email"));
    }
    public void terminate() throws Exception {
        try {
            accountGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sessionId = api.jsobj.getString("SessionID");
        String cmdtrm = "&Command=ConnectionsTerminate";
        String trm = "&SessionID=" + sessionId + cmdtrm;
        api.sendPost(url,trm);
    }

    public boolean sereverCheck() throws Exception {
        api.sendGet();
        //   System.out.println(api.getsrv);

        if (api.getsrv == false)
        // if(!api.jsobj.getString("Result").equals("ok"))
        {
            return false;
            // checksrv = true;

        }else{
            return true;
        }

    }



     /*   public void confirmlogout() {
            addMessage("", "");
        }*/

        public void addMessage(String summary, String detail) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    public String logout() throws Exception {

        loggedIn=false;
        session.invalidate();

        //  uname="";
        terminate();
        //  return "login?faces-redirect=true";
        return navigationBean.redirectToLogin();
    }
}
