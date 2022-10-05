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
	
		
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}




	@Override
	public void keyTyped(KeyEvent e) {}




	@Override
	public void keyPressed(KeyEvent e) {
		
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
		
		
	}




	@Override
	public void focusLost(FocusEvent e) {
		
		
	}

}
