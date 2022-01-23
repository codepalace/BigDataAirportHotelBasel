package tech.codepalace.view.frames;

import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 *@version 1.0.0 Jan, 23, 2022 - 7:34:00 PM
 *
 *@description Main GUI Class, extends JFrame. First Frame displayed when we start the application.
 */
public class BigDataAirportHotelBaselStartFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//we create an instance of a background panel for our main Gui
		private PanelWithBackgroundOption panelWithBackgroundOption;
		
		//We create the panels we need
		private JPanel toppanel, centerPanel, southPanel, panelNameHotel, buttonsPanelSouthContainer, buttonsPanelSouth, menuButtonPanel,
			containerPicsHotel;
		
		//Some JLabel for the name of the Hotel, icon, etc.
		private JLabel nameHotelImage, picHoteLabel,  picHotelLabelRight, iconHotel, redLineNorth, redLineSouth;
		
		//main buttons to access the different databases of the program
		public JButton  logoutButton, parkingButton, fundsachenButton, phonebookButton, fitnessButton;
		
		
		//JLabel to display the user logged in
		public JLabel loginUserText;
		
		
		//Some MyButton to access other application options kontoVerwalten(manage account) benutzerVerwalten(Manage User)
		//I am creating an Application in German language.
		public MyButton btn_createDB, btn_kontoVerwalten, btn_benutzerVerwalten, btn_exit;
		
		
		//Some other JLabel for a Car picture and a Face Icon
		private JLabel cadillac, faceIcon;
		
		//Variable to set the Image to JLabel
		private Image myImage;

}
