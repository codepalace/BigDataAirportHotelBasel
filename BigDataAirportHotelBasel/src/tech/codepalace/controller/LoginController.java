package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelLogin;
import tech.codepalace.model.LogicModelStartFrame;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.LoginUser;

public class LoginController implements ActionListener, KeyListener, WindowListener, FocusListener {
	
	private LoginUser loginUser;
//	private UserAHB userAHB;
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	
	/*
	 * We need the logicModelStartFrame variable so we can call from here to set The UserAHB values and so we can get the values from the main GUI Frame after having logged in.
	 */

	
	private LogicModelLogin logicModelLogin;
	
	private LogicModelStartFrame logicModelStartFrame;
	
	
	/*
	 * we need this variable because depending on the privileges of the user, 
	 * we can activate some features of the program or not
	 */
	protected String privilegeUser;
	
	//Create an instance DataEncryption to decrypt the data
	protected DataEncryption dataEncryption;
	
	
	
	
	public LoginController(LoginUser loginUser, BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, LogicModelLogin logicModelLogin, LogicModelStartFrame logicModelStartFrame) {
		
		this.loginUser = loginUser;
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.logicModelLogin = logicModelLogin;
		this.logicModelStartFrame = logicModelStartFrame;
		
		this.loginUser.addWindowListener(this);
	
		this.loginUser.loginButton.addActionListener(this);
		this.loginUser.loginButton.addKeyListener(this);
		this.loginUser.loginButton.addFocusListener(this);
		this.loginUser.passwordField.addKeyListener(this);
		
		this.loginUser.cancelLoginButton.addActionListener(this);
		this.loginUser.cancelLoginButton.addKeyListener(this);
		this.loginUser.cancelLoginButton.addFocusListener(this);
		
		this.dataEncryption = new DataEncryption();
		
		
		
	}
	
	

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
	
		//We call the confirmClose Method for the Login JDialog.
		this.loginUser.confirmClose();
		
		
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
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {

		
		if (e.getSource()== this.loginUser.cancelLoginButton && e.getKeyCode()== 10) {


			this.loginUser.confirmClose();

			
		}else
		
		
		if(e.getSource()== this.loginUser.loginButton && e.getKeyCode()== 10) {
		

			this.logicModelLogin.setLoginValue();
			
			//We check is the password entered by the user is correct
			if(this.logicModelLogin.isPasswordIsCorrect()) {
				
				
				try {
		
					//we decrypt the user's privileges
					this.privilegeUser = this.dataEncryption.decryptData(this.logicModelLogin.getUserAHB().getPrivilege());
					
					//We set the user Value to the JLabel for displaying the user inside the JLabel GUI JFrame
					this.bigDataAirportHotelBaselStartFrame.loginUserText.setText(this.bigDataAirportHotelBaselStartFrame.loginUserText.getText() + 
							" " + this.dataEncryption.decryptData(this.logicModelLogin.getUserAHB().getUserName()));
					
					
				     this.logicModelStartFrame.setUserAHB(this.logicModelLogin.getUserAHB());
					
					//Set the Focus to the parkingButton
					this.bigDataAirportHotelBaselStartFrame.parkingButton.requestFocus();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
				//we get the user privile
				checkPrivilegeUser();
			
		}
			
		}else if (e.getSource()==this.loginUser.passwordField && e.getKeyCode() == 10) {

			//We set the Loginvalue
			this.logicModelLogin.setLoginValue();
			
			//We check is the password entered by the user is correct
			if(this.logicModelLogin.isPasswordIsCorrect()) {
				
				
				try {
					
					//we decrypt the user's privileges
					this.privilegeUser = this.dataEncryption.decryptData(this.logicModelLogin.getUserAHB().getPrivilege());
					
					//We set the user Value to the JLabel for displaying the user inside the JLabel GUI JFrame
					this.bigDataAirportHotelBaselStartFrame.loginUserText.setText(this.bigDataAirportHotelBaselStartFrame.loginUserText.getText() + 
							" " + this.dataEncryption.decryptData(this.logicModelLogin.getUserAHB().getUserName()));
					
					 this.logicModelStartFrame.setUserAHB(this.logicModelLogin.getUserAHB());
					
					//Set the Focus to the parkingButton
					this.bigDataAirportHotelBaselStartFrame.parkingButton.requestFocus();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
				//we get the user privile
				checkPrivilegeUser();
			}

		}
		
		
	}
	
	
	
	

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		

		if (e.getSource()== this.loginUser.cancelLoginButton) {


			this.loginUser.confirmClose();
			
			

			
		}else
		
		
		if(e.getSource()== this.loginUser.loginButton) {
		
			this.logicModelLogin.setLoginValue();
			
			//We check is the password entered by the user is correct
			if(this.logicModelLogin.isPasswordIsCorrect()) {

				
				
				
				try {
					//we decrypt the user's privileges
					this.privilegeUser = this.dataEncryption.decryptData(this.logicModelLogin.getUserAHB().getPrivilege());
					
					//We set the user Value to the JLabel for displaying the user inside the JLabel GUI JFrame
					this.bigDataAirportHotelBaselStartFrame.loginUserText.setText(this.bigDataAirportHotelBaselStartFrame.loginUserText.getText() + 
							" " + this.dataEncryption.decryptData(this.logicModelLogin.getUserAHB().getUserName()));
					
					 this.logicModelStartFrame.setUserAHB(this.logicModelLogin.getUserAHB());

					//Set the Focus to the parkingButton
					this.bigDataAirportHotelBaselStartFrame.parkingButton.requestFocus();
				
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				
				checkPrivilegeUser();
			
		}
			
			
		}
	}
		
		
	
	
	


		
		/**
		 * @description this method evaluates the privileges of the user and depending on the privileges of the user we offer different functionalities of the program.
		 * 
		 * <p>We use this Method inside this Controller Class because depend of the Privilege Value we will make the buttons below visible or not calling the bigDataAirportHOtelBaselStartFrame object.</p>
		 */
		protected void checkPrivilegeUser() {
				
				switch (this.privilegeUser) {
				case "ADMIN":
					

					//As an administrator we make visible the buttons only for administrators
					this.bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setVisible(true);
					this.bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setEnabled(true);
					this.bigDataAirportHotelBaselStartFrame.btn_createDB.setVisible(true);
					this.bigDataAirportHotelBaselStartFrame.btn_createDB.setEnabled(true);
					break;

				default:
					break;
				}
			}



		@Override
		public void focusGained(FocusEvent e) {
			
			if(e.getSource()==this.loginUser.loginButton) {
				this.loginUser.loginButton.setOpacity(1);
			}
			
			else if(e.getSource()==this.loginUser.cancelLoginButton) {
				this.loginUser.cancelLoginButton.setOpacity(1);
			}
			
		}



		@Override
		public void focusLost(FocusEvent e) {
			
			if(e.getSource()==this.loginUser.loginButton) {
				this.loginUser.loginButton.setOpacity(0.5f);
			}
			
			else if(e.getSource()==this.loginUser.cancelLoginButton) {
				this.loginUser.cancelLoginButton.setOpacity(0.5f);
			}
		}

}
