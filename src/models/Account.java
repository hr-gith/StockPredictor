package models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hamideh on 24/02/2017.
 */
public class Account {
    public String Fname;
    public String Lname;
    public String email;
    public String password;
    public ArrayList<String> watchList= new ArrayList<String>();
    //public HashMap<String,String> watchList = new HashMap<>();

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getWatchList(){return watchList;}

    public void setWatchList(ArrayList<String> watchList){this.watchList = watchList;}
}