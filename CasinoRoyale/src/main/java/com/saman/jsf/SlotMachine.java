package com.saman.jsf;

import com.saman.jsf.com.saman.jsf.moneymaking.AddUser;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Created by Saman on 2015-07-13.
 */
@ManagedBean
@RequestScoped
public class SlotMachine implements Serializable{
    UserDAO dao;
    public SlotMachine(){
         dao = new UserDAO();

    }
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    private int intMinBet = 0 ;
    private int intMaxBet = 20000 ;
    private int intMinLine = 1 ;
    private int intMaxLine = 17 ;
    private int intWinX = 0 ;

    public int getIntBank() {
        return intBank;
    }

    public void setIntBank(int intBank) {
        this.intBank = intBank;
    }

    public int getIntNewBank() {
        return intNewBank;
    }

    public void setIntNewBank(int intNewBank) {
        this.intNewBank = intNewBank;
    }

    public int getIntEndBank() {
        intBank = dao.slotMachineBank(loginBean.getUserN(),-1);
        return intEndBank;
    }

    public void setIntEndBank(int intEndBank) {
        this.intEndBank = intEndBank;
    }

    private int intBank ;
    private int intNewBank = 0;
    private int intEndBank = 0;

    public int getIntWinX1() {
        return intWinX1;
    }

    public void setIntWinX1(int intWinX1) {
        this.intWinX1 = intWinX1;
    }

    public int getIntWinX2() {
        return intWinX2;
    }

    public void setIntWinX2(int intWinX2) {
        this.intWinX2 = intWinX2;
    }

    public int getIntWinX3() {
        return intWinX3;
    }

    public void setIntWinX3(int intWinX3) {
        this.intWinX3 = intWinX3;
    }

    public int getIntWinX4() {
        return intWinX4;
    }

    public void setIntWinX4(int intWinX4) {
        this.intWinX4 = intWinX4;
    }

    public int getIntWinX5() {
        return intWinX5;
    }

    public void setIntWinX5(int intWinX5) {
        this.intWinX5 = intWinX5;
    }

    public int getIntWinX6() {
        return intWinX6;
    }

    public void setIntWinX6(int intWinX6) {
        this.intWinX6 = intWinX6;
    }

    public int getIntWinX7() {
        return intWinX7;
    }

    public void setIntWinX7(int intWinX7) {
        this.intWinX7 = intWinX7;
    }

    public int getIntWinX8() {
        return intWinX8;
    }

    public void setIntWinX8(int intWinX8) {
        this.intWinX8 = intWinX8;
    }

    public int getIntWinX9() {
        return intWinX9;
    }

    public void setIntWinX9(int intWinX9) {
        this.intWinX9 = intWinX9;
    }

    public int getIntWinX10() {
        return intWinX10;
    }

    public void setIntWinX10(int intWinX10) {
        this.intWinX10 = intWinX10;
    }

    public int getIntWinX11() {
        return intWinX11;
    }

    public void setIntWinX11(int intWinX11) {
        this.intWinX11 = intWinX11;
    }

    public int getIntWinX12() {
        return intWinX12;
    }

    public void setIntWinX12(int intWinX12) {
        this.intWinX12 = intWinX12;
    }

    public int getIntWinX13() {
        return intWinX13;
    }

    public void setIntWinX13(int intWinX13) {
        this.intWinX13 = intWinX13;
    }

    public int getIntWinX14() {
        return intWinX14;
    }

    public void setIntWinX14(int intWinX14) {
        this.intWinX14 = intWinX14;
    }

    public int getIntWinX15() {
        return intWinX15;
    }

    public void setIntWinX15(int intWinX15) {
        this.intWinX15 = intWinX15;
    }

    public int getIntWinX16() {
        return intWinX16;
    }

    public void setIntWinX16(int intWinX16) {
        this.intWinX16 = intWinX16;
    }

    public int getIntWinX17() {
        return intWinX17;
    }

    public void setIntWinX17(int intWinX17) {
        this.intWinX17 = intWinX17;
    }

    public String getStrResult() {
        return strResult;
    }

    public void setStrResult(String strResult) {
        this.strResult = strResult;
    }

