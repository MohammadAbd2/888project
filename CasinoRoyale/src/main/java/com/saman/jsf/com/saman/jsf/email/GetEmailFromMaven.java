package com.saman.jsf.com.saman.jsf.email;

import com.saman.jsf.ApiIncome;
import com.saman.jsf.Database;
import com.saman.jsf.com.saman.jsf.moneymaking.AddUser;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;

import javax.faces.context.FacesContext;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-09-07.
 */
public class GetEmailFromMaven {

    public  static void main(String args[]) throws JSONException, IOException {

      String cmdGetPlayer = "&Command=AccountsList&Fields=Player";
      String cmdAccountGet = "&Command=AccountsGet&Player=";
      String email = "";
      //   System.out.println(url);
          List<String> results = new ArrayList<String>();
        ApiIncome api = new ApiIncome();
          api.sendPost(cmdGetPlayer);

          if (api.jsobj.getString("Result").equals("Ok")) {

              JSONArray array = api.jsobj.getJSONArray("Player");
              for (int i = 0; i < array.length(); i++) {
                  results.add(array.getString(i));
                  //   System.out.println(array.getString(i));
              }
              for (String player : results) {
                  api.sendPost(cmdAccountGet + player);
                  //    System.out.println(url+cmdAccountGet+player);
                   email += api.jsobj.getString("Email")+";";

              }
              File edit = new File("D:/EmailList/888MailList.txt");

              FileWriter fw = new FileWriter(edit.getAbsoluteFile());
              BufferedWriter bw = new BufferedWriter(fw);
              bw.write(email);
              bw.close();
          }
      }

}
