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
		
		
		
	}
	
	
	
	

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}