package com.saman.jsf.com.saman.jsf.cashier;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import com.saman.jsf.com.saman.jsf.moneymaking.AddUser;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by Saman on 2015-05-31.
 */
@ManagedBean
@ViewScoped
public class Cashier implements Serializable {
    AddUser addUser ;
    UserDAO dao;
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String username;
    private String amount;

    public String getAmountDP() {
        return amountDP;
    }

    public void setAmountDP(String amountDP) {
        this.amountDP = amountDP;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private String amountDP;
    private String cardNumber;

    public String getAmountcash() {
        return amountcash;
    }

    public void setAmountcash(String amountcash) {
        this.amountcash = amountcash;
    }

    private String amountcash;

    public String getCardNumberCash() {
        return cardNumberCash;
    }

    public void setCardNumberCash(String cardNumberCash) {
        this.cardNumberCash = cardNumberCash;
    }

    private String cardNumberCash;
    public void depositChip() {
        if (getAmountDP() == null || StringUtils.isBlank(getAmountDP()) || getCardNumber() == null || StringUtils.isBlank(getCardNumber())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Information can't empty"));
            return;

        } else if (Integer.parseInt(getAmountDP()) < 1 || Integer.parseInt(getAmountDP()) > 3000) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Amount must between 1-3000"));
            return;

        } else {
            String cn = getCardNumber().replace(" ", "");
            dao = new UserDAO();
            dao.checkDpRequest(loginBean.getUserN(), getAmountDP(), cn);
            if (!dao.isCheckDpReq()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "",
                                "Please wait...This request has already been registered and is being evaluated"));
            } else {
                dao.addDpRequest(loginBean.getUserN(), getAmountDP(), cn);

                dao.sendDeposit2();
           }
        }
    }

    public void cashOutChip() throws Exception {
        loginBean.chipBalance();
        if (getAmountcash() == null || StringUtils.isBlank(getAmountcash()) || getCardNumberCash() == null || StringUtils.isBlank(getCardNumberCash())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Information can't empty"));
            return;

        } else if (Integer.parseInt(getAmountcash()) < 50 || Integer.parseInt(getAmountcash()) > 90000) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Amount must between 50-90000"));
            return;

        } else {
            String cn = getCardNumberCash().replace(" ", "");
            int chipBalanceFrom = loginBean.getChipB() / 1000;
            if (Integer.parseInt(getAmountcash()) > chipBalanceFrom) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "This amount greater than your balance!"));
                return;
            } else {
                addUser =new AddUser();
                addUser.decChip(Integer.parseInt(getAmountcash()) * 1000, loginBean.getUserN());
                dao = new UserDAO();
                dao.addCashRequest(loginBean.getUserN(), getAmountcash(), cn,loginBean.getRealname());
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Your cashout request submited and will be done soon"));
            }
        }
    }





public void executeCancelPanel(){
    RequestContext.getCurrentInstance().execute("tabCashier:btnCancelCash");
}

    public void adminDoneCashout(){

        dao = new UserDAO();
      //  dao.doneCashout(loginBean.getUserN());
        RequestContext.getCurrentInstance().update("FormMenu:messages");

    }
    public void transferChip() throws Exception {
        loginBean.chipBalance();
        if (getUsername() == null || StringUtils.isBlank(getUsername()) || getAmount() == null || StringUtils.isBlank(getAmount()) ) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Information can't empty"));
            return;

        }
      else  if(Integer.parseInt(getAmount())<5 || Integer.parseInt(getAmount())>90000) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "Amount must between 5-90000"));
            return;

            }else {
            addUser =new AddUser();
            addUser.checkUsername(getUsername());
            int chipBalanceFrom = loginBean.getChipB()/1000;
            if(addUser.isCheckUN()){
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "Transfer failed! Player not found!"));
                return;
            }else  if(Integer.parseInt(getAmount())>chipBalanceFrom){
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "This amount greater than your balance!"));
                return;
            }
            else  if(getUsername().equalsIgnoreCase(loginBean.getUserN())) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "You can't transfer to yourself!"));
                return;
            }
            else{
                addUser.decChip(Integer.parseInt(getAmount()) * 1000, loginBean.getUserN());
                addUser.addChip(Integer.parseInt(getAmount()) * 1000, getUsername());
                dao =new UserDAO();
                dao.addTransferRequest(loginBean.getUserN(),getAmount(),getUsername());
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Transfer Completed!"));
                return;
            }
        }

    }


}
