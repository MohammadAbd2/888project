package com.saman.jsf.com.saman.jsf.report;

import com.saman.jsf.DatabaseIncome;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.TimerTask;

/**
 * Created by Saman on 2015-06-28.
 */


public class InsertNewIncomeRecord extends TimerTask {

    public void run() {

        try {
           insertIncomeRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("InsertNewIncomeRecord");
        //TODO generate report
    }

    public  void insertIncomeRecord() throws Exception {
        DatabaseIncome db = new DatabaseIncome();

        try {
            String sql = "INSERT IGNORE INTO income (statusAce7,statusHead,statusMana,statusSaten,date) VALUES (?,?,?,?,NOW())";
            Connection con = db.initConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "Waiting");
            ps.setString(2, "Waiting");
            ps.setString(3, "Waiting");
            ps.setString(4, "Waiting");
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error in insertIncomeRecord() -->" + ex.getMessage());

        } finally {
           db.closeConnection();
        }
    }



}
