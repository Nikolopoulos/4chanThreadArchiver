/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4chanthreadarchiver;

import Logging.MyLogger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pkg4chanthreadarchiver.http.activeDownloads;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static boolean test = false;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        MyLogger.init();
        boolean test = false;
        //System.setErr(new PrintStream(MyLogger.logfile));
        for (int i = 0; i < 24; i++) {
            test = test || procedure(args[args.length - 1]);
            //test = test || procedure("http://boards.4chan.org/gif/thread/12339936");
            if (!test) {
                break;
            }
            while (http.activeDownloads > 0) {
                //System.out.println("Tryin to upper chill mane");
            }
            System.out.println("Going Into Stasis for an hour");
            Thread.sleep(1000 * 60 * 60);
        }

    }

    public static boolean procedure(String url) {

        try {
            test = false;
            String page = http.get(url);
            ArrayList<WebmTitleFilename> pageStripped = PageStripper.getLinks(page);
            System.out.println("Page Stripped");
            String urlParts[] = url.split("/");
            String title = urlParts[urlParts.length - 1];
            System.out.println("Title is " + urlParts[urlParts.length - 1]);
            fileManager.createFolders(title);
            try {
                for (final WebmTitleFilename s : pageStripped) {
                    if (http.activeDownloads < 11) {

                        Thread t = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                boolean result = false;
                                try {
                                    System.out.println("http active " + http.activeDownloads + s);
                                    result = http.downloadFile(s);
                                } catch (IOException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                test = test || result;
                            }
                        });
                        if (!fileManager.fileExists(s.getTitle())) {
                            t.start();
                            Thread.sleep(1000);
                        }
                    } else {
                        while (http.activeDownloads > 10) {
                            System.out.println("Tryin to chill mane "+http.activeDownloads);
                            Thread.sleep(http.activeDownloads*1000);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (http.activeDownloads > 0) {
           System.out.println("Chilling cause there are "+http.activeDownloads+" downloads active");
        }
        System.out.println("return test");
        return test;
    }

}
