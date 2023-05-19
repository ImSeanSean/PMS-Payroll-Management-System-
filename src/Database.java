/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sean
 */
public class Database {
    //Instance Fields
    private String DBUrl;
    private String DBUser;
    private String DBPassword;
    //Constructor
    public Database(String url, String user, String password){
        this.DBUrl = url;
        this.DBUser = user;
        this.DBPassword = password;
    }
    //Getter and Setter
    public String getURL(){
        return DBUrl;
    }
    public void setURL(String url){
        DBUrl = url;
    }
    public String getUser(){
        return DBUser;
    }
    public void setUSer(String user){
        DBUser = user;
    }
    public String getPass(){
        return DBPassword;
    }
    public void setPass(String password){
        DBPassword = password;
    }
}
