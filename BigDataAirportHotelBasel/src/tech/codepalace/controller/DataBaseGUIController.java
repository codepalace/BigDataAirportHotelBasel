package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelFundSachen;
import tech.codepalace.model.LogicModelParking;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.DataBaseGUI;


/**
 * 
 * @author tonimacaroni
 * @description Controller Class for DataBaseGUI.
 * <p>This class receives as an argument BigDataAirportHotelBaselStartFrame Object so we can call this object anyTime back.</p>
 * <p>The DataBaseGUI Object is the class will be controlled by this one.</p>
 * <p>The LogicModelParking so we can call anyTime for creating a new Parking-Reservation for example.</p>
 *
 */
public class DataBaseGUIController implements ActionListener, KeyListener, WindowListener {
	

	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	private DataBaseGUI dataBaseGUI;
	private LogicModelParking logicModelParking;
	private LogicModelFundSachen logicModelFundSachen;

	
	public DataBaseGUIController(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, 
			DataBaseGUI dataBaseGUI, LogicModelParking logicModelParking, LogicModelFundSachen logicModelFundSachen) {
		
		//We set the values to the Objects
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.dataBaseGUI = dataBaseGUI;
		this.logicModelParking = logicModelParking;
		this.logicModelFundSachen = logicModelFundSachen;
		
		
		//Add Listener
		this.dataBaseGUI.addWindowListener(this);
		
		this.dataBaseGUI.btnHome.addActionListener(this);
		
		this.dataBaseGUI.btnExitDBGUI.addActionListener(this);
		this.dataBaseGUI.btnNoExitDBGUI.addActionListener(this);
		
		this.dataBaseGUI.btnNewParking.addActionListener(this);
		this.dataBaseGUI.btnNewParking.addKeyListener(this);
		
		this.dataBaseGUI.btnNewFundsachen.addActionListener(this);
		this.dataBaseGUI.btnNewFundsachen.addKeyListener(this);
		
		
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==this.dataBaseGUI.btnHome) {
		
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.dispose();
			
		} else if(e.getSource()== this.dataBaseGUI.btnExitDBGUI) {
			
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.exitDBGUI.dispose();
			this.dataBaseGUI.dispose();
			
		} else if (e.getSource()== this.dataBaseGUI.btnNoExitDBGUI) {
			
			this.dataBaseGUI.exitDBGUI.dispose();
		
		} else if(e.getSource()==this.dataBaseGUI.btnNewParking) {
			
			this.logicModelParking.createNewParkingReservation(dataBaseGUI);
		
		} else if(e.getSource()==this.dataBaseGUI.btnNewFundsachen) {
			
			this.logicModelFundSachen.enterNewFoundsachenEntries(dataBaseGUI);
		}
		
		
		
	}

	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource()==this.dataBaseGUI.btnExitDBGUI && e.getKeyCode() ==10) {
			
		}

		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	
	
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		
		this.bigDataAirportHotelBaselStartFrame.dispose();

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
	
	
	
	
	
	

}
