package com.saman.jsf.com.saman.jsf.email;

import org.apache.commons.mail.EmailException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Administrator on 8/12/2015.
 */
public class ParseEmail {

    public  static void main(String args[]) throws IOException, InterruptedException {
String subject ="5 Tourney - FreeRoll Everyday";
        File dir = new File("D:/EmailList/NewMail888.txt");
       /* String Msg = "<h2 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 18px; font-weight: normal;" + "\"" + ">be 888Casino khosh amadid.<br/>888 ham aknoon be rooye application dar dastresh shomast</h2><br/>" +
                "<h3 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">Link haye download baraye windows va mac va address http://888pkr.club baraye mobile ha faal mibashad.</h3><br/>" +
                "<h3 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.1); padding: 15px 20px; margin-top:0;" + "\"" + ">ma talash kardim ke dar in version amniat,soraat ,support 24 saate ra dar be rooz tarin technology java estefade konim ta dar miyane digar site ha be komake shoma doostan bi hamta bashim.ham aknoon bazi haye poker va slotMachine be rooye site hastand va be zoodi BlackJack va... niz ezafe mishavad.</h3>";
*/
        String Msg = "<h1 style=" + "\"" + "color: Red; font-family: verdana; font-size: 24px; font-weight: normal;" + "\"" + ">888Casino Big Tourney.</h1><br/>" +
                "<h3 style=" + "\"" + "color: orange; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">FreeRoll - 300K - 15:00</h3><br/>" +
                "<h3 style=" + "\"" + "color: brown; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">FreeRoll - 500K - 18:00</h3><br/>" +
                "<h3 style=" + "\"" + "color: orange; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">FreeRoll - OM 1000K - 21:00 Buy in 5k</h3><br/>" +
                "<h3 style=" + "\"" + "color: red; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">FreeRoll - 2000K - 23:00 Buy in 5k</h3><br/>" +
                "<h3 style=" + "\"" + "color: orange; font-family: verdana; font-size: 14px; font-weight: normal;" + "\"" + ">FreeRoll - OM 500K - 02:00</h3><br/>" +
                "<h3 style=" + "\"" + "color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.1); padding: 15px 20px; margin-top:0;" + "\"" + ">" +
                "Tourney ha rebuy darand ettelaate bishtar dar Tab Tournaments Menu Tournament Info Dar dastres mibashad.</h3>";

        int counter =0;
        int sum = 0;
        try {
            Scanner scanner = new Scanner(dir);
            String content = "";
            String[] email = new String[5];
            //now read the file line by line...
            //  int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
            //    content += line.replaceAll(",",";");
 email = line.split(";");

             //   sum += counter;
            }
         /*   for(String result : email){
                counter++;
             //   System.out.println(counter);
            }*/
        //    System.out.println(counter);
            for(String result : email){
                counter++;

               MailServer mail = new MailServer();
            //    Sendmail mail = new Sendmail();

                    mail.sendEmail(result, subject, Msg);

                if (mail.isMailResult()) {

                    System.out.println("Mail sent to "+ result);
                } else {
                    System.out.println("Mail Server Error!");
                }
                System.out.println(counter);
                Thread.sleep(20000);
            }
        /*    File edit = new File("E:/EmailList/NewMail.txt");

            FileWriter fw = new FileWriter(edit.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();*/
        } catch(FileNotFoundException e) {

        }
    }


}
