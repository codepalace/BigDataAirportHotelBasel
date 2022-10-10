package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tech.codepalace.model.LogicModelUserManager;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.UserManager;

public class UserManagerController implements ActionListener, KeyListener, WindowListener {
	
	private UserManager userManager;
	private LogicModelUserManager logicModelUserManager;
	
	//Image error JOptionPane
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	private DataEncryption dataEncryption;
	
	//Variable to store the currentUser value decrypted
	private String currentUser;

	
	public UserManagerController(UserManager userManager, LogicModelUserManager logicModelUserManager) {
		
		this.userManager = userManager;
		this.logicModelUserManager = logicModelUserManager;
		
		this.userManager.addWindowListener(this);
		
		//Add actionListener to the JButtons
		this.userManager.addUserJButton.addActionListener(this);
		this.userManager.editUserJButton.addActionListener(this);
		this.userManager.deleteUserJButton.addActionListener(this);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.userManager.addUserJButton) {
			this.logicModelUserManager.addNewUser();
		}
		
		else if(e.getSource()==this.userManager.editUserJButton) {
			
			//If user was selected to edit 
			if(this.userManager.list.getSelectedValue()!=null) {
				//Call the editUser Method
				this.logicModelUserManager.editUser(this.userManager.list.getSelectedValue());
				
			}else {

				//User was not selected to edit but the editUserJButton was pressed 
				
				//Invoke a new Thread with the error message.
				SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Sie haben keinen Benutzer zum editieren ausgewählt"
						   , "Keinen Benutzer asugewählt", JOptionPane.ERROR_MESSAGE, this.errorImg));
			}
			
		}
		
		else if(e.getSource()==this.userManager.deleteUserJButton) {
			
			//If user was selected to delete
			if(this.userManager.list.getSelectedValue()!=null) {
				
				//Initialize the dataEncryption Object
				this.dataEncryption = new DataEncryption();
				
				//Set value currentUser
				try {
					this.currentUser = this.dataEncryption.decryptData(this.logicModelUserManager.getCurrentUser());
				} catch (Exception e2) {

					e2.printStackTrace();
				}
				
				/*
				 * If the selected user is equals the current logged user 
				 */
				if(this.currentUser.equals(userManager.list.getSelectedValue())) {
					
					//Selected user equals current user cannot be deleted
					SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Der angemeldete Benutzer kann nicht gelöscht werden"
							   , "Nicht erlaubt angemeldeten Benutzer zu löschen", JOptionPane.ERROR_MESSAGE, this.errorImg));
				
				}else {
					
					
					
					//else(The current user is not equals selected user
					
					//Variable to give one Option to the user if he is sure he wants to delete the selected user. 
					int selectedOption = 
							JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie den Benutzer:( "
									+ userManager.list.getSelectedValue() + ") löschen möchten", "Benutzer löschen", 
									JOptionPane.YES_NO_OPTION);
							
					
					//If the user confirm with Yes
					if (selectedOption == JOptionPane.YES_OPTION) {
						
						//New Instance DefaultListModel to get the userManager JList(list) Model
						DefaultListModel<String> model = (DefaultListModel<String>) userManager.list.getModel();
						
						//Variable to get the selected Index
						int selectedIndex = userManager.list.getSelectedIndex();
						
						//If some index was selected
						if (selectedIndex != -1) {
							
							//we call to deleteSelectedUser with the argument selected value. 
						    this.logicModelUserManager.deleteSelectedUser(userManager.list.getSelectedValue());
							
							//Remove this selected index from our JList(list) throw the model of course.
						    model.remove(selectedIndex);
						    
						    
						}
						
						
						
						
					}
					
					
				}
				
			
			}else {
				
				//User was not selected to delete. Invoke a new Thread with the error message
				SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Sie haben keinen Benutzer zum löschen ausgewählt"
						   , "Keinen Benutzer asugewählt", JOptionPane.ERROR_MESSAGE, this.errorImg));
				
				
			}
		}
	}


	@Override
	public void keyPressed(KeyEvent arg0) {}


	@Override
	public void keyReleased(KeyEvent arg0) {}


	@Override
	public void keyTyped(KeyEvent arg0) {}


	@Override
	public void windowActivated(WindowEvent e) {}


	@Override
	public void windowClosed(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
	
		this.userManager.confirmClose();
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {}


	@Override
	public void windowDeiconified(WindowEvent e) {}


	@Override
	public void windowIconified(WindowEvent e) {}


	@Override
	public void windowOpened(WindowEvent e) {}

}
