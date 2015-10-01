package com.saman.jsf.com.saman.jsf.bankroll;

import com.saman.jsf.LoginBean;
import com.saman.jsf.UserDAO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by Saman on 2015-08-05.
 */
@ManagedBean
@ViewScoped
public class DepositReport implements Serializable{



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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public void getTotalDbDeposit(){
    //    System.out.println(date);
        dao = new UserDAO();
        dao.getTotalDepositFromDb(date);
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

    public int getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(int totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    /*
            public static void main(String[] args)  {

               parseCSV();

            }*/
private int totalDeposit = 0 ;
    public  void parseCSV(InputStream file)  {
     setTotalDeposit(0);
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

                deposit = Double.parseDouble(rowData[3]);
                if ((int)deposit!=0) {
                    int amnt = ((int)deposit/10000);
                   // String amount = amnt+"";
               totalDeposit += amnt;
              //      dao.addDepositDB(loginBean.getUserN(),amount,cardNumber,rowData[1],rowData[2]);
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
