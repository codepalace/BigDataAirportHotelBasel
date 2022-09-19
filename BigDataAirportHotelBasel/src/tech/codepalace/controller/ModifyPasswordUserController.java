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
import tech.codepalace.view.frames.ModifyPasswordUserGUI;

public class ModifyPasswordUserController implements ActionListener, KeyListener, WindowListener, FocusListener {

	
	
	
	private ModifyPasswordUserGUI modifyPasswordUserGUI;
	
	private LogicModelModifyPasswordUser logicModelModifyPasswordUser;
	
	
	public ModifyPasswordUserController(ModifyPasswordUserGUI modifyPasswordUserGUI, LogicModelModifyPasswordUser logicModelModifyPasswordUser) {
		
		this.modifyPasswordUserGUI = modifyPasswordUserGUI;
		this.logicModelModifyPasswordUser = logicModelModifyPasswordUser;
		
		this.modifyPasswordUserGUI.addWindowListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}




	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}




	
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
		
		
	}




	@Override
	public void focusLost(FocusEvent e) {
		
		
	}

}
