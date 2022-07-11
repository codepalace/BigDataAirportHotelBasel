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

import javax.swing.UIManager;

import tech.codepalace.model.LogicModelNewFundsachen;
import tech.codepalace.view.frames.NewFundsachen;


/**
 * @description Controller Class for the NewFundSachen GUI
 * @author Antonio Estevez Gonzalez
 *
 */
public class NewFundsachenController implements ActionListener, WindowListener, FocusListener, MouseListener, KeyListener, ItemListener {
	
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
		
		
		this.newFundsachen.cancelFoundSachen.addMouseListener(this);
		this.newFundsachen.cancelFoundSachen.addActionListener(this);
		this.newFundsachen.cancelFoundSachen.addKeyListener(this);
		
		this.newFundsachen.saveFoundSachen.addActionListener(this);
		this.newFundsachen.saveFoundSachen.addKeyListener(this);
		
		
		//We add also FocusListener to the entries Boxes
		this.newFundsachen.datumItemFoundPlaceHolderTextField.addFocusListener(this);
		this.newFundsachen.fundItemsJTexfield.addFocusListener(this);
		this.newFundsachen.fundOrtJTextField.addFocusListener(this);
		this.newFundsachen.inhaberJTextField.addFocusListener(this);
		this.newFundsachen.kisteNummerJComboBox.addFocusListener(this);
		this.newFundsachen.kisteNameJTextField.addFocusListener(this);
		
		this.newFundsachen.kisteNummerJComboBox.addItemListener(this);
		
		this.newFundsachen.inhaberJTextField.addFocusListener(this);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.newFundsachen.jaJButton) {
			
			this.newFundsachen.dialog.dispose();
			this.newFundsachen.dispose();
		
		}else if(e.getSource()==this.newFundsachen.neinJButton) {
			
			this.logicModelNewFundSachen.setClosingNewFundsachen(false);
			this.newFundsachen.dialog.dispose();
			
			
		}else if(e.getSource()==this.newFundsachen.cancelFoundSachen) {
			
			this.newFundsachen.confirmClose();
			
		}
		
		else if(e.getSource()==this.newFundsachen.saveFoundSachen) {
			
			this.logicModelNewFundSachen.checkDateFormat();
			this.logicModelNewFundSachen.checkAllEntries();
			
		
			
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
		this.newFundsachen.inhaberJTextField.setText("Unbekannt");
		
	}

	@Override
	public void windowClosing(WindowEvent e) {

		//We set the value as true to avoid displaying an error message when the datumItemFoundPlaceHolderTextField Lost focus.
//		this.logicModelNewFundSachen.setClosingNewFundsachen(true);
		
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
		
		if(e.getSource()==this.newFundsachen.datumItemFoundPlaceHolderTextField) {
			
			
			this.newFundsachen.datumItemFoundPlaceHolderTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));

		}
		
		else if(e.getSource()==this.newFundsachen.fundItemsJTexfield) {
			
			this.newFundsachen.fundItemsJTexfield.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}
		
		else if(e.getSource()==this.newFundsachen.fundOrtJTextField) {
			
			this.newFundsachen.fundOrtJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}
		
		else if(e.getSource()== this.newFundsachen.inhaberJTextField) {
			
			this.newFundsachen.inhaberJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}
		
		else if(e.getSource()==this.newFundsachen.kisteNummerJComboBox) {
			
			this.newFundsachen.kisteNummerJComboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
		}
		
		else if(e.getSource()==this.newFundsachen.kisteNameJTextField) {
			
			this.newFundsachen.kisteNameJTextField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			
		}
		
		
		
		
		
		
		
		
		
		
		
	}

	
	
	@Override
	public void focusLost(FocusEvent e) {
		
		if(e.getSource()==this.newFundsachen.datumItemFoundPlaceHolderTextField) {

			
				//lost focus by datumItemFoundPlaceHolderTextField(Date was found the Lost and found item). then we check format.
				logicModelNewFundSachen.checkDateFormat();

		}
		
else if(e.getSource()==this.newFundsachen.inhaberJTextField) {
			
			
			
			if(this.newFundsachen.inhaberJTextField.getText().equals("")) {
				
				this.newFundsachen.inhaberJTextField.setText("Unbekannt");
			}
			
		}
		
		
else if(e.getSource()==this.newFundsachen.kisteNummerJComboBox) {
	
	this.logicModelNewFundSachen.checkKistenNummer();
	
}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getSource()==this.newFundsachen.cancelFoundSachen) {
			
			//In case we pressed the cancelFoundSachen JButton we call setClosingNewFunsachen to set value true.
			//So we do not have problems with the LostFocus by the datumItemFoundPlaceHolderTextField component.
			this.logicModelNewFundSachen.setClosingNewFundsachen(true);
		}
		
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
			
			
		}else if(e.getSource()==this.newFundsachen.cancelFoundSachen & e.getKeyCode()==10) {
			
			this.newFundsachen.confirmClose();
			
		}
		
		else if(e.getSource()==this.newFundsachen.saveFoundSachen && e.getKeyCode() == 10) {
			
			this.logicModelNewFundSachen.checkDateFormat();
			this.logicModelNewFundSachen.checkAllEntries();
			
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {}

	
	
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getSource()==this.newFundsachen.kisteNummerJComboBox) {
			
			this.logicModelNewFundSachen.checkKistenNummer();
		}
		
	}

}
