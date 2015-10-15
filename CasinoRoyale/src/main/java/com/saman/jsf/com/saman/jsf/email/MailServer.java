package com.saman.jsf.com.saman.jsf.email;

import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class MailServer {


        public  void sendEmail (String mailTo, String mailSubject, String strMsg) {
            FacesContext ctx = FacesContext.getCurrentInstance();

            String mailSmtpHost = "888casino.co.com";
            String mailFrom = "888Casino@888casino.co.com";
            String strIP = "http://www.888pkr.club";
            String  strApplicationPath = strIP + "/downloads/888Casino-Setup.exe";
            String  strApplicationMacPath = strIP + "/downloads/888Casino.app.zip";
         /*   String mailSmtpHost = ctx.getExternalContext().getInitParameter("mailHost");
            String mailFrom = ctx.getExternalContext().getInitParameter("mailFrom");
            String strIP = ctx.getExternalContext().getInitParameter("domain");
            String  strApplicationPath = strIP + ctx.getExternalContext().getInitParameter("winApp");
            String  strApplicationMacPath = strIP + ctx.getExternalContext().getInitParameter("macApp");*/
            String strApplicationIosPath = "http://888pkr.club";
            String  strApplicationWinMobPath = "http://888pkr.club";
            String  strApplicationAndroidPath = "http://888pkr.club";

            String strMsgSend = "<html><div style="+"\""+"background: #111111); width: 740px; padding: 30px;"+"\""+"><img src="+"\""+ strIP + "/images/logo.png"+"\""+"  alt=\"888\" style="+"\""+"float: left; margin-right: 10px;"+"\""+"><h1 style="+"\""+"color: #fff; font-family: verdana; font-size: 28px; font-weight: normal;"+"\""+">888Casino</h1><div style="+"\""+"background: #05283a; padding: 20px;"+"\""+">"+
                    "<img src="+"\"" + strIP + "/images/ad2.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>"+
                    "<div style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #115793; padding: 15px 20px; margin-top:0"+"\""+">" + strMsg + "</div>"+
                    "<img src="+"\"" + strIP + "/images/ad1.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>"+
                    "<h2 style="+"\""+"color: #fff; font-family: verdana; font-size: 25px; font-weight: normal; padding-top: 15px; margin: 0; height: 60px;"+"\""+">Download Our Applications</h2><br/>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/windows.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Application for Windows:<br/>" + strApplicationPath + "</h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/mac.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Application for MAC:<br/>" + strApplicationMacPath + "</h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/android.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Application on (mobile and tablet):<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationWinMobPath + "</span></h4></div></div></html>";


         /*   String strMsgSend = "<html><div style="+"\""+"background: #111111); width: 600px; padding: 30px;"+"\""+"><img src="+"\""+ strIP + "/images/logo.png"+"\""+"  alt=\"888\" style="+"\""+"float: left; margin-right: 10px;"+"\""+"><h1 style="+"\""+"color: #fff; font-family: verdana; font-size: 28px; font-weight: normal;"+"\""+">888Casino</h1><div style="+"\""+"background: #05283a; padding: 20px;"+"\""+">"+
                    "<div style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #115793; padding: 15px 20px; margin-top:0"+"\""+">" + strMsg + "</div>"+
                    "<h2 style="+"\""+"color: #fff; font-family: verdana; font-size: 25px; font-weight: normal; padding-top: 15px; margin: 0; height: 60px;"+"\""+">Download Our Applications</h2>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/windows.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Application for Windows:<br/>" + strApplicationPath + "</h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/mac.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Application for MAC:<br/>" + strApplicationMacPath + "</h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/android.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Application for Android (mobile and tablet):<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationAndroidPath + "</span></h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP +  "/images/ios.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Application for iOS (mobile and tablet):<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationIosPath + "</span></h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: #123344; padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/windows.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Application for Windows Mobile:<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationWinMobPath + "</span></h4></div></div></html>";
*/
          /*  String strMsgSend = "<html><div style="+"\""+"background: #000; width: 600px; padding: 30px;"+"\""+"><img src="+"\""+ strIP + "/images/logoCr.png"+"\""+" style="+"\""+"float: left; margin-right: 10px;"+"\""+"><h1 style="+"\""+"color: #fff; font-family: verdana; font-size: 28px; font-weight: normal;"+"\""+">Casino Royale</h1><div style="+"\""+"background: #111; padding: 20px;"+"\""+">"+
                    "<div style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.05); padding: 15px 20px; margin-top:0"+"\""+">" + strMsg + "</div>"+
                    "<h2 style="+"\""+"color: #fff; font-family: verdana; font-size: 25px; font-weight: normal; padding-top: 25px; margin: 0; height: 60px;"+"\""+">Casino Royale Games</h2>"+
                    "<div style="+"\""+"height: 200px; margin-top:0"+"\""+"><img src="+"\"" + strIP + "/images/pki.png"+"\""+" style="+"\""+"float: left; width: 30%; margin-right: 20px;"+"\""+"/><img src="+"\"" + strIP + "/images/smi.png"+"\""+" style="+"\""+"float: left; width: 30%; margin-right: 20px;"+"\""+"/><img src="+"\"" + strIP + "/images/bji.png"+"\""+" style="+"\""+"float: left; width: 30%;"+"\""+"/></div>"+
                    "<h2 style="+"\""+"color: #fff; font-family: verdana; font-size: 25px; font-weight: normal; padding-top: 15px; margin: 0; height: 60px;"+"\""+">Download Casino Royale Applications</h2>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.05); padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/windows.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Casino Royale Application for Windows:<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationPath + "</h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.05); padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/mac.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Casino Royale Application for MAC:<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationMacPath + "</span></h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.05); padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/android.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Casino Royale Application for Android (mobile and tablet):<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationAndroidPath + "</span></h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.05); padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP +  "/images/ios.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Casino Royale Application for iOS (mobile and tablet):<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationIosPath + "</span></h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; background: rgba(255,255,255,.05); padding: 15px 20px; margin-bottom: 0; height: 60px;"+"\""+"><img src="+"\"" + strIP + "/images/windows.png"+"\""+" style="+"\""+"float: left; margin-right: 20px;"+"\""+"/>Download Casino Royale Application for Windows Mobile:<br/><span style="+"\""+"color: #9C9277"+"\""+">" + strApplicationWinMobPath + "</span></h4>"+
                    "<h4 style="+"\""+"color: #fff; font-family: verdana; font-size: 13px; font-weight: normal; padding: 15px 20px; margin-bottom: 0; margin-top: 35px; text-align: center;"+"\""+"><a href="+"https://www.facebook.com/pages/Casino-Royale-Fans/334327310084024"+" style="+"\""+"color: #666; text-decoration: none;"+"\""+"><img src=" +"\""+ strIP + "/images/fb.gif"+"\""+" style="+"\""+" width: 30px; margin-bottom: 5px;"+"\""+"/><br/>Follow Casino Royal News on Facebook</a></h4></div></div></html>";

*/
            sendEmail(mailTo, mailFrom, mailSubject, strMsgSend, mailSmtpHost);
        }

    public boolean isMailResult() {
        return mailResult;
    }

    public void setMailResult(boolean mailResult) {
        this.mailResult = mailResult;
    }

    public static boolean mailResult ;
        public static boolean sendEmail(String to, String from, String subject, String text, String smtpHost) {

          //  mailResult = false;


            try {
                Properties properties = new Properties();
                properties.put("mail.smtp.host", smtpHost);
                Session emailSession = Session.getDefaultInstance(properties);

                Message emailMessage = new MimeMessage(emailSession);
                emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //    emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
                emailMessage.setFrom(new InternetAddress(from));
                emailMessage.setSubject(subject);
                emailMessage.setContent(text, "text/html");

                emailSession.setDebug(true);

                Transport.send(emailMessage);
                mailResult = true;
            } catch (AddressException e) {
                e.printStackTrace();
                mailResult = false;
            } catch (MessagingException e) {
                e.printStackTrace();
                mailResult = false;
            }
            return mailResult;
        }
    }


