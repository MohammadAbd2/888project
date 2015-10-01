package com.saman.jsf.com.saman.jsf.activetable;

import com.saman.jsf.ApiIncome;
import com.saman.jsf.DatabaseIncome;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by Saman on 2015-07-21.
 */
@ManagedBean
@ViewScoped
public class ActiveTable extends TimerTask implements Serializable {
    ApiIncome api = new ApiIncome();
    public void run() {
        try {
           insertActiveTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    //    System.out.println("UpdateActiveTable.");

    }

    public String getColorActiveTable() {
        return colorActiveTable;
    }

    public void setColorActiveTable(String colorActiveTable) {
        this.colorActiveTable = colorActiveTable;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPlayerSeat() {
        return playerSeat;
    }

    public void setPlayerSeat(String playerSeat) {
        this.playerSeat = playerSeat;
    }

public void initActiveTable(){
    RingGames ringGames = new RingGames();
    activeTables = ringGames.getActiveTable();
  //  System.out.println(tableName);
}
    @PostConstruct
    public void initActiveTable2(){
        RingGames ringGames = new RingGames();
        activeTables = ringGames.getActiveTable();
    }

    public void setActiveTables(ArrayList<ActiveTable> activeTables) {
        this.activeTables = activeTables;
    }


    private String colorActiveTable;
    private String tableName;
    private String playerSeat;

    public  void insertActiveTable() throws Exception {
        DatabaseIncome db = new DatabaseIncome();
        try {
            PreparedStatement s =null;
            Connection con = db.initConnection();
            String _deleteTableData ="TRUNCATE TABLE activetable";
            s = con.prepareStatement(_deleteTableData);
            s.executeUpdate();
            String sql = "INSERT INTO activetable (tableName,seatNumber,color) VALUES (?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);


        String command = "&Command=RingGamesList&Fields=Name,Status,Seats&Log=no";
        api.sendPost(command);
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

                    ps.setString(1, tableN[i]);
                    ps.setString(2, statusN[i] + "/" + seatN[i]);



                    if(tableN[i].indexOf("1K/2K") != -1) {
                        ps.setString(3, "rgba(175, 115, 5, .8)");
                    }else if(tableN[i].indexOf("2K/4K") != -1){
                        ps.setString(3,"rgba(175, 115, 5, 1)" );
                    }else if (tableN[i].indexOf("3K/6K") != -1 ){
                        ps.setString(3,"rgba(255, 255, 255, .4)");
                    }else if (tableN[i].indexOf("5K/10K") != -1) {
                        ps.setString(3,"rgba(255, 50, 48, 0.5)");
                    }else if( tableN[i].indexOf("10K/20K") != -1){
                        ps.setString(3,"rgba(255, 50, 48, 0.4)");
                    }else if( tableN[i].indexOf("15K/30K") != -1 ) {
                        ps.setString(3,"rgba(5, 165, 223, 1)");
                    }else if (tableN[i].indexOf("25K/50K") != -1 ) {
                        ps.setString(3,"rgba(5, 165, 223, .8)");
                    }else if( tableN[i].indexOf("40K/80K") != -1 || tableN[i].indexOf("50K/100K") != -1 ) {
                        ps.setString(3,"rgba(5, 165, 223, .6)");
                    }else if( tableN[i].indexOf("100K/200K") != -1 ){
                        ps.setString(3,"rgba(5, 165, 223, .6)");
                    }else{
                        ps.setString(3,"rgba(0,0,0,.5)");
                    }
                    ps.executeUpdate();

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


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error in insertActiveTable() -->" + ex.getMessage());

        } finally {
            db.closeConnection();
        }
    }

    public ArrayList<ActiveTable> getActiveTables() {
        return activeTables;
    }

    private ArrayList<ActiveTable> activeTables;
}
