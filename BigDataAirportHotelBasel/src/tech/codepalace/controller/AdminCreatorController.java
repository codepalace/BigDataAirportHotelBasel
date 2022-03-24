package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicAdminCreator;
import tech.codepalace.view.frames.AdminCreator;

public class AdminCreatorController implements ActionListener, WindowListener {
	
	private AdminCreator adminCreator;
	private LogicAdminCreator logicAdminCreator;
	
	public AdminCreatorController(AdminCreator adminCreator, LogicAdminCreator logicAdminCreator) {
		
		this.adminCreator = adminCreator;
		this.logicAdminCreator = logicAdminCreator;
		
		this.adminCreator.addWindowListener(this);
		
		this.adminCreator.exitApplicationButton.addActionListener(this);

		this.adminCreator.saveAdminButton.addActionListener(this);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.adminCreator.exitApplicationButton) {
			this.logicAdminCreator.closeApp();
	
		}else if (e.getSource()==adminCreator.saveAdminButton) {
		
			this.logicAdminCreator.validateAdminEntry(this.adminCreator.adminJTextField.getText(), 
					new String(this.adminCreator.passwordField.getPassword()), this.adminCreator.abkuerzungJTextField.getText());
		}
		
		
	}


	
	
	
	@Override
	public void windowOpened(WindowEvent e) {}

	
	@Override
	public void windowClosing(WindowEvent e) {

		this.logicAdminCreator.closeApp();
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

}
