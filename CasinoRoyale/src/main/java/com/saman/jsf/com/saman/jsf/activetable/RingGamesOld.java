package com.saman.jsf.com.saman.jsf.activetable;

import com.saman.jsf.ApiIncome;
import com.saman.jsf.LoginBean;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Created by Saman on 2015-07-21.
 */
@ManagedBean
@ViewScoped
public class RingGamesOld {
    ApiIncome api = new ApiIncome();




    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    public void openTable(ActiveTable at){

        String command = "&Command=RingGamesOpen&Name="+at.getTableName()+"&Player="+loginBean.getUserN();
        api.sendPost(command);
    }

public ArrayList<ActiveTable> getActiveTable(){

    try {
    boolean foundTable = false;
    String command = "&Command=RingGamesList&Fields=Name,Status,Seats";
    api.sendPost(command);
    ArrayList<ActiveTable> activeTables = null;
    activeTables = new ArrayList<ActiveTable>();
    if (api.jsobj.getString("Result").equals("Ok")) {

        String tableName = api.jsobj.getString("Name");
        String t1 = tableName.replace("[", "");
        String t2 = t1.replace("]", "");
        String t3 = t2.replace("\"", "");
        String[] tableN = t3.split(",");

        String tableSeat = api.jsobj.getString("Seats");
        String s1 = tableSeat.replace("[", "");
        String s2 = s1.replace("]", "");
        String[] seatN = s2.split(",");

        String status = api.jsobj.getString("Status");
        String ar = status.replace("[", "");
        String ar2 = ar.replace("]", "");
        String ar3 = ar2.replace("\"", "");
        String ar4 = ar3.replace("Offline", "Playing: 0");
        String ar5 = ar4.replace("Playing: ","");
        String[] statusN = ar5.split(",");

        for(int i = 0;i<statusN.length;i++) {
            if (Integer.parseInt(statusN[i]) > 0) {
              //  System.out.println(tableN[i] + "   " + statusN[i] + "/" + seatN[i]);
                ActiveTable data = new ActiveTable();

                data.setTableName(tableN[i]);
                data.setPlayerSeat(statusN[i] + "/" + seatN[i]);

                if(tableN[i].indexOf("1K/2K") != -1) {
                    data.setColorActiveTable("rgba(175, 115, 5, .8)");
                }else if(tableN[i].indexOf("2K/4K") != -1){
                    data.setColorActiveTable("rgba(175, 115, 5, 1)");
                }else if (tableN[i].indexOf("3K/6K") != -1 ){
                    data.setColorActiveTable("rgba(255, 255, 255, .4)");
                }else if (tableN[i].indexOf("5K/10K") != -1) {
                    data.setColorActiveTable("rgba(255, 50, 48, 0.5)");
                }else if( tableN[i].indexOf("10K/20K") != -1){
                    data.setColorActiveTable("rgba(255, 50, 48, 0.4)");
                }else if( tableN[i].indexOf("15K/30K") != -1 ) {
                    data.setColorActiveTable("rgba(5, 165, 223, 1)");
                }else if (tableN[i].indexOf("25K/50K") != -1 ) {
                    data.setColorActiveTable("rgba(5, 165, 223, .8)");
                }else if( tableN[i].indexOf("40K/80K") != -1 || tableN[i].indexOf("50K/100K") != -1 ) {
                    data.setColorActiveTable("rgba(5, 165, 223, .6)");
                }else if( tableN[i].indexOf("100K/200K") != -1 ){
                    data.setColorActiveTable("rgba(5, 165, 223, .6)");
                }else{
                    data.setColorActiveTable("rgba(0,0,0,.5)");
                }
                activeTables.add(data);
                foundTable = true;
             //   System.out.println(data.getTableName());
            }
        }
    }
    else {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Warning",
                        "Server can't get ActiveTable!"));
        RequestContext.getCurrentInstance().update("FormMenu:messages");
    }
    if (foundTable) {

        return activeTables;
    }
    else {
        return null; // no entires found
    }
} catch (Exception ex) {
        System.out.println("Error in getActiveTable() -->" + ex.getMessage());

    }
    return (null);
}
}
