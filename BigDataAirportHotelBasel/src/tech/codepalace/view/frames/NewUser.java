package tech.codepalace.view.frames;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * @description Class to add new User
 * @author Antonio Estevez Gonzalez
 * 
 *
 */
@SuppressWarnings("serial")
public class NewUser extends JDialog {
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	public NewUser(UserManager userManager, boolean modal) {
		
		//Call super with the arguments JFrame should be blocked in Background and modal(true) for blocked
		super(userManager, modal);
		
		init();
	}
	
	
	private void init() {
		
		setSize(530, 200);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setTitle("Neuen Benutzer Eingeben");
		
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
		 this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		  this.panelWithBackgroundOption.setLayout(new BorderLayout());
		 setContentPane(panelWithBackgroundOption);
		  
		  setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		
		
	}
	
	
	  /**
		 * 
		 * @param evt
		 * @description method to re-center the window in case the user tries to drag the window
		 */
		private void formComponentMoved(java.awt.event.ComponentEvent evt) {                                    
		       this.setLocationRelativeTo(null);
		    }   
		

		
		/**
		 * @description Method to confirm application closure
		 */
		public void confirmClose() {

			 this.dispose();
						
				
		}
		

}
