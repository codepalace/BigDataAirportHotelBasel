package tech.codepalace.model;

import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tech.codepalace.controller.ModifyPasswordUserController;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.PropertiesReader;
import tech.codepalace.utility.PropertiesWriter;
import tech.codepalace.view.frames.EditUserGUI;
import tech.codepalace.view.frames.ModifyPasswordUserGUI;


/**
 * @description Logic Class for EditUserGUI
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelEditUser {


	private EditUserGUI editUserGUI;
	
	//Variable to store the value of the user we want to edit.
	private String userToEdit="";
	
	//Variable to store the PropertieName for the user we want to edit.
	private String userNamePropertieName="", abkuerzungPropertieName="", privilegePropertieName, passwordPropertieName;
	
	//Variable for the DataEncryption
	private DataEncryption dataEncryption;
	
	//Instance PropertiesReader
	private PropertiesReader propertiesReader;
	
	//Instance UserAHB
	protected UserAHB userAHB;
	
	protected String userName, abbkuerzungMA, privilege, password;
	
	//Instance PropertiesWriter to modify the properties
	private PropertiesWriter propertiesWriter;
	
	//Image error JOptionPane
	public ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	public LogicModelEditUser(EditUserGUI editUserGUI) {
		
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
	
	
	
	/**
	 * @description Method to save the edited user changes.
	 * @param userToEdit
	 */
	public void saveChangesUser(String userToEdit) {
		
//		JOptionPane.showMessageDialog(null, "A guardar cambios usuario:" +this.userToEdit);
		
		// Set the value of the properties we want to modify 
		this.abkuerzungPropertieName = "db.abkuerzungma.user" + this.userToEdit;
		this.abbkuerzungMA = this.editUserGUI.abkuerzungMAJTextField.getText();
		
		this.privilegePropertieName = "db.privilege.user." + this.userToEdit;
		
		if(this.editUserGUI.benutzerRechtenJComboBox.getSelectedItem().toString().equalsIgnoreCase("Benutzerrechte auswählen")) {
			
			//We invoke a new Thread with the error message.
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Sie müssen die Rechte des Benutzers konfigurieren"
					   , "Kritischer Fehler Benutzerrechten", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
		}else {
			
			//switch block to evaluate the selected item by benutzerRechtenJComboBox(User Rights).
			switch (this.editUserGUI.benutzerRechtenJComboBox.getSelectedItem().toString()) {
				
				case "Mitarbeiter":
					this.privilege = "STAFF";
					break;

				case "Manager":
					
					 this.privilege = "ADMIN";
					break;
			}
			

			
			// encrypt the data we need to modify
			try {

				this.abkuerzungPropertieName = this.dataEncryption.encryptData(this.abkuerzungPropertieName);
				this.privilegePropertieName = this.dataEncryption.encryptData(this.privilegePropertieName);
				this.abbkuerzungMA = this.dataEncryption.encryptData(this.abbkuerzungMA);
				this.privilege = this.dataEncryption.encryptData(this.privilege);


			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Initialize the PropertiesWriter instance
			this.propertiesWriter = new PropertiesWriter();
			
			//write the modification
			this.propertiesWriter.modifyAndWriteProperties(this.abkuerzungPropertieName, this.abbkuerzungMA);
			this.propertiesWriter.modifyAndWriteProperties(this.privilegePropertieName, this.privilege);
			
			this.editUserGUI.dispose();
		}
		
		
	}
	
	
	
	/**
	 * @description Method to invoke a new Thread to call the ModifyPasswordUserGUI.
	 */
	public void modifyPasswordUser() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				ModifyPasswordUserGUI modifyPasswordUserGUI = new ModifyPasswordUserGUI(editUserGUI, true);
				
				LogicModelModifyPasswordUser logicModelModifyPasswordUser = new LogicModelModifyPasswordUser(modifyPasswordUserGUI, userAHB.getPassword());
				
				new ModifyPasswordUserController(modifyPasswordUserGUI, logicModelModifyPasswordUser);
				
				modifyPasswordUserGUI.setVisible(true);
				
			}
		});

	}

}
