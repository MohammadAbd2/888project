package com.saman.jsf.com.saman.jsf.report;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import com.saman.jsf.com.saman.jsf.info.AccountInformationBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@ManagedBean
@ViewScoped
public class SlotMachineReport implements Serializable {

    private java.util.Date startCalaedar ;
    private java.util.Date endCalendar ;
    private ArrayList<SlotMachineReport> slotMachineReports;
    private int startBank;
    private int endBank;
    private int amountWin;
    private int amountTotal;
    private String descriptions;
    private String date;
    private int sumTotal;

    public AccountInformationBean getAccountInformationBean() {
        return accountInformationBean;
    }

    public void setAccountInformationBean(AccountInformationBean accountInformationBean) {
        this.accountInformationBean = accountInformationBean;
    }

    @ManagedProperty(value="#{accountInformationBean}")
    private AccountInformationBean accountInformationBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

    public void initSlotMachineReport() {
        UserDAO dao = new UserDAO();
        slotMachineReports = dao.getSlotMachineReport(accountInformationBean.getUsername(), startCalaedar, endCalendar);
        int total = 0;
        for (SlotMachineReport slotMachineReport : getSlotMachineReports()) {
            total += slotMachineReport.getAmountTotal();
        }
        setSumTotal(total);
    }

    public void initSlotMachineReportUser() {
        UserDAO dao = new UserDAO();
        slotMachineReports = dao.getSlotMachineReport(loginBean.getUserN(), startCalaedar, endCalendar);
        int total = 0;
        for (SlotMachineReport slotMachineReport : getSlotMachineReports()) {
            total += slotMachineReport.getAmountTotal();
        }
        setSumTotal(total);
    }

    public int getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(int sumTotal) {
        this.sumTotal = sumTotal;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(int amountTotal) {
        this.amountTotal = amountTotal;
    }

    public int getAmountWin() {
        return amountWin;
    }

    public void setAmountWin(int amountWin) {
        this.amountWin = amountWin;
    }

    public int getEndBank() {
        return endBank;
    }

    public void setEndBank(int endBank) {
        this.endBank = endBank;
    }

    public int getStartBank() {
        return startBank;
    }

    public void setStartBank(int startBank) {
        this.startBank = startBank;
    }

    public ArrayList<SlotMachineReport> getSlotMachineReports() {
        return slotMachineReports;
    }

    public void setSlotMachineReports(ArrayList<SlotMachineReport> slotMachineReports) {
        this.slotMachineReports = slotMachineReports;
    }

    public Date getEndCalendar() {
        return endCalendar;
    }

    public void setEndCalendar(Date endCalendar) {
        this.endCalendar = endCalendar;
    }

    public Date getStartCalaedar() {
        return startCalaedar;
    }

    public void setStartCalaedar(Date startCalaedar) {
        this.startCalaedar = startCalaedar;
    }


}
