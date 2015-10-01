package com.saman.jsf.com.saman.jsf.cashier;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Saman on 2015-06-11.
 */
@ManagedBean
@ViewScoped
public class RequestList implements Serializable {

    public boolean isLastActionAmount() {
        return lastActionAmount;
    }

    public void setLastActionAmount(boolean lastActionAmount) {
        this.lastActionAmount = lastActionAmount;
    }

    public boolean isLastActionNewuser() {
        return lastActionNewuser;
    }

    public void setLastActionNewuser(boolean lastActionNewuser) {
        this.lastActionNewuser = lastActionNewuser;
    }

    private boolean lastActionAmount;
    private boolean lastActionNewuser;
    public Date getStartcln() {
        return startcln;
    }

    public void setStartcln(Date startcln) {
        this.startcln = startcln;
    }

    public Date getEndcln() {
        return endcln;
    }

    public void setEndcln(Date endcln) {
        this.endcln = endcln;
    }

    private java.sql.Date startcln ;
    private java.sql.Date endcln ;
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

    private UserDAO dao;


    private ArrayList<RequestList> dpl;
    private ArrayList<RequestList> rql;
    private ArrayList<RequestList> rlp;
    private ArrayList<RequestList> cashls;



    private ArrayList<RequestList> dprl;
    private ArrayList<RequestList> report;



    public ArrayList<RequestList> getReport() {
        return report;
    }

    public void setReport(ArrayList<RequestList> report) {
        this.report = report;
    }


    public ArrayList<RequestList> getLastAction() {
        dao =new UserDAO();
        return  dao.getlastActionPanel();
    }

    public void setLastAction(ArrayList<RequestList> lastAction) {
        this.lastAction = lastAction;
    }

    private ArrayList<RequestList> lastAction;
  //  @PostConstruct
    public void init() {

        dao =new UserDAO();
        rql =    dao.getRequestList(null,loginBean.getUserN(),null,null,null);

     /*   rlp = dao.getWaitingPanel(loginBean.getUserN());
        dpl = dao.getRequestList("Deposit",null,"Waiting");
        cashls = dao.getRequestList("Cashout",null,"Waiting");
        dprl = dao.getDpListAction();*/
    }

    public boolean isUndoDoneCashout() {
        return undoDoneCashout;
    }

    public void setUndoDoneCashout(boolean undoDoneCashout) {
        this.undoDoneCashout = undoDoneCashout;
    }

    private boolean undoDoneCashout;
    public void initReport(){
        dao = new UserDAO();
        report = dao.getRequestList("Report",null,null,startCalaedar,endCalendar);
    }
    public void initCashls() {
        dao =new UserDAO();
     //   dao.setdate();
     //   setStartcln(new java.sql.Date(startCalaedar.getTime()));
    //    setEndcln(new java.sql.Date(endCalendar.getTime()));
      //  caledarset();
        cashls = dao.getRequestList("Cashout",null,"Waiting",startCalaedar,endCalendar);
   //     System.out.println(getStartCalaedar());
     //   System.out.println(getEndCalendar());
    }
    public void updateDoneCashout() {
        dao =new UserDAO();
         caledarset2();
        cashls = dao.getRequestList("Cashout",null,"Waiting",startCalaedar,endCalendar);

    }

    public java.util.Date getStartCalaedar() {
        return startCalaedar;
    }

    public void setStartCalaedar(java.util.Date startCalaedar) {
        this.startCalaedar = startCalaedar;
    }

    public java.util.Date getEndCalendar() {
        return endCalendar;
    }

    public void setEndCalendar(java.util.Date endCalendar) {
        this.endCalendar = endCalendar;
    }

    private java.util.Date startCalaedar ;
    private java.util.Date endCalendar ;

    public void caledarset(){
        long millis=System.currentTimeMillis();
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);

        startCalaedar = new java.util.Date (c.getTimeInMillis());
        endCalendar = new java.util.Date(System.currentTimeMillis()+(1*1000));
   //     System.out.println(getStartCalaedar());
    //    System.out.println(getEndCalendar());
    }
    public void caledarset2(){

        endCalendar = new java.util.Date(System.currentTimeMillis()+(1*1000));
        //     System.out.println(getStartCalaedar());
        //    System.out.println(getEndCalendar());
    }
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    private String request;
    private String cardNumber;
    private  int amount;
    private String status;

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    private String manager;

    public boolean isCashoutCancel() {
        return cashoutCancel;
    }

    public void setCashoutCancel(boolean cashoutCancel) {
        this.cashoutCancel = cashoutCancel;
    }

    private boolean cashoutCancel;


    public boolean isUndoCancelCashout() {
        return undoCancelCashout;
    }

    public void setUndoCancelCashout(boolean undoCancelCashout) {
        this.undoCancelCashout = undoCancelCashout;
    }

    private boolean undoCancelCashout;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    private String transferTo;
    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    private String statusColor ;
    public String getDateRQ() {
        return dateRQ;
    }

    public void setDateRQ(String dateRQ) {
        this.dateRQ = dateRQ;
    }

    private String dateRQ;

    public String getDateDone() {
        return dateDone;
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public boolean isLastActionTransfer() {
        return lastActionTransfer;
    }

    public void setLastActionTransfer(boolean lastActionTransfer) {
        this.lastActionTransfer = lastActionTransfer;
    }

    private boolean lastActionTransfer;
    private String dateDone;



    public ArrayList<RequestList> getRlp() {
        dao =new UserDAO();
        return  dao.getWaitingPanel(loginBean.getUserN());
    }

    public void setRlp(ArrayList<RequestList> rlp) {
        this.rlp = rlp;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    private String realName;
    public ArrayList<RequestList> getCashls() {
      //  dao =new UserDAO();
        return  cashls; //dao.getRequestList("Cashout",null,"Waiting");
    }

    public void setCashls(ArrayList<RequestList> cashls) {
        this.cashls = cashls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public boolean isWaitingRender() {
        return waitingRender;
    }

    public void setWaitingRender(boolean waitingRender) {
        this.waitingRender = waitingRender;
    }

    private boolean waitingRender;

    public ArrayList<RequestList> getDprl() {
       dao =new UserDAO();
        return  dao.getDpListAction();

    }

    public void setDprl(ArrayList<RequestList> dprl) {
        this.dprl = dprl;
    }

    public ArrayList<RequestList> getDpl() {
       dao =new UserDAO();
        return dao.getRequestList("Deposit",null,"Waiting",null,null);
    }

    public void setDpl(ArrayList<RequestList> dpl) {
        this.dpl = dpl;
    }

    public ArrayList<RequestList> getRql() {
     //  dao =new UserDAO();
        return rql; //dao.getRequestList(null,loginBean.getUserN(),null);
    }

    public void setRql(ArrayList<RequestList> rql) {
        this.rql = rql;
    }


}
