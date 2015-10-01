package com.saman.jsf.com.saman.jsf.report;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Saman on 2015-06-26.
 */
@ManagedBean
@ViewScoped
public class Income implements Serializable{
    public int getRake() {
        return rake;
    }

    public void setRake(int rake) {
        this.rake = rake;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }

    public int getPayouts() {
        return payouts;
    }

    public void setPayouts(int payouts) {
        this.payouts = payouts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCasinoRake() {
        return casinoRake;
    }

    public void setCasinoRake(int casinoRake) {
        this.casinoRake = casinoRake;
    }

    public int getUserRake() {
        return userRake;
    }

    public void setUserRake(int userRake) {
        this.userRake = userRake;
    }

    private int casinoRake;
    private  int userRake;
    private int rake;
    private int entryFee;
    private int payouts;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    private int slot;
    private int total;
    private int percent;
    private String date;

    public int getTourney() {
        return tourney;
    }

    public void setTourney(int tourney) {
        this.tourney = tourney;
    }

    public String getSumTourney() {
        return sumTourney;
    }

    public void setSumTourney(String sumTourney) {
        this.sumTourney = sumTourney;
    }

    private String sumTourney;
    private  int tourney;
    public Date getStartCalaedar() {
        return startCalaedar;
    }

    public void setStartCalaedar(Date startCalaedar) {
        this.startCalaedar = startCalaedar;
    }

    public Date getEndCalendar() {
        return endCalendar;
    }

    public void setEndCalendar(Date endCalendar) {
        this.endCalendar = endCalendar;
    }

    public String getSumCasinoRake() {
        return sumCasinoRake;
    }

    public void setSumCasinoRake(String sumCasinoRake) {
        this.sumCasinoRake = sumCasinoRake;
    }

    public String getSumUserRake() {
        return sumUserRake;
    }

    public void setSumUserRake(String sumUserRake) {
        this.sumUserRake = sumUserRake;
    }

    public int getCosts() {
        return costs;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    private int costs ;
    public void setCost(){
        UserDAO dao = new UserDAO();
        dao.setCosts(getCosts()*-1);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success",
                        "Costs:"+(getCosts()*-1)+" add to DataBase"));
        RequestContext.getCurrentInstance().update("FormMenu:messages");
    }

    public int getCostsDb() {
        return costsDb;
    }

    public void setCostsDb(int costsDb) {
        this.costsDb = costsDb;
    }



    private int costsDb;

    public String getSumCostsDb() {
        return sumCostsDb;
    }

    public void setSumCostsDb(String sumCostsDb) {
        this.sumCostsDb = sumCostsDb;
    }

    private String sumCostsDb;
    private String sumCasinoRake;
    private String sumUserRake;
    private Date startCalaedar ;
    private Date endCalendar ;
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    public void caledarset(){

        startCalaedar = new Date (System.currentTimeMillis());
        endCalendar = new Date(System.currentTimeMillis());
    //        System.out.println(getStartCalaedar());
      //      System.out.println(getEndCalendar());
    }
    public void initIncome(){
        UserDAO dao = new UserDAO();
        totalIncome = dao.getTotalIncome(loginBean.getUserN(),startCalaedar,endCalendar);
        int total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getRake();
        }
        setSumRake(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getCasinoRake();
        }
        setSumCasinoRake(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getUserRake();
        }
        setSumUserRake(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getTourney();
        }
        setSumTourney(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getSlot();
        }
        setSumSlot(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getCostsDb();
        }
        setSumCostsDb(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getTotal();
        }
        setSumTotal(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getEntryFee();
        }
        setSumEntryfee(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getPayouts();
        }
        setSumPayouts(String.valueOf(total));
        total = 0;
        for(Income income : getTotalIncome()) {
            total += income.getPercent();
        }
        setSumPercent(String.valueOf(total));
    }
    public ArrayList<Income> getTotalIncome() {

        return totalIncome;
    }

    public void setTotalIncome(ArrayList<Income> totalIncome) {
        this.totalIncome = totalIncome;
    }



    private ArrayList<Income> totalIncome;

    public String getSumTotal() {
        return sumTotal;
        }

    public void setSumTotal(String sumTotal) {
        this.sumTotal = sumTotal;
    }

    private String sumTotal;

    public String getSumSlot() {
        return sumSlot;
    }

    public void setSumSlot(String sumSlot) {
        this.sumSlot = sumSlot;
    }

    private String sumSlot;
    public String getSumRake() {


        return  sumRake;
    }

    public void setSumRake(String sumRake) {
        this.sumRake = sumRake;
    }

    private String sumRake ;
private String sumEntryfee;
    private String sumPayouts;

    public String getSumPercent() {
        return sumPercent;
    }

    public void setSumPercent(String sumPercent) {
        this.sumPercent = sumPercent;
    }

    public String getSumPayouts() {
        return sumPayouts;
    }

    public void setSumPayouts(String sumPayouts) {
        this.sumPayouts = sumPayouts;
    }

    public String getSumEntryfee() {
        return sumEntryfee;
    }

    public void setSumEntryfee(String sumEntryfee) {
        this.sumEntryfee = sumEntryfee;
    }

    private String sumPercent;

}
