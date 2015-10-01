package com.saman.jsf;

/**
 * Created by Saman on 1/25/2015.
 */

import com.saman.jsf.com.saman.jsf.cashier.RequestList;
import com.saman.jsf.com.saman.jsf.info.UserUpline;
import com.saman.jsf.com.saman.jsf.messages.Messages;
import com.saman.jsf.com.saman.jsf.moneymaking.AddUser;
import com.saman.jsf.com.saman.jsf.moneymaking.Downlines;
import com.saman.jsf.com.saman.jsf.moneymaking.ParseHandHistory;
import com.saman.jsf.com.saman.jsf.report.Income;
import com.saman.jsf.com.saman.jsf.report.SlotMachineReport;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ManagedBean
@SessionScoped
public  class UserDAO implements Serializable {
    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
    public String getLastRake() {
        return lastRake;
    }

    public void setLastRake(String lastRake) {
        this.lastRake = lastRake;
    }

    private static String lastRake ="0";

    public String getLastPayout() {
        return lastPayout;
    }

    public void setLastPayout(String lastPayout) {
        this.lastPayout = lastPayout;
    }

    private String lastPayout;
    public String getBtnColorPayout() {
        return btnColorPayout;
    }

    public void setBtnColorPayout(String btnColorPayout) {
        this.btnColorPayout = btnColorPayout;
    }

    private static String  btnColorPayout = "white";
    DateTime endDate = null;
     Database db = null;
     Database db2 = null;
     Connection con = null;
     Connection con2 = null;
     PreparedStatement ps = null;
    PreparedStatement ps2 = null;
     ParseHandHistory hh;
    public  void endTime() {
    Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        endDate = new DateTime(calendar.getTime());
  //  System.out.println("Yesterday's date = "+ endDate);
}
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

  private static int sum=0;

    public String getTotal() {
        String t = Integer.toString(sum/1000);
        return t;
}

    public void setTotal(String total) {
        this.total = total;
    }

    private String total;

    public boolean isCheckDpReq() {
        return checkDpReq;
    }

    public void setCheckDpReq(boolean checkDpReq) {
        this.checkDpReq = checkDpReq;
    }

    private boolean checkDpReq;



