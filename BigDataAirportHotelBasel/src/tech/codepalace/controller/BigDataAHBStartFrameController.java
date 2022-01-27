package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelStartFrame;
import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description Class Controller for the Main-Frame BigDataAirportHotelBaselStartFrame
 *
 */

public class BigDataAHBStartFrameController implements ActionListener, KeyListener, WindowListener{
	
	// Create an instance of the main Frame class. The first GUI Class JFrame
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;

	// We create instance of the UserAHB that interacts with users
	private UserAHB userAHB;

	
	//Instance of LogicModelStartFrame
	private LogicModelStartFrame logicModelStartFrame;
	
	
	/**
	 * @description constructor method with parameters
	 * @param bigDataAirportHotelBaselStartFrame
	 * @param userAHB
	 */
	public BigDataAHBStartFrameController(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, UserAHB userAHB, LogicModelStartFrame logicModelStartFrame) {
		
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.userAHB = userAHB;
		this.logicModelStartFrame = logicModelStartFrame;
		
		//ActionListener for the JButtons(Create Admin Dialog)
		this.logicModelStartFrame.abbrechenJButton.addActionListener(this);
		this.logicModelStartFrame.okButtonAdmin.addActionListener(this);
		
		
		//KeyListner for the JButtons(Create Administrator User Dialog and also for kuerselMAJTextField
		this.logicModelStartFrame.okButtonAdmin.addKeyListener(this);
		this.logicModelStartFrame.abbrechenJButton.addKeyListener(this);
		this.logicModelStartFrame.kuerselMAJTextField.addKeyListener(this);
		
		//Add Windows Listener to BigDataAirportHotelBaselStartFrame
		this.bigDataAirportHotelBaselStartFrame.addWindowListener(this);
		
		//Add Action and KeyListener for some elements of the Login Dialgo Box
		this.logicModelStartFrame.loginButton.addActionListener(this);
		this.logicModelStartFrame.loginButton.addKeyListener(this);
		this.logicModelStartFrame.cancelLoginButton.addActionListener(this);
		this.logicModelStartFrame.cancelLoginButton.addKeyListener(this);
		this.logicModelStartFrame.passwordLoginJPasswordField.addKeyListener(this);
		
		
		
	}
	
	
	
	

	@Override
	public void windowOpened(WindowEvent e) {

		
		/*
		 * The JFrame BigDataAirportHotelBaselStartFrame is loaded, so we call the checkConfigurationFileProperties, located in LogicModelStartFrame. 
		 * 
		 * This method will check if the configuration file exists in the root of the program. 
		 * 
		 * If it does not exist, proceed to establish the path of the local database and create the administrator user. All this goes through the class LogicModelStartFrame.
		 */
		try {
			this.logicModelStartFrame.checkConfigurationFileProperties();
			
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
		

		if (e.getSource()== this.logicModelStartFrame.okButtonAdmin && e.getKeyCode()== 10) {

			this.logicModelStartFrame.checkEntryAdmin();
		}else
		
		
		if (e.getSource()== this.logicModelStartFrame.kuerselMAJTextField && e.getKeyCode()== 10) {

			this.logicModelStartFrame.checkEntryAdmin();
		}else if (e.getSource()==this.logicModelStartFrame.abbrechenJButton && e.getKeyCode() == 10) {
			
			System.exit(0);
		}else if (e.getSource()== this.logicModelStartFrame.loginButton && e.getKeyCode() == 10) {
			
			
			System.out.println("Ready for login");
			
			/*
			 *The user pressed login, we have to create a method to set the entered login values to the variables. 
			 *
			 *@ToDO
			 *
			 *The method to set the values will be called from here and we need also a class to read configuration file properties. 
			 *
			 *And now that I'm thinking about that, we need to first call the class that decrypts the data saved in the configuration file.
			 *
			 *In the class that reads the properties we will have to compare 
			 *if the password entered by the user matches the password we have in the configuration file for that particular user.
			 *
			 *
			 */
			
			//Call the setLoginValue method
			this.logicModelStartFrame.setLoginValue();
	
		
		}else if (e.getSource()== this.logicModelStartFrame.cancelLoginButton && e.getKeyCode() == 10) {
			System.exit(0);
		
		}else if (e.getSource()==this.logicModelStartFrame.passwordLoginJPasswordField && e.getKeyCode() == 10) {

			System.out.println("Ready for login");
			
			/*
			 *The user pressed login, we have to create a method to set the entered login values to the variables. 
			 *
			 *@ToDO
			 *
			 *The method to set the values will be called from here and we need also a class to read configuration file properties. 
			 *
			 *And now that I'm thinking about that, we need to first call the class that decrypts the data saved in the configuration file.
			 *
			 *In the class that reads the properties we will have to compare 
			 *if the password entered by the user matches the password we have in the configuration file for that particular user.
			 *
			 *
			 */
			
			
			//Call the setLoginValue method
			this.logicModelStartFrame.setLoginValue();
		} 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==this.logicModelStartFrame.okButtonAdmin) {
			
			//We call the Method checkEntryAdmin to check if the data entered is correctly
			this.logicModelStartFrame.checkEntryAdmin();
			
		//abort, we leave the program.
		}else if (e.getSource()==this.logicModelStartFrame.abbrechenJButton) {
		
			System.exit(0);
			
		}else if(e.getSource()==this.logicModelStartFrame.loginButton) {
			System.out.println("You have pressed login");
			
			/*
			 *The user pressed login, we have to create a method to set the entered login values to the variables. 
			 *
			 *@ToDO
			 *
			 *The method to set the values will be called from here and we need also a class to read configuration file properties. 
			 *
			 *And now that I'm thinking about that, we need to first call the class that decrypts the data saved in the configuration file.
			 *
			 *In the class that reads the properties we will have to compare 
			 *if the password entered by the user matches the password we have in the configuration file for that particular user.
			 *
			 *
			 */
			
			//Call the setLoginValue method
			this.logicModelStartFrame.setLoginValue();
			
		}else if(e.getSource()==this.logicModelStartFrame.cancelLoginButton) { //User Cancelled login
			
			//We close the application
			System.exit(0);
			
		}

		
	}

}