    private int intWinX1 = 0 ;
    private int intWinX2 = 0 ;
    private int intWinX3 = 0 ;
    private int intWinX4 = 0 ;
    private int intWinX5 = 0 ;
    private int intWinX6 = 0 ;
    private int intWinX7 = 0 ;
    private int intWinX8 = 0 ;
    private int intWinX9 = 0 ;
    private int intWinX10 = 0 ;
    private int intWinX11 = 0 ;
    private int intWinX12 = 0 ;
    private int intWinX13 = 0 ;
    private int intWinX14 = 0 ;
    private int intWinX15 = 0 ;
    private int intWinX16 = 0 ;
    private int intWinX17 = 0 ;
    private String strResult = "Sorry! You Lost.";
    private String strStatus1 = "You Lost.";
    private String  strStatus2 = "You Lost.";
    private String  strStatus3 = "You Lost.";
    private String  strStatus4 = "You Lost.";
    private String  strStatus5 = "You Lost.";
    private String   strStatus6 = "You Lost.";
    private String   strStatus7 = "You Lost.";
    private String   strStatus8 = "You Lost.";
    private String   strStatus9 = "You Lost.";
    private String   strStatus10 = "You Lost.";
    private String   strStatus11 = "You Lost.";
    private String   strStatus12 = "You Lost.";
    private String  strStatus13 = "You Lost.";
    private String   strStatus14 = "You Lost.";
    private String   strStatus15 = "You Lost.";
    private String   strStatus16 = "You Lost.";
    private String    strStatus17 = "You Lost.";
    private int NWheel1 = 0 ;
    private int NWheel2 = 0;
    private int NWheel3 = 0;
    String[] arrSlot1 = "1,2,3,4,5,6,7,8,1,2,3,4,5,6,1,2,3,4,2,3,1,2".split(",");
    String[] arrSlot2 = "3,1,2,1,4,3,2,1,6,5,4,3,2,1,8,7,6,5,4,3,2,1".split(",");
    String[] arrSlot3 = "1,8,7,3,2,6,5,4,3,2,3,1,2,2,1,4,3,2,1,6,5,4".split(",");
    private int getX3(int wheelName){
        int x3 = 0;
        if(wheelName == 1){
            x3 = 3;
        }
        if(wheelName == 2){
            x3 = 6;
        }
        if(wheelName == 3){
            x3 = 8;
        }
        if(wheelName == 4){
            x3 = 15;
        }
        if(wheelName == 5){
            x3 = 30;
        }
        if(wheelName == 6){
            x3 = 50;
        }
        if(wheelName == 7){
            x3 = 100;
        }
        if(wheelName == 8){
            x3 = 200;
        }
        return x3;
    }
    private int getX2(int wheelName){
        int x2 = 0;
        if(wheelName == 1){
            x2 = 2;
        }
        if(wheelName == 2){
            x2 = 5;
        }
        if(wheelName == 3){
            x2 = 7;
        }
        if(wheelName == 4){
            x2 = 10;
        }

        return x2;
    }

    private void setWinX(int correctLine,int amount){
        if(correctLine == 1){
            intWinX1 = amount;
        }
        if(correctLine == 2){
            intWinX2 = amount;
        }
        if(correctLine == 3){
            intWinX3 = amount;
        }
        if(correctLine == 4){
            intWinX4 = amount;
        }
        if(correctLine == 5){
            intWinX5 = amount;
        }
        if(correctLine == 6){
            intWinX6 = amount;
        }
        if(correctLine == 7){
            intWinX7 = amount;
        }
        if(correctLine == 8){
            intWinX8 = amount;
        }
        if(correctLine == 9){
            intWinX9 = amount;
        }
        if(correctLine == 10){
            intWinX10 = amount;
        }
        if(correctLine == 11){
            intWinX11 = amount;
        }
        if(correctLine == 12){
            intWinX12 = amount;
        }
        if(correctLine == 13){
            intWinX13 = amount;
        }
        if(correctLine == 14){
            intWinX14 = amount;
        }
        if(correctLine == 15){
            intWinX15 = amount;
        }
        if(correctLine == 16){
            intWinX16 = amount;
        }
        if(correctLine == 17){
            intWinX17 = amount;
        }
    }
private int getWinX(int correctLine){
    int getwinX = 0;
    if(correctLine == 1){
        getwinX = intWinX1;
    }
    if(correctLine == 2){
        getwinX = intWinX2;
    }
    if(correctLine == 3){
        getwinX = intWinX3 ;
    }
    if(correctLine == 4){
        getwinX = intWinX4 ;
    }
    if(correctLine == 5){
        getwinX = intWinX5 ;
    }
    if(correctLine == 6){
        getwinX = intWinX6 ;
    }
    if(correctLine == 7){
        getwinX =  intWinX7 ;
    }
    if(correctLine == 8){
        getwinX =  intWinX8;
    }
    if(correctLine == 9){
        getwinX =  intWinX9 ;
    }
    if(correctLine == 10){
        getwinX = intWinX10 ;
    }
    if(correctLine == 11){
        getwinX =   intWinX11 ;
    }
    if(correctLine == 12){
        getwinX = intWinX12 ;
    }
    if(correctLine == 13){
        getwinX = intWinX13 ;
    }
    if(correctLine == 14){
        getwinX =  intWinX14 ;
    }
    if(correctLine == 15){
        getwinX = intWinX15 ;
    }
    if(correctLine == 16){
        getwinX =  intWinX16 ;
    }
    if(correctLine == 17){
        getwinX = intWinX17 ;
    }
    return getwinX;
}

