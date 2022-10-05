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
		
		
		
		
	
		
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// btn_abbrechen(Cancel and Close the GUI)
		if(e.getSource()==this.dateChronologyCorrection.btn_abbrechen) {
		 
			//We need to return back the value we had before calling dateChronologyCorrection GUI
			
		}
		
		else
			//if btn_save_changes
			if(e.getSource()==this.dateChronologyCorrection.btn_save_changes) {
				
				/* We have to call to check if the new Date for the Departure is correct after the arrival date. 
				 * 
				 * if this is not the case we return back the value we had before calling the dateChronologCorrection GUI by 
				 * arrival and departure. We also display one message nothing has change and we can opt to ask for a new value or
				 * we just close dateChronologyCorrection and apply the old values so that the user has access again to the 
				 * JTable and click to modify again. 
				 */
				
			}
		
	}




	@Override
	public void keyTyped(KeyEvent e) {}




	@Override
	public void keyPressed(KeyEvent e) {
		
		//if btn_abbrechen(Cancel and Close the GUI)
		if(e.getSource()==this.dateChronologyCorrection.btn_abbrechen && e.getKeyCode()==10) {
			
			//We need to return back the value we had before calling dateChronologyCorrection GUI
			
		}
		
		else
			//if btn_save_changes
			if(e.getSource()==this.dateChronologyCorrection.btn_save_changes && e.getKeyCode() == 10) {
				
				/* We have to call to check if the new Date for the Departure is correct after the arrival date. 
				 * 
				 * if this is not the case we return back the value we had before calling the dateChronologCorrection GUI by 
				 * arrival and departure. We also display one message nothing has change and we can opt to ask for a new value or
				 * we just close dateChronologyCorrection and apply the old values so that the user has access again to the 
				 * JTable and click to modify again. 
				 * 
				 * 
				 */
				
				
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
			
		}
		
		else
			
			//if btn_save_changes
			if(e.getSource()==this.dateChronologyCorrection.btn_save_changes) {
				
			}
		
	}




	@Override
	public void focusLost(FocusEvent e) {
		
		//if btn_abbrechen
		if(e.getSource()==this.dateChronologyCorrection.btn_abbrechen) {
					
		}
				
			else
					
		//if btn_save_changes
		if(e.getSource()==this.dateChronologyCorrection.btn_save_changes) {
						
		}
		
	}

}