    public void sendDeposit(String admin){
      //  System.out.println("Run");
        try {
            db = new Database();
boolean msg=true;
            AddUser addUser = new AddUser();
         //   String sql = "SELECT * FROM (SELECT username,amount,cardnumber,status FROM dprequest UNION ALL SELECT username,amount,cardnumber,status FROM deposit) AS tbl GROUP BY amount,cardnumber,status='waiting' HAVING count(*) = 2";
            String sql = "SELECT * FROM dprequest WHERE  request='Deposit' AND  status='Waiting'";
            String dpDb = "SELECT * FROM deposit WHERE status='Waiting'";
            con = db.initConnection();

            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String user = rs.getString("username");
                String cnumber = rs.getString("cardnumber");
                String amnt = rs.getString("amount");
                String stus = rs.getString("status");

                if(user != null && stus.equals("Waiting")) {
               //     db2 = new Database();
                 //   con2 = db2.initConnection();
                    ps2 = con.prepareStatement(dpDb,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        if (rs2.getString("amount").equals(amnt) && rs2.getString("cardnumber").equals(cnumber)) {
                            int chip = Integer.parseInt(amnt) * 1000;
                            addUser.addChip(chip, user);
                            if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                                rs2.updateString("status", "Done");
                                rs2.updateRow();
                                rs.updateString("status", "Done");
                                rs.updateRow();
                                rs.updateString("manager", rs2.getString("manager"));
                                rs.updateRow();
                                rs.updateString("errorDeposit", "false");
                                rs.updateRow();
                                rs.updateTimestamp("dateDone", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                                rs.updateRow();

                            } else {
                                rs.updateString("errorDeposit", "true");
                                rs.updateRow();
                                rs.updateString("status", "Waiting");
                                rs.updateRow();
                                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "ServerError",
                                                "Send deposit has been failed! Please try again"));
                                //     RequestContext.getCurrentInstance().update("Form:messages");

                            }
                        } else {

                            if (rs.getString("status").equals("Waiting") && rs.getString("errorDeposit").equals("false")) {
                                rs.updateString("status", "Cancel");
                                rs.updateRow();
                                rs.updateString("manager", admin);
                                rs.updateRow();
                                rs.updateTimestamp("dateDone", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                                rs.updateRow();
                                msg = true;
                            } else {
                                //   msg=false;
                            }
                        }
                    }
                    rs2.close();

                }

            }
            rs.close();
            if (msg){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesfull  uploaded.", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception ex) {
            System.out.println("Error in sendDeposit() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public void sendDeposit2(){
        //  System.out.println("Run");
        try {
            db = new Database();
            boolean msg = true;
            AddUser addUser = new AddUser();
            //   String sql = "SELECT * FROM (SELECT username,amount,cardnumber,status FROM dprequest UNION ALL SELECT username,amount,cardnumber,status FROM deposit) AS tbl GROUP BY amount,cardnumber,status='waiting' HAVING count(*) = 2";
            String sql = "SELECT * FROM dprequest WHERE request='Deposit' AND status='Waiting'";
            String dpDb = "SELECT * FROM deposit WHERE status='Waiting'";
            con = db.initConnection();

            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String user = rs.getString("username");
                String cnumber = rs.getString("cardnumber");
                String amnt = rs.getString("amount");
                String stus = rs.getString("status");
                if(user != null && stus.equals("Waiting")) {
                    //     db2 = new Database();
                    //   con2 = db2.initConnection();
                    ps2 = con.prepareStatement(dpDb,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        if (rs2.getString("amount").equals(amnt) && rs2.getString("cardnumber").equals(cnumber)) {
                            int chip = Integer.parseInt(amnt) * 1000;
                            addUser.addChip(chip, user);
                            if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                                rs2.updateString("status", "Done");
                                rs2.updateRow();
                                rs.updateString("status", "Done");
                                rs.updateRow();
                                rs.updateString("manager", rs2.getString("manager"));
                                rs.updateRow();
                                rs.updateTimestamp("dateDone", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                                rs.updateRow();
                                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                "Success",
                                                "Your deposit request has been done"));
                                msg = false;
                            }
                        }


                    }

                    rs2.close();

                }

            }
            rs.close();
            if (msg){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Your deposit request submited and will be done soon"));
        }
        } catch (Exception ex) {
            System.out.println("Error in sendDeposit() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }



    public void doneCashout(RequestList rl){

       // System.out.println(rl.getCardNumber());
        try {
            db = new Database();

            AddUser addUser = new AddUser();
         //   String cn = rl.getCardNumber().replace(" ","");
            //   String sql = "SELECT * FROM (SELECT username,amount,cardnumber,status FROM dprequest UNION ALL SELECT username,amount,cardnumber,status FROM deposit) AS tbl GROUP BY amount,cardnumber,status='waiting' HAVING count(*) = 2";
            String sql = "SELECT * FROM dprequest WHERE id='"+rl.getId()+"'";

            con = db.initConnection();

            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
           while (rs.next()){
                String user = rs.getString("username");
                String amnt = rs.getString("amount");
                String stus = rs.getString("status");
                if(user != null && rs.getString("request").equals("Cashout") && stus.equals("Waiting")) {


                            rs.updateString("status", "Done");
                            rs.updateRow();
                    rs.updateString("manager", loginBean.getUserN());
                    rs.updateRow();
                    rs.updateTimestamp("dateDone", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                    rs.updateRow();
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Success",
                                            "Cashout has been doned"));


                }

            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error in DoneCashout() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }


    public void undoCancelCashout(RequestList rl){

        try {
            db = new Database();
            AddUser addUser = new AddUser();
            //   String sql = "SELECT * FROM (SELECT username,amount,cardnumber,status FROM dprequest UNION ALL SELECT username,amount,cardnumber,status FROM deposit) AS tbl GROUP BY amount,cardnumber,status='waiting' HAVING count(*) = 2";
            String sql = "SELECT * FROM dprequest WHERE id='"+rl.getId()+"'" ;


            con = db.initConnection();

            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String user = rs.getString("username");
                String manager = rs.getString("manager");
                String amnt = rs.getString("amount");
                String stus = rs.getString("status");
                int chip = Integer.parseInt(amnt) * 1000;
                if(stus.equals("CancelByAdmin")) {

                        rs.updateString("status", "Waiting");
                        rs.updateRow();
                        rs.updateString("manager", loginBean.getUserN());
                        rs.updateRow();

                }else if(stus.equals("Done")){
                    rs.updateString("status", "Waiting");
                    rs.updateRow();
                    rs.updateString("manager", loginBean.getUserN());
                    rs.updateRow();
                }

            }
            RequestContext.getCurrentInstance().update("FormMenu:messages");
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error in UndoCancelCashout() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public void cancelCashoutAdmin(RequestList rl){

        try {
            db = new Database();
            AddUser addUser = new AddUser();
            //   String sql = "SELECT * FROM (SELECT username,amount,cardnumber,status FROM dprequest UNION ALL SELECT username,amount,cardnumber,status FROM deposit) AS tbl GROUP BY amount,cardnumber,status='waiting' HAVING count(*) = 2";
            String sql = "SELECT * FROM dprequest WHERE id='"+rl.getId()+"'" ;


            con = db.initConnection();

            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String user = rs.getString("username");
                String amnt = rs.getString("amount");
                String stus = rs.getString("status");
                if(rs.getString("request").equals("Cashout") && stus.equals("Waiting")) {

    rs.updateString("status", "CancelByAdmin");
    rs.updateRow();
    rs.updateString("manager", loginBean.getUserN());
    rs.updateRow();
                    rs.updateTimestamp("dateDone", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                    rs.updateRow();




                }

            }
            RequestContext.getCurrentInstance().update("FormMenu:messages");
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error in CancelCashoutAdmin() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public void cancelCashoutUser(RequestList rl){

        try {
            db = new Database();
            AddUser addUser = new AddUser();

            //   String sql = "SELECT * FROM (SELECT username,amount,cardnumber,status FROM dprequest UNION ALL SELECT username,amount,cardnumber,status FROM deposit) AS tbl GROUP BY amount,cardnumber,status='waiting' HAVING count(*) = 2";
            String sql = "SELECT * FROM dprequest WHERE  id='"+rl.getId()+"'" ;
            con = db.initConnection();
            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String user = rs.getString("username");
             //   System.out.println(user);
                String amnt = rs.getString("amount");
              //  System.out.println(amnt);
                String stus = rs.getString("status");
                int chip = Integer.parseInt(amnt) * 1000;
                if(user.equals(loginBean.getUserN()) && rs.getString("request").equals("Cashout") && stus.equals("Waiting")) {

                        addUser.addChip(chip, user);
                        if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                            rs.updateString("status", "Cancel");
                            rs.updateRow();
                            rs.updateTimestamp("dateDone", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                            rs.updateRow();
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Success",
                                            "Cashout has been canceled"));
                        }else {
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "ServerError",
                                            "Cancel cashout has been failed! Please try again"));
                        }
                    }



            }
            RequestContext.getCurrentInstance().update("FormMenu:messages");
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error in CancelCashoutUser() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public boolean checkDpRequest(String username,String amount,String cardNumber){
checkDpReq = true;
    try {
        db = new Database();
        String sqlcheck = "SELECT * FROM dprequest WHERE status='Waiting'";
        con = db.initConnection();
        ps = con.prepareStatement(sqlcheck);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String amnt = rs.getString("amount");
            String cnumber = rs.getString("cardnumber");
            String usern = rs.getString("username");
            if (amount.equals(amnt) && cardNumber.equals(cnumber)) {

                checkDpReq = false;
                break;
            }

        }
        rs.close();
    } catch (Exception ex) {
        System.out.println("Error in addDepositRequest() -->" + ex.getMessage());

    } finally {
        db.closeConnection();
    }
  //  System.out.println(checkDpReq);
    return checkDpReq;
}
    public void addDpRequest(String username,String amount,String cardNumber){
        try {
            db = new Database();
            String sql = "INSERT  INTO dprequest (request,username,amount,cardnumber,status,errorDeposit,date) VALUES (?,?,?,?,?,?,NOW())";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, "Deposit");
            ps.setString(2, username);
            ps.setString(3, amount);
            ps.setString(4, cardNumber);
            ps.setString(5,"Waiting");
            ps.setString(6,"false");
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in addDepositRequest() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public void addCashRequest(String username,String amount,String cardNumber,String realName){
        try {
            db = new Database();
            String sql = "INSERT  INTO dprequest (request,username,amount,cardnumber,realname,status,manager,date) VALUES (?,?,?,?,?,?,?,NOW())";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, "Cashout");
            ps.setString(2, username);
            ps.setString(3, amount);
            ps.setString(4, cardNumber);
            ps.setString(5, realName);
            ps.setString(6,"Waiting");
            ps.setString(7,username);
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in addCashoutRequest() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }


    public void addTransferRequest(String username,String amount,String transferTo){
        try {
            db = new Database();
            String sql = "INSERT  INTO dprequest (request,username,amount,transferTo,status,date) VALUES (?,?,?,?,?,NOW())";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, "Transfer");
            ps.setString(2, username);
            ps.setString(3, amount);
            ps.setString(4, transferTo);
            ps.setString(5,"Complete");
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in addTransferRequest() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public  void addDepositDB(String manager,String amount,String cardNumber,String datedp,String timedp){
        try {
            db = new Database();
            String sql = "INSERT IGNORE INTO deposit (amount,cardnumber,datedp,timedp,status,manager) VALUES (?,?,?,?,?,?)";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, amount);
            ps.setString(2, cardNumber);
            ps.setString(3,datedp);
            ps.setString(4,timedp);
            ps.setString(5,"Waiting");
            ps.setString(6, manager);
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in addDepositDB() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public  void userList(JSONArray user) {

        PreparedStatement s =null;
        try {
            db = new Database();
            String _deleteTableData ="TRUNCATE TABLE playerInfo";
            String sql = "INSERT INTO playerInfo (username) VALUES (?)";
            con = db.initConnection();
            s = con.prepareStatement(_deleteTableData);
            s.executeUpdate();

            ps = con.prepareStatement(sql);

            for (int i =0;i<user.length();i++) {
                String pl = user.getString(i);
                ps.setString(1, pl);

                ps.executeUpdate();
            }

        } catch (Exception ex) {
           System.out.println("Error in login() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public  void addDeleteUserToDB(String username){
        try {
            db = new Database();
            String sql = "SELECT * FROM playerInfo WHERE username='" + username + "'";
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
           //     System.out.println("!rs run");
                String sqlInsert = "INSERT INTO playerInfo (username,reffer,lastRake,lastpayout,lastRakeTrue,lastpayoutTrue) VALUES (?,?,?,NOW(),?,Now())";
                con = db.initConnection();
                ps = con.prepareStatement(sqlInsert);

                ps.setString(1, username);

                ps.setString(2, "Regiser");

                ps.setString(3,"0");
                ps.setInt(4, 0);
                ps.executeUpdate();

            }

          //  System.out.println("rs run");

        } catch (Exception ex) {
            System.out.println("Error in addDeleteUserToDB() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

public  void addUserDB(String username,String reffer){
    try {
        db = new Database();
        String sql = "INSERT INTO playerInfo (username,reffer,lastRake,lastpayout,lastRakeTrue,lastpayoutTrue) VALUES (?,?,?,NOW(),?,NOW())";
        con = db.initConnection();
        ps = con.prepareStatement(sql);

            ps.setString(1, username);
        if (reffer == null){
            ps.setString(2, "Regiser");
        }else{
            ps.setString(2, reffer);
        }
ps.setString(3,"0");
        ps.setInt(4, 0);
            ps.executeUpdate();


    } catch (Exception ex) {
        System.out.println("Error in addUserDB() -->" + ex.getMessage());

    } finally {
        db.closeConnection();
    }
}
    public  void addNewUserRequest(String username,String reffer){
        try {
            db = new Database();
            String sql = "INSERT INTO dprequest (request,manager,username,status,date) VALUES (?,?,?,?,NOW())";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, "NewUser");
            if (reffer == null){
                ps.setString(2, "Regiser");
            }else{
                ps.setString(2, reffer);
            }
            ps.setString(3, username);
            ps.setString(4, "Done");
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in addNewUserRequest() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    ArrayList<Downlines> al;

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    private DateTime startDate;


    public ArrayList<Downlines> getDownlines(String upline) {
     //   System.out.println("call check");
        sum = 0;
      getlastpayOut(upline);
   endTime();

    try {

        db = new Database();
        String sql = "SELECT * FROM playerInfo WHERE reffer='" + upline + "'";
        con = db.initConnection();
        ps = con.prepareStatement(sql);
        al = new ArrayList<Downlines>();
        ResultSet rs = ps.executeQuery();

        Downlines dl ;

        boolean found = false;
        while (rs.next()) {
             dl = new Downlines();
            dl.setUsername(rs.getString("username"));

                hh = new ParseHandHistory();
                hh.getDaysBetweenDates(dl.getUsername(), getStartDate(), getEndDate());
         //   System.out.println(dl.getUsername()+"---"+ getStartDate()+"---"+ endDate);
                dl.setPayout(hh.getSumRake() / 1000 + "K");
            if((hh.getSumRake() / 1000) > 0){
                dl.setRakeColor("gold");
            }
                sum += hh.getSumRake();
                al.add(dl);
                found = true;

        }

        rs.close();

        if (found && (sum/1000)!=0) {
setBtnColorPayout("gold");
            return al;
        }else if (found && (sum/1000)==0){
          setBtnColorPayout("white");
           setlastpayoutTimeEmpty(upline);

            return al;
        } else {

            return null; // no entires found
        }
    } catch (Exception ex) {
        System.out.println("Error in getDownlines() -->" + ex.getMessage());

    } finally {
        db.closeConnection();
    }

     //   System.out.println("al null nist");
            return (null);
        }

    public String getDisable() {
        if(sum!=0){
            disable="false";
        }else {
            disable="true";
        }
        return disable;
    }



    public void setDisable(String disable) {
        this.disable = disable;
    }

    private String disable;

    public  void getCustomFields(){
    String cmdGetPlayer = "&Command=AccountsList&Fields=Player";
    String cmdAccountGet = "&Command=AccountsGet&Player=";
        FacesContext ctx = FacesContext.getCurrentInstance();
        String server = ctx.getExternalContext().getInitParameter("ipPort");
        String myapi = ctx.getExternalContext().getInitParameter("mavenPassword")+"&JSON=Yes";
        String url = server + myapi;
                //   System.out.println(url);
                try {
                    db2 = new Database();
                    List<String> results = new ArrayList<String>();
                    AddUser addUser = new AddUser();
                    addUser.getApi().sendPost(url,cmdGetPlayer);

                    if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {

                        JSONArray array = addUser.getApi().jsobj.getJSONArray("Player");
                        for(int i=0;i<array.length();i++) {
                            results.add(array.getString(i));
                            //   System.out.println(array.getString(i));
                        }
                        for (String player : results) {
                            addUser.getApi().sendPost(url,cmdAccountGet+player);
                            //    System.out.println(url+cmdAccountGet+player);
                            String custom = addUser.getApi().jsobj.getString("Custom");
                 //   System.out.println(custom);
                if(custom != null && !StringUtils.isBlank(custom)) {
                       String[] getTime = custom.split(",");
                      String[] time = getTime[1].split(" ");
                   int lastrake2 =(int) Double.parseDouble(getTime[0])/1000;

                    String conv = time[0] + " " +time[1];
                  //  System.out.println(lastrake);
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    java.util.Date date = format.parse(conv);
                    Date sqlStartDate = new Date(date.getTime());
                    String sql = "update playerInfo set lastpayout = ? ,lastRake = ?  where username = ?";
                        con2 = db2.initConnection();
                        ps2 = con2.prepareStatement(sql);

                        ps2.setDate(1,sqlStartDate);
                        ps2.setString(2,String.valueOf(lastrake2)+"k");
                        ps2.setString(3,player);
                        ps2.executeUpdate();

                }
                }
           }


    } catch (Exception ex) {
        System.out.println("Error in getCustomFields() -->" + ex.getMessage());

    } finally {
        db2.closeConnection();
    }
}

public void addPayout(){
    try {

        AddUser addUser = new AddUser();
        addUser.addChip(sum, loginBean.getUserN());
        if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
            setlastpayoutTime(loginBean.getUserN());
            getlastpayOut(loginBean.getUserN());
            hh = new ParseHandHistory();
            hh.setSumRake(0);
            setSum(0);
            addlastpayoutToRequestTable(loginBean.getUserN());
        /*    sum += hh.getSumRake();
            Downlines dl = new Downlines();
            dl.setPayout(0 + "k");*/
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Payout add to your bank"));
        }else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Failed",
                            "Payout don't add to your bank.Please try again"));
        }

    }catch (Exception e){
        System.out.println("Error in addChip -->" + e.getMessage());
    }
}


    public void getlastpayOut(String username) {

        try {

            db = new Database();
            String sql = "SELECT * FROM playerInfo WHERE username='" + username + "'";
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                DateTime stDate = new DateTime(rs.getDate("lastpayout")) ;
                String sqlDate = rs.getString("lastpayout");
                if (sqlDate != null && !StringUtils.isBlank(sqlDate)) {
                //    String[] convert = sqlDate.split(" ");
                 //   String dateTime = convert[0] + "T" + convert[1] + "00Z";
               //     setStartDate(ISODateTimeFormat.dateTime().parseDateTime(dateTime));
setStartDate(stDate);
lastPayout = (rs.getString("lastRake"));
                //    System.out.println(getLastRake());
                }
                String datelpot = rs.getString("lastpayoutTrue");

                if (datelpot != null && !StringUtils.isBlank(datelpot)) {
                    String[] convertlpot = datelpot.split(" ");
                    lastRake = (rs.getString("lastRakeTrue")+"k "+convertlpot[0]);
                }
            }

            rs.close();

        } catch (Exception ex) {
            System.out.println("Error in getLastPayout() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }

    }
    public void cashoutCounter() {
        try {

            db = new Database();

            String sql = "SELECT * FROM dprequest WHERE request='Cashout' AND status='Waiting'";
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String statusclr = "";
            int counter = 0;
            setSumCashout(0);
            while (rs.next()) {

                counter++;
                sumCashout +=Integer.parseInt(rs.getString("amount"));
            }

            rs.close();
            if(counter <= 0){
                setNumberCashout("No New Cashout");
                setColorCashout("gray");
                setColorCashoutBack("");
            }else {
                setNumberCashout(String.valueOf(getSumCashout()) + "k is waiting in " + String.valueOf(counter) + " Cashout request");
                setColorCashout("gold");
                setColorCashoutBack("darkred");
            }

        } catch (Exception ex) {
            System.out.println("Error in getCashoutCounter() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }

    }
public void depositCounter() {
    try {

        db = new Database();

            String sql = "SELECT * FROM dprequest WHERE request='Deposit' AND status='Waiting'";
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String statusclr = "";
            int counter = 0;
            while (rs.next()) {

                counter++;

            }

            rs.close();
            if (counter <= 0) {
                setNumberWaiting("No New Deposit");
                setColorWaiting("gray");
                setColorWaitingBack("");
            } else {
                setNumberWaiting(String.valueOf(counter) + " New Deposit");
                setColorWaiting("darkred");
                setColorWaitingBack("seagreen");
            }

    } catch (Exception ex) {
        System.out.println("Error in getDepositCounter() -->" + ex.getMessage());

    } finally {
        db.closeConnection();
    }

}
private String colorCashout = "gray";

    public String getColorCashoutBack() {
        return colorCashoutBack;
    }

    public void setColorCashoutBack(String colorCashoutBack) {
        this.colorCashoutBack = colorCashoutBack;
    }

    private String colorCashoutBack;
    public String getNumberCashout() {
        return numberCashout;
    }

    public void setNumberCashout(String numberCashout) {
        this.numberCashout = numberCashout;
    }

    public String getColorCashout() {
        return colorCashout;
    }

    public void setColorCashout(String colorCashout) {
        this.colorCashout = colorCashout;
    }


    private String numberCashout= "Loading...";

    public int getSumCashout() {
        return sumCashout;
    }

    public void setSumCashout(int sumCashout) {
        this.sumCashout = sumCashout;
    }

    private int sumCashout ;

    public String getPlayerMultiAccount() {
        return playerMultiAccount;
    }

    public void setPlayerMultiAccount(String playerMultiAccount) {
        this.playerMultiAccount = playerMultiAccount;
    }

    private static String playerMultiAccount;
    public void checkMultiAccount(String username,String pcu){

            try {
                db = new Database();
                String sql = "SELECT * FROM playerinfo WHERE userPc ='" + pcu + "'";
                String sql3 = "SELECT * FROM playerinfo WHERE username ='" + username + "'";
                String sql2 = "update playerinfo set userPc = '" + pcu + "'  where username = '" + username + "'";
                con = db.initConnection();
                con2 = db.initConnection();
                ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    ps = con.prepareStatement(sql3);
                    ResultSet rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        String pcNumber = rs2.getString("userPc");
                        if (pcNumber == null || StringUtils.isBlank(pcNumber) || pcNumber.equals("") || pcNumber.equals("NULL")) {
                            ps2 = con.prepareStatement(sql2);
                            ps2.executeUpdate();
                        }else {
                            RequestContext context = RequestContext.getCurrentInstance();
                            context.execute("PF('dlgChangePc').show()");
                            System.out.println("change pc show");
                        }
                    }
                }else {

                     playerMultiAccount = rs.getString("username");
                    System.out.println("multi account show");
                    if (!username.equals(playerMultiAccount) ) {
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("PF('dlgMultiAccount').show()");

                    }

                }


                rs.close();



            } catch (Exception ex) {
                System.out.println("Error in checkMultiAccount() -->" + ex.getMessage());

            } finally {
                db.closeConnection();
            }
        }

    public void changePc(String username,String pcu){

        try {
            db = new Database();
            String sql = "SELECT * FROM playerinfo WHERE userPc ='" + pcu + "'";
            String sql2 = "update playerinfo set userPc = ?   where username = ?";
            con = db.initConnection();
            con2 = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {


                        ps2 = con.prepareStatement(sql2);
                        ps2.setString(1, pcu);
                        ps2.setString(2, username);
                        ps2.executeUpdate();


            }


            rs.close();



        } catch (Exception ex) {
            System.out.println("Error in changePc() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }



    public  ArrayList<UserUpline> getUserUpline(String username) {
        try {
            db = new Database();
            boolean foundUpl = false;
            boolean foundDnl = false;
            String user = username;
            ArrayList<UserUpline> upl = null;
            upl = new ArrayList<UserUpline>();
            ResultSet rs = null;
                String sql = "SELECT reffer FROM playerinfo WHERE  username = ? ";
                con = db.initConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1, user);
                 rs = ps.executeQuery();

                while (rs.next()) {
                    UserUpline  data = new UserUpline();
                    if(rs.getString("reffer")==null || rs.getString("reffer").equals("")){
                        data.setUpline("Regiser");
                    }else {
                        data.setUpline(rs.getString("reffer"));

                        user = rs.getString("reffer");
                        String sql2 = "SELECT reffer FROM playerinfo WHERE  username = ? ";
                        ps = con.prepareStatement(sql2);
                        ps.setString(1, user);
                        rs = ps.executeQuery();
                        data.setUplineCheck(true);
                    }
                   upl.add(data);
                    foundUpl = true;

            }
            String sql3 = "SELECT * FROM playerinfo WHERE  reffer = '"+username+"' ";
            ps = con.prepareStatement(sql3);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserUpline  data = new UserUpline();
                data.setDownline(rs.getString("username"));
                data.setDownlineCheck(true);
                upl.add(data);
                foundDnl = true;
            }
            rs.close();
            if (foundUpl || foundDnl) {

                return upl;
            }
            else {
                return null; // no entires found
            }
        } catch (Exception ex) {
            System.out.println("Error in getUserUpline() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
        return (null);

    }

    public  ArrayList<RequestList> getDpListAction() {
        try {
        db = new Database();
        boolean foundDprl = false;
        ArrayList<RequestList> dprl = null;
        String sql = "SELECT * FROM dprequest WHERE  request='Deposit' AND  status != 'Waiting'  AND dateDone BETWEEN ? - INTERVAL 1 MINUTE AND ?  ";
        con = db.initConnection();
        ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()+(1*1000)));
            ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()+(1*1000)));
        ResultSet rs = ps.executeQuery();

        dprl = new ArrayList<RequestList>();

        String statusclr = "";
        while (rs.next()) {
            RequestList data = new RequestList();
            String cn3 = "";
            try {
                String cn = rs.getString("cardnumber");
                String[] cn2 = {cn.substring(0, 4), cn.substring(4, 8), cn.substring(8, 12), cn.substring(12, 16)};
                cn3 = cn2[0] + " " + cn2[1] + " " + cn2[2] + " " + cn2[3];
            } catch (Exception e) {
                e.getMessage();
            }
            data.setCardNumber(cn3);
            data.setId(rs.getString("id"));
            data.setManager(rs.getString("manager"));
            data.setUsername(rs.getString("username"));
            data.setAmount(rs.getInt("amount"));
            statusclr = rs.getString("status");
            data.setStatus(statusclr);
            data.setDateRQ(rs.getString("date"));
            data.setDateDone(rs.getString("dateDone"));
            //   System.out.println(rs.getString("date"));
            dprl.add(data);
            foundDprl = true;
            if (statusclr.equals("Done")) {
                data.setStatusColor("green");
            } else if (statusclr.equals("Cancel")) {
                data.setStatusColor("red");
            }

    }
            rs.close();
    if (foundDprl) {

        return dprl;
    }
    else {
        return null; // no entires found
    }
} catch (Exception ex) {
        System.out.println("Error in getDpListAction() -->" + ex.getMessage());

        } finally {
        db.closeConnection();
        }
        return (null);

    }
    public int slotMachineBank(String username,int amount){
        int bank = 0;
        try {
            db = new Database();
            String sql = "SELECT * FROM playerinfo WHERE  username = '"+username+"' ";
            con = db.initConnection();
            ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                if(amount == -1) {
                    bank = rs.getInt("slotMachine");

                }else {
                    rs.updateInt("slotMachine", amount);
                    rs.updateRow();
                }
            }

            rs.close();

        } catch (Exception ex) {
            System.out.println("Error in SlotMachineBank() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
        return bank;
    }

    public void addLogSlotMachine(String username,int startBank,int endBank,int amountTotal,int amountWin,String descriptions){
        try {
            db = new Database();
            String sql = "INSERT  INTO slotmachine (username,startBank,endBank,amountTotal,amountWin,descriptions,date) VALUES (?,?,?,?,?,?,NOW())";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setInt(2, startBank);
            ps.setInt(3, endBank);
            ps.setInt(4, amountTotal);
            ps.setInt(5, amountWin);
            ps.setString(6,descriptions);
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in addLogSlotMachine() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public int getTotalDepositDb() {
        return totalDepositDb;
    }

    public void setTotalDepositDb(int totalDepositDb) {
        this.totalDepositDb = totalDepositDb;
    }

    private static int totalDepositDb;
    public void getTotalDepositFromDb(String date){
        int sumAmount = 0;
        try {
            db = new Database();
            String sql = "SELECT * FROM deposit WHERE  datedp LIKE '%" + date + "%'";
        //    System.out.println(sql);
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

           //     System.out.println(rs.getString("amount"));
                sumAmount += Integer.parseInt(rs.getString("amount"));

            }
totalDepositDb = sumAmount;
          //  System.out.println(totalDepositDb);
            rs.close();



        } catch (Exception ex) {
            System.out.println("Error in getTotalDepositFromDb() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

public ArrayList<Income> getTotalIncome( String adminUser,java.util.Date start, java.util.Date end){

    try {
        db = new Database();
        FacesContext ctx = FacesContext.getCurrentInstance();
        String[] adminNames = ctx.getExternalContext().getInitParameter("adminNames").split(",");
        AddUser addUser = new AddUser();
        boolean foundincome = false;
        ArrayList<Income> income = null;

       String sql = "SELECT * FROM income WHERE  DATE_FORMAT(date,'%y-%m-%d') >= DATE_FORMAT(?,'%y-%m-%d')  AND DATE_FORMAT(date,'%y-%m-%d') <= DATE_FORMAT(?,'%y-%m-%d') ORDER by date desc ";
     //   String sql = "SELECT * FROM income WHERE  DATE_FORMAT(date,'%y-%m-%d')= DATE_FORMAT(NOW(),'%y-%m-%d')  ORDER by date desc ";

        con = db.initConnection();
        ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

      ps.setDate(1, new Date(start.getTime()));
        ps.setDate(2, new Date(end.getTime()));

        ResultSet rs = ps.executeQuery();

        income = new ArrayList<Income>();

        while (rs.next()) {
            Income data = new Income();
            data.setDate(rs.getString("date"));
            data.setEntryFee(rs.getInt("entryfee"));
            data.setTourney(rs.getInt("tourney"));
            data.setRake(rs.getInt("rake"));
            data.setCostsDb(rs.getInt("costs"));
            data.setCasinoRake(rs.getInt("casinoRake"));
            data.setUserRake(rs.getInt("userRake"));
            data.setSlot(rs.getInt("slot"));
            data.setPayouts(rs.getInt("payouts"));
            data.setTotal(rs.getInt("total"));


            foundincome = true;

            if (adminUser.equals(adminNames[0])){
                int ace7 = ((rs.getInt("total")*20)/100);

                        data.setPercent(ace7);
                if(rs.getString("statusAce7").equals("Complete")) {
                    int chip = ace7 * 1000;
                    if (chip < 0 ) {
                        addUser.decChip((chip*-1),adminUser);
                        if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                            rs.updateString("statusAce7", "Done");
                            rs.updateRow();
                        }
                    }else {
                        if(rs.getInt("total") <= 0){
                            rs.updateString("statusAce7", "Done");
                            rs.updateRow();
                        }else {
                            addUser.addChip(chip, adminUser);
                            if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                                rs.updateString("statusAce7", "Done");
                                rs.updateRow();
                            }
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    (ace7)+" k add to your bank"));
                }
            }else if(adminUser.equals(adminNames[1])){
                int head = ((rs.getInt("total")*30)/100);

                        data.setPercent(head);
                if(rs.getString("statusHead").equals("Complete")) {
                    int chip = head * 1000;
                    if (chip < 0 ) {
                        addUser.decChip((chip*-1),adminUser);
                        if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                            rs.updateString("statusHead", "Done");
                            rs.updateRow();
                        }
                    }else {
                        if(rs.getInt("total") <= 0){
                            rs.updateString("statusHead", "Done");
                            rs.updateRow();
                        }else {
                            addUser.addChip(chip, adminUser);
                            if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                                rs.updateString("statusHead", "Done");
                                rs.updateRow();
                            }
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    (head)+" k add to your bank"));
                }
            }else if(adminUser.equals(adminNames[3])){
                int mana = ((rs.getInt("total")*20)/100);

                        data.setPercent(mana);
                if(rs.getString("statusMana").equals("Complete")) {
                    int chip = mana * 1000;
                    if (chip < 0) {
                        addUser.decChip((chip*-1),adminUser);
                        if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                            rs.updateString("statusMana", "Done");
                            rs.updateRow();
                        }
                    }else {
                        if (rs.getInt("total") <= 0) {
                            rs.updateString("statusMana", "Done");
                            rs.updateRow();
                        } else {
                            addUser.addChip(chip, adminUser);
                            if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                                rs.updateString("statusMana", "Done");
                                rs.updateRow();
                            }
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    (mana)+" k add to your bank"));
                }
            }else if(adminUser.equals(adminNames[2])){
                int saten = ((rs.getInt("total")*30)/100);

                        data.setPercent(saten);
                if(rs.getString("statusSaten").equals("Complete")) {
                    int chip = saten * 1000;
                    if (chip < 0 ) {
                        addUser.decChip((chip*-1),adminUser);
                        if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                            rs.updateString("statusSaten", "Done");
                            rs.updateRow();
                        }
                    }else {
                        if(rs.getInt("total") <= 0){
                            rs.updateString("statusSaten", "Done");
                            rs.updateRow();
                        }else {
                            addUser.addChip(chip, adminUser);
                            if (addUser.getApi().jsobj.getString("Result").equals("Ok")) {
                                rs.updateString("statusSaten", "Done");
                                rs.updateRow();
                            }
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    (saten)+" k add to your bank"));
                }
            }else {
                data.setPercent(0);

            }

            income.add(data);
            RequestContext.getCurrentInstance().update("FormMenu:messages");
       //     System.out.println(data.getTotal());

        }

        rs.close();

        if (foundincome) {

            return income;
        }
        else {
            System.out.println("null");
            return null; // no entires found
        }
    } catch (Exception ex) {
        System.out.println("Error in getTotalIncome() -->" + ex.getMessage());

    } finally {
        db.closeConnection();
    }
    return (null);

}


    public ArrayList<SlotMachineReport> getSlotMachineReport( String username,java.util.Date start, java.util.Date end){

        try {
            db = new Database();
            boolean foundSlotReport = false;
            ArrayList<SlotMachineReport> slotMachineReports = null;

            String sql = "SELECT * FROM slotmachine WHERE username = ? AND DATE_FORMAT(date,'%y-%m-%d') >= DATE_FORMAT(?,'%y-%m-%d')  AND DATE_FORMAT(date,'%y-%m-%d') <= DATE_FORMAT(?,'%y-%m-%d') ORDER by date desc ";
            //   String sql = "SELECT * FROM income WHERE  DATE_FORMAT(date,'%y-%m-%d')= DATE_FORMAT(NOW(),'%y-%m-%d')  ORDER by date desc ";

            con = db.initConnection();
            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1,username);
            ps.setDate(2, new Date(start.getTime()));
            ps.setDate(3, new Date(end.getTime()));

            ResultSet rs = ps.executeQuery();

            slotMachineReports = new ArrayList<SlotMachineReport>();

            while (rs.next()) {
                SlotMachineReport data = new SlotMachineReport();
                data.setDate(rs.getString("date"));
                data.setStartBank(rs.getInt("startBank")/1000);
                data.setEndBank(rs.getInt("endBank")/1000);
                data.setAmountWin(rs.getInt("amountWin")/1000);
                data.setAmountTotal(rs.getInt("amountTotal")/1000);
                data.setDescriptions(rs.getString("descriptions"));

                foundSlotReport = true;



                slotMachineReports.add(data);

            }

            rs.close();

            if (foundSlotReport) {

                return slotMachineReports;
            }
            else {
                System.out.println("null");
                return null; // no entires found
            }
        } catch (Exception ex) {
            System.out.println("Error in getSlotMachineReport() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
        return (null);

    }




    public ArrayList<RequestList> getRequestList(String request,String username,String status, java.util.Date start, java.util.Date end) {

        try {
            db = new Database();
            boolean foundDpl = false;

            boolean foundRql = false;
            boolean foundcashls = false;
            boolean foundReport = false;
            ArrayList<RequestList> dpl=null;

            ArrayList<RequestList> rql=null;
            ArrayList<RequestList> cashls=null;
            ArrayList<RequestList> report = null;
            if(username==null && status=="Waiting" && request=="Deposit" ){

                String sql = "SELECT * FROM dprequest WHERE request='Deposit' AND status='Waiting'";
                con = db.initConnection();
                ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                dpl = new ArrayList<RequestList>();

                String statusclr = "";
                int counter = 0;
                while (rs.next()){
                    RequestList data = new RequestList();
                    String cn3="";
                    try {
                        String cn = rs.getString("cardnumber");
                        String[] cn2 = {cn.substring(0, 4), cn.substring(4, 8), cn.substring(8, 12), cn.substring(12, 16)};
                        cn3 = cn2[0] + " " + cn2[1] + " " + cn2[2] + " " + cn2[3];
                    }catch (Exception e){
                        e.getMessage();
                    }
                    data.setCardNumber(cn3);
                    data.setUsername(rs.getString("username"));
                    data.setId(rs.getString("id"));
                    data.setAmount(rs.getInt("amount"));
                    statusclr = rs.getString("status");
                    data.setStatus(statusclr);
                    data.setDateRQ(rs.getString("date"));
                    data.setDateDone(rs.getString("dateDone"));
                    counter++;
                    //   System.out.println(rs.getString("date"));
                    dpl.add(data);
                    foundDpl = true;
                    if(statusclr.equals("Waiting")){
                        data.setStatusColor("gold");
                    }
                    else if(statusclr.equals("Done")){
                        data.setStatusColor("green");
                    }
                    else if(statusclr.equals("Cancel")){
                        data.setStatusColor("red");
                    }
                }

                rs.close();
                if(counter <= 0){
                    setNumberWaiting("No New Deposit");
                    setColorWaiting("gray");
                    setColorWaitingBack("");
                }else {
                    setNumberWaiting(String.valueOf(counter)+" New Deposit");
                    setColorWaiting("darkred");
                    setColorWaitingBack("seagreen");
                }

            }

            if(request=="Report" && status==null ){
                String sql = "";
                con = db.initConnection();
              // System.out.println(username);
               /* if(username != null && !StringUtils.isBlank(username)){
                    sql = "SELECT * FROM dprequest WHERE (username = '"+username+"' OR manager = '"+username+"' OR transferTo = '"+username+"') AND  date >= ? AND date <= ? ORDER by date desc";
                    ps = con.prepareStatement(sql);
                    ps.setTimestamp(1, new java.sql.Timestamp(start.getTime()));
                    ps.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
                }else {*/
                    sql = "SELECT * FROM dprequest WHERE  date >= ? AND date <= ? ORDER by date desc ";

                    ps = con.prepareStatement(sql);
                    ps.setTimestamp(1, new java.sql.Timestamp(start.getTime()));
                    ps.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
           //     }
                ResultSet rs = ps.executeQuery();

                report = new ArrayList<RequestList>();

                String statusclr = "";
            //    System.out.println("ok 2");
                while (rs.next()){
                    RequestList data = new RequestList();
                    String cn3="";
                    try {
                        String cn = rs.getString("cardnumber");
                        String[] cn2 = {cn.substring(0, 4), cn.substring(4, 8), cn.substring(8, 12), cn.substring(12, 16)};
                        cn3 = cn2[0] + " " + cn2[1] + " " + cn2[2] + " " + cn2[3];
                    }catch (Exception e){
                        e.getMessage();
                    }
                    data.setCardNumber(cn3);
                    data.setUsername(rs.getString("username"));
                    data.setId(rs.getString("id"));
                    data.setAmount(rs.getInt("amount"));
                    data.setManager(rs.getString("manager"));
                    data.setRequest(rs.getString("request"));
                    statusclr = rs.getString("status");
                    data.setStatus(statusclr);
                    data.setDateRQ(rs.getString("date"));
                    data.setDateDone(rs.getString("dateDone"));
                    data.setTransferTo(rs.getString("transferTo"));
                    report.add(data);
                    foundReport = true;
                    if(statusclr.equals("Waiting")){
                        data.setStatusColor("gold");
                    }
                    else if(statusclr.equals("Done")){
                        data.setStatusColor("green");
                    }
                    else if(statusclr.equals("Cancel")){
                        data.setStatusColor("red");
                    }
                    else if(statusclr.equals("CancelByAdmin")){
                        data.setStatusColor("red");
                    }
                }

                rs.close();
            //    System.out.println("ok 3");

            }

            if(username==null && status=="Waiting" && request=="Cashout"){
             //   String sql = "SELECT * FROM dprequest WHERE request='Cashout' AND date BETWEEN NOW() - INTERVAL 1 DAY AND NOW() ORDER by date desc";
                String sql = "";

             //   java.sql.Date endcln = new java.sql.Date(endCalendar.getTime());
                con = db.initConnection();

if(start==null || end==null){
    sql = "SELECT * FROM dprequest WHERE request='Cashout' AND date BETWEEN NOW() - INTERVAL 1 DAY AND NOW() ORDER by date desc";
    ps = con.prepareStatement(sql);
}else {
  //  String sStart = start.toString();
  //  String eEnd = end.toString();
 //   SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
 //   java.util.Date dateS = format.parse(sStart);
 //   java.util.Date dateE = format.parse(eEnd);
    sql = "SELECT * FROM dprequest WHERE request='Cashout' AND status != 'Cancel' AND date >= ? AND date <= ? ORDER by date desc";
    ps = con.prepareStatement(sql);
  //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ps.setTimestamp(1, new java.sql.Timestamp(start.getTime()));
    ps.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
  //  ps.setTimestamp(2, Timestamp.valueOf(sdf.format(new Timestamp(end.getTime()))));

  // System.out.println("Start "+sdf.format(new java.sql.Timestamp(start.getTime())));
  //  System.out.println("End "+sdf.format(new java.sql.Timestamp(end.getTime())));
    /*   RequestContext requestContext = RequestContext.getCurrentInstance();
    requestContext.update("Form:display");
    requestContext.execute("PF('dlg').show()");*/
}




                ResultSet rs = ps.executeQuery();

                cashls = new ArrayList<RequestList>();

                String statusclr = "";
                int counter = 0;

                while (rs.next()){
                    RequestList data = new RequestList();
                    String cn3="";
                    try {
                        String cn = rs.getString("cardnumber");
                        String[] cn2 = {cn.substring(0, 4), cn.substring(4, 8), cn.substring(8, 12), cn.substring(12, 16)};
                        cn3 = cn2[0] + " " + cn2[1] + " " + cn2[2] + " " + cn2[3];
                    }catch (Exception e){
                        e.getMessage();
                    }
                    data.setCardNumber(cn3);
                    data.setId(rs.getString("id"));
                    data.setRealName(rs.getString("realname"));
                    data.setManager(rs.getString("manager"));
                    data.setUsername(rs.getString("username"));
                    data.setAmount(rs.getInt("amount"));
                    statusclr = rs.getString("status");
                    data.setStatus(statusclr);
                    data.setDateRQ(rs.getString("date"));
                    data.setDateDone(rs.getString("dateDone"));

                    counter++;
                    sumCashout +=Integer.parseInt(rs.getString("amount"));
                    //   System.out.println(rs.getString("date"));
                    cashls.add(data);
                    foundcashls = true;
                    if(statusclr.equals("Waiting")){
                        data.setStatusColor("gold");
                        data.setCashoutCancel(true);
                        data.setUndoCancelCashout(false);
                        data.setUndoDoneCashout(false);
                    }
                    else if(statusclr.equals("CancelByAdmin")){
                        data.setStatusColor("red");
                        data.setUndoCancelCashout(true);
                        data.setCashoutCancel(false);
                        data.setUndoDoneCashout(false);
                    }
                    else if(statusclr.equals("Done") ){
                        data.setStatusColor("green");
                        data.setUndoDoneCashout(true);
                        data.setCashoutCancel(false);
                        data.setUndoCancelCashout(false);
                    }

                }

                rs.close();
                if(counter <= 0){
                    setNumberCashout("No New Cashout");
                    setColorCashout("gray");
                    setColorCashoutBack("");
                }else {
                    setNumberCashout(String.valueOf(getSumCashout()) + "k is waiting in " + String.valueOf(counter) + " Cashout request");
                    setColorCashout("gold");
                    setColorCashoutBack("darkred");
                }

            }else {
                String sql = "SELECT * FROM dprequest WHERE username='" + username + "' AND date BETWEEN NOW() - INTERVAL 3 DAY AND NOW() ORDER by date desc ";
                con = db.initConnection();
                ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                rql = new ArrayList<RequestList>();

                String statusclr = "";
                while (rs.next()) {
                    RequestList data = new RequestList();
                    data.setRequest(rs.getString("request"));
                    String cn3 = "";
                    try {
                        String cn = rs.getString("cardnumber");
                        String[] cn2 = {cn.substring(0, 4), cn.substring(4, 8), cn.substring(8, 12), cn.substring(12, 16)};
                        cn3 = cn2[0] + " " + cn2[1] + " " + cn2[2] + " " + cn2[3];
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    data.setCardNumber(cn3);
                    data.setId(rs.getString("id"));
                    data.setAmount(rs.getInt("amount"));
                    statusclr = rs.getString("status");
                    data.setStatus(statusclr);
                    data.setDateRQ(rs.getString("date"));
                    data.setDateDone(rs.getString("dateDone"));
                    data.setTransferTo(rs.getString("transferTo"));
                    //   System.out.println(rs.getString("date"));
                    rql.add(data);
                    foundRql = true;
                    if(rs.getString("request").equals("Cashout") && statusclr.equals("Waiting")){
                        data.setCashoutCancel(true);
                        data.setStatusColor("gold");
                    }
                   else if (statusclr.equals("Waiting")) {
                        data.setStatusColor("gold");
                    } else if (statusclr.equals("Done") || statusclr.equals("Complete")) {
                        data.setStatusColor("green");
                    } else if (statusclr.equals("CancelByAdmin")) {
                        data.setStatusColor("red");
                    } else if (statusclr.equals("Cancel")) {
                        data.setStatusColor("red");
                    }
                }

                rs.close();
            }
            if (foundDpl) {
                return dpl;
            }else if (foundcashls){
                return cashls;
            } else if(foundRql){
                return rql;
            } else if(foundReport){
                return report;
            }
            else {
                return null; // no entires found
            }
        } catch (Exception ex) {
            System.out.println("Error in getRequestList() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
return (null);
    }

    public ArrayList<RequestList> getWaitingPanel(String username){
        try {
            db = new Database();
            boolean foundRlp = false;
            ArrayList<RequestList> rlp = null;
        String sql = "SELECT * FROM dprequest WHERE status='Waiting' AND username='" + username + "' AND date BETWEEN NOW() - INTERVAL 3 DAY AND NOW() ORDER by date desc ";
        con = db.initConnection();
        ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        rlp = new ArrayList<RequestList>();

        String statusclr = "";
        while (rs.next()) {
            RequestList data = new RequestList();
            data.setId(rs.getString("id"));
            data.setUsername(rs.getString("username"));
            data.setManager(rs.getString("manager"));
            data.setRequest(rs.getString("request"));
            data.setAmount(rs.getInt("amount"));
            statusclr = rs.getString("status");
            data.setStatus(statusclr);
            rlp.add(data);
            foundRlp = true;
            if (rs.getString("request").equals("Cashout") && statusclr.equals("Waiting")) {
                data.setCashoutCancel(true);
                data.setStatusColor("gold");
                data.setWaitingRender(true);
            } else  {
                data.setStatusColor("gold");
data.setWaitingRender(true);
            }
        }
            rs.close();

    if (foundRlp) {

        return rlp;
    }
    else {
        return null; // no entires found
    }
} catch (Exception ex) {
        System.out.println("Error in getWaitingListPanel() -->" + ex.getMessage());

        } finally {
        db.closeConnection();
        }
        return (null);
        }
public void resetMessageDescription(){
    setMessageDescription("");
}

    public ArrayList<RequestList> getlastActionPanel(){
        try {
            db = new Database();
            boolean foundlastAction = false;
            ArrayList<RequestList> lastAction = null;
            String sql = "SELECT * FROM dprequest WHERE request != 'Cashout' AND status = 'Done' OR status = 'Complete'  AND date BETWEEN NOW() - INTERVAL 12 HOUR AND NOW() ORDER by date desc ";
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            lastAction = new ArrayList<RequestList>();

            String statusclr = "";
            while (rs.next()) {
                RequestList data = new RequestList();
                data.setId(rs.getString("id"));
                data.setUsername(rs.getString("username"));
                data.setManager(rs.getString("manager"));
                data.setRequest(rs.getString("request"));
                data.setAmount(rs.getInt("amount"));
                data.setDateRQ(rs.getString("date"));
                data.setTransferTo(rs.getString("transferTo"));
                statusclr = rs.getString("status");
                data.setStatus(statusclr);
                lastAction.add(data);
                foundlastAction = true;
                if(rs.getString("request").equals("NewUser")){
                    data.setLastActionNewuser(true);

                }else if (rs.getString("request").equals("Transfer")){
                    data.setLastActionTransfer(true);
                    data.setLastActionAmount(true);
                }
                else {

                    data.setLastActionAmount(true);
                }

            }

            rs.close();
            if (foundlastAction) {

                return lastAction;
            }
            else {
                return null; // no entires found
            }
        } catch (Exception ex) {
            System.out.println("Error in getLastActionPanel() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
        return (null);
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public  void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    private  String messageDescription;

    public void readMsg(Messages msg){

        // System.out.println(rl.getCardNumber());
        try {
            db = new Database();

            String sql = "SELECT * FROM messages WHERE id='"+msg.getId()+"'";
        //    ArrayList<Messages> desc = new ArrayList<Messages>();
            con = db.initConnection();

            ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
           //     Messages data = new Messages();
                String user = rs.getString("username");
                String stus = rs.getString("status");

                if(user != null && stus.equals("unRead")) {

                    rs.updateString("status", "Read");
                    rs.updateRow();
                }
                setMessageDescription(rs.getString("message"));
            }
            rs.close();
          //  return desc;
        } catch (Exception ex) {
            System.out.println("Error in readMsg() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
     //   return (null);
    }

    public int getMsgCounter() {
        return msgCounter;
    }

    public void setMsgCounter(int msgCounter) {
        this.msgCounter = msgCounter;
    }

    private static int msgCounter = 0;
public void checkMessages(String player){
setMsgCounter(0);
    try {

        db = new Database();
        String sql = "SELECT * FROM messages WHERE username = '"+player+"' AND date BETWEEN NOW() - INTERVAL 30 DAY AND NOW() ORDER by date desc ";
        con = db.initConnection();
        ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        String statusclr = "";
        while (rs.next()) {

            statusclr = rs.getString("status");

            if(statusclr.equals("unRead")){
                msgCounter++;
            }

        }

        if(msgCounter != 0){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgMsgAlert').show()");
        }
        rs.close();

    } catch (Exception ex) {
        System.out.println("Error in checkMessages() -->" + ex.getMessage());

    } finally {
        db.closeConnection();
    }
}



    public ArrayList<Messages> getMsgForUser(String player){
        try {

            db = new Database();
            boolean foundMsg = false;
            ArrayList<Messages> msg = null;
            String sql = "SELECT * FROM messages WHERE username = '"+player+"' AND date BETWEEN NOW() - INTERVAL 30 DAY AND NOW() ORDER by date desc ";
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
int counter = 0;
            msg = new ArrayList<Messages>();
            Messages data = null;
            String statusclr = "";
            while (rs.next()) {
                 data = new Messages();
                data.setId(rs.getString("id"));
                data.setSubject(rs.getString("subject"));
             //   data.setMessageDescription(rs.getString("message"));
                data.setDate(rs.getTimestamp("date"));
                statusclr = rs.getString("status");
                data.setStatus(statusclr);

                msg.add(data);
                foundMsg = true;
                if(statusclr.equals("unRead")){
                    data.setStatusColor("red");
                    counter++;
                }else  if(statusclr.equals("Read")){
                    data.setStatusColor("green");;

                }
            }
data.setNumberUnreadMsg(counter);
            if(counter != 0){
                data.setNumberColor("red");
            }else {
                data.setNumberColor("white");
            }
            rs.close();
            if (foundMsg) {

                return msg;
            }
            else {
                return null; // no entires found
            }
        } catch (Exception ex) {
            System.out.println("Error in getMsgForUser() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
        return (null);
    }


    public String getNumberWaiting() {
        return numberWaiting;
    }

    public void setNumberWaiting(String numberWaiting) {
        this.numberWaiting = numberWaiting;
    }

    private String numberWaiting = "Loading..." ;

    public String getColorWaiting() {
        return colorWaiting;
    }

    public void setColorWaiting(String colorWaiting) {
        this.colorWaiting = colorWaiting;
    }

    private String colorWaiting ="gray";

    public String getColorWaitingBack() {
        return colorWaitingBack;
    }

    public void setColorWaitingBack(String colorWaitingBack) {
        this.colorWaitingBack = colorWaitingBack;
    }

    private String colorWaitingBack;

    public  void setOfflineMessages(String username,String subject,String msg){
        try {
            db = new Database();
            String sql = "INSERT INTO messages (username,subject,message,status,date) VALUES (?,?,?,?,NOW())";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1,username);
            ps.setString(2, subject);
            ps.setString(3, msg);
            ps.setString(4,"unRead");
            ps.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Message to "+username+ " has been sent."));


        } catch (Exception ex) {
            System.out.println("Error in setOfflineMessages() -->" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "unSuccess",
                            "Message hasn't been saved!"));
        }

        finally {
            db.closeConnection();
        }  RequestContext.getCurrentInstance().update("FormMenu:messages");
    }

    public  void setlastpayoutTime(String username){
        try {
            db = new Database();
            String sql = "update playerInfo set lastpayout = ? ,lastRake = ?,lastpayoutTrue = ?,lastRakeTrue = ?  where username = ?";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setDate(1,new Date(System.currentTimeMillis()));
            ps.setString(2,getTotal());
            ps.setDate(3,new Date(System.currentTimeMillis()));
            ps.setString(4,getTotal());
            ps.setString(5,username);
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in setlastpayoutTime() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public  void setlastpayoutTimeEmpty(String username){
        try {
            db = new Database();
            String sql = "update playerInfo set lastpayout = ? ,lastRake = ? where username = ?";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setDate(1,new Date(System.currentTimeMillis()));
            ps.setString(2,getTotal());
            ps.setString(3,username);
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in setlastpayoutTimeEmpty() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public  void setCosts(int costs){
        try {
            db = new Database();
            String sql = "update income set costs = ? where date = DATE_FORMAT(NOW(),'%y-%m-%d')";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, costs);
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in setCosts() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public  void addlastpayoutToRequestTable(String username){
        try {
            db = new Database();
            String sql = "INSERT INTO dprequest (request,username,amount,status,date) VALUES (?,?,?,?,NOW())";
            con = db.initConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1,"Payout");
            ps.setString(2,username);
            ps.setString(3,getLastPayout());
            ps.setString(4,"Done");
            ps.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in addLastPayoutToRequestTable() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public  void firstlastpayoutTime(String username){
        try {
            db = new Database();
            String sql2 = "update playerInfo set lastpayout = ? where username = ?";
            String sql = "SELECT lastpayout FROM playerInfo WHERE username='" + username + "'";
            con = db.initConnection();
            ps = con.prepareStatement(sql);
            ps2 = con.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Date sqlDate = rs.getDate("lastpayout");
             //   System.out.println(sqlDate);
                if (sqlDate == null || sqlDate.equals("")) {
                    ps2.setDate(1,new Date(System.currentTimeMillis()));
                    ps2.setString(2,username);
                    ps2.executeUpdate();
                }
            }

            rs.close();



        } catch (Exception ex) {
            System.out.println("Error in FirstLastPayoutTime() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }



}
