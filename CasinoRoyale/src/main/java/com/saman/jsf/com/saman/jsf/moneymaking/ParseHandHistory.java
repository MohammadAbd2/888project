package com.saman.jsf.com.saman.jsf.moneymaking;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.format.ISODateTimeFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Saman on 5/21/2015.
 */
@ManagedBean
@ViewScoped

public class ParseHandHistory implements Serializable {


    public int getSumRake() {
        return sumRake;
    }

    public void setSumRake(int sumRake) {
        this.sumRake = sumRake;
    }

    private int sumRake =0 ;
    FacesContext ctx = FacesContext.getCurrentInstance();
    String folderadd = ctx.getExternalContext().getInitParameter("handhistory");
    String payouts = ctx.getExternalContext().getInitParameter("payout");
    double payout = Double.parseDouble(payouts);
     Date startdt = null;
     String selectdate = "" ;
    static String strDate = "2015-05-24T00:01:00.000Z";
    static DateTime startDate = ISODateTimeFormat.dateTime().parseDateTime(strDate);
    static DateTime endDate = new DateTime(System.currentTimeMillis());

  //  public static void main(String args[]) throws Exception {

        //    deleteFiles(8, folderadd);

      //  getDaysBetweenDates(startDate, endDate);


    //}


    public  void listFilesForFolder(String user) {

    File dir = new File(folderadd);
      //  System.out.println(folderadd);
    final File[] foundFiles = dir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.startsWith(selectdate) && name.endsWith(".txt");


        }
    });

    for(File file:foundFiles)
    {
     //  System.out.println(file);
        try {
            Scanner scanner = new Scanner(file);

            //now read the file line by line...
          //  int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
            //    System.out.println("scan "+file);
                if(line.startsWith(user)) {
                 //   System.out.println(line);
                    String[] ln = line.split(" ");
                    if (ln[1].equals("wins") && ln[2].equals("Pot") || ln[1].equals("wins") && ln[2].equals("Side") && ln[3].equals("Pot")||ln[1].equals("wins") && ln[2].equals("Main") && ln[3].equals("Pot")) {
line = scanner.nextLine();
                        if(line.startsWith("Rake")) {
                            //   System.out.println(line);
                            String[] ln2 = line.split(" ");

                            String r1 = ln2[1].replace("(", "");
                            String r2 = r1.replace(")", "");
                            int rake = Integer.parseInt(r2);
                    //        System.out.println(rake);

                            int po =  (int)(rake * payout);
                         //  System.out.println(po);
                            sumRake += po;
                        }
                    }

                }
            }

        } catch(FileNotFoundException e) {
            //handle this
        }

    }

      //  System.out.println(sumRake);
}
    public static void deleteFiles(int daysBack, String dirWay) {
        long purgeTime =0;
        File directory = new File(dirWay);
        if(directory.exists()){

            File[] listFiles = directory.listFiles();
             purgeTime = System.currentTimeMillis() - (daysBack * 24 * 60 * 60 * 1000);
            for(File listFile : listFiles) {
                if(listFile.lastModified() < purgeTime) {
             //       System.out.println(listFile);
                  /*  if(!listFile.delete()) {
                        System.err.println("Unable to delete file: " + listFile);
                    }*/
                }
            }
        } else {

        }
    }



    public  List<DateTime> getDaysBetweenDates(String user,DateTime startdate, DateTime enddate)
    {

        List<DateTime> dates = null;

     dates = new ArrayList<DateTime>();
    //    System.out.println("Start "+startdate+"End "+enddate);
    int days = Days.daysBetween(startdate, enddate).getDays();
     //   System.out.println(days);
    for (int i = 0; i < days; i++) {
        DateTime d = startdate.withFieldAdded(DurationFieldType.days(), i);
        dates.add(d);
   //     System.out.println(d);
    //    System.out.println(startdate);
    //    System.out.println(enddate);
   //     System.out.println(d);
        parse(d.toString());

    listFilesForFolder(user);

}

        return dates;

    }
               private  final String[] formats = {
            "yyyy-MM-dd'T'HH:mm:ss'Z'",   "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss",        "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
            "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
            "MM/dd/yyyy'T'HH:mm:ssZ",     "MM/dd/yyyy'T'HH:mm:ss",
            "yyyy:MM:dd HH:mm:ss"};




public  void parse(String d) {
        if (d != null) {
        //    for (String p : formats) {
         //       SimpleDateFormat sdf = new SimpleDateFormat(p);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {

                         startdt = sdf.parse(d);

           //            sdf.applyPattern("yyyy-MM-dd");
                         selectdate = "HH"+sdf.format(startdt);
               //     System.out.println(selectdate);
                    }catch(Exception ex) { // here forgot the exact exception class Parse exception was used
                        // do something here
                    }
         //   }

        }
    }





}