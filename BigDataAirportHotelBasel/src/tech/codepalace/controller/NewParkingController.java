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

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import tech.codepalace.model.LogicModelNewParking;
import tech.codepalace.view.frames.NewParking;

public class NewParkingController implements ActionListener, KeyListener, WindowListener, FocusListener, MouseListener{
	
	private NewParking newParking;
	private LogicModelNewParking logicModelNewParking;
	
	protected boolean closingNewParkingReservation = false;
	
	//Regex Format to evaluate the date Format.
	private  String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$";
	
	
	
	
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

		if(e.getSource()==this.newParking.ja) {
			this.newParking.dialog.dispose();
			this.newParking.dispose();
			
		}else if (e.getSource()==this.newParking.nein) {
			this.newParking.dialog.dispose();
			this.logicModelNewParking.setClosingNewParkingReservation(false);
		} 
		
		
		/*
		 * else if(e.getSource()==this.newParking.nein) {
			this.newParking.dialog.dispose();
		}else if (e.getSource()==this.newParking.ja) {
			this.newParking.dialog.dispose();
			this.newParking.dispose();
		}
		 */
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
			this.logicModelNewParking.setErrorDateDepartureMessageDelivered(false);
			this.logicModelNewParking.setErrorDateArrivalMessageDelivered(false);
			this.logicModelNewParking.checkAnreiseDateFormat();
			this.logicModelNewParking.checkAbreiseDateFormat();
			this.logicModelNewParking.checkAllEntries();
			
			
			
			
		}else if (e.getSource()==this.newParking.cancelParkingReservation) {
			this.closingNewParkingReservation = true;
			System.out.println("closingNewParkingReservation" + closingNewParkingReservation);
			this.newParking.confirmClose();
		
		}else if(e.getSource()==this.newParking.nein) {
			this.newParking.dialog.dispose();
			this.logicModelNewParking.setClosingNewParkingReservation(false);
		}else if (e.getSource()==this.newParking.ja) {
			this.newParking.dialog.dispose();
			this.newParking.dispose();
		}
	}

	
	
	
	@Override
	public void focusGained(FocusEvent e) {
		
		if(e.getSource()==this.newParking.anreiseDatumPlaceHolderTextField) {
			this.newParking.anreiseDatumPlaceHolderTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			this.logicModelNewParking.setErrorDateDepartureMessageDelivered(false);
			
		
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
					logicModelNewParking.setAnreiseFocus(false);
					
					

			
		}else if (e.getSource()==this.newParking.abreiseDatumPlaceholderTextField) {
			
				
					logicModelNewParking.checkAbreiseDateFormat();

		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.newParking.cancelParkingReservation) {
			this.logicModelNewParking.setClosingNewParkingReservation(true);
		}else if(e.getSource()==this.newParking.anreiseDatumPlaceHolderTextField){
			this.logicModelNewParking.setAnreiseFocus(true);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	


}
