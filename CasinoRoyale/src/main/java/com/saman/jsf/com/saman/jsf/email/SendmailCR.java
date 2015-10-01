package com.saman.jsf.com.saman.jsf.email;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.faces.context.FacesContext;

/**
 * Created by Saman on 3/12/2015.
 */
 public  class SendmailCR {



    public  boolean isMailResult() {
        return mailResult;
    }

    public void setMailResult(boolean mailResult) {
        this.mailResult = mailResult;
    }

    public  boolean mailResult ;
    public  boolean sendEmail(String toEmail, String subject, String strMsg) throws EmailException {
        mailResult = false;
        String strIP ="http://www.crgame2013.com";
        String  strApplicationPath = strIP + "/WindowsApp";
        String  strApplicationMacPath = strIP + "/downloads/?f=macapp";
        String strApplicationIosPath = "is ready and published very soon...";
        String  strApplicationWinMobPath = "is ready and published very soon...";
        String  strApplicationAndroidPath = strIP + "/downloads/?f=androidapp";


        String strMsgSend = "<html><div style="+"\""+"background: #000; width: 600px; padding: 30px;"+"\""+"><img src="+"\""+ strIP + "/images/logoCr.png"+"\""+" style="+"\""+"float: left; margin-right: 10px;"+"\""+"><h1 style="+"\""+"color: #fff; font-family: verdana; font-size: 28px; font-weight: normal;"+"\""+">Casino Royale</h1><div style="+"\""+"background: #111; padding: 20px;"+"\""+">"+
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

        FacesContext ctx = FacesContext.getCurrentInstance();
        String authuser = ctx.getExternalContext().getInitParameter("gmailUser");
        String authpwd = ctx.getExternalContext().getInitParameter("gmailPass");


        try {

            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
            email.setDebug(true);
            //    System.out.println("debug true shod");
            email.addTo(toEmail, "Casino Royale");
            email.setFrom(authuser, "Casino Royale");
            email.setSubject(subject);
            //  email.updateContentType("");
            email.setContent(strMsgSend, "text/html");
            email.setSSLOnConnect(true);
            // EmailAttachment attachment = new EmailAttachment();
      /*  email.attach(new ByteArrayDataSource(pdfBytes, "application/pdf"),
                "document.pdf", "Document description",
                EmailAttachment.ATTACHMENT);*/
            email.send();
            mailResult = true;
        }catch (EmailException ex) {
         mailResult = false;
        }
        return mailResult;

    }


}
