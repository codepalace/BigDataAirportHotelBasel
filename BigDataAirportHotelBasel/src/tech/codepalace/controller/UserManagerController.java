package tech.codepalace.controller;

import tech.codepalace.model.LogicModelUserManager;
import tech.codepalace.view.frames.UserManager;

public class UserManagerController {
	
	private UserManager userManager;
	private LogicModelUserManager logicModelUserManager;
	
	
	public UserManagerController(UserManager userManager, LogicModelUserManager logicModelUserManager) {
		
		this.userManager = userManager;
		this.logicModelUserManager = logicModelUserManager;
		
	}

}
