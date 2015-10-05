package com.saman.jsf.com.saman.jsf.email;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.faces.context.FacesContext;

/**
 * Created by Saman on 3/12/2015.
 */
 public  class  Sendmail {



    public  boolean isMailResult() {
        return mailResult;
    }

    public void setMailResult(boolean mailResult) {
        this.mailResult = mailResult;
    }

    public  boolean mailResult ;
    public  boolean sendEmail(String toEmail, String subject, String strMsg) throws EmailException {
        mailResult = false;
        FacesContext ctx = FacesContext.getCurrentInstance();
        String mailSmtpHost = "888casino.co.com";
        String mailFrom = "888Casino@888casino.co.com";
        String strIP = "http://www.888pkr.club";
        String  strApplicationPath = strIP + "/downloads/888Casino-Setup.exe";
        String  strApplicationMacPath = strIP + "/downloads/888Casino.app.zip";
      /*  String strIP = ctx.getExternalContext().getInitParameter("domain");
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


       /* String authuser = ctx.getExternalContext().getInitParameter("gmailUser");
        String authpwd = ctx.getExternalContext().getInitParameter("gmailPass");*/
        String authuser = "888pkr.mail@gmail.com";
        String authpwd = "42101365aS";

        try {

            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
            email.setDebug(true);
            //    System.out.println("debug true shod");
            email.addTo(toEmail, "888Casino");
            email.setFrom(authuser, "888Casino");
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
