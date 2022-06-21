package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelStartFrame;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description Class Controller for the Main-Frame BigDataAirportHotelBaselStartFrame
 *
 */

public class BigDataAHBStartFrameController implements ActionListener, KeyListener, WindowListener, FocusListener, ComponentListener{
	
	// Create an instance of the main Frame class. The first GUI Class JFrame
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;

	//Instance of LogicModelStartFrame
	private LogicModelStartFrame logicModelStartFrame;
		

	/*
	 * we need this variable because depending on the privileges of the user, 
	 * we can activate some features of the program or not
	 */
	protected String privilegeUser;
	
	//Create an instance DataEncryption to decrypt the data
	protected DataEncryption dataEncryption;

	
	
	/**
	 * @description constructor method with parameters
	 * @param bigDataAirportHotelBaselStartFrame
	 * @param userAHB
	 */
	public BigDataAHBStartFrameController(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, LogicModelStartFrame logicModelStartFrame) {
		
		
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.logicModelStartFrame = logicModelStartFrame;
		
		
		//Add Windows Listener to BigDataAirportHotelBaselStartFrame
		this.bigDataAirportHotelBaselStartFrame.addWindowListener(this);
		
		
		//Initialize DataEncription for using with the login data
		this.dataEncryption = new DataEncryption();
		
		this.bigDataAirportHotelBaselStartFrame.addComponentListener(this);
		
		//addActionListener to the buttons in JFrame
		this.bigDataAirportHotelBaselStartFrame.parkingButton.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.fundsachenButton.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.fitnessButton.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.uebergabeButton.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.phonebookButton.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.logoutButton.addActionListener(this);
		
		this.bigDataAirportHotelBaselStartFrame.btn_createDB.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.btn_kontoVerwalten.addActionListener(this);
		this.bigDataAirportHotelBaselStartFrame.btn_exit.addActionListener(this);
		
		
		//Add FocusListener to the Buttons
		this.bigDataAirportHotelBaselStartFrame.parkingButton.addFocusListener(this);
		this.bigDataAirportHotelBaselStartFrame.fundsachenButton.addFocusListener(this);
		this.bigDataAirportHotelBaselStartFrame.fitnessButton.addFocusListener(this);
		this.bigDataAirportHotelBaselStartFrame.uebergabeButton.addFocusListener(this);
		this.bigDataAirportHotelBaselStartFrame.phonebookButton.addFocusListener(this);
		this.bigDataAirportHotelBaselStartFrame.logoutButton.addFocusListener(this);
		
		this.bigDataAirportHotelBaselStartFrame.btn_createDB.addFocusListener(this);
		this.bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.addFocusListener(this);
		this.bigDataAirportHotelBaselStartFrame.btn_kontoVerwalten.addFocusListener(this);	
		this.bigDataAirportHotelBaselStartFrame.btn_exit.addFocusListener(this);
		
		
		//Add KeyListener to the parkingButton. only to this button because is going to have always the focus
		this.bigDataAirportHotelBaselStartFrame.parkingButton.addKeyListener(this);
		
		
		this.bigDataAirportHotelBaselStartFrame.btn_createDB.setToolTipText("Benutzerdefinierte Datenbank Erstellen");
		this.bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setToolTipText("Benutzer Verwalten");
		this.bigDataAirportHotelBaselStartFrame.btn_kontoVerwalten.setToolTipText("Eigene Konto Verwalten");
		this.bigDataAirportHotelBaselStartFrame.btn_exit.setToolTipText("Programm beenden");
		

		
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
	public void windowClosing(WindowEvent e) {}

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
		
	
		if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.parkingButton &&  e.getKeyCode() == 113) {
			
			

			 logicModelStartFrame.displayParking(this.bigDataAirportHotelBaselStartFrame, "StartFrame");


			
		}else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.parkingButton &&  e.getKeyCode()==114) {
			
			logicModelStartFrame.displayFundSachen(this.bigDataAirportHotelBaselStartFrame, "StartFrame");
			
		}else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.parkingButton &&  e.getKeyCode()==115) {
			System.out.println("Opening the database Fitness!");
		}else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.parkingButton &&  e.getKeyCode()==117) {
			System.out.println("Opening the database Uebergabe!");
		}else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.parkingButton &&  e.getKeyCode()==118) {
			System.out.println("Opening the database Telefonbuch!");
		}else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.parkingButton &&  e.getKeyCode()==119) {
			//We call logoutApplication Method by logicModelStartFrame
			
			
			this.logicModelStartFrame.logoutApplication(this.bigDataAirportHotelBaselStartFrame, this.logicModelStartFrame);
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.parkingButton) {
			

			
			logicModelStartFrame.displayParking(this.bigDataAirportHotelBaselStartFrame, "StartFrame");
			

			
		} else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.fundsachenButton) {

			logicModelStartFrame.displayFundSachen(this.bigDataAirportHotelBaselStartFrame, "StartFrame");
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.fitnessButton) {
			System.out.println("you pressed the fitness button");
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.uebergabeButton) {
			System.out.println("you pressed the uebergabe button");
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.phonebookButton) {
			System.out.println("you pressed the phonebook button");
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.logoutButton) {
			//We call logoutApplication Method by logicModelStartFrame
			
			
			this.logicModelStartFrame.logoutApplication(this.bigDataAirportHotelBaselStartFrame, this.logicModelStartFrame);
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.btn_kontoVerwalten) {
			System.out.println("you pressed the kontoverwalten button");
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.btn_exit) {
			System.exit(0);
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten) {
			System.out.println("you pressed the benutzerVerwalten button");
		} 
		else if (e.getSource()==this.bigDataAirportHotelBaselStartFrame.btn_createDB) {
			System.out.println("you pressed the create DB button");
		} 
		
		
			
			
		

		
	}
	
	
	
	
/**
 * @description this method evaluates the privileges of the user and depending on the privileges of the user we offer different functionalities of the program.
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

//	System.out.println("component "+ e.getComponent().getClass().getName() + " has the focus." );
	
	//if the parking button does not have the focus 
	if(e.getSource()!=this.bigDataAirportHotelBaselStartFrame.parkingButton) {
		
		/* we will return or request the focus for parkingButton.
		 * 
		 * this means that the parking button will always have the focus. 
		 * this will help us to be able to configure the keyboard keys that will be programmed for the different accesses.
		 * 
		 * of course we could try to use setMnemonic(KeyEvent.F2) to give an example. 
		 * 
		 * but the most practical and safe option I've found has been this. Sending the Focus always to parkingButton and exit keyPressed method.
		 */
		
		
		this.bigDataAirportHotelBaselStartFrame.parkingButton.requestFocus();
	}

	
	
	
}





@Override
public void focusLost(FocusEvent e) {
	
	
	if (e.getSource()!=this.bigDataAirportHotelBaselStartFrame.parkingButton) {

		this.bigDataAirportHotelBaselStartFrame.parkingButton.requestFocus();
	}
	
}



@Override
public void componentResized(ComponentEvent e) {}





@Override
public void componentMoved(ComponentEvent e) {
	
	this.bigDataAirportHotelBaselStartFrame.setLocationRelativeTo(null);
	
}





@Override
public void componentShown(ComponentEvent e) {}





@Override
public void componentHidden(ComponentEvent e) {}



}