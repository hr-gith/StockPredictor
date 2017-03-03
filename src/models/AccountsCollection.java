package models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
/**
 * Created by hamideh on 02/03/2017.
 */
public class AccountsCollection {
    private static final Type ACCOUNT_TYPE = new TypeToken<List<Account>>() {
    }.getType();

    private static final String filename = "Accounts.json";

    public static ArrayList<Account> accounts = new ArrayList<Account>();

    public void LoadAccounts() {

        Gson gson = new Gson();
        try {
            File f = new File(filename);
            if (f.exists()) {
                JsonReader reader = new JsonReader(new FileReader(filename));
                accounts = gson.fromJson(reader, ACCOUNT_TYPE); // contains the
                // whole account
                // list
            }

        } catch (Exception e) {
            System.out.println("File not found" + e.getStackTrace());
        }
    }

    public static void Add(String f,String l,String e, String p){
        Account a1 = new Account();
        a1.Fname = f;
        a1.Lname = l;
        a1.email = e;
        a1.password = p;

        accounts.add(a1);
    }

    public static void WriteToJson(){
        try{
            String collaboratedAccounts = new Gson().toJson(accounts);
            File myFoo = new File(filename);
            FileWriter fooWriter = new FileWriter(myFoo, false); // true to append
            // false to overwrite.
            fooWriter.write(collaboratedAccounts);
            fooWriter.close();
        }catch (Exception e) {
            System.out.println("File not found" + e.getStackTrace());
        }
    }

    public boolean SearchAccounts(String em,String pwd){
        // Search the ArrayList
        for(Account account : accounts){
            if(em.toLowerCase().equals(account.email.toLowerCase()) && pwd.equals(account.password)){
                return true;
            }
        }
        return false;
    }
}
