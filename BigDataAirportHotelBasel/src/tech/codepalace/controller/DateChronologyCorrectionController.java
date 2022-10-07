package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelDateChronologyCorrection;
import tech.codepalace.view.frames.DateChronologyCorrection;

public class DateChronologyCorrectionController implements ActionListener, KeyListener, WindowListener, FocusListener {
	
	private DateChronologyCorrection dateChronologyCorrection;
	LogicModelDateChronologyCorrection logicDateChronologyCorrection;
	
	public DateChronologyCorrectionController(DateChronologyCorrection dateChronologyCorrection, 
									LogicModelDateChronologyCorrection logicModelDateChronologyCorrection ) {
		
		this.dateChronologyCorrection = dateChronologyCorrection;
		this.logicDateChronologyCorrection = logicModelDateChronologyCorrection;
		
		//Add WindowListener to the dateChronologyCorrection GUI
		this.dateChronologyCorrection.addWindowListener(this);
		
		//Add KeyListener to the MyButtons
		this.dateChronologyCorrection.btn_abbrechen.addKeyListener(this);
		this.dateChronologyCorrection.btn_save_changes.addKeyListener(this);
		
		//Add ActionListener to the MyButtons
		this.dateChronologyCorrection.btn_abbrechen.addActionListener(this);
		this.dateChronologyCorrection.btn_save_changes.addActionListener(this);
		
		//Add FocusListener to the MyButtons
		this.dateChronologyCorrection.btn_abbrechen.addFocusListener(this);
		this.dateChronologyCorrection.btn_save_changes.addFocusListener(this);
		
		//Add KeyListener to the dateJTextField
		this.dateChronologyCorrection.dateJTextField.addKeyListener(this);
		
		
		
		
	
		
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// btn_abbrechen(Cancel and Close the GUI)
		if(e.getSource()==this.dateChronologyCorrection.btn_abbrechen) {
		 
			//User cancel the Dates modifications we reset the Dates back
			this.logicDateChronologyCorrection.resetDates();
			
			//Dispose the GUI
			this.dateChronologyCorrection.dispose();
		}
		
		else
			//if btn_save_changes
			if(e.getSource()==this.dateChronologyCorrection.btn_save_changes) {
				
				
			  /*
			   * If checkDateFormatBeforeSaveDate return false Departure Date has a correct format dd.mm.yyyy. 
			   * If not the case we return the focus to the dateJTextField and set the text to ""
			   * 
			   * One error message will be displayed.
			   */
			  if(!this.logicDateChronologyCorrection.checkDateFormatBeforeSaveData(this.dateChronologyCorrection.dateJTextField.getText()
					  , "Abreisedatum", "")) {
				  
				  //Set dateJTextField text value to ""
				  this.dateChronologyCorrection.dateJTextField.setText("");
				  
				  //We return back the focus
				  this.dateChronologyCorrection.dateJTextField.requestFocus();
				  
			  }
			  //Date format is correct
			  else {
				  
				  this.logicDateChronologyCorrection.setDepartureDate(this.dateChronologyCorrection.dateJTextField.getText());
			  }
				
			}
		
	}




	@Override
	public void keyTyped(KeyEvent e) {}




	@Override
	public void keyPressed(KeyEvent e) {
		
		//if btn_abbrechen(Cancel and Close the GUI)
		if(e.getSource()==this.dateChronologyCorrection.btn_abbrechen && e.getKeyCode()==10) {
			
			//User cancel the Dates modifications we reset the Dates back
			this.logicDateChronologyCorrection.resetDates();
			
			//Dispose the GUI
			this.dateChronologyCorrection.dispose();
			
		}
		
		else
			//if btn_save_changes
			if(e.getSource()==this.dateChronologyCorrection.btn_save_changes && e.getKeyCode() == 10) {
				
				 /*
				   * If checkDateFormatBeforeSaveDate return false Departure Date has a correct format dd.mm.yyyy. 
				   * If not the case we return the focus to the dateJTextField and set the text to ""
				   * 
				   * One error message will be displayed.
				   */
				  if(!this.logicDateChronologyCorrection.checkDateFormatBeforeSaveData(this.dateChronologyCorrection.dateJTextField.getText()
						  , "Abreisedatum", "")) {
					  
					  //Set dateJTextField text value to ""
					  this.dateChronologyCorrection.dateJTextField.setText("");
					  
					  //We return back the focus
					  this.dateChronologyCorrection.dateJTextField.requestFocus();
					  
				  }
				  //Date format is correct
				  else {
					  
					  this.logicDateChronologyCorrection.setDepartureDate(this.dateChronologyCorrection.dateJTextField.getText());
				  }
				
				
			}
		
			else
				if(e.getSource()==this.dateChronologyCorrection.dateJTextField && e.getKeyCode() == 10) {
					
					 /*
					   * If checkDateFormatBeforeSaveDate return false Departure Date has a correct format dd.mm.yyyy. 
					   * If not the case we return the focus to the dateJTextField and set the text to ""
					   * 
					   * One error message will be displayed.
					   */
					  if(!this.logicDateChronologyCorrection.checkDateFormatBeforeSaveData(this.dateChronologyCorrection.dateJTextField.getText()
							  , "Abreisedatum", "")) {
						  
						  //Set dateJTextField text value to ""
						  this.dateChronologyCorrection.dateJTextField.setText("");
						  
						  //We return back the focus
						  this.dateChronologyCorrection.dateJTextField.requestFocus();
						  
					  }
					  //Date format is correct
					  else {
						  
						  this.logicDateChronologyCorrection.setDepartureDate(this.dateChronologyCorrection.dateJTextField.getText());
					  }
				}
	}




	@Override
	public void keyReleased(KeyEvent e) {}




	@Override
	public void windowOpened(WindowEvent e) {}




	@Override
	public void windowClosing(WindowEvent e) {
		//by pressing the close Window call confirmClose Method by dateChronologyCorrection GUI.
		this.dateChronologyCorrection.confirmClose();
		this.logicDateChronologyCorrection.resetDates();
		
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
	public void focusGained(FocusEvent e) {
		
		//if btn_abbrechen
		if(e.getSource()==this.dateChronologyCorrection.btn_abbrechen) {
			
			//Set the Opacity to 1
			this.dateChronologyCorrection.btn_abbrechen.setOpacity(1);
		}
		
		else
			
			//if btn_save_changes
			if(e.getSource()==this.dateChronologyCorrection.btn_save_changes) {
				
				//Set the Opacity to 1
				this.dateChronologyCorrection.btn_save_changes.setOpacity(1);
				
			}
		
	}




	@Override
	public void focusLost(FocusEvent e) {
		
		//if btn_abbrechen
		if(e.getSource()==this.dateChronologyCorrection.btn_abbrechen) {
			
			//Set the Opacity back to 0.5 
			this.dateChronologyCorrection.btn_abbrechen.setOpacity(0.5f);
			
		}
				
			else
					
		//if btn_save_changes
		if(e.getSource()==this.dateChronologyCorrection.btn_save_changes) {
			
			//Set the Opacity back to 0.5 
			this.dateChronologyCorrection.btn_save_changes.setOpacity(0.5f);
						
		}
		
	}

}
