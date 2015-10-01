package com.saman.jsf;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Saman on 2015-06-28.
 */


    public class DatabaseIncome implements Serializable {

    private static final long serialVersionUID = 9204275723046653468L;
    private String db_server   = "";
    private String db_user     = "";
    private String db_password = "";
    private String db_driver   = "";

    public Connection connection = null;

    public DatabaseIncome() throws Exception {
        init();
    }

    private void init()throws Exception{

        db_server   = "jdbc:Mysql://localhost:3377/888poker";
        db_user     = "root";
        db_password = "42101365@dmin";
        db_driver   = "com.mysql.jdbc.Driver";
        Class.forName(db_driver);
    }

    public  Connection initConnection() throws Exception{
        if( this.connection == null ){
            this.connection = DriverManager.getConnection(db_server, db_user, db_password);
            this.connection.setAutoCommit(true);
        }else if( this.connection.isClosed() ){
            this.connection = null;
            this.connection = DriverManager.getConnection(db_server, db_user, db_password);
            this.connection.setAutoCommit(false);
        }
        return this.connection;
    }

    public void closeConnection(){
        try {
            if( this.connection != null ){
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commitConnection(){
        try {
            if( this.connection != null && !this.connection.isClosed() ){
                this.connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollbackConnection(){
        try {
            if( this.connection != null && !this.connection.isClosed() ){
                this.connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



