package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelNewUser;
import tech.codepalace.view.frames.NewUser;

/**
 * @description Class controller for the NewUser Class
 * @author Antonio Estevez Gonzalez
 *
 */
public class NewUserController implements ActionListener, KeyListener, WindowListener, FocusListener {
	
	private NewUser newUser;
	private LogicModelNewUser logicModelNewUser;
	
	public NewUserController(NewUser newUser, LogicModelNewUser logicModelNewUser) {
		
		this.newUser = newUser;
		this.logicModelNewUser = logicModelNewUser;
		
		this.newUser.addWindowListener(this);
		
		//Add the Listener for the btnCancelSave button.
		this.newUser.btnCancelSave.addActionListener(this);
		this.newUser.btnCancelSave.addKeyListener(this);
		this.newUser.btnCancelSave.addFocusListener(this);
		
		
		//Add the Listener for the btnSave button.
		this.newUser.btnSave.addActionListener(this);
		this.newUser.btnSave.addKeyListener(this);
		this.newUser.btnSave.addFocusListener(this);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.newUser.btnCancelSave) {
			
			//Close the NewUser GUI.
			this.newUser.confirmClose();
		}
		
		else if(e.getSource()==this.newUser.btnSave) {
			
			//We call the method to save or write the new user information.
			this.logicModelNewUser.saveNewUser(this.newUser.newUserJTextField.getText(), 
					new String(this.newUser.passwordField.getPassword()), 
					this.newUser.benutzerRechtenJComboBox.getSelectedItem().toString(),
					this.newUser.abkuerzungMAJTextField.getText());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource()==this.newUser.btnCancelSave && e.getKeyCode()==10) {
			
			//Close the NewUser GUI.
			this.newUser.confirmClose();
		}
		
		//We call the method to save or write the new user information.
		else if(e.getSource()==this.newUser.btnSave && e.getKeyCode() ==10) {
			this.logicModelNewUser.saveNewUser(this.newUser.newUserJTextField.getText(), 
					new String(this.newUser.passwordField.getPassword()), 
					this.newUser.benutzerRechtenJComboBox.getSelectedItem().toString(), 
					this.newUser.abkuerzungMAJTextField.getText());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		
		this.newUser.confirmClose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
}

	@Override
	public void windowIconified(WindowEvent e) {
}

	@Override
	public void windowDeiconified(WindowEvent e) {
}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {
}

	
	
	@Override
	public void focusGained(FocusEvent e) {
		
		if(e.getSource()==this.newUser.btnCancelSave) {
			
			//Set the Opacity to 1
			this.newUser.btnCancelSave.setOpacity(1);
		}
		
		else if(e.getSource()==this.newUser.btnSave) {
			
			//Set the Opacity to 1
			this.newUser.btnSave.setOpacity(1);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		
		if(e.getSource()==this.newUser.btnCancelSave) {
			
			//Set the Opacity to 0.5 back
			this.newUser.btnCancelSave.setOpacity(0.5f);
		}
		
		else if(e.getSource()==this.newUser.btnSave) {
			
			//Set the Opacity to 0.5 back
			this.newUser.btnSave.setOpacity(0.5f);
		}
		
	}

}
