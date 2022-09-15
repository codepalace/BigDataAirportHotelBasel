package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;

import tech.codepalace.model.LogicModelEditUser;
import tech.codepalace.view.frames.EditUserGUI;

/**
 * @description Controller Class for EditUserGUI
 * @author Antonio Estevez Gonzalez
 *
 */
public class EditUserController implements ActionListener, KeyListener, WindowListener, FocusListener {
	
	private EditUserGUI editUserGUI;
	private LogicModelEditUser logicModelEditUser;
	
	public EditUserController(EditUserGUI editUserGUI, LogicModelEditUser logicModelEditUser) {
		
		this.editUserGUI = editUserGUI;
		this.logicModelEditUser = logicModelEditUser;
		
		//Add the Listener to the JButtons
		this.editUserGUI.btnCancelSave.addActionListener(this);
		this.editUserGUI.btnCancelSave.addKeyListener(this);
		this.editUserGUI.btnCancelSave.addFocusListener(this);
		
		this.editUserGUI.btnSave.addActionListener(this);
		this.editUserGUI.btnSave.addKeyListener(this);
		this.editUserGUI.btnSave.addFocusListener(this);
		
		this.editUserGUI.changePassword.addActionListener(this);
		this.editUserGUI.changePassword.addKeyListener(this);
		this.editUserGUI.changePassword.addFocusListener(this);
		
		this.editUserGUI.addWindowListener(this);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.editUserGUI.btnCancelSave) {
			
			//Close the GUI
			this.editUserGUI.confirmClose();
		}
		
		else if(e.getSource()==this.editUserGUI.btnSave) {
			
			
		}
		
		else if(e.getSource()==this.editUserGUI.changePassword) {
			
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource()==this.editUserGUI.btnCancelSave && e.getKeyCode()==10) {
			
			//Close the GUI
			this.editUserGUI.confirmClose();
		}
		
		else if(e.getSource()==this.editUserGUI.btnSave && e.getKeyCode()==10) {
			
			
		}
		
		else if(e.getSource()==this.editUserGUI.changePassword && e.getKeyCode()==10) {
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	
	
	@Override
	public void windowOpened(WindowEvent e) {
		
		//Store the CaretPosition of the userJTextField that will be displayed with the user name to be edited.
		 int caretPosition = this.editUserGUI.abkuerzungMAJTextField.getCaretPosition();
		 
		 //invoke a new Thread 
		 SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		        	
		        	//call setCaretPosition to set the cursor position at the end of the JTextField element.
		        	editUserGUI.abkuerzungMAJTextField.setCaretPosition(caretPosition);
		        	
		        	//Call the openUserToEdit Method
		        	logicModelEditUser.openUserToEdit(editUserGUI.userNameToBeDisplayed.getText());
		        }
		    });
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//Close the GUI
		this.editUserGUI.confirmClose();
		
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
		
		//Change the opacity to the JButtons to 1
		if(e.getSource()==this.editUserGUI.btnCancelSave) {
			
			this.editUserGUI.btnCancelSave.setOpacity(1);
		}
		
		else if(e.getSource()==this.editUserGUI.btnSave) {
			
			this.editUserGUI.btnSave.setOpacity(1);
		}
		
		else if(e.getSource()==this.editUserGUI.changePassword) {
			
			this.editUserGUI.changePassword.setOpacity(1);
		}
		
		
		
	}

	
	
	
	@Override
	public void focusLost(FocusEvent e) {
		
		//Change the opacity to the JButtons to 0.5
		if(e.getSource()==this.editUserGUI.btnCancelSave) {
			
			this.editUserGUI.btnCancelSave.setOpacity(0.5f);
		}
		
		else if(e.getSource()==this.editUserGUI.btnSave) {
			
			this.editUserGUI.btnSave.setOpacity(0.5f);
		}
		
		else if(e.getSource()==this.editUserGUI.changePassword) {
			
			this.editUserGUI.changePassword.setOpacity(0.5f);
		}
	}

}
