package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.UIManager;

import tech.codepalace.model.LogicModelNewParking;
import tech.codepalace.view.frames.NewParking;

public class NewParkingController implements ActionListener, KeyListener, WindowListener, FocusListener{
	
	private NewParking newParking;
	private LogicModelNewParking logicModelNewParking;
	
	protected boolean closingNewParkingReservation = false;
	
	
	
	public NewParkingController(NewParking newParking, LogicModelNewParking logicModelNewParking) {
		this.newParking = newParking;
		this.logicModelNewParking = logicModelNewParking;
		
		this.newParking.addWindowListener(this);
		
		this.newParking.saveParkingReservation.addActionListener(this);
		this.newParking.cancelParkingReservation.addActionListener(this);
		this.newParking.cancelParkingReservation.addFocusListener(this);
		this.newParking.anreiseDatumPlaceHolderTextField.addFocusListener(this);
		this.newParking.abreiseDatumPlaceholderTextField.addFocusListener(this);
		
		this.newParking.buchungsNameJTextField.addFocusListener(this);
		this.newParking.autoKFZJTextField.addFocusListener(this);
		this.newParking.buchunsKanalJTextField.addFocusListener(this);
		
		
		
		
		
		
		
		
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
		
	}

	@Override
	public void windowClosing(WindowEvent e) {

		this.newParking.confirmClose();
		
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

		if(e.getSource()== this.newParking.saveParkingReservation) {
			this.logicModelNewParking.setReadyToSaveArrival(true);
			this.logicModelNewParking.setReadyToSaveDeparture(true);
			this.logicModelNewParking.checkAnreiseDateFormat();
			this.logicModelNewParking.checkAbreiseDateFormat();
			this.logicModelNewParking.checkAllEntries();
			
			
			
			
		}else if (e.getSource()==this.newParking.cancelParkingReservation) {
			this.closingNewParkingReservation = true;
			System.out.println("closingNewParkingReservation" + closingNewParkingReservation);
			this.newParking.confirmClose();
		} 
	}

	
	
	
	@Override
	public void focusGained(FocusEvent e) {
		
		if(e.getSource()==this.newParking.anreiseDatumPlaceHolderTextField) {
			this.newParking.anreiseDatumPlaceHolderTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		
		}else if (e.getSource()==this.newParking.abreiseDatumPlaceholderTextField) {
			this.newParking.abreiseDatumPlaceholderTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}else if (e.getSource()==this.newParking.buchungsNameJTextField) {
			this.newParking.buchungsNameJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}else if(e.getSource()==this.newParking.autoKFZJTextField) {
			this.newParking.autoKFZJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}else if (e.getSource()==this.newParking.buchunsKanalJTextField) {
			this.newParking.buchunsKanalJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}

		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
		if(e.getSource()==this.newParking.anreiseDatumPlaceHolderTextField) {
			

					logicModelNewParking.checkAnreiseDateFormat();

			
		}else if (e.getSource()==this.newParking.abreiseDatumPlaceholderTextField) {
			

					logicModelNewParking.checkAbreiseDateFormat();
		
		}
		
	}
	


}
