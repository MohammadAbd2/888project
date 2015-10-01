package com.saman.jsf.com.saman.jsf.info;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Saman on 2015-06-25.
 */
@ManagedBean
@SessionScoped
public class AvatarName implements Serializable{
AccountInformationBean accountInformationBean;


    public ArrayList<AvatarName> getAvatarList() {
        return avatarList;
    }

    public void setAvatarList(ArrayList<AvatarName> avatarList) {
        this.avatarList = avatarList;
    }

    private ArrayList<AvatarName> avatarList;

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    private String avatarName;
    @PostConstruct
    public void avatarInit(){
      accountInformationBean = new AccountInformationBean();
       avatarList = accountInformationBean.getAname();
    }

}