    private void setStatus(int correctLine){
        if(correctLine == 1){
            strStatus1 = "You have won.";
        }
        if(correctLine == 2){
            strStatus12 = "You have won.";
        }
        if(correctLine == 3){
            strStatus3 = "You have won.";
        }
        if(correctLine == 4){
            strStatus4 = "You have won.";
        }
        if(correctLine == 5){
            strStatus5 = "You have won.";
        }
        if(correctLine == 6){
            strStatus6 = "You have won.";
        }
        if(correctLine == 7){
            strStatus7 = "You have won.";
        }
        if(correctLine == 8){
            strStatus8 = "You have won.";
        }
        if(correctLine == 9){
            strStatus9 = "You have won.";
        }
        if(correctLine == 10){
            strStatus10 = "You have won.";
        }
        if(correctLine == 11){
            strStatus11 = "You have won.";
        }
        if(correctLine == 12){
            strStatus12 = "You have won.";
        }
        if(correctLine == 13){
            strStatus13 = "You have won.";
        }
        if(correctLine == 14){
            strStatus14 = "You have won.";
        }
        if(correctLine == 15){
            strStatus15 = "You have won.";
        }
        if(correctLine == 16){
            strStatus16 = "You have won.";
        }
        if(correctLine == 17){
            strStatus17 = "You have won.";
        }
    }

    private String getStatus(int correctLine){
        String status = "";
        if(correctLine == 1){
            status = strStatus1 ;
        }
        if(correctLine == 2){
            status = strStatus12;
        }
        if(correctLine == 3){
            status = strStatus3 ;
        }
        if(correctLine == 4){
            status = strStatus4 ;
        }
        if(correctLine == 5){
            status = strStatus5 ;
        }
        if(correctLine == 6){
            status = strStatus6 ;
        }
        if(correctLine == 7){
            status = strStatus7 ;
        }
        if(correctLine == 8){
            status = strStatus8 ;
        }
        if(correctLine == 9){
            status = strStatus9 ;
        }
        if(correctLine == 10){
            status = strStatus10 ;
        }
        if(correctLine == 11){
            status = strStatus11 ;
        }
        if(correctLine == 12){
            status = strStatus12 ;
        }
        if(correctLine == 13){
            status = strStatus13 ;
        }
        if(correctLine == 14){
            status = strStatus14 ;
        }
        if(correctLine == 15){
            status = strStatus15 ;
        }
        if(correctLine == 16){
            status = strStatus16 ;
        }
        if(correctLine == 17){
            status = strStatus17 ;
        }
        return status;
    }

