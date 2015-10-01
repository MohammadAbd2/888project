package com.saman.jsf.com.saman.jsf.cashier;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.faces.bean.ManagedProperty;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Saman on 2015-06-10.
 */
public class ParsingHtmlFile {

    public  void parseHtml()  {
        UserDAO dao  = new UserDAO();
        String htmlFile = "E:/Java/Saman Java/JSF/PokerMaven/billDepositList.html";
        String line = "";
        String cvsSplitBy = ",";
        Scanner scanner =null;
        Document doc = null;
        Elements body = null;
        try {

            String card = "";
            double deposit;
            String  cardNumber ="";
            File file = new File(htmlFile);
            try {
                 doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
                 body = doc.select("body");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (body.hasText()) {
                line = body.text();
                //     System.out.println(line);
                // use comma as separator
                String[] rowData = line.split(cvsSplitBy);
                String[] findCard = rowData[0].split(" ");
                for(int i = 0; i<findCard.length;i++){
                    if(findCard[i].length()==16){
                        cardNumber = findCard[i];
                    }
                }
                double cardN = Double.parseDouble(cardNumber);
                //    System.out.println(cardN);
                double  fourDigit = cardN % 10000;
                int fd = (int)fourDigit;
                String compileDigit = fd+"";
                if(compileDigit.length()==1){
                    card = "000"+compileDigit;
                }
                else if(compileDigit.length()==2){
                    card = "00"+compileDigit;
                }
                else if(compileDigit.length()==3){
                    card = "0"+compileDigit;
                }else {
                    card = compileDigit;
                }
                deposit = Double.parseDouble(rowData[3]);
                if ((int)deposit!=0) {
                    int amnt = ((int)deposit/10000);
                    String amount = amnt+"";
                    System.out.println("Deposit =" + amount + " , Time=" + rowData[2] + " , Date=" + rowData[1] + " , Card=" + card);

               //     dao.addDepositDB(loginBean.getUserN(),amount,card,rowData[1],rowData[2]);
                }

            }
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
        finally {
            if (scanner != null) {

                scanner.close();

            }
        }

        //     System.out.println("Done");
    }
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
}
