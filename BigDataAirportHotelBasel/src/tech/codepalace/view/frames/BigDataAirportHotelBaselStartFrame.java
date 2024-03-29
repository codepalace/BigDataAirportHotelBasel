package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 *@version 1.0.0 Jan, 23, 2022 - 7:34:00 PM
 *
 *@description Main GUI Class, extends JFrame. First Frame displayed when we start the application.
 *
 *<p>Important note. In this project we have added a jar applejavaextensions-1.4.jar 
 *so that in windows it does not give us an error when calling the class com.apple.eawt.Application;</p>
 *
 *<p>As our application can run on both Windows and macOS, we need the Application class belonging to the package com.apple.eawt to be present.</p>
 *
 *<p>The Application class is not present in the Windows Java virtual machine. Therefore we have had to avail ourselves of using the jar applejavaextensions-1.4.jar.</p>
 *
 *<p>com.apple.eawt.Application. This class will help us to be able to change the icon of our application in a more comfortable way when it runs on MacOS.</p>
 *
 *<p>That's why we have added this class in our library packages and we have referenced it to the project libraries.</p>
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
		public JButton  logoutButton, parkingButton, fundsachenButton, phonebookButton, fitnessButton, uebergabeButton;
		
		
		//JLabel to display the user logged in
		public JLabel loginUserText;
		
		//Panel for loginUser JLabel
		private JPanel loginPanel;
		
		
		//Some MyButton to access other application options kontoVerwalten(manage account) benutzerVerwalten(Manage User)
		//I am creating an Application in German language.
		public MyButton btn_createDB, btn_kontoVerwalten, btn_benutzerVerwalten, btn_exit;
		
		
		//Some other JLabel for a Car picture and a Face Icon
		private JLabel cadillac, faceIcon;
		
		//Variable to set the Image to JLabel
		private Image myImage;
		

		
		
		
		/**
		 * Create a BigDataAirportHotelBaselStartFrame object passing a reference to the BigDataAHBController for use by the BigDataAirportHotelBaselStartFrame object.
		 * @param bigDataAHBController The reference to the BigDataAHBController object for MVC.
		 */
		public BigDataAirportHotelBaselStartFrame() {
			
			
			//We initialize the panels
			this.panelWithBackgroundOption = new PanelWithBackgroundOption();
			
			
			this.toppanel = new JPanel();
			this.centerPanel = new JPanel();
			this.southPanel = new JPanel();
			this.panelNameHotel = new JPanel();
			this.buttonsPanelSouthContainer = new JPanel();
			this.buttonsPanelSouth = new JPanel();
			this.menuButtonPanel = new JPanel();
			this.containerPicsHotel = new JPanel();
			
			this.loginPanel = new JPanel();
			
			
			
			//We instantiate the buttons by passing the images they are going to have.
			 this.logoutButton = new MyButton("/img/btn_logout.png");
			 this.parkingButton = new MyButton("/img/btn_parking.png");
			 this.fundsachenButton = new MyButton("/img/btn_fundsachen.png");    
			 this.fitnessButton = new MyButton("/img/btn_fitness_abo.png");
			 this.phonebookButton = new MyButton("/img/btn_telefonbuch.png");
			 this.uebergabeButton = new MyButton("/img/btn_uebergabe.png");
			
			 
			//We created the south central container panel
			this.buttonsPanelSouthContainer = new JPanel();
			
			//We create the container of the buttons.
			this.buttonsPanelSouth = new JPanel();
			
			
			
			//Call setupFrame
			setupFrame();
			
			


			}
		
		/**
		 * Sets the contentpane, size, and makes frame visible.
		 */
		public void setupFrame() {


			this.setTitle("Big Data Airport Hotel Basel - powered by Antonio Estevez Gonzalez");
			
			this.setLocationRelativeTo(null);
			
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			
			this.setResizable(false);

			 this.setSize(1110, 710);
			 this.setResizable(false);
			 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 
			 
			//Set the background color of the main panel
				this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");

				//Set the main panel LayoutManager as BorderLayout
				this.panelWithBackgroundOption.setLayout(new BorderLayout());

			 //we set panelWithBackgroundOption as the ContentPane
			 setContentPane(panelWithBackgroundOption);


			 //Add the elements to the panel
			 addElementsToPanel();



		}
		
	
	
		
		/**
		 * @description method to add the different elements to the panel
		 */
		protected void addElementsToPanel() {
			
			
			   this.toppanel.setLayout(new BorderLayout());
			   this.toppanel.setOpaque(false);
			   
			   this.panelNameHotel.setLayout(new BorderLayout());
			   this.panelNameHotel.setOpaque(false);
			   
			   //We call setImageJLabel to set the Image 
			   this.nameHotelImage = new JLabel(setImageJLabel("/img/hotel_name_panel.png"));
			   this.nameHotelImage.setPreferredSize(new Dimension(820,90));
			   
			
			   this.nameHotelImage.setOpaque(false);
			   
			   
			   this.iconHotel = new JLabel(setImageJLabel("/img/icon_inside.png"));
				this.iconHotel.setPreferredSize(new Dimension(179, 161));
			   

			   this.containerPicsHotel.setLayout(new FlowLayout(FlowLayout.CENTER));
				
				this.picHoteLabel = new JLabel(setImageJLabel("/img/apt_h.jpg"));
				this.picHoteLabel.setPreferredSize(new Dimension(575,170));

				this.picHotelLabelRight = new JLabel();
				
				this.picHotelLabelRight = new JLabel(setImageJLabel("/img/hotel_pic_right.jpg"));
				this.picHotelLabelRight.setPreferredSize(new Dimension(500,170));
				
				
				
				this.containerPicsHotel.add(this.picHoteLabel);
				this.containerPicsHotel.add(picHotelLabelRight);
				this.containerPicsHotel.setOpaque(false);
				

				 
				 
				this.panelNameHotel.add(nameHotelImage, BorderLayout.NORTH);
				this.toppanel.add(panelNameHotel, BorderLayout.WEST);
				
				this.toppanel.add(iconHotel, BorderLayout.EAST);
				
				
				this.centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				
				this.centerPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), 
						"Bitte wählen Sie das gewünschte Datenbankprogramm", TitledBorder.CENTER, 
						TitledBorder.DEFAULT_POSITION, new Font("Verdana", Font.BOLD, 18), Color.decode("#FFFFFF")));  
				
				this.centerPanel.setOpaque(false);
				

				this.menuButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
				 this.menuButtonPanel.setOpaque(false);
				 
				
		    
				    
				    this.logoutButton.setPreferredSize(new Dimension(150,70));
				    this.parkingButton.setPreferredSize(new Dimension(150,70));
				    this.fundsachenButton.setPreferredSize(new Dimension(150,70));
				    this.fitnessButton.setPreferredSize(new Dimension(150,70));     
				    this.phonebookButton.setPreferredSize(new Dimension(150,70));
				    this.uebergabeButton.setPreferredSize(new Dimension(150, 70));

				
			    

				this.menuButtonPanel.add(parkingButton);
				this.menuButtonPanel.add(fundsachenButton);
				this.menuButtonPanel.add(fitnessButton);
				this.menuButtonPanel.add(uebergabeButton);
				this.menuButtonPanel.add(phonebookButton);
				this.menuButtonPanel.add(logoutButton);
				
				
				this.centerPanel.add(menuButtonPanel, BorderLayout.NORTH);
				this.centerPanel.add(containerPicsHotel, BorderLayout.CENTER);
				
				
				
				this.loginPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				
				this.loginUserText = new JLabel("Benutzer: ");
				this.loginUserText.setFont(new Font("Verdana", Font.PLAIN, 14));
				this.loginUserText.setForeground(Color.WHITE);
				
				this.loginPanel.add(loginUserText);
				this.loginPanel.setBackground(Color.GRAY);
				
				
				this.toppanel.add(loginPanel, BorderLayout.SOUTH);
				
				
				
				this.cadillac = new JLabel(setImageJLabel("/img/cadillac_oldtimer350x200.png"));
				this.cadillac.setPreferredSize(new Dimension(350,200));

				this.redLineNorth = new JLabel(setImageJLabel("/img/redline.png"));
				this.redLineSouth = new JLabel(setImageJLabel("/img/redline.png"));

				this.redLineSouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
				
				
				
				this.southPanel.setLayout(new BorderLayout());
				this.southPanel.setOpaque(false);
				
				
				
				



				
				
				
				this.buttonsPanelSouth.setOpaque(false);
				this.buttonsPanelSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
				
				
				
				//We create the buttons with their respective images, buttons that go south buttonPanelSouth

					this.btn_createDB = new MyButton("/img/create_db.png");
					this.btn_createDB.setToolTipText("Create personalized Data Base");
					this.btn_createDB.setEnabled(false);
					this.btn_createDB.setVisible(false);
					this.btn_kontoVerwalten = new MyButton("/img/konto_verwalten.png");
					this.btn_benutzerVerwalten  = new MyButton("/img/benutzer_verwalten.png");
					this.btn_benutzerVerwalten.setEnabled(false);
					this.btn_benutzerVerwalten.setVisible(false);
					this.btn_exit = new MyButton("/img/exit_btn.png");
					
					
					//add to the panel
					buttonsPanelSouth.add(btn_createDB);
					buttonsPanelSouth.add(btn_kontoVerwalten);
					buttonsPanelSouth.add(btn_benutzerVerwalten);
					buttonsPanelSouth.add(btn_exit);
					
					
				
				
				this.buttonsPanelSouthContainer.setLayout(new BorderLayout());
				this.buttonsPanelSouthContainer.setOpaque(false);
				
				this.buttonsPanelSouthContainer.add(this.redLineNorth, BorderLayout.NORTH);
				this.buttonsPanelSouthContainer.add(buttonsPanelSouth, BorderLayout.CENTER);
				this.buttonsPanelSouthContainer.add(this.redLineSouth, BorderLayout.SOUTH);

				
				
				this.faceIcon = new JLabel(setImageJLabel("/img/facew.png"));
				this.faceIcon.setPreferredSize(new Dimension(200,200));
				
				
				this.southPanel.add(cadillac, BorderLayout.WEST);
				this.southPanel.add(faceIcon, BorderLayout.EAST);
				this.southPanel.add(buttonsPanelSouthContainer);
				
				
				this.panelWithBackgroundOption.add(this.toppanel, BorderLayout.NORTH);
				this.panelWithBackgroundOption.add(centerPanel, BorderLayout.CENTER);
				this.panelWithBackgroundOption.add(this.southPanel, BorderLayout.SOUTH);
				


			}
		
		
		
		
		
		public ImageIcon setImageJLabel(String img) {

			
			this.myImage = new ImageIcon(getClass().getResource(img)).getImage();


		     return new ImageIcon(this.myImage);

		}

		
		

		

}
