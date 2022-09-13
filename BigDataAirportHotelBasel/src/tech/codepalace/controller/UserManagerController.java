package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tech.codepalace.model.LogicModelUserManager;
import tech.codepalace.view.frames.UserManager;

public class UserManagerController implements ActionListener, KeyListener, WindowListener {
	
	private UserManager userManager;
	private LogicModelUserManager logicModelUserManager;
	
	//Image error JOptionPane
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	public UserManagerController(UserManager userManager, LogicModelUserManager logicModelUserManager) {
		
		this.userManager = userManager;
		this.logicModelUserManager = logicModelUserManager;
		
		this.userManager.addWindowListener(this);
		
		//Add actionListener to the JButtons
		this.userManager.addUserJButton.addActionListener(this);
		this.userManager.editUserJButton.addActionListener(this);
		
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
