package com.jsfcourse.ctrl;

import java.io.IOException;
import java.io.Serializable;


import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;

import com.jsf.dao.UserDAO;
import com.jsf.entities.User;

@Named
@ViewScoped
public class UserEditGET implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_USER_LIST = "/pages/admin/userslistview?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User user = new User();
	private User loaded = null;
	
	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && user.getUsersId() != null) {
				loaded = userDAO.find(user.getUsersId());
			}
			if (loaded != null) {
				user = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
				 if (!context.isPostback()) { 
				 context.getExternalContext().redirect("/planer/pages/admin/userslistview.xhtml");
				 context.responseComplete();
				 }
			}
		}

	}

	public String saveData() {
	
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		try {
			if (user.getUsersId() == null) {
						
				userDAO.create(user);
			} else {
				
				userDAO.merge(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USER_LIST;
	}
	public String cancelEdit() {
		return PAGE_USER_LIST;
	}

}
