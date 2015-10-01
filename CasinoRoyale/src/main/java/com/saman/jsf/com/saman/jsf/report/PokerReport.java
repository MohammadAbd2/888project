package com.saman.jsf.com.saman.jsf.report;

import com.saman.jsf.LoginBean;
import com.saman.jsf.com.saman.jsf.info.AccountInformationBean;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Saman on 2015-07-04.
 */
@ManagedBean
@ViewScoped
public class PokerReport implements Serializable {

    public void initPokerReport() throws FileNotFoundException {
        LogsEvents logsEvents = new LogsEvents();
      pokerReports = logsEvents.getEvent(accountInformationBean.getUsername(),getStartCalaedar(),getEndCalendar());
    }

    public void initPokerReportUser() throws FileNotFoundException {
        LogsEvents logsEvents = new LogsEvents();
        pokerReports = logsEvents.getEvent(loginBean.getUserN(),getStartCalaedar(),getEndCalendar());
    }

    public java.util.Date getToday() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public java.util.Date getSevenDaysAgo() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -6);
        return c.getTime();
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String dateTime;

    private String pokerDescription;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;
    public ArrayList<PokerReport> getPokerReports() {
        return pokerReports;
    }

    public void setPokerReports(ArrayList<PokerReport> pokerReports) {
        this.pokerReports = pokerReports;
    }

    public String getPokerDescription() {
        return pokerDescription;
    }

    public void setPokerDescription(String pokerDescription) {
        this.pokerDescription = pokerDescription;
    }

    private ArrayList<PokerReport> pokerReports;


    public Date getStartCalaedar() {
        return startCalaedar;
    }

    public void setStartCalaedar(Date startCalaedar) {
        this.startCalaedar = startCalaedar;
    }

    public Date getEndCalendar() {
        return endCalendar;
    }

    public void setEndCalendar(Date endCalendar) {
        this.endCalendar = endCalendar;
    }

    private java.util.Date startCalaedar ;
    private java.util.Date endCalendar ;


    public AccountInformationBean getAccountInformationBean() {
        return accountInformationBean;
    }

    public void setAccountInformationBean(AccountInformationBean accountInformationBean) {
        this.accountInformationBean = accountInformationBean;
    }

    @ManagedProperty(value="#{accountInformationBean}")
    private AccountInformationBean accountInformationBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

    /**
     * Created by Saman on 2015-07-19.
     */
    static class LogsEvents {

        FacesContext ctx = FacesContext.getCurrentInstance();
        String folderadd = ctx.getExternalContext().getInitParameter("eventLogs");

        Date startdt = null;
        String selectdate = "" ;


        public  void parse(String d) {
            if (d != null) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    try {

                        startdt = sdf.parse(d);

                    //    sdf.applyPattern("yyyy-MM-dd");
                        selectdate = "EventLog"+sdf.format(startdt);

                    }catch(Exception ex) {
                        System.out.println("Error on parse EventLog.");
                    }


            }
        }

        public ArrayList<PokerReport> getEvent(String username,Date startdate,Date enddate) throws FileNotFoundException {
            try {
                List<DateTime> dates = null;
                boolean foundPr = false;
                ArrayList<PokerReport> pokerReports = null;
                pokerReports = new ArrayList<PokerReport>();

                int counter = 1;
                dates = new ArrayList<DateTime>();
                DateTime st = new DateTime(startdate);
                DateTime en = new DateTime(enddate);
                int days = Days.daysBetween(st, en).getDays();
            //    System.out.println(days);
                for (int i = 0; i <= days; i++) {
                    DateTime d = st.withFieldAdded(DurationFieldType.days(), i);
                    dates.add(d);
                    //   System.out.println(d);
                    parse(d.toString());


                    File dir = new File(folderadd);
                    File[] foundFiles = dir.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.startsWith(selectdate) && name.endsWith(".txt");

                        }

                    });
               //     System.out.println(selectdate);
                    for (File file : foundFiles) {
                   //     System.out.println(file);

                        Scanner scanner = new Scanner(file);
                        String line = "";
                        while (scanner.hasNextLine()) {
                            line = scanner.nextLine();
                            //   System.out.println(line);

                            //     String[] dataEvent = line.split("\"" + "," + "\"");
                            //   for (String playerEvent : dataEvent) {
                            if (line.indexOf(username) != -1) {
                                PokerReport data = new PokerReport();
                                //    System.out.println(playerEvent);
                                String[] rs = line.split("\\|");
                                //    System.out.println(rs[0]);
                                data.setDateTime(rs[0]);
                                //    System.out.println(rs[0] + "  " +rs[1]+" " +rs[2]);
                                data.setPokerDescription(rs[1] + " " + rs[2]);
                                data.setNumber(counter);
                                counter++;
                                pokerReports.add(data);
                                foundPr = true;
                            }
                        }

                    }
                }

                            if (foundPr) {

                                return pokerReports;
                            } else {
                                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "Warning",
                                                "Server can't get report!"));
                                RequestContext.getCurrentInstance().update("FormMenu:messages");
                                return null; // no entires found
                            }


                            } catch (Exception ex) {
                                System.out.println("Error in getLogsEvents() -->" + ex.getMessage());

                            }
                            return (null);
                        }

            }
}
