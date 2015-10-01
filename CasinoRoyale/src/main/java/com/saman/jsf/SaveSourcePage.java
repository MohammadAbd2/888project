package com.saman.jsf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Created by Saman on 4/21/2015.
 */
public class SaveSourcePage {

    public static void URLDownloader(String site, int startPage, int endPage) throws Exception {
       String[] pages ;
        String webPage = site;
        int fileNumber = startPage;
        String name = "e:\\saveSite";
        if (startPage == 0)
            fileNumber++;

//change pages
    //    for (int i = 0; i < pages.length; i++) {
      //      webPage = pages[i];
            URL url = new URL(webPage);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            PrintWriter out = new PrintWriter(name + (fileNumber) + ".xml", "utf-8");
            String inputLine;


            //while stuff to read on current page
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine); //write line of text
            }
            out.close();    //end writing text
            if (startPage == 0)
                startPage++;
         //   console.append("Finished page " + startPage + "\n");
            startPage++;
       // }

    }
    public static void main(String[] args) throws Exception {
        URLDownloader("http://iranfile.ir/details-info__2592921__%D8%AA%D9%87%D8%B1%D8%A7%D9%86%D8%B3%D8%B1.html",1,1);

    }
}

