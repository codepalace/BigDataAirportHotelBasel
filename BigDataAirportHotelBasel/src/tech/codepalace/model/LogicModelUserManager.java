package tech.codepalace.model;

import javax.swing.SwingUtilities;

import tech.codepalace.controller.EditUserController;
import tech.codepalace.controller.NewUserController;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.PropertiesWriter;
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
	
	//Variable to store the value of privilege who is call the User Manager
	protected String privilegeWhoEditUser = "";
	
	//Variable for the current User who edits other user or delete
	private String currentUser;
	
	
	//Variable to store the selected user to be deleted
	private String selectedUser;
	
	//Instance of PropertiesWrirter
	private PropertiesWriter propertiesWriter;
	
	//Variables Name of the Properties or Keys that we one to remove from the configuration File. 
	private String userNamePropertieName, passwordPropertieName, privilegePropertieName, abkuerzungPropertieName;
	
	private DataEncryption dataEncryption;
	
	
	
	public LogicModelUserManager(UserManager userManager, String privilegeWhoEditUser, String currentUser) {
		

		this.userManager = userManager;
		this.privilegeWhoEditUser = privilegeWhoEditUser;
		this.currentUser = currentUser;
		
		
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
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//New instance of LogicModelEditUser initialized
				LogicModelEditUser logicModelEditUser = new LogicModelEditUser(editUserGUI, privilegeWhoEditUser);
				
				//new Instance of EditUserController
				new EditUserController(editUserGUI, logicModelEditUser);
				
				//set visible editUserGUI
				editUserGUI.setVisible(true);
				
			}
		});

		
	}


	/**
	 * @return the currentUser
	 */
	public String getCurrentUser() {
		return currentUser;
	}
	
	
	
	/**
	 * @description Method to delete from the configuration file the Properties selected user.
	 * @param selectedUser
	 */
	public void deleteSelectedUser(String selectedUser) {
		//Set the value for the selectedUser
		this.selectedUser = selectedUser;

		//Initialize the PropertiesWriter instance
		this.propertiesWriter = new PropertiesWriter();
		
		//Set the values of the Properties we want to remove from the List of Properties saved by the configuration File. 
		this.userNamePropertieName = "db.user." + this.selectedUser;
		this.passwordPropertieName = "db.password.user." + this.selectedUser;
		this.privilegePropertieName = "db.privilege.user." + this.selectedUser;
		this.abkuerzungPropertieName = "db.abkuerzungma.user." + this.selectedUser;
		
		//Initialize the DataEncryption instance.
		this.dataEncryption = new DataEncryption();
		
		
		//We encrypt the data and store them in the variables
		try {
			this.userNamePropertieName = this.dataEncryption.encryptData(this.userNamePropertieName);

			this.passwordPropertieName = this.dataEncryption.encryptData(this.passwordPropertieName);

			this.privilegePropertieName = this.dataEncryption.encryptData(this.privilegePropertieName);

			this.abkuerzungPropertieName = this.dataEncryption.encryptData(this.abkuerzungPropertieName);
			
	
			
			//We call to delete the Properties
			this.propertiesWriter.removeProperties(userNamePropertieName);
			this.propertiesWriter.removeProperties(passwordPropertieName);
			this.propertiesWriter.removeProperties(privilegePropertieName);
			this.propertiesWriter.removeProperties(abkuerzungPropertieName);

			//remove the user from the list
//			this.userManager.list.getModel().
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
