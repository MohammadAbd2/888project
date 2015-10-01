package com.saman.jsf.com.saman.jsf.autocomplete;

import com.saman.jsf.Api;
import com.saman.jsf.UserDAO;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saman on 2/16/2015.
 */
@ManagedBean
@SessionScoped
public class JqueryAutoComplete implements Serializable{
    Api ap;
    UserDAO userdao;
    private static final long serialVersionUID = 1L;


    public String getArrayP() {
        return arrayP;
    }

    public void setArrayP(String arrayP) {
        this.arrayP = arrayP;
    }

    private String arrayP;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String user = "";
    //  String url = "http://188.138.41.216:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
   // String url = "http://localhost:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    // String url = "http://87.247.179.233:4210/myapi?Password=42101365MY@dmin&JSON=Yes";
    FacesContext ctx = FacesContext.getCurrentInstance();
    String server = ctx.getExternalContext().getInitParameter("ipPort");
    String myapi = ctx.getExternalContext().getInitParameter("mavenPassword")+"&JSON=Yes";
    String url = server + myapi;
    public JqueryAutoComplete() {
        try {
            ap = new Api();
             userdao = new UserDAO();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }


    private List<String> results = new ArrayList<String>();
    public List<String> completeItem(String query) throws JSONException {

        List<String> filteredList = new ArrayList<String>();
        List<String> ignorecase = new ArrayList<String>();
        for (String item : results) {
            if (item.toLowerCase().startsWith(query)) {
                filteredList.add(item);

            }
        }
        return filteredList;
    }


    public List<String> autocompletePF() throws JSONException {
        for(int i=0;i<array.length();i++){
            results.add(array.getString(i));
       //     System.out.println(results.get(i));
        }

        return results;
    }



    public void searchjson()  {
        if(!FacesContext.getCurrentInstance().isPostback()) {
            String cmdsrch = "&Command=AccountsList&Fields=Player";

            try {
                ap.sendPost(url, cmdsrch);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (ap.jsobj.getString("Result").equals("Ok")) {

                    array = ap.jsobj.getJSONArray("Player");
                    String arrayy = ap.jsobj.getString("Player");

                    String ar = arrayy.replace("[", "");
                    String ar2 = ar.replace("]", "");
                    String ar3 = ar2.replace("\"", "");
                    arrayP = ar3;
                    autocompletePF();
               //     userdao.login(array2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
       //     System.out.println(arrayP);
        }
      //  return pList;
   }
    public void checkUserList()  {

            String cmdsrch = "&Command=AccountsList&Fields=Player";

            try {
                ap.sendPost(url, cmdsrch);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (ap.jsobj.getString("Result").equals("Ok")) {

                    JSONArray array2 = ap.jsobj.getJSONArray("Player");
                    String arrayy = ap.jsobj.getString("Player");

                    String ar = arrayy.replace("[", "");
                    String ar2 = ar.replace("]", "");
                    String ar3 = ar2.replace("\"", "");
                    arrayP = ar3;

                    //     userdao.login(array2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //     System.out.println(arrayP);
        }

    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }

    //  return pList;
        JSONArray array;
    public void searchP()  {
        String cmdsrch = "&Command=AccountsList&Fields=Player";
        FacesContext context  = FacesContext.getCurrentInstance() ;
        ExternalContext econtext = context.getExternalContext() ;
        String pl = econtext.getInitParameter("playerList");

        try {
            ap.sendPost(url,cmdsrch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(ap.jsobj.getString("Result").equals("Ok")){



                 array = ap.jsobj.getJSONArray("Player");
                PrintWriter writer = new PrintWriter(pl, "UTF-8");
                try {

                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pl, true)));
                    out.println("var data = [");
                    out.close();
                 //   System.out.println(user);
                } catch (IOException e) {
                    //exception handling left as an exercise for the reader
                }
                for(int i = 0 ; i <= array.length() ; i++){
                    user = array.getString(i);
                    if(i== array.length()-1){
                        try {
                            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pl, true)));
                            out.println("\"" +user +"\""+"];");
                            out.close();
                       //     System.out.println(user);
                        } catch (IOException e) {
                            //exception handling left as an exercise for the reader
                        }
                    }else {

                        try {
                            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pl, true)));
                            out.println("\"" +user +"\""+ ",");
                            out.close();
                        //    System.out.println(user);
                        } catch (IOException e) {
                            //exception handling left as an exercise for the reader
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
