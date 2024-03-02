package com.jsfcourse.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.event.UnselectEvent;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.jsf.dao.AttractionDAO;
import com.jsf.entities.Attraction;

@Named
@RequestScoped
public class TripCreationViewCtrl{

    private String selectedOption;
    private String rtl;
    private String hideNoSelectOption;

    private String option;
    private List<String> options;

    private String longItemLabel;
    private String labeled;

    private String country;
    private List<Attraction> countryList;

	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	AttractionDAO attractionDAO;
	
	public String getCountry() {
		return country;
	}

	public void setName(String country) {
		this.country = country;
	}

	public List<Attraction> getFullList(){
		return attractionDAO.getFullList();
	}

	public List<Attraction> getList(){
		List<Attraction> list = null;
		
		//2. Get list
		list = attractionDAO.getCountryList();
		
		return list;
	}
	public List<Attraction> getListc(){
		List<Attraction> listc = null;
		

		
		//2. Get list
		listc = attractionDAO.getCityList();
		
		return listc;
	}


    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getRtl() {
        return rtl;
    }

    public String getHideNoSelectOption() {
        return hideNoSelectOption;
    }

    public void setHideNoSelectOption(String hideNoSelectOption) {
        this.hideNoSelectOption = hideNoSelectOption;
    }

    public void setRtl(String rtl) {
        this.rtl = rtl;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getLongItemLabel() {
        return longItemLabel;
    }

    public void setLongItemLabel(String longItemLabel) {
        this.longItemLabel = longItemLabel;
    }

    public String getLabeled() {
        return labeled;
    }

    public void setLabeled(String labeled) {
        this.labeled = labeled;
    }


}