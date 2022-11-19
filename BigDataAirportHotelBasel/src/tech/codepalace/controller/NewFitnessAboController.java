package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tech.codepalace.model.LogicModelNewFitnessAbo;
import tech.codepalace.view.frames.NewFitnessAbo;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Controller Class for the NewFitnessAbo
 *
 */
public class NewFitnessAboController implements ActionListener, WindowListener, FocusListener, MouseListener, KeyListener, ItemListener  {

	private NewFitnessAbo newFitnessAbo;
	private LogicModelNewFitnessAbo logicModelNewFitnessAbo;
	
	public NewFitnessAboController(NewFitnessAbo newFitnessAbo, LogicModelNewFitnessAbo logicModelNewFitnessAbo) {
		
		this.newFitnessAbo = newFitnessAbo;
		this.logicModelNewFitnessAbo = logicModelNewFitnessAbo;
		
		this.newFitnessAbo.addWindowListener(this);
		
		//Add actionsListeners
		this.newFitnessAbo.cancelNewFitnessAbo.addActionListener(this);
		
		//Add FocusListenrs
		this.newFitnessAbo.saveNewFitnessAbo.addFocusListener(this);
		this.newFitnessAbo.cancelNewFitnessAbo.addFocusListener(this);
		this.newFitnessAbo.nameJTextfield.addFocusListener(this);
		this.newFitnessAbo.eintrittsdatumPlaceHolderTextField.addFocusListener(this);
		this.newFitnessAbo.anzahlDerMonat.addFocusListener(this);
		
		
		//Add MouseListeners
		this.newFitnessAbo.cancelNewFitnessAbo.addMouseListener(this);
		
		//Add ItemListener
		this.newFitnessAbo.anzahlDerMonat.addItemListener(this);
		

	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.newFitnessAbo.cancelNewFitnessAbo) {
			
			this.logicModelNewFitnessAbo.closeNeueFitnessAbo();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
		this.logicModelNewFitnessAbo.setTryingToCancel(false);
	}

