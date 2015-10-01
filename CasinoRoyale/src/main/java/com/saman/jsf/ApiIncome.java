package com.saman.jsf;

import org.primefaces.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Saman on 2015-06-29.
 */
public class ApiIncome implements Serializable {
    private static final long serialVersionUID = 9204275723046653468L;

    public JSONObject sendPost( String param)  {

        try {
            URL obj = new URL("http://188.138.57.28:4210/myapi?Password=888Casino_Saman_?!&JSON=Yes");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //  String urlParameters = "&Command=AccountsSessionKey&Player=ace7";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(param);
            wr.flush();
            wr.close();

            //    responseCode = con.getResponseCode();
        /*    String resp = con.getHeaderField(0);

              System.out.println("Response : " + resp);
            if(resp != "HTTP/1.1 200 OK") {
                getsrv = false;

            }else {
                getsrv = true;
            }*/
            // System.out.println("\nSending 'POST' request to URL : " + url);
            //System.out.println("Post parameters : " + param);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);

            }
            in.close();

            //print result
            res = response.toString();
            jsobj = new JSONObject(res);

            //  System.out.println(jsobj.getString("Result"));
            //  System.out.println(jsobj.getString("Verified"));



        }catch (Exception e){
            System.out.println("Error Api");
        }
        return jsobj;
    }

    public JSONObject getJsobj() {
        return jsobj;
    }

    public void setJsobj(JSONObject jsobj) {
        this.jsobj = jsobj;
    }

    public boolean isGetsrv() {
        return getsrv;
    }

    public void setGetsrv(boolean getsrv) {
        this.getsrv = getsrv;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public JSONObject jsobj = null;
    private boolean getsrv;
    int responseCode ;
    private String res;
    private final String USER_AGENT = "Mozilla/5.0";
}
