package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.UIManager;

import tech.codepalace.model.LogicModelNewParking;
import tech.codepalace.view.frames.NewParking;

public class NewParkingController implements ActionListener, KeyListener, WindowListener, FocusListener, MouseListener{
	
	private NewParking newParking;
	private LogicModelNewParking logicModelNewParking;
	
	
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
		
		this.newParking.cancelParkingReservation.addMouseListener(this);
		
		
		this.newParking.ja.addActionListener(this);
		this.newParking.ja.addKeyListener(this);
		this.newParking.nein.addActionListener(this);
		this.newParking.nein.addKeyListener(this);
		
		
		this.newParking.anreiseDatumPlaceHolderTextField.addMouseListener(this);
		

	}

	
	
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		
		
	}

	@Override
	public void windowClosing(WindowEvent e) {

		/*
		 * by JFrame closing we call newParking.confirmClose Method to ask the user if he is really ready to close.
		 */
		this.newParking.confirmClose();
		
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

		if(e.getSource()==this.newParking.ja  && e.getKeyCode()== 10) {
			//We close the JDialog dialog message and the newParking class extended from JDialog.
			this.newParking.dialog.dispose();
			this.newParking.dispose();
			
		}else if (e.getSource()==this.newParking.nein && e.getKeyCode()== 10) {
			
			//the user keeps editing, we keep the dialog open
			this.newParking.dialog.dispose();
			
			//Set the closingNewParkingReservation to false
			this.logicModelNewParking.setClosingNewParkingReservation(false);
		
		}
		
	
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()== this.newParking.saveParkingReservation) {

			/*
			 * the user presses the saveParkingReservation button, we establish that the error messages have not been delivered yet. 
			 * 
			 * This helps us to check the dates of arrival and departure and avoid warning messages when the loss of focus occurs by arrival and departure entry boxes.
			 */
			this.logicModelNewParking.setErrorDateDepartureMessageDelivered(false);
			this.logicModelNewParking.setErrorDateArrivalMessageDelivered(false);
			
			//We call to check Arrival Date and Departure Date.
			this.logicModelNewParking.checkAnreiseDateFormat();
			this.logicModelNewParking.checkAbreiseDateFormat();
			
			//We call to check if all the entries are filled correctly.
			this.logicModelNewParking.checkAllEntries();
			
			
			
			
		}else if (e.getSource()==this.newParking.cancelParkingReservation) {
			/*
			 * The user pressed canceParkingReservation. We call the confirClose Method
			 */
			this.newParking.confirmClose();
		
		}else if(e.getSource()==this.newParking.nein) {
			/*
			 * the user does not want to close the edit dialog box. We close only the dialog JDialog alert. We also set closingNewParkingReservation to false.
			 */
			this.newParking.dialog.dispose();
			this.logicModelNewParking.setClosingNewParkingReservation(false);
			
		}else if (e.getSource()==this.newParking.ja) {
			
			/*
			 * The user wants to close, then we close everything concerning the new reservation entry
			 */
			this.newParking.dialog.dispose();
			this.newParking.dispose();
		}
	}

	
	
	
	
	
	@Override
	public void focusGained(FocusEvent e) {
		
		
		//In case the focus is on newParking.anreiseDatumPlaceHolderTextField for the Arrival Date
		if(e.getSource()==this.newParking.anreiseDatumPlaceHolderTextField) {
			
			//We set errorDateDepartureMessageDelivered to false
			this.logicModelNewParking.setErrorDateDepartureMessageDelivered(false);	
		}
		
		else if (e.getSource()==this.newParking.abreiseDatumPlaceholderTextField) {
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
			
					//lost focus by Arrival then we call checkAnreiseDateFormat
					logicModelNewParking.checkAnreiseDateFormat();
					
					//We set the focus for Arrival to false if we do not, when the arrival date is later than the departure date, it throws an error message only once.
					//To avoid this and that we always get an error message in the event that the arrival date is later than the departure date, we set the anreiseFocus value to false.
					logicModelNewParking.setAnreiseFocus(false);

			
		}else if (e.getSource()==this.newParking.abreiseDatumPlaceholderTextField) {
			
					//We call to check the Departure Date
					logicModelNewParking.checkAbreiseDateFormat();

		}
		
	}
	
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) {}

	
	@Override
	public void mousePressed(MouseEvent e) {
		
		//In case we pressed the cancelParkingReservation button we set the closingNewParkingReservation (true)
		if(e.getSource()==this.newParking.cancelParkingReservation) {
			this.logicModelNewParking.setClosingNewParkingReservation(true);
		}else if(e.getSource()==this.newParking.anreiseDatumPlaceHolderTextField){
			//in this case we set the arrival date focus true.
			this.logicModelNewParking.setAnreiseFocus(true);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	


}
