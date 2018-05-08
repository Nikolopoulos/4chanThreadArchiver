/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4chanthreadarchiver;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class PageStripper {

    public static ArrayList<WebmTitleFilename> getLinks(String input) {

        String patternTest = "(title=\"([^\"]*)\")? href=\"(//([a-zA-Z\\.0-9/-])*\\.(webm|gif|jpg|jpeg|png))\"";
        String pattern = "(//([a-zA-Z\\.0-9/-])*\\.(webm|gif|jpg|jpeg|png))";

        // Create a Pattern object
        Pattern r = Pattern.compile(patternTest);

        // Now create matcher object.
        Matcher m = r.matcher(input);
      
        ArrayList <WebmTitleFilename> links= new ArrayList<WebmTitleFilename>();

        while (m.find()) {
            for(int i=0;i< m.groupCount();i++){
                System.out.println("Group "+i+": "+m.group(i));
            }
            links.add( new WebmTitleFilename(m.group(2)==null?m.group(3):m.group(2),"http:"+m.group(3)));                  
        }
        return links;
    }
}