    private void getLineWheels(int lineCounter,int Wheel1,int Wheel2,int Wheel3){
        NWheel1 = Wheel1;
        NWheel2 = Wheel2;
        NWheel3 = Wheel3;
        if(lineCounter == 2){
            NWheel1 = Wheel1-1;
            NWheel2 = Wheel2-1;
            NWheel3 = Wheel3-1;
        }
        if(lineCounter == 3){
            NWheel1 = Wheel1+1;
            NWheel2 = Wheel2+1;
            NWheel3 = Wheel3+1;
        }
        if(lineCounter == 4){
            NWheel1 = Wheel1+1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3-1;
        }
        if(lineCounter == 5){
            NWheel1 = Wheel1-1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3+1;
        }
        if(lineCounter == 6){
            NWheel1 = Wheel1;
            NWheel2 = Wheel2-1;
            NWheel3 = Wheel3;
        }
        if(lineCounter == 7){
            NWheel1 = Wheel1;
            NWheel2 = Wheel2+1;
            NWheel3 = Wheel3;
        }
        if(lineCounter == 8){
            NWheel1 = Wheel1-1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3-1;
        }
        if(lineCounter == 9){
            NWheel1 = Wheel1+1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3+1;
        }
        if(lineCounter == 10){
            NWheel1 = Wheel1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3-1;
        }
        if(lineCounter == 11){
            NWheel1 = Wheel1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3+1;
        }
        if(lineCounter == 12){
            NWheel1 = Wheel1;
            NWheel2 = Wheel2+1;
            NWheel3 = Wheel3+1;
        }
        if(lineCounter == 13){
            NWheel1 = Wheel1;
            NWheel2 = Wheel2-1;
            NWheel3 = Wheel3-1;
        }
        if(lineCounter == 14){
            NWheel1 = Wheel1-1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3;
        }
        if(lineCounter == 15){
            NWheel1 = Wheel1+1;
            NWheel2 = Wheel2;
            NWheel3 = Wheel3;
        }
        if(lineCounter == 16){
            NWheel1 = Wheel1-1;
            NWheel2 = Wheel2-1;
            NWheel3 = Wheel3;
        }
        if(lineCounter == 17){
            NWheel1 = Wheel1+1;
            NWheel2 = Wheel2+1;
            NWheel3 = Wheel3;
        }
    }



    public String getCheckBank() {
        return checkBank;
    }

    public void setCheckBank(String checkBank) {
        this.checkBank = checkBank;
    }

    private String checkBank ;
    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getWheel1() {
        return wheel1;
    }

    public void setWheel1(int wheel1) {
        this.wheel1 = wheel1;
    }



    public int getWheel2() {
        return wheel2;
    }

    public void setWheel2(int wheel2) {
        this.wheel2 = wheel2;
    }

    public int getWheel3() {
        return wheel3;
    }

    public void setWheel3(int wheel3) {
        this.wheel3 = wheel3;
    }

    private  int wheel1;
    private  int wheel2;
    private  int wheel3;

     String lineslabel =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lineslabel");
    String betslabel =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("betslabel");

    private int lines ;

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    private int bet;

