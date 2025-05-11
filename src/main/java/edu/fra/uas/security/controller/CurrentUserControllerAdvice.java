package edu.fra.uas.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.fra.uas.security.model.CurrentUser;

//CurrentUser object as a model attribute, which can be accessed by views to display information about the currently authenticated user
@ControllerAdvice
public class CurrentUserControllerAdvice {

	@Autowired
	CurrentUser currentUser;

	@ModelAttribute("currentUser")
	public CurrentUser getCurrentUser() {
		return currentUser;
	}

}
