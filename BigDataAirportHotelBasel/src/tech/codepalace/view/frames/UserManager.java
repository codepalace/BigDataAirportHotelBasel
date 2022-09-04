package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * 
 * @description Class to manage users.
 * <h2>Functions performed by this class.</h2>
 * <ul>
 * <li>Adding new users</il>
 * <li>Delete existing user</li>
 * <li>Edit user. for example change password</li>
 * </ul>
 * 
 * @author Antonio Estevez Gonzalez
 *
 */

@SuppressWarnings("serial")
public class UserManager extends JDialog {
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	private GridBagLayout mainGBL, gbl;
	private GridBagConstraints mainGBC, gbc;
	
	
 public UserManager(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, boolean modal) {
	 
	 //Call super with the arguments JFrame should be blocked in Background and modal(true) for blocked
	 super(bigDataAirportHotelBaselStartFrame, modal);
	 
	 init();
 }
 
 

	
 
 
 
  private void init() {
	  
	  setSize(500, 180);
	  setTitle("Benutzer Manager");
	  
	  setLocationRelativeTo(null);
	  setResizable(false);
	  setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	  
	  this.panelWithBackgroundOption = new PanelWithBackgroundOption();
	  this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
	  
	  setContentPane(panelWithBackgroundOption);
	  
	  setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
	  
	  
  }
 
 
 
 

}
