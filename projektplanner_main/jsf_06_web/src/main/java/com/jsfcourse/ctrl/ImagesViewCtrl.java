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
public class ImagesViewCtrl {
     
    private List<String> images;
    private List<String> captions;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images.add("sample" + i + ".jpg");
        }
        captions = new ArrayList<String>();
        captions.add("Stwórz wycieczkę");
        captions.add("Przeglądaj swoje wycieczki");
        captions.add("Panel admina");
    }
 
    public List<String> getImages() {
        return images;
    }
    public List<String> getCaptions() {
        return captions;
    }
    public String navigateToTripCreationView(int index) {
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
        	if (index == 0) {
            externalContext.redirect("tripcreationview.xhtml"); 
        	} else if (index == 1) {
        		externalContext.redirect("userslistview.xhtml"); 
        	} else if (index == 2) {
        		externalContext.redirect("adminpanelview.xhtml");
        	}
        	
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return null; 
    }
}