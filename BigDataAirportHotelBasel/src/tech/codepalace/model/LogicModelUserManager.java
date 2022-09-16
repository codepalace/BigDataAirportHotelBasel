package tech.codepalace.model;

import tech.codepalace.controller.EditUserController;
import tech.codepalace.controller.NewUserController;
import tech.codepalace.view.frames.EditUserGUI;
import tech.codepalace.view.frames.NewUser;
import tech.codepalace.view.frames.UserManager;

public class LogicModelUserManager {
	
	//Instance UserManager
	private UserManager userManager;
	
	//Instance NewUser
	private NewUser newUser;
	
	private String newUserString;
	
	//intances EditUserGUI
	private EditUserGUI editUserGUI;
	
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
	
	
	
	/**
	 * @description Method to call for edit a existing user.
	 * @param userToEdit
	 */
	public void editUser(String userToEdit) {
		
//		JOptionPane.showMessageDialog(null, "ready to edit the user: " + userToEdit);
		
		//New instance of EditUserGUI extended from JDialog. userManager instance will be blocked in background with the argument true. 
		this.editUserGUI = new EditUserGUI(userManager, true);
		
		this.editUserGUI.userNameToBeDisplayed.setText(userToEdit);
		
		//New instance of LogicModelEditUser initialized
		LogicModelEditUser logicModelEditUser = new LogicModelEditUser(editUserGUI);
		
		//new Instance of EditUserController
		new EditUserController(editUserGUI, logicModelEditUser);
		
		//set visible editUserGUI
		this.editUserGUI.setVisible(true);
		
		
		
	}

}
