package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.LoginUser;

public class LoginController implements ActionListener, KeyListener, WindowListener {
	
	private LoginUser loginUser;
	private UserAHB userAHB;
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	
	
	
	public LoginController(LoginUser loginUser, UserAHB userAHB, BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
		
		this.loginUser = loginUser;
		this.userAHB = userAHB;
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		
		this.loginUser.addWindowListener(this);
	
		this.loginUser.loginButton.addActionListener(this);
		this.loginUser.loginButton.addKeyListener(this);
		
		this.loginUser.cancelLoginButton.addActionListener(this);
		this.loginUser.cancelLoginButton.addKeyListener(this);
		
		
		
		
		
	}
	
	

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
	
		//We call the confirmClose Method for the Login JDialog.
		this.loginUser.confirmClose();
		
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		
		if (e.getSource()== this.loginUser.cancelLoginButton && e.getKeyCode()== 10) {


			this.loginUser.confirmClose();

			
		}else
		
		
		if(e.getSource()== this.loginUser.loginButton && e.getKeyCode()== 10) {
		

			System.out.println("Time to call setLoginValue Method");
			
			
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource()== this.loginUser.cancelLoginButton) {


			this.loginUser.confirmClose();

			
		}else
		
		
		if(e.getSource()== this.loginUser.loginButton) {
		

			System.out.println("Time to call setLoginValue Method");
			
			
		}
		
		
	}

}
