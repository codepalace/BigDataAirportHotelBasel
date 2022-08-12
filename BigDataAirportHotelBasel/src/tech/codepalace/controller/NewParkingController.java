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
		this.newParking.saveParkingReservation.addKeyListener(this);
		this.newParking.saveParkingReservation.addFocusListener(this);
		
		this.newParking.cancelParkingReservation.addActionListener(this);
		this.newParking.cancelParkingReservation.addFocusListener(this);
		this.newParking.cancelParkingReservation.addKeyListener(this);
		
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
			
			//Set the readyToCloseNewParkingReservation to false
			this.logicModelNewParking.setReadyToCloseNewParkingReservation(false);
		
		}else if (e.getSource()==this.newParking.saveParkingReservation && e.getKeyCode()==10) {
			/*
			 * the user pressed the saveParkingReservation button, we establish that the error messages have not been delivered yet. 
			 * 
			 * This helps us to check the dates of arrival and departure and avoid warning messages when the loss of focus occurs by arrival and departure entry boxes.
			 */
	
			
			this.logicModelNewParking.setEntryCompleted(false);
			
			//We call to check if all the entries are filled correctly.
			this.logicModelNewParking.checkAllEntries();
			
		
		}else if(e.getSource()==this.newParking.cancelParkingReservation && e.getKeyCode()==10) {
			
			/*
			 * The user pressed canceParkingReservation. We call the confirClose Method
			 */
			this.newParking.confirmClose();
			
		}
		
		
	
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()== this.newParking.saveParkingReservation) {


			/* We set the Entries completed as false when the user pressed to the Button to save Parking in DataBase.
			 * 
			 * this is useful because in case the user delete some entries before saving the information and after we call to save then Data again pressing the Save Button 
			 * we can always start from zero supposing that all the entries are not filled out completed yet. 
			 * 
			 * After that we can call to checkAllEntries.
			 * 
			 */
			this.logicModelNewParking.setEntryCompleted(false);
			
			//We call to check if all the entries are filled out correctly.
			this.logicModelNewParking.checkAllEntries();

			
		}else if (e.getSource()==this.newParking.cancelParkingReservation) {

			/*
			 * The user pressed canceParkingReservation. We call the confirClose Method
			 */
			this.newParking.confirmClose();
		
		}else if(e.getSource()==this.newParking.nein) {
			
			this.logicModelNewParking.setReadyToCloseNewParkingReservation(false);
			
			 switch (this.logicModelNewParking.getElementHadFocus()) {
				 
				
				case "anreiseDatumPlaceHolderTextField":
					
					this.newParking.anreiseDatumPlaceHolderTextField.requestFocus();
					this.newParking.dialog.dispose();
					break;

				default:
					break;
			}
			
			/*
			 * the user does not want to close the edit dialog box. We close only the dialog JDialog alert. We also set closingNewParkingReservation to false.
			 */
			this.newParking.dialog.dispose();
			this.logicModelNewParking.setReadyToCloseNewParkingReservation(false);
			
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
				
			//reset the original border for the element
			this.newParking.anreiseDatumPlaceHolderTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
			//We set readyToCloseNewParkingReservation to initial value false.
			this.logicModelNewParking.setReadyToCloseNewParkingReservation(false);
			
			//Set anreiseOK to initial value false
			this.logicModelNewParking.setAnreiseOk(false);
			
			//Set anreiseLocalDate to initial value null
			this.logicModelNewParking.setAnreiseLocalDate(null);

		}
		
		else if (e.getSource()==this.newParking.abreiseDatumPlaceholderTextField) {
			
			//reset the original border for the element
			this.newParking.abreiseDatumPlaceholderTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
			//We set readyToCloseNewParkingReservation to initial value false.
			this.logicModelNewParking.setReadyToCloseNewParkingReservation(false);
			
			this.logicModelNewParking.setAbreiseOK(false);
			
			//We set the abreiseLocalDate initial value to null
			this.logicModelNewParking.setAbreiseLocalDate(null);

			/*
			 * In case everything is NOT OK by anreiseOK value == false
			 */
			if(!this.logicModelNewParking.isAnreiseOK()) {
				
				/* We do not keep the focus by abreiseDatumPlaceholderTextField it will be send it back to anreiseDatumPlaceHolderTextField.
				 * We call anreiseDatumPlaceHolderTextField.requestFocus to set the Mouse cursor to this element below.
				 */
				this.newParking.anreiseDatumPlaceHolderTextField.requestFocus();
			}
			
		}else if (e.getSource()==this.newParking.buchungsNameJTextField) {
			this.newParking.buchungsNameJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}else if(e.getSource()==this.newParking.autoKFZJTextField) {
			this.newParking.autoKFZJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}else if (e.getSource()==this.newParking.buchunsKanalJTextField) {
			this.newParking.buchunsKanalJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}
		
		//Setting the opacity value to 1 to MyButton
		else if(e.getSource()==this.newParking.saveParkingReservation) {
			
			this.newParking.saveParkingReservation.setOpacity(1);
		}
		
		else if(e.getSource()==this.newParking.cancelParkingReservation) {
			
			this.newParking.cancelParkingReservation.setOpacity(1);
		}

		
	}
	
	
	

	@Override
	public void focusLost(FocusEvent e) {
		
		if(e.getSource()==this.newParking.anreiseDatumPlaceHolderTextField) {
			
			//In case the element anreiseDatumPlaceHolderTextField lost focus we checkAnreiseDate(Arrival Date Format...)
			this.logicModelNewParking.checkAnreiseDate();
			

		}
		//In case the element abreiseDatumPlaceholderTextField lost Focus we checkAbreiseDate(Departure Date Format....)
		else if (e.getSource()==this.newParking.abreiseDatumPlaceholderTextField) {
			

			this.logicModelNewParking.checkAbreiseDate();
			
	
		}
		
else if(e.getSource()==this.newParking.saveParkingReservation) {
			
			//Giving back the Opacity initial value to MyButton
			this.newParking.saveParkingReservation.setOpacity(0.5f);
		}
		
		else if(e.getSource()==this.newParking.cancelParkingReservation) {
			
			this.newParking.cancelParkingReservation.setOpacity(0.5f);
		}
		
	}
	
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) {}

	
	@Override
	public void mousePressed(MouseEvent e) {
		
		//In case we pressed the cancelParkingReservation button we set the closingNewParkingReservation (true)
		if(e.getSource()==this.newParking.cancelParkingReservation) {
			

			this.logicModelNewParking.setReadyToCloseNewParkingReservation(true);

		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
	
	  //If the Mouse Entered by cancelParkingReservation JButton
	  if(e.getSource()==this.newParking.cancelParkingReservation) {
		  
		  //Evaluate if anreiseDatumPlaceHolderTextField has the Focus
		  if(this.newParking.anreiseDatumPlaceHolderTextField.hasFocus()) {
				
			    /*
			     * We set one Value String with the Element name has actually the Focus so we can use this information in case type error not correct to 
			     * return the focus to this element.
			     */
				this.logicModelNewParking.setElementHadFocus("anreiseDatumPlaceHolderTextField");
			}
			
		    //in case abreiseDatumPlaceholderTextField
			else if(this.newParking.abreiseDatumPlaceholderTextField.hasFocus()) {
				this.logicModelNewParking.setElementHadFocus("abreiseDatumPlaceholderTextField");
			}
		  
		   /*
		    * We set the value readyToCloseNewParkingReservation as true so we don't have any Error Message or alert when anreiseDatumPlaceHolderTextField or
		    * when abreiseDAtumPlaceHolderTextField lost Focus.
		    */
			this.logicModelNewParking.setReadyToCloseNewParkingReservation(true);
	  }
		
		
		
		
	}

	
	@Override
	public void mouseExited(MouseEvent e) {
		
		 //If the Mouse Exited by cancelParkingReservation JButton
		  if(e.getSource()==this.newParking.cancelParkingReservation) {
			  
		//We set element had focus
		if(this.newParking.anreiseDatumPlaceHolderTextField.hasFocus()) {
		
			this.logicModelNewParking.setElementHadFocus("anreiseDatumPlaceHolderTextField");
		}
		
		else if(this.newParking.abreiseDatumPlaceholderTextField.hasFocus()) {
			this.logicModelNewParking.setElementHadFocus("abreiseDatumPlaceholderTextField");
		}
		
		//And here we are not ready to close the GUI also we set the value as false.
		this.logicModelNewParking.setReadyToCloseNewParkingReservation(false);
		
	 }
	}
	


}
