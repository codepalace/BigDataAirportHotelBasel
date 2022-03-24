package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelConfigurationDirectory;
import tech.codepalace.view.frames.ConfigurationDirectory;


public class ConfigurationDirectoryController implements ActionListener, MouseListener, WindowListener {
	
	private ConfigurationDirectory configurationDirectory;
	private LogicModelConfigurationDirectory logicModelConfigurationDirectory;
	
	
	public ConfigurationDirectoryController(ConfigurationDirectory configurationDirectory, LogicModelConfigurationDirectory logicModelConfigurationDirectory) {
		
		
		this.configurationDirectory = configurationDirectory;
		this.logicModelConfigurationDirectory = logicModelConfigurationDirectory;
		
		this.configurationDirectory.addWindowListener(this);
		
		this.configurationDirectory.selectDatabaseLocalJButton.addActionListener(this);
		this.configurationDirectory.selectBackupDataBaseJButton.addActionListener(this);
		
		this.configurationDirectory.dataBaseSelectedJLabel.addMouseListener(this);
		this.configurationDirectory.backupDataBaseSelectedJLabel.addMouseListener(this);
		
		this.configurationDirectory.savePathsJButton.addActionListener(this);
		this.configurationDirectory.cancelSavePathsJButton.addActionListener(this);
		
		this.configurationDirectory.exitJButton.addActionListener(this);
		this.configurationDirectory.cancelJButton.addActionListener(this);
		
		
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==this.configurationDirectory.selectDatabaseLocalJButton) {
			
			this.logicModelConfigurationDirectory.selectPathDataBaseLocal();
		}else if (e.getSource()==this.configurationDirectory.selectBackupDataBaseJButton) {
			
			this.logicModelConfigurationDirectory.selectPathBackupDataBaseLocal();
		
		}else if (e.getSource()==this.configurationDirectory.cancelSavePathsJButton) {
			

					logicModelConfigurationDirectory.closeConfigurationGUI();
			
		
		}else if (e.getSource()==this.configurationDirectory.exitJButton) {
			System.exit(0);
		
		}else if (e.getSource()==this.configurationDirectory.cancelJButton) {
			
			this.configurationDirectory.exitDialog.dispose();
			
		}else if(e.getSource()==this.configurationDirectory.savePathsJButton) {
			
			
			/*
			 * The user wants to save the database and backup paths so we call the checkUrlDataBase method to check first the URLs.
			 */
			this.logicModelConfigurationDirectory.checkUrlDataBase();
		
		}

	}



	@Override
	public void mouseClicked(MouseEvent e) {}



	@Override
	public void mousePressed(MouseEvent e) {}



	@Override
	public void mouseReleased(MouseEvent e) {}



	@Override
	public void mouseEntered(MouseEvent e) {
	

		if(e.getSource()==this.configurationDirectory.dataBaseSelectedJLabel) {
			this.configurationDirectory.dataBaseSelectedJLabel.setToolTipText(this.configurationDirectory.dataBaseSelectedJLabel.getText());
		
		}else if (e.getSource()==this.configurationDirectory.backupDataBaseSelectedJLabel) {
			this.configurationDirectory.backupDataBaseSelectedJLabel.setToolTipText(this.configurationDirectory.backupDataBaseSelectedJLabel.getText());
		
		}
	}



	@Override
	public void mouseExited(MouseEvent e) {}



	@Override
	public void windowOpened(WindowEvent e) {}



	@Override
	public void windowClosing(WindowEvent e) {

		this.logicModelConfigurationDirectory.closeConfigurationGUI();
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
