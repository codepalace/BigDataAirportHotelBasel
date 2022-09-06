package tech.codepalace.model;

import javax.swing.JOptionPane;

import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.UserManager;

public class LogicModelUserManager {
	
	BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	UserManager userManager;
	
	public LogicModelUserManager(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, UserManager userManager) {
		
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.userManager = userManager;
	}
	
	
	/**
	 * @description Method to call for writing a new Properties to add the new user to the configuration File
	 * 
	 */
	public void addNewUser() {
		JOptionPane.showMessageDialog(null, "Time to add new User to the configuration file");
	}

}
