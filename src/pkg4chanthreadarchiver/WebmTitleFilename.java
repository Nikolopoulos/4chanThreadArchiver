/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4chanthreadarchiver;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class WebmTitleFilename {
    private String title;
    private String link;

    public WebmTitleFilename(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
}
