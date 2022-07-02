package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFundsachen;


/**
 * @description Logic Class for the NewFundsachen GUI.
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelNewFundsachen {
	
	private DataBaseGUI dataBaseGUI;
	private NewFundsachen newFundsachen; 
	private UserAHB userAHB;
	private Loading loading;
	
	public JDialog errorDateLostAndFound;
	
	private JButton okButtonErrorDateLostAndFound  = new JButton("OK");;
	
	private JLabel messageErrorDateLostAndFound;
	
	private JPanel panelErrorDateLostAndFound;
	
	
	private Object[] optionButtonErrorDateLostAndFound = {this.okButtonErrorDateLostAndFound};
	
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	//Variable to check if the user has pressed clothing NewFundSachen.
	private boolean closingNewFundsachen = false;
	
	
	
	

	public LogicModelNewFundsachen(DataBaseGUI dataBaseGUI, NewFundsachen newFundsachen, UserAHB userAHB, Loading loading) {
		
		this.dataBaseGUI = dataBaseGUI;
		this.newFundsachen = newFundsachen;
		this.userAHB = userAHB;
		this.loading = loading;
		
		
	}
	
	
	
	public void checkDateFormat() {
		
		
		//Regex Format to evaluate the date Format.
		String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$";
		
		if(!Pattern.matches(formatDateRegex, this.newFundsachen.datumItemFoundPlaceHolderTextField.getText())) {
			
			this.messageErrorDateLostAndFound = new JLabel("Sie haben ein falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy");
			
			
			this.panelErrorDateLostAndFound = new JPanel(new BorderLayout());
			
			this.panelErrorDateLostAndFound.add(messageErrorDateLostAndFound, BorderLayout.CENTER);
			
			
			
			if(!closingNewFundsachen) {
				
				
				this.okButtonErrorDateLostAndFound.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						errorDateLostAndFound.dispose();
						
					}
				});
				
				
				this.okButtonErrorDateLostAndFound.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {}

					@Override
					public void keyPressed(KeyEvent e) {
						
						errorDateLostAndFound.dispose();
						
					}

					@Override
					public void keyReleased(KeyEvent e) {}
					
				});
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						errorDateLostAndFound = new JOptionPane(panelErrorDateLostAndFound, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
								optionButtonErrorDateLostAndFound, null).createDialog("Falsches Datumsformat");
						
						
						errorDateLostAndFound.setAlwaysOnTop(true);
						errorDateLostAndFound.setVisible(true);
						errorDateLostAndFound.dispose();
						newFundsachen.datumItemFoundPlaceHolderTextField.requestFocus();
					}
				});
			}
			

		
			
		}
	}
	
	
	
	
	/**
	 * @description Method to set whether the user intends to close or not the NewFundsachen GUI, so we can play with the Lost Focus by the datumItemFoundPlaceHolderTextField.
	 * @param closingNewFundsachen
	 */
	public void setClosingNewFundsachen(boolean closingNewFundsachen ) {
		
		this.closingNewFundsachen = closingNewFundsachen;
	}

}
