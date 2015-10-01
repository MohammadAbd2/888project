package com.saman.jsf.com.saman.jsf.info;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 * Created by Saman on 3/7/2015.
 */
@ManagedBean
@RequestScoped()
public class UserInfo {

    private String username;
    private  String Logins;
    private String TotalChip;
    private  String FirstLogin;
    private  String LastLogin;
    private String RealName;
    private String ChipOnTable;
    private String Email;

    public UserInfo(String username,String realName,String email,String totalChip,String chipOnTable,String firstLogin,String lastLogin,String logins){
this.username = username;
        this.RealName = realName;
        this.Email = email;
        this.TotalChip = totalChip;
        this.ChipOnTable = chipOnTable;
        this.FirstLogin = firstLogin;
        this.LastLogin = lastLogin;
        this.Logins = logins;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogins() {
        return Logins;
    }

    public void setLogins(String logins) {
        Logins = logins;
    }

    public String getTotalChip() {
        return TotalChip;
    }

    public void setTotalChip(String totalChip) {
        TotalChip = totalChip;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getChipOnTable() {
        return ChipOnTable;
    }

    public void setChipOnTable(String chipOnTable) {
        ChipOnTable = chipOnTable;
    }

    public String getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
    }

    public String getFirstLogin() {
        return FirstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        FirstLogin = firstLogin;
    }



}
