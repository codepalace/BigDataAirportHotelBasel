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
		
		//Add Listeners to the JButtons(MyButton)
		this.modifyPasswordUserGUI.btnSave.addActionListener(this);
		this.modifyPasswordUserGUI.btnSave.addKeyListener(this);
		this.modifyPasswordUserGUI.btnSave.addFocusListener(this);
		
		this.modifyPasswordUserGUI.btnCancelSave.addActionListener(this);
		this.modifyPasswordUserGUI.btnCancelSave.addKeyListener(this);
		this.modifyPasswordUserGUI.btnCancelSave.addFocusListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.modifyPasswordUserGUI.btnCancelSave) {
			
			//call to close the GUI
			this.modifyPasswordUserGUI.confirmClose();
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

}
