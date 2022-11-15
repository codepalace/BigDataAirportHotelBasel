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
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.newFitnessAbo.cancelNewFitnessAbo) {
			this.logicModelNewFitnessAbo.closeNeueFitnessAbo();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	
	
	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
	}

	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

}
