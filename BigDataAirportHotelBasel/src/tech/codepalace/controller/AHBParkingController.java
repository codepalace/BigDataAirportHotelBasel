package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelParking;
import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.AHBParking;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version 1.0.0 Jan 30.2022 10:40PM
 * @description Class Controller for the application of parking databases
 *
 */
public class AHBParkingController  implements ActionListener, KeyListener, WindowListener, FocusListener {
	
	private AHBParking ahbParking;
	private UserAHB userAHB;
	private LogicModelParking logicModelParking;
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;


	
	
	public AHBParkingController(AHBParking ahbParking, UserAHB userAHB, LogicModelParking logicModelParking, 
			BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
		
		this.ahbParking = ahbParking;
		this.userAHB = userAHB;
		this.logicModelParking = logicModelParking;
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;

		
		this.ahbParking.addWindowListener(this);
		
		
		
		this.ahbParking.exitParking.addActionListener(this);
		this.ahbParking.exitParking.addKeyListener(this);
		this.ahbParking.noExitParking.addActionListener(this);
		this.ahbParking.noExitParking.addKeyListener(this);
		
        
		//Menu JButtons Action And KeyListener
		this.ahbParking.btnHome.addActionListener(this);
		this.ahbParking.btnHome.addKeyListener(this);
		this.ahbParking.btnFundsachen.addActionListener(this);
		this.ahbParking.btnFundsachen.addKeyListener(this);
		this.ahbParking.btnFitness.addActionListener(this);
		this.ahbParking.btnFitness.addKeyListener(this);
		this.ahbParking.btnPhonebook.addActionListener(this);
		this.ahbParking.btnPhonebook.addKeyListener(this);
		this.ahbParking.btnLogout.addActionListener(this);
		this.ahbParking.btnLogout.addKeyListener(this);
		
		
		
	}




	
	
	
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void windowOpened(WindowEvent e) {
		
		this.logicModelParking.checkParkingDataBase();
		
	}




	@Override
	public void windowClosing(WindowEvent e) {
	
		
	}




	@Override
	public void windowClosed(WindowEvent e) {

		
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

		if (e.getSource()== this.ahbParking.exitParking && e.getKeyCode()== 10) {

			this.ahbParking.exitParkingDialog.dispose();
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.ahbParking.dispose();
			
		}else
		
		
		if(e.getSource()== this.ahbParking.noExitParking && e.getKeyCode()== 10) {
			this.ahbParking.exitParkingDialog.dispose();
		}else 
		
			if (e.getSource()== this.ahbParking.noExitParking && e.getKeyCode()== 39) {
			this.ahbParking.exitParking.requestFocus();
		}else 
		
			if (e.getSource()== this.ahbParking.exitParking && e.getKeyCode()== 37) {
			this.ahbParking.noExitParking.requestFocus();
		}
		
		
	}




	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource()== this.ahbParking.exitParking) {

			this.ahbParking.exitParkingDialog.dispose();
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.ahbParking.dispose();
			
			
		}else
		
		
		if(e.getSource()== this.ahbParking.noExitParking ) {
			
			this.ahbParking.exitParkingDialog.dispose();
			
		}else
			
			
			if(e.getSource()== this.ahbParking.btnHome ) {
				this.bigDataAirportHotelBaselStartFrame.setVisible(true);
				this.ahbParking.dispose();
				
		}else
			
			
			if(e.getSource()== this.ahbParking.btnFitness ) {
				
				//Call the Fitness DataBase Application
				
		}else
			
			
			if(e.getSource()== this.ahbParking.btnFundsachen ) {
				
				//Call the Fitness Fundsachen Application
				
		}else
			
			
			if(e.getSource()== this.ahbParking.btnPhonebook ) {
				
				//Call the PhoneBook DataBase Application
				
		}else
			
			
			if(e.getSource()== this.ahbParking.btnLogout ) {
				
				
//				logoutApplication();
				
		}
		
	}
	
	
	
	
	
		

}
