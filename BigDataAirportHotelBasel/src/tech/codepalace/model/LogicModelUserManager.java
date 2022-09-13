package tech.codepalace.model;

import tech.codepalace.controller.NewUserController;
import tech.codepalace.view.frames.NewUser;
import tech.codepalace.view.frames.UserManager;

public class LogicModelUserManager {
	

	private UserManager userManager;
	
	private NewUser newUser;
	
	private String newUserString;
	
	public LogicModelUserManager(UserManager userManager) {
		

		this.userManager = userManager;
		
		
	}
	
	
	/**
	 * @description Method to call for writing a new Properties to add the new user to the configuration File
	 * 
	 */
	public void addNewUser() {
		
		
		//New instance of NewUser Class extended from JDialog, will be blocked in background the userManager instance with true
		this.newUser = new NewUser(userManager, true);
		
		//New instance the LogicModelNewUser. As argument become LogicModelUserManager.
		LogicModelNewUser logicModelNewUser = new LogicModelNewUser(this, newUser);
		
		//new instance Controller Class NewUserController
		new NewUserController(newUser, logicModelNewUser);
		
		//set Visible newUser Class(extended JDialog)
		newUser.setVisible(true);
		
		
	}
	
	
	/**
	 * @description Method to add the new user to the list before we encrypt the value and save in the configuration file
	 * @param newUserString
	 */
	public void addNewUserToTheList(String newUserString) {
		
		this.newUserString = newUserString;
		
		//We call to add the new user in the GUI list
		this.userManager.addNewUserToList(this.newUserString);
	}

}
