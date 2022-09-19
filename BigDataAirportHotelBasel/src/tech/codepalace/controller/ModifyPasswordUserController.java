package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelModifyPasswordUser;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.ModifyPasswordUserGUI;

public class ModifyPasswordUserController implements ActionListener, KeyListener, WindowListener, FocusListener {

	
	
	
	private ModifyPasswordUserGUI modifyPasswordUserGUI;
	
	private LogicModelModifyPasswordUser logicModelModifyPasswordUser;
	
	protected String privilegeWhoEditUser;
	
	private DataEncryption dataEncryption;
	
	
	public ModifyPasswordUserController(ModifyPasswordUserGUI modifyPasswordUserGUI, LogicModelModifyPasswordUser logicModelModifyPasswordUser) {
		
		this.modifyPasswordUserGUI = modifyPasswordUserGUI;
		this.logicModelModifyPasswordUser = logicModelModifyPasswordUser;
		
		
		this.modifyPasswordUserGUI.addWindowListener(this);
		
		//Add Listeners to the JButtons(MyButton)
		this.modifyPasswordUserGUI.btnSave.addActionListener(this);
		this.modifyPasswordUserGUI.btnSave.addKeyListener(this);
		this.modifyPasswordUserGUI.btnSave.addFocusListener(this);
		
		this.modifyPasswordUserGUI.btnCancelSave.addActionListener(this);
		this.modifyPasswordUserGUI.btnCancelSave.addKeyListener(this);
		this.modifyPasswordUserGUI.btnCancelSave.addFocusListener(this);
		
		this.modifyPasswordUserGUI.newPasswordConfirmationField.addKeyListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.modifyPasswordUserGUI.btnCancelSave) {
			
			//call to close the GUI
			this.modifyPasswordUserGUI.confirmClose();
		}
		
		else if(e.getSource()==this.modifyPasswordUserGUI.btnSave) { //If the btnSave MyButton will be pressed.
			
			//We call to modify the password. 
			modifyPasswordAppropriateUser();

			
		}
		
	}

	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource()==this.modifyPasswordUserGUI.btnCancelSave && e.getKeyCode()==10) {
			
			//call to close the GUI
			this.modifyPasswordUserGUI.confirmClose();
		}
		
		else if(e.getSource()==this.modifyPasswordUserGUI.btnSave && e.getKeyCode()==10) {
			
			//We call to modify the password. 
			modifyPasswordAppropriateUser();
		}
		
		else if(e.getSource()==this.modifyPasswordUserGUI.newPasswordConfirmationField && e.getKeyCode() == 10) {
			
			//We call to modify the password. 
			modifyPasswordAppropriateUser();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}




	@Override
	public void windowOpened(WindowEvent e) {}




	
	@Override
	public void windowClosing(WindowEvent e) {
		
		
		this.modifyPasswordUserGUI.confirmClose();
	}




	@Override
	public void windowClosed(WindowEvent e) {}




	@Override
	public void windowIconified(WindowEvent e) {}




	@Override
	public void windowDeiconified(WindowEvent e) {}




	@Override
	public void windowActivated(WindowEvent e) {}




	@Override
	public void windowDeactivated(WindowEvent e) {}




	@Override
	public void focusGained(FocusEvent e) {
		
		//set the opacity to 1
		if(e.getSource()==this.modifyPasswordUserGUI.btnSave) {
			this.modifyPasswordUserGUI.btnSave.setOpacity(1);
		}
		
		else if(e.getSource()==this.modifyPasswordUserGUI.btnCancelSave) {
			this.modifyPasswordUserGUI.btnCancelSave.setOpacity(1);
		}
		
	}




	@Override
	public void focusLost(FocusEvent e) {
		
		//set the opacity to 0.5
		if(e.getSource()==this.modifyPasswordUserGUI.btnSave) {
			this.modifyPasswordUserGUI.btnSave.setOpacity(0.5f);
		}
		
		else if(e.getSource()==this.modifyPasswordUserGUI.btnCancelSave) {
			this.modifyPasswordUserGUI.btnCancelSave.setOpacity(0.5f);
		}
	}
	
	
	
	/**
	 * @description Method to call for modifying the user password depending appropriate user who is calling to modify ADMIN or STAFF.
	 */
	private void modifyPasswordAppropriateUser() {
		
		//Initialize DataEncryption
		this.dataEncryption = new DataEncryption();
		
		//decrypt data
		try {
			this.privilegeWhoEditUser = this.dataEncryption.decryptData(logicModelModifyPasswordUser.getPrivilegeWhoEditsUser());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		//Block switch to evaluate the privilegeWhoEditUser value
		switch (this.privilegeWhoEditUser) {
			
			
			//Case ADMIN we call the appropriate method to modify the Password 
			case "ADMIN":
				
				
				this.logicModelModifyPasswordUser.modifyPasswordUser(
						 new String(this.modifyPasswordUserGUI.newPasswordField.getPassword()),
						 new String(this.modifyPasswordUserGUI.newPasswordConfirmationField.getPassword()));
				
				break;

			//Case STAFF we call the appropriate method to modify the Password 
			case "STAFF":
				
				this.logicModelModifyPasswordUser.modifyPasswordUser(
						new String(this.modifyPasswordUserGUI.oldPasswordField.getPassword()),
						new String(this.modifyPasswordUserGUI.newPasswordField.getPassword()),
						new String(this.modifyPasswordUserGUI.newPasswordConfirmationField.getPassword()));
				break;
		}
	}

}
