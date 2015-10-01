package com.saman.jsf.com.saman.jsf.cashier;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by Saman on 2015-06-09.
 */
@ManagedBean
@SessionScoped
public class ParsingCsvFile implements Serializable{
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    private UploadedFile uploadedFile;
private UserDAO dao;
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        try {
            uploadedFile = event.getFile();
            if (uploadedFile != null) {
                try {
                    parseCSV(uploadedFile.getInputstream());
                }catch (IOException e){
                    e.getMessage();
                    System.out.println("parseCSV Failed");
                }
                dao = new UserDAO();
                dao.sendDeposit(loginBean.getUserN());

            }
        }catch (Exception e){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Failed!", event.getFile().getFileName() + " don't uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
 /*
        public static void main(String[] args)  {

           parseCSV();

        }*/

        public  void parseCSV(InputStream file)  {
            UserDAO   dao  = new UserDAO();
         //   String csvFile = "E:/Java/Saman Java/JSF/PokerMaven/billDepositList(1).csv";
            String line = "";
            String cvsSplitBy = ",";
            Scanner scanner =null;
            try {

String card = "";
                double deposit;
                String  cardNumber ="";
                String  cardNumber2 ="";
             //   File file = new File(csvFile);
                scanner = new Scanner( file,"utf-8");
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
            if (line.isEmpty()||line==null){
                line = scanner.nextLine();
            }
                    String[] rowData = line.split(cvsSplitBy);
               String[] findCard = rowData[0].split(" ");
                    for(int i = 0; i<findCard.length;i++){
                        if(findCard[i].length()==19){
                            cardNumber2 = findCard[i];
                            cardNumber = cardNumber2.substring(0,16);
                      //      System.out.println(cardNumber);
                        }
                        else if(findCard[i].length()==16){
                            cardNumber = findCard[i];

                        }
                    }
               //    double cardN = Double.parseDouble(cardNumber);
             //    double  fourDigit = cardN % 10000;
               //     int fd = (int)cardN;
              //      String compileDigit = fd+"";

                 /*   if(compileDigit.length()==1){
                        card = "000"+compileDigit;
                    }
                    else if(compileDigit.length()==2){
                        card = "00"+compileDigit;
                    }
                    else if(compileDigit.length()==3){
                        card = "0"+compileDigit;
                    }else {*/
                      //  card = compileDigit;
                 //   }
                    deposit = Double.parseDouble(rowData[3]);
                    if ((int)deposit!=0) {
                        int amnt = ((int)deposit/10000);
                        String amount = amnt+"";
                   //     System.out.println("Deposit =" + amount + " , Time=" + rowData[2] + " , Date=" + rowData[1] + " , Card=" + card);

                        dao.addDepositDB(loginBean.getUserN(),amount,cardNumber,rowData[1],rowData[2]);
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

}
