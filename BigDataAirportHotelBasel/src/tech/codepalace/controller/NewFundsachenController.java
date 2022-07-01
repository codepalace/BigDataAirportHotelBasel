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

import tech.codepalace.model.LogicModelNewFundsachen;
import tech.codepalace.view.frames.NewFundsachen;


/**
 * @description Controller Class for the NewFundSachen GUI
 * @author Antonio Estevez Gonzalez
 *
 */
public class NewFundsachenController implements ActionListener, WindowListener, FocusListener, MouseListener, KeyListener {
	
	private NewFundsachen newFundsachen;
	private LogicModelNewFundsachen logicModelNewFundSachen;


	
	public NewFundsachenController(NewFundsachen newFundsachen, LogicModelNewFundsachen logicModelNewFundSachen) {
		
		this.newFundsachen = newFundsachen;
		this.logicModelNewFundSachen = logicModelNewFundSachen;
		
		this.newFundsachen.addWindowListener(this);
		
		this.newFundsachen.jaJButton.addActionListener(this);
		this.newFundsachen.jaJButton.addKeyListener(this);
		this.newFundsachen.neinJButton.addActionListener(this);
		this.newFundsachen.neinJButton.addKeyListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.newFundsachen.jaJButton) {
			
			this.newFundsachen.dialog.dispose();
			this.newFundsachen.dispose();
		
		}else if(e.getSource()==this.newFundsachen.neinJButton) {
			
			this.newFundsachen.dialog.dispose();
			
			
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {

		/*
		 * by JFrame closing we call newFundsachen.confirmClose Method to ask the user if he is really ready to close.
		 */
		this.newFundsachen.confirmClose();
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

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource()==this.newFundsachen.jaJButton  && e.getKeyCode()== 10) {
			
			this.newFundsachen.dialog.dispose();
			this.newFundsachen.dispose();
		
		}else if(e.getSource()==this.newFundsachen.neinJButton  && e.getKeyCode()== 10) {
			
			this.newFundsachen.dialog.dispose();
			
			
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {}

}
