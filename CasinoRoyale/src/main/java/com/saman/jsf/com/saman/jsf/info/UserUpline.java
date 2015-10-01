package com.saman.jsf.com.saman.jsf.info;

import com.saman.jsf.UserDAO;
import com.saman.jsf.com.saman.jsf.cashier.RequestList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Saman on 2015-07-02.
 */
@ManagedBean
@ViewScoped
public class UserUpline implements Serializable{

    public AccountInformationBean getAccountInformationBean() {
        return accountInformationBean;
    }

    public void setAccountInformationBean(AccountInformationBean accountInformationBean) {
        this.accountInformationBean = accountInformationBean;
    }

    @ManagedProperty(value="#{accountInformationBean}")
    private AccountInformationBean accountInformationBean;
    public boolean isUplineCheck() {
        return uplineCheck;
    }

    public void setUplineCheck(boolean uplineCheck) {
        this.uplineCheck = uplineCheck;
    }

    public boolean isDownlineCheck() {
        return downlineCheck;
    }

    public void setDownlineCheck(boolean downlineCheck) {
        this.downlineCheck = downlineCheck;
    }

    private boolean uplineCheck;
    private boolean downlineCheck;
    public String getUpline() {
        return upline;
    }

    public void setUpline(String upline) {
        this.upline = upline;
    }

    private String upline;

    public String getDownline() {
        return downline;
    }

    public void setDownline(String downline) {
        this.downline = downline;
    }

    private String downline;

    public ArrayList<UserUpline> getuUpline() {
        return uUpline;
    }

    public void setuUpline(ArrayList<UserUpline> uUpline) {
        this.uUpline = uUpline;
    }

    private ArrayList<UserUpline> uUpline;

    public void initUpline(){
        UserDAO dao = new UserDAO();
       uUpline = dao.getUserUpline(accountInformationBean.getUsername());
    }
    public void initUplineLastAction(RequestList dl4){
        UserDAO dao = new UserDAO();
        uUpline = dao.getUserUpline(dl4.getUsername());
    }
}
