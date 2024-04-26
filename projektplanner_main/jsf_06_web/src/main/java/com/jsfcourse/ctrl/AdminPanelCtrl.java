package com.jsfcourse.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class AdminPanelCtrl {
     
    private List<String> images;
    private List<String> captions;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images.add("admin" + i + ".jpg");
        }
        captions = new ArrayList<String>();
        captions.add("Wyświetl listę atrakcji");
        captions.add("Wyświetl listę uzytkowników");
        captions.add("Wyświetl listę wycieczek");
    }
 
    public List<String> getImages() {
        return images;
    }
    public List<String> getCaptions() {
        return captions;
    }
    public String navigateToAdminSettingsView(int index) {
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
        	if (index == 0) {
            externalContext.redirect("/planer/pages/trippages/attractionslistview.xhtml");
        	} else if (index == 1) {
        		externalContext.redirect("/planer/pages/admin/userslistview.xhtml"); 
        	} else if (index == 2) {
        		externalContext.redirect("/planer/pages/trippages/triplistview.xhtml");
        	}
        	
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return null; 
    }
}