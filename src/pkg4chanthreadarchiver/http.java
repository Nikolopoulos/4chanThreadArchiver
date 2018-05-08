/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4chanthreadarchiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class http {

    public static int activeDownloads = 0;

    public static String get(String link) throws MalformedURLException, IOException {
        URL url = new URL(link);
        URLConnection uc = url.openConnection();
        uc.addRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        uc.connect();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
            ex.getLocalizedMessage();
        }
        String webPage = "";
        String temp = "";
        while ((temp = br.readLine()) != null) {
            webPage += temp + "\n";
        }
        br.close();
        return webPage;
    }

    public static boolean downloadFile(WebmTitleFilename link) throws FileNotFoundException, IOException {
        //System.out.println("Trying to download "+link);
        try {
            if (link.getLink().length() < 4) {
                return false;
            }
            if (!link.getLink().startsWith("http:")) {
                link.setLink("http:" + link.getLink());// = "http:" + link;
            }
            URL website = new URL(link.getLink());
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());

            String filename = "";
            if (link.getLink().equalsIgnoreCase("http:" + link.getTitle())) {
                String[] parts = link.getLink().split("/");
                filename = parts[parts.length - 1];
                System.out.println("Filename is same with link so " + filename);
            } else {
                filename = link.getTitle();
                System.out.println("Filename is not same with link so " + filename);
            }

            String extension = "";
            try {
                extension = filename.split("\\.")[1];
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (link.getLink().contains("s." + extension)) {
                //System.out.println("link.contains(\"s.\"+extension) "+link);
                return false;
            }
            FileOutputStream fos;
            Path path;

            if (extension.equalsIgnoreCase("gif")) {
               // path = Paths.get(fileManager.gifs.getAbsolutePath() + "/" + filename);
                File f = new File(fileManager.gifs.getAbsolutePath() + "/" + filename);
                if (f.exists()) {
                    //System.out.println("FileExists "+path);
                    return false;
                }

                fos = new FileOutputStream(fileManager.gifs.getAbsolutePath() + "/" + filename);
            } else if (extension.equalsIgnoreCase("webm")) {
                //path = Paths.get(fileManager.webms.getAbsolutePath() + "/" + filename);
                File f = new File(fileManager.webms.getAbsolutePath() + "/" + filename);
                if (f.exists()) {
                    //System.out.println("FileExists "+path);
                    return false;
                }
                fos = new FileOutputStream(fileManager.webms.getAbsolutePath() + "/" + filename);
            } else {
                //path = Paths.get(fileManager.images.getAbsolutePath() + "/" + filename);
                File f = new File(fileManager.images.getAbsolutePath() + "/" + filename);
                if (f.exists()) {
                    //System.out.println("FileExists "+path);
                    return false;
                }
                fos = new FileOutputStream(fileManager.images.getAbsolutePath() + "/" + filename);
            }
            /* works in jdk 1.7 and onwards
             switch (extension) {
             case "gif":
             path = Paths.get(fileManager.gifs.getAbsolutePath() + "/" + filename);
             if (Files.exists(path)) {
             //System.out.println("FileExists "+path);
             return false;
             }

             fos = new FileOutputStream(fileManager.gifs.getAbsolutePath() + "/" + filename);
             break;
             case "webm":
             path = Paths.get(fileManager.webms.getAbsolutePath() + "/" + filename);
             if (Files.exists(path)) {
             //System.out.println("FileExists "+path);
             return false;
             }
             fos = new FileOutputStream(fileManager.webms.getAbsolutePath() + "/" + filename);
             break;
             default:
             path = Paths.get(fileManager.images.getAbsolutePath() + "/" + filename);
             if (Files.exists(path)) {
             //System.out.println("FileExists "+path);
             return false;
             }
             fos = new FileOutputStream(fileManager.images.getAbsolutePath() + "/" + filename);
             break;
             }*/
            activeDownloads++;
            //System.out.println("Initiating download "+path);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            activeDownloads--;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
