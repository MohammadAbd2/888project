package com.saman.jsf.com.saman.jsf.activetable;

import com.saman.jsf.ApiIncome;
import com.saman.jsf.Database;
import com.saman.jsf.LoginBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Saman on 2015-07-21.
 */
@ManagedBean
@ViewScoped
public class RingGames {
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

public ArrayList<ActiveTable> getActiveTable()  {
    Database db =null;
    try {
    boolean foundTable = false;
    ArrayList<ActiveTable> activeTables = null;
    activeTables = new ArrayList<ActiveTable>();

        db = new Database();
        Connection con = db.initConnection();
        PreparedStatement ps =null;
        String sql = "SELECT * FROM activetable ";
        con = db.initConnection();
        ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            ActiveTable data = new ActiveTable();

            data.setTableName(rs.getString("tableName"));
            data.setPlayerSeat(rs.getString("seatNumber"));
            data.setColorActiveTable(rs.getString("color"));

            activeTables.add(data);
            foundTable = true;

        }

        rs.close();
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
