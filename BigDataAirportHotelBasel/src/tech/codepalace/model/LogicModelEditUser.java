package tech.codepalace.model;

import java.awt.HeadlessException;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.PropertiesReader;
import tech.codepalace.view.frames.EditUserGUI;


/**
 * @description Logic Class for EditUserGUI
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelEditUser {

	private LogicModelUserManager logicModelUserManager;
	private EditUserGUI editUserGUI;
	
	//Variable to store the value of the user we want to edit.
	private String userToEdit="";
	
	//Variable to store the PropertieName for the user we want to edit.
	private String userNamePropertieName="", abkuerzungPropertieName="", privilegePropertieName, passwordPropertieName;
	
	//Variable for the DataEncryption
	private DataEncryption dataEncryption;
	
	//Instance Propert
	private PropertiesReader propertiesReader;
	
	//Instance UserAHB
	protected UserAHB userAHB;
	
	protected String userName, abbkuerzungMA, privilege, password;
	
	public LogicModelEditUser(LogicModelUserManager logicModelUserManager, EditUserGUI editUserGUI) {
		
		this.logicModelUserManager = logicModelUserManager;
		this.editUserGUI = editUserGUI;
		
		this.dataEncryption = new DataEncryption();
	}
	
	
	/**
	 * @description Method to open the configuration File to get the values of the Properties from the user we want to edit.
	 * @param userToEdit
	 */
	public void openUserToEdit(String userToEdit) {
		
		//set the value for the userToEdit variable
		this.userToEdit = userToEdit;
		
		//Set the value of the properties.
		this.userNamePropertieName = "db.user." + this.userToEdit;
		this.abkuerzungPropertieName = "db.abkuerzungma.user" + this.userToEdit;
		this.privilegePropertieName = "db.privilege.user." + this.userToEdit;
		this.passwordPropertieName = "db.password.user." + this.userToEdit;
		
		
		
		//encrypt the data
		try {
			this.userNamePropertieName = this.dataEncryption.encryptData(this.userNamePropertieName);
			this.abkuerzungPropertieName = this.dataEncryption.encryptData(this.abkuerzungPropertieName);
			this.privilegePropertieName = this.dataEncryption.encryptData(this.privilegePropertieName);
			this.passwordPropertieName = this.dataEncryption.encryptData(this.passwordPropertieName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Initialize the instance PropertiesReader
		this.propertiesReader = new PropertiesReader();
		
		//Initialize the UserAHB
		this.userAHB = new UserAHB();
		
		/*
		 * Set the value we need for the userAHB instance.
		 * 
		 * For the moment i set the complete encrypted Properties Names. I am going to store the correct value later when i get the User 
		 * with the all properties values.
		 */
		this.userAHB.setUserName(this.userNamePropertieName);
		this.userAHB.setAbkuerzungMA(this.abkuerzungPropertieName);
		this.userAHB.setPrivilege(this.privilegePropertieName);
		this.userAHB.setPassword(this.passwordPropertieName);
		
		//Now we get the userAHB values from the PropertieReader
		this.userAHB = this.propertiesReader.readPropertiesUserAHBToEdit(this.userAHB);
		
		
		try {
			
			//decrypt data and store the values.
			this.userName = dataEncryption.decryptData(this.userAHB.getUserName());
			this.abbkuerzungMA = dataEncryption.decryptData(this.userAHB.getAbkuerzungMA());
			this.privilege = dataEncryption.decryptData(this.userAHB.getPrivilege());
			this.password = dataEncryption.decryptData(this.userAHB.getPassword());

			
			//set the text value for abbreviation name
			this.editUserGUI.abkuerzungMAJTextField.setText(this.abbkuerzungMA);

			//Evaluate the value privilege
			switch (this.privilege) {
			
			//Depending of the value select the correct index.
			case "STAFF":
				
				this.editUserGUI.benutzerRechtenJComboBox.setSelectedItem("Mitarbeiter");
				break;
				
			case "ADMIN":
				
				this.editUserGUI.benutzerRechtenJComboBox.setSelectedItem("Manager");

				break;
			}
			
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
