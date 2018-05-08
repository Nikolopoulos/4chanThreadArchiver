/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4chanthreadarchiver;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class fileManager {

    public static File directory, gifs, webms, images, main;

    /*Java 6 hack*/
    String toPath = "/srv/www/htdocs/4chan/";
    /*Stopped hacking*/
    public static void createFolders(String title) throws IOException {

        try {
            main = directory = new File("files/downloads");
            directory = new File("files/downloads/" + title);
            gifs = new File("files/downloads/" + title + "/gifs");
            webms = new File("files/downloads/" + title + "/webms");
            images = new File("files/downloads/" + title + "/images");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (main.exists() && main.isDirectory()) {
            System.out.println("Directory already exists!");
        } else if (!main.exists()) {
           // Files.createDirectory(main.toPath());
            main.mkdirs();
        }
        System.out.println("gonna create " + directory.getAbsolutePath());
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("Directory already exists!");
        } else if (!directory.exists()) {
           /* Files.createDirectory(directory.toPath());
            Files.createDirectory(gifs.toPath());
            Files.createDirectory(webms.toPath());
            Files.createDirectory(images.toPath());*/
            directory.mkdirs();
            gifs.mkdirs();
            webms.mkdirs();
            images.mkdirs();
            System.out.println("Directory DOESN'T EXIST");
            System.out.println("creating directory " + directory.exists());
            System.out.println("creating gifs " + gifs.exists());
            System.out.println("creating webms " + webms.exists());
            System.out.println("creating images  " + images.exists());

        }
        if (!gifs.exists()) {
            gifs.mkdirs();
            System.out.println("creating gifs " + gifs.exists());
        }
        if (!webms.exists()) {
            webms.mkdirs();
            System.out.println("creating webms " + webms.exists());
        }
        if (!images.exists()) {
            images.mkdirs();
            System.out.println("creating images  " + images.exists());
        }

    }

    public static boolean fileExists(String s) {
        String[] parts = s.split("/");
        String filename = parts[parts.length - 1];
        String extension = "";
        try {
            extension = filename.split("\\.")[1];
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (s.contains("s." + extension)) {
            //System.out.println("link.contains(\"s.\"+extension) "+link);
            return true;
        }
        Path path;
        File f;
        if (extension.equalsIgnoreCase("gif")) {
            //path = Paths.get(fileManager.gifs.getAbsolutePath() + "/" + filename);
            f = new File(fileManager.gifs.getAbsolutePath() + "/" + filename);
            if (f.exists()) {
                //System.out.println("FileExists "+path);
                return true;
            }
        } else if (extension.equalsIgnoreCase("webm")) {
            //path = Paths.get(fileManager.webms.getAbsolutePath() + "/" + filename);
             f = new File(fileManager.webms.getAbsolutePath() + "/" + filename);
            if (f.exists()) {
                //System.out.println("FileExists "+path);
                return true;
            }
        } else {
            //path = Paths.get(fileManager.images.getAbsolutePath() + "/" + filename);
             f = new File(fileManager.images.getAbsolutePath() + "/" + filename);
            if (f.exists()) {
                //System.out.println("FileExists "+path);
                return true;
            }
        }
        /* works in java 1.7 and forwards
        switch (extension) {
            case "gif":
                path = Paths.get(fileManager.gifs.getAbsolutePath() + "/" + filename);
                if (Files.exists(path)) {
                    //System.out.println("FileExists "+path);
                    return true;
                }

                break;
            case "webm":
                path = Paths.get(fileManager.webms.getAbsolutePath() + "/" + filename);
                if (Files.exists(path)) {
                    //System.out.println("FileExists "+path);
                    return true;
                }

                break;
            default:
                path = Paths.get(fileManager.images.getAbsolutePath() + "/" + filename);
                if (Files.exists(path)) {
                    //System.out.println("FileExists "+path);
                    return true;
                }

                break;
        }*/
        return false;

    }
}
