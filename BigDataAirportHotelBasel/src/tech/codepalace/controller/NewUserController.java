package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class NewUserController implements ActionListener, KeyListener, WindowListener {
	
	private NewUser newUser;
	private LogicModelNewUser logicModelNewUser;
	
	public NewUserController(NewUser newUser, LogicModelNewUser logicModelNewUser) {
		
		this.newUser = newUser;
		this.logicModelNewUser = logicModelNewUser;
		
		this.newUser.addWindowListener(this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

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

}
