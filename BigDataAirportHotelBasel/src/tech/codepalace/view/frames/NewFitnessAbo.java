package tech.codepalace.view.frames;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tech.codepalace.view.panels.PanelWithBackgroundOption;

@SuppressWarnings("serial")
public class NewFitnessAbo extends JDialog {
	
	//Variable for the User Abbreviation Name
	private static String abkuerzungMA;
	
	//Our Panel with Background Image
	private PanelWithBackgroundOption panelWithBackgroundOption;
			
	private JPanel entriesPanel, buttonPanel, mainJPanel;
	
	
	

	public NewFitnessAbo(DataBaseGUI dataBaseGUI, boolean modal, String abkuerzungMA) {
		
		//We call super and the DataBaseGUI as argument so we specify that dataBaseGUI is the Object that we send that will be blocked 
 		super(dataBaseGUI, modal);
 		
 		NewFitnessAbo.abkuerzungMA = abkuerzungMA;
 		
 		setSize(730, 470);
 		
 		setTitle("Neues Fitness-Abo");
 		
 		setUndecorated(true);
 		
 		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 		setResizable(false);
 		
 	// addComponentListener to center the window again.
 	this.addComponentListener(new java.awt.event.ComponentAdapter() {
 		
 		public void componentMoved(java.awt.event.ComponentEvent evt) {
 			formComponentMoved(evt);
 		}
 				
 	});
 				
 				//We call the init(), Method
 				init();
 				
 				
 	  
	}
	
	
	/**
	 * 
	 * @param evt
	 * @description method to re-center the window in case the user tries to drag the window
	 */
	private void formComponentMoved(java.awt.event.ComponentEvent evt) {                                    
	       this.setLocationRelativeTo(null);
	    }   
	
	
	private void init() {
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		
		setContentPane(this.panelWithBackgroundOption);
		
		setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		
		

		//We add all the Elements to the Panel.
		addElementsToPanel();
	}
	
	
	
	private void addElementsToPanel() {
		
		//mainJPanel will be added to the panelWithBckgroundOption and it will be added to this JPanel the other elements.
		this.mainJPanel = new JPanel();
		
		//entriesPanel will contain all the edit elements.
		this.entriesPanel = new JPanel();
		
	}

}
