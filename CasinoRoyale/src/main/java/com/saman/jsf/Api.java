package com.saman.jsf;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Saman on 1/21/2015.
 */
public class Api implements Serializable {
    private static final long serialVersionUID = 9204275723046653468L;
    public static JSONObject jsobj = null;
   public boolean getsrv;
    @ManagedProperty(value="#{navigationBean}")
    private NavigationBean navigationBean;

    int responseCode ;

    String res;
    // static String urlParameter = "&Command=AccountsPassword&PW=6s96S9&Player=ace7";
    // static  String urlParameter2 = "&Command=AccountsSessionKey&Player=ace7";
    public Api() throws IOException, JSONException {

    }
    private final String USER_AGENT = "Mozilla/5.0";

    // public static void main(String[] args) throws Exception {

//      Api http = new Api();

    //  System.out.println("Testing 1 - Send Http GET request");
    // http.sendGet();

    //      System.out.println("\nTesting 2 - Send Http POST request");
    //   http.sendPost(urlParameter);
    //  if ((jsobj.getString("Result").equals("Ok"))&&(jsobj.getString("Verified").equals("Yes"))) {

    //            http.sendPost(urlParameter2);
    //          if ((jsobj.getString("Result").equals("Ok"))) {
    //            String key = jsobj.getString("SessionKey");
    //          System.out.println(key);
    //    } else {
    //      checkpass = false;
    //  }
    // }
    //   }
    FacesContext ctx = FacesContext.getCurrentInstance();
    String server = ctx.getExternalContext().getInitParameter("ipPort");

    public boolean sendGet() throws IOException {

        String url = server ;
       // String url = "http://localhost:4210/" ;
      //  String url = "http://188.138.41.216:4210";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        // responseCode = con.getResponseCode();
        String resp = con.getHeaderField(0);
        //   System.out.println("\nSending 'GET' request to URL : " + url);
        // System.out.println("Response Code : " + responseCode);
        //   System.out.println("Response : " + resp);
        if(resp == null) {
            getsrv = false;

        }else {
            getsrv = true;
            //    BufferedReader in = new BufferedReader(
            //          new InputStreamReader(con.getInputStream()));
            //  String inputLine;
            //   StringBuffer response = new StringBuffer();

//            while ((inputLine = in.readLine()) != null) {
            //              response.append(inputLine);
            //        }
            //      in.close();

        }
        //print result
        //  System.out.println(response.toString());
        return getsrv;
    }


    public JSONObject sendPost( String url,String param)  {

        try {
            URL obj = new URL(url);
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
        //    FacesContext.getCurrentInstance().addMessage(
          //          null,
            //        new FacesMessage(FacesMessage.SEVERITY_WARN,
              //              "Can't Connect To Server!",
                //            "Please Try Again!"));
         //   navigationBean.redirectToLogin();
        }
        return jsobj;
    }

}
