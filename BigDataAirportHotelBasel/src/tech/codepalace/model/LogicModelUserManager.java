package tech.codepalace.model;

import tech.codepalace.controller.NewUserController;
import tech.codepalace.utility.PropertiesWriter;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.NewUser;
import tech.codepalace.view.frames.UserManager;

public class LogicModelUserManager {
	
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	private UserManager userManager;
	
	//Instance PropertiesWriter to write new Properties
	private PropertiesWriter propertieswriter;
	
	//Variables for the new user properties
	private String userNamePropertieName, userNamePropertieValue, passwordPropertieName, passwordPropertieValue ;


	
	public LogicModelUserManager(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, UserManager userManager) {
		
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.userManager = userManager;
		
		
	}
	
	
	/**
	 * @description Method to call for writing a new Properties to add the new user to the configuration File
	 * 
	 */
	public void addNewUser() {
		
		
		//Initialize the PropertiesWriter Instance
		this.propertieswriter = new PropertiesWriter();
		
		//Initialize the variables for the new User Properties. 
		this.userNamePropertieName = "db.user.";
		
		this.userNamePropertieValue = ""; //We have to get the value from a new GUI we are going to create
		
		
		this.passwordPropertieName = "db.password.user." + this.userNamePropertieValue;
		
		this.passwordPropertieValue = ""; //We have to get the value from a new GUI we are going to create
		
		//New instance of NewUser Class extended from JDialog, will be blocked in background the userManager instance with true
		NewUser newUser = new NewUser(userManager, true);
		
		//New instance the LogicModelNewUser. As argument become LogicModelUserManager.
		LogicModelNewUser logicModelNewUser = new LogicModelNewUser(this);
		
		//new instance Controller Class NewUserController
		new NewUserController(newUser, logicModelNewUser);
		
		//set Visible newUser Class(extended JDialog)
		newUser.setVisible(true);
		
		
	}

}
