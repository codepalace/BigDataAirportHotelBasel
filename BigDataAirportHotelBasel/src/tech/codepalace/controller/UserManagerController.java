package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelUserManager;
import tech.codepalace.view.frames.UserManager;

public class UserManagerController implements ActionListener, KeyListener, WindowListener {
	
	private UserManager userManager;
	private LogicModelUserManager logicModelUserManager;
	
	
	public UserManagerController(UserManager userManager, LogicModelUserManager logicModelUserManager) {
		
		this.userManager = userManager;
		this.logicModelUserManager = logicModelUserManager;
		
		this.userManager.addWindowListener(this);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {}


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
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