    public int getTotal() {

        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private int total;

    public void setTotals(){
        setTotal((getLines() * getBet())/1000);
    }
    private SecureRandom random = new SecureRandom();

    public int nextSessionId(int min,int max) {
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }


    public String getModeCashier() {
        return modeCashier;
    }

    public void setModeCashier(String modeCashier) {
        this.modeCashier = modeCashier;
    }

    private String modeCashier;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String amount;

    public void cashierSlotMachine()  {
        try {
            loginBean.chipBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        intBank = dao.slotMachineBank(loginBean.getUserN(),-1);
        try {


        if (getAmount() == null || StringUtils.isBlank(getAmount())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Amount can't empty"));
            return;
        }
        else if (Integer.parseInt(getAmount()) < 1) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Amount must between 1 to Chips in Your Bank"));
            return;

        }else if(modeCashier == null || StringUtils.isBlank(getModeCashier())){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Please Select Mode of Transfer"));
            return;
        }
        else {
            if (modeCashier.equals("bankToSlot")){
               AddUser addUser =new AddUser();
                int chipBalanceBank = loginBean.getChipB()/1000;
                if(Integer.parseInt(getAmount())>chipBalanceBank){
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "This amount greater than your balance!"));
                    return;
                }
                addUser.decChip(Integer.parseInt(getAmount()) * 1000, loginBean.getUserN());
                dao =new UserDAO();
                int getSlotBank = dao.slotMachineBank(loginBean.getUserN(),-1);
                int setSlotBank = getSlotBank + Integer.parseInt(getAmount()) * 1000;
                dao.slotMachineBank(loginBean.getUserN(),setSlotBank);
                intBank = dao.slotMachineBank(loginBean.getUserN(),-1);
                dao.addTransferRequest(loginBean.getUserN(),getAmount(),"BankToSlot");
                try {
                    loginBean.chipBalance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Transfer To SlotMachine Completed!"));
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("Currency()");
            }
          else if (modeCashier.equals("slotToBank")){
                AddUser addUser =new AddUser();
                int chipBalanceSlot = dao.slotMachineBank(loginBean.getUserN(), -1)/1000;
                if(Integer.parseInt(getAmount())>chipBalanceSlot){
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "This amount greater than your balance!"));
                    return;
                }

                dao =new UserDAO();
                int getSlotBank = dao.slotMachineBank(loginBean.getUserN(),-1);
                int setSlotBank = getSlotBank - Integer.parseInt(getAmount()) * 1000;
                dao.slotMachineBank(loginBean.getUserN(),setSlotBank);
                addUser.addChip(Integer.parseInt(getAmount()) * 1000, loginBean.getUserN());
                intBank = dao.slotMachineBank(loginBean.getUserN(),-1);
                dao.addTransferRequest(loginBean.getUserN(),getAmount(),"SlotToBank");
                try {
                    loginBean.chipBalance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Success",
                                "Transfer To Bank Completed!"));
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("Currency()");
            }
        }
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Enter numbers"));

        }
    }



    public void startSlot() throws Exception {

        intBank = dao.slotMachineBank(loginBean.getUserN(), -1);
        lines = Integer.parseInt(lineslabel);
        String betformat = betslabel.replace(",", "");
        bet = Integer.parseInt(betformat);
        if ((lines * bet) > intBank) {
            setCheckBank("false");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("endForce()");
         //   Thread.sleep(500);
            loginBean.chipBalance();
            context.execute("PF('dlgslotcashier').show()");
        } else {
            setWheel1(nextSessionId(1, 20));
            setWheel2(nextSessionId(1, 20));
            setWheel3(nextSessionId(1, 20));
            setCheckBank("true");

            //  System.out.println(lines +"    " + bet +" "+ arrSlot1[7]+ " " +arrSlot2[14]+" "+arrSlot3[1]);

            for (int i = 1; i <= lines; i++) {
                getLineWheels(i, wheel1, wheel2, wheel3);
                if (arrSlot1[NWheel1].equals(arrSlot2[NWheel2]) && arrSlot2[NWheel2].equals(arrSlot3[NWheel3])) {
                    setWinX(i, getX3(Integer.parseInt(arrSlot3[NWheel3])));
                } else if (arrSlot1[NWheel1].equals(arrSlot2[NWheel2]) && !arrSlot2[NWheel2].equals(arrSlot3[NWheel3])) {
                    setWinX(i, getX2(Integer.parseInt(arrSlot2[NWheel2])));
                } else if (Integer.parseInt(arrSlot1[NWheel1]) == 1) {
                    setWinX(i, 1);

                }
                if (getWinX(i) > 0) {
                    setStatus(i);
                }
            }
            intWinX = intWinX1 + intWinX2 + intWinX3 + intWinX4 + intWinX5 + intWinX6 + intWinX7 + intWinX8 + intWinX9 + intWinX10 + intWinX11 + intWinX12 + intWinX13 + intWinX14 + intWinX15 + intWinX16 + intWinX17;
            if (intWinX > 0) {
                strResult = "Congratulations! You have won <span style='color: gold' class='currency'>" + (intWinX * bet) + "</span> chips.<br/>";
            }
            intNewBank = intBank - (bet * lines);
            intEndBank = intNewBank + (bet * intWinX);
            dao.addLogSlotMachine(loginBean.getUserN(),intBank,intEndBank,(intEndBank-intBank),(intWinX*bet),"bet : "+bet+" | line : "+lines);
            intBank = dao.slotMachineBank(loginBean.getUserN(),intEndBank);

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("start()");
            //    Thread.sleep(4000);
            //  context.execute("PF('slotStartSpin').enable()");
            //  context.execute("PF('slotCashier').enable()");

      /*  FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        String.valueOf(wheel1),
                        ""));*/

//RequestContext.getCurrentInstance().update("slotid:FormSlot:messages");
        }
    }

}