	@Override
	public void windowClosing(WindowEvent e) {
			
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
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getSource()==this.newFitnessAbo.cancelNewFitnessAbo) {
			this.logicModelNewFitnessAbo.setTryingToCancel(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	
	
	@Override
	public void focusGained(FocusEvent e) {
		
		if(e.getSource()==this.newFitnessAbo.saveNewFitnessAbo) {
			this.newFitnessAbo.saveNewFitnessAbo.setOpacity(1);
		}
		
		else if(e.getSource()==this.newFitnessAbo.cancelNewFitnessAbo) {
			this.newFitnessAbo.cancelNewFitnessAbo.setOpacity(1);

		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		
		if(e.getSource()==this.newFitnessAbo.saveNewFitnessAbo) {
			this.newFitnessAbo.saveNewFitnessAbo.setOpacity(0.5f);
		}
		
		else if(e.getSource()==this.newFitnessAbo.cancelNewFitnessAbo) {
			this.newFitnessAbo.cancelNewFitnessAbo.setOpacity(0.5f);
		}
		
		
		
		else if(e.getSource()==this.newFitnessAbo.nameJTextfield && !this.logicModelNewFitnessAbo.isTryingToCancel()) {
			
			//if nameJTextField isEmpty we display the corresponding error message.
			if(this.newFitnessAbo.nameJTextfield.getText().isEmpty()) {
				this.logicModelNewFitnessAbo.displayErrorMessage("Es wurden keine Daten eingegeben", "Sie müssen einen Namen eingeben",
						this.newFitnessAbo.nameJTextfield);
					
				//Set the value nameLostFocusWithError as true
				this.logicModelNewFitnessAbo.setNameLostFocusWithError(true);

			} else //Otherwise if is not empty 
			
			//Will be evaluated if the name entered by the user is minor than 3.
			//if minor than 3, the corresponding error message will be displayed
			if(this.newFitnessAbo.nameJTextfield.getText().length()<3) {
				
			
				this.logicModelNewFitnessAbo.displayErrorMessage("Name zu kurz", 
						"Der eingegebene Name muss aus mindestens drei Buchstaben bestehen",
						this.newFitnessAbo.nameJTextfield);
				
			    //set the value of nameLostFocusWithError as true. This helps us to play with the loss of focus of the other elements
				this.logicModelNewFitnessAbo.setNameLostFocusWithError(true);
			
			}else {
				//Otherwise we set the value as false.
				this.logicModelNewFitnessAbo.setNameLostFocusWithError(false);
			}


			
		}
		//if eintrittsdatumPlaceHolderTextField lost focus and the other conditions....
		else if(e.getSource()==this.newFitnessAbo.eintrittsdatumPlaceHolderTextField && 
				!this.logicModelNewFitnessAbo.isTryingToCancel() && !this.logicModelNewFitnessAbo.isNameLostFocusWithError()) {
			
			if(!this.logicModelNewFitnessAbo.checkDateFormat(this.newFitnessAbo.eintrittsdatumPlaceHolderTextField.getText(), 
															 this.newFitnessAbo.eintrittsdatumPlaceHolderTextField, 
															 "geben Sie ein korrektes Format für das Datum ein(dd.MM.JJJ)")) {
				
				
				//Set the value that it was true error by loosing the focus
				this.logicModelNewFitnessAbo.setStartDateLostFocusWithError(true);
				
				//We indicate the value of startDateAndNumberOfMonthAreValid as false. So we can not calculate the Fitness Cost.
				this.logicModelNewFitnessAbo.setStartDateAndNumberOfMonthAreValid(false);
				
			}else {
				
				/*
				 * Otherwise: - We set the value lost focus with error as false(every data was correct in this element).
				 * 			  - Set the startDateAndNumberOfMonthAreValid as true.
				 * 			  - We call to calculateFinessCost
				 */
				this.logicModelNewFitnessAbo.setStartDateLostFocusWithError(false);
				this.logicModelNewFitnessAbo.setStartDateAndNumberOfMonthAreValid(true);
				this.logicModelNewFitnessAbo.calculateFitnessCost();
			}
			
		}
		
		    //if anzahlDerMonat-Vertragslaufzeit(Contract period)
		else if(e.getSource()==this.newFitnessAbo.anzahlDerMonat &&
				!this.logicModelNewFitnessAbo.isTryingToCancel() && !this.logicModelNewFitnessAbo.isNameLostFocusWithError()
				&& !this.logicModelNewFitnessAbo.isStartDateLostFocusWithError() 
				) {
			
		
			//if not any options was selected(stay the predefined index(wählen Sie die Vertragslaufzeit) we display error
			if(this.newFitnessAbo.anzahlDerMonat.getSelectedItem().toString().equalsIgnoreCase("wählen Sie die Vertragslaufzeit")) {
				
				this.logicModelNewFitnessAbo.displayErrorMessage("Die Vertragslaufzeit wurde nicht ausgewählt", 
						"Sie müssen eine Vertragslaufzeit wählen",
						this.newFitnessAbo.anzahlDerMonat);
				
				this.logicModelNewFitnessAbo.setStartDateAndNumberOfMonthAreValid(false);
				
			}else {
				
				if(this.logicModelNewFitnessAbo.isStartDateAndNumberOfMonthAreValid()) {
					
					//Time to calculate the cost of the Fitness contract depending how many month was selected by the User.
					this.logicModelNewFitnessAbo.calculateFitnessCost();
				}
			}
			
		}
		
		
		
		
	
	}

	
	
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {

		 //If the user selected one item
		 if(e.getStateChange() == ItemEvent.SELECTED) {
			 
			 //First it will be evaluated the start Date of the fitness subscription contract. We check if eintrittsdatumPlaceHolderTextField isEmpty
			 if(this.newFitnessAbo.eintrittsdatumPlaceHolderTextField.getText().isEmpty()) {

				 
				 //Is Empty then we check Date format to display the error message
				 this.logicModelNewFitnessAbo.checkDateFormat(this.newFitnessAbo.eintrittsdatumPlaceHolderTextField.getText(), 
						 this.newFitnessAbo.eintrittsdatumPlaceHolderTextField, 
						 "geben Sie ein korrektes Format für das Datum ein(dd.MM.JJJ) item changed");
				 
				 this.logicModelNewFitnessAbo.setStartDateLostFocusWithError(true);
				 this.logicModelNewFitnessAbo.setStartDateAndNumberOfMonthAreValid(false);
				
				 
			 }else {
				 
				 if(this.newFitnessAbo.anzahlDerMonat.getSelectedIndex()==0) {
					 this.logicModelNewFitnessAbo.setStartDateAndNumberOfMonthAreValid(false);
				 }else {
					 this.logicModelNewFitnessAbo.setStartDateAndNumberOfMonthAreValid(true);
					 this.logicModelNewFitnessAbo.calculateFitnessCost();
				 }
				
			 }
			 
			 
			 }
			 
			 
			 

			 
		 }

	



}
	
	
	
	


