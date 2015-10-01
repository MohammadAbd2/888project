package com.saman.jsf.com.saman.jsf.report;

import com.saman.jsf.ApiIncome;
import com.saman.jsf.DatabaseIncome;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by Saman on 2015-06-28.
 */
public class UpdateIncomeWaitingRecord extends TimerTask {
    ApiIncome apiIncome = new ApiIncome();

    public void run() {
        try {
            waitingIncome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("UpdateWaitingIncomeRecord.");
        //TODO generate report
    }

    public void setRake(int rake) {
        this.rake = rake;
    }

    public int getRake() {
        return rake;
    }

    private int rake;
    public int getUserRake() {
        return userRake;
    }

    public void setUserRake(int userRake) {
        this.userRake = userRake;
    }

    public int getCasinoRake() {
        return casinoRake;
    }

    public void setCasinoRake(int casinoRake) {
        this.casinoRake = casinoRake;
    }

    private int casinoRake;
    private int userRake;
    public int getEntryfee() {
        return entryfee;
    }

    public void setEntryfee(int entryfee) {
        this.entryfee = entryfee;
    }

    private int entryfee;

    int sumNetBonus = 0;
    public void getNetBonusTourney() throws JSONException {
        Calendar c = Calendar.getInstance();
        String selectdate = "" ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date (c.getTimeInMillis());
        selectdate = sdf.format(date);
        //     System.out.println(selectdate);

        String getTourneyList = "&Command=TournamentsList&Fields=Name";
        apiIncome.sendPost(getTourneyList);
        JSONArray tourneyName = apiIncome.jsobj.getJSONArray("Name");
        for(int i=0;i<tourneyName.length();i++) {
            //   System.out.println(tourneyName.getString(i));
            String getTourneyResults = "&Command=TournamentsResults&Date=" + selectdate + "&Name=" + tourneyName.getString(i);
            apiIncome.sendPost(getTourneyResults);
            if (apiIncome.jsobj.getString("Result").equals("Ok")) {
                String getData = apiIncome.jsobj.getString("Data");
                //  System.out.println(getData);
                String[] parseData = getData.split(",");
                for(int j = 0;j < parseData.length;j++){
                    if(parseData[j].indexOf("NetBonus") != -1){
                        String netBonus1 =  parseData[j].replaceAll("\"NetBonus=", "");
                        String netBonus = netBonus1.replace("\"","");
                        sumNetBonus += Integer.parseInt(netBonus)/1000;

                    }
                }
            }
        }
        //   System.out.println(sumNetBonus);
    }

    public void resetRake_Entryfee() throws JSONException {
        String setentryfee = "&Command=SystemEntryFeeSet&Amount=0";
        String setrake = "&Command=SystemRakeSet&Amount=0";
        apiIncome.sendPost(setentryfee);
        apiIncome.sendPost(setrake);
    }

    public void getRakes() throws JSONException {

        String getrake = "&Command=SystemRakeGet";
        apiIncome.sendPost(getrake);
        setRake(Integer.parseInt(apiIncome.jsobj.getString("Balance"))/(1000));
        setUserRake((getRake()*30)/100);
        setCasinoRake(getRake()-getUserRake());
      /*  System.out.println(getRake());
        System.out.println(getUserRake());
        System.out.println(getCasinoRake());*/
    }
    public void entryFee() throws JSONException {

        String getentryfee = "&Command=SystemEntryFeeGet";
        apiIncome.sendPost(getentryfee);
        setEntryfee(Integer.parseInt(apiIncome.jsobj.getString("Balance")) / (1000));
        //  System.out.println(getRake());
    }

    public  void waitingIncome()  {
        sumNetBonus = 0;
        DatabaseIncome db = null;
        try {
            db = new DatabaseIncome();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error db");
        }
        int payouts=0;
        int totalSlot=0;
        int costs = 0;
        int userRakeStack = 0;
        try {

            String sqlUserRakeStack = "SELECT * FROM userrakestack ";
            Connection con2 = db.initConnection();
            PreparedStatement ps2 = con2.prepareStatement(sqlUserRakeStack);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()){
                int urs = (rs.getInt("userRakeStack"));
                userRakeStack = urs ;
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error dbGetUserRakeStack");

        }

        try {

            String sqlpayout = "SELECT lastRakeTrue FROM playerInfo where DATE_FORMAT(lastpayoutTrue,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d')";
            Connection con2 = db.initConnection();
            PreparedStatement ps2 = con2.prepareStatement(sqlpayout);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()){
                int p = Integer.parseInt(rs.getString("lastRakeTrue"));
                payouts += p;
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error dbPayout");

        }
        try {

            String sqlCosts = "SELECT costs FROM income where DATE_FORMAT(date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d') ";
            Connection con2 = db.initConnection();
            PreparedStatement ps2 = con2.prepareStatement(sqlCosts);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()){
                int cost = (rs.getInt("costs"));
                costs = cost ;
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error dbCosts");

        }
        try {

            String sqlSlotMachine = "SELECT amountTotal FROM slotmachine where DATE_FORMAT(date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d')";
            Connection con2 = db.initConnection();
            PreparedStatement ps2 = con2.prepareStatement(sqlSlotMachine);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()){
                int p2 = (rs.getInt("amountTotal"));
                totalSlot += p2/1000;
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error dbSlot");

        }

        try{
            getNetBonusTourney();
            getRakes();
            if(apiIncome.jsobj.getString("Result").equals("Ok")) {
           //     userRakeStack += getUserRake();
           //     userRakeStack = userRakeStack - payouts;
                entryFee();
                if (apiIncome.jsobj.getString("Result").equals("Ok")) {
                    String sql = "update income set   rake = ?,casinoRake = ?,userRake = ?, entryfee = ?,slot = ?,tourney = ?,payouts = ?, total = ?  where date = DATE_FORMAT(NOW(),'%y-%m-%d')";
                    Connection con = db.initConnection();
                    PreparedStatement ps = con.prepareStatement(sql);


                    ps.setInt(1, getRake());
                    ps.setInt(2, getCasinoRake());
                    ps.setInt(3, getUserRake());
                    ps.setInt(4, getEntryfee());
                    ps.setInt(5, (totalSlot*-1));
                    ps.setInt(6, (sumNetBonus*-1));
                    ps.setInt(7, (payouts*-1));
                    ps.setInt(8, (getCasinoRake()+getEntryfee())+(sumNetBonus*-1)+(totalSlot*-1)+costs);
                    ps.executeUpdate();

                }
            }

        } catch (Exception ex) {
            System.out.println("Error in WaitingIncome() -->" + ex.getMessage());

        }
      /*  try {

            String sqlUserRakeStack = "UPDATE userrakestack set userRakeStack = ? ";
            Connection con2 = db.initConnection();
            PreparedStatement ps2 = con2.prepareStatement(sqlUserRakeStack);
         ps2.setInt(1,userRakeStack);
            ps2.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error dbSetUserRakeStack");

        }*/
        finally {
            db.closeConnection();
        }
    }

}
