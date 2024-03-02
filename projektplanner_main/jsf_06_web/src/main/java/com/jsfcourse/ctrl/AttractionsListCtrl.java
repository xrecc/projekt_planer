package com.jsfcourse.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;

import com.jsf.dao.AttractionDAO;
import com.jsf.entities.Attraction;

@Named
@RequestScoped
public class AttractionsListCtrl {
	//private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	//private static final String PAGE_STAY_AT_THE_SAME = null;

	private String name;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	AttractionDAO attractionDAO;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Attraction> getFullList(){
		return attractionDAO.getFullList();
	}

	public List<Attraction> getList(){
		List<Attraction> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (name != null && name.length() > 0){
			searchParams.put("name", name);
		}
		
		//2. Get list
		list = attractionDAO.getList(searchParams);
		
		return list;
	}

}
