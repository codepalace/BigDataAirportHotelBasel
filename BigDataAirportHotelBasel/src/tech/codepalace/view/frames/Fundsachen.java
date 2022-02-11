package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version 0.1.0
 * 
 * @description Class for the FundSachen(Lost And Found) database application.
 *
 */
public class Fundsachen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	
	//Variable to get the Display Size
	private Dimension screenSize;
	
	private JPanel topPanel, centerPanel, southPanel;
	
	private JLabel treasure;
	
	private Image myImage;
	
	
	//JPanels for the Menu Buttons
		 private JPanel centerPanelButtons, containerPanelButton;
		
		 //Menu JButtons
		 public JButton btnHome, btnParking, btnFitness, btnPhonebook, btnLogout;
		 
		//Border for the centerPanelButtons
		private Border etchedBorder;
		
		//Jpanel for the user logged
		private JPanel loginPanel;
		
		//JLable for the user logged
		public JLabel loginUserLabel;
		
		
		
		public Fundsachen() {
			
			this.panelWithBackgroundOption = new PanelWithBackgroundOption();
			
			// Initialize the JButtons as MyButton
			this.btnHome = new MyButton("/img/btn_home.png");
			this.btnParking = new MyButton("/img/btn_parking.png");
			this.btnFitness = new MyButton("/img/btn_fitness_abo.png");
			this.btnPhonebook = new MyButton("/img/btn_telefonbuch.png");
			this.btnLogout = new MyButton("/img/btn_logout.png");
			
			
			//We initialize the panel of the buttons
			this.centerPanelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			//Initialize the Border
			this.etchedBorder = BorderFactory.createEtchedBorder();
			
			this.centerPanelButtons.setBorder(etchedBorder);
			
			this.centerPanelButtons.setOpaque(false);
			
			this.containerPanelButton = new JPanel(new BorderLayout());
			
			
			setupFrame();
			
			
			
		}
		
		
		
		
		/**
		 * @description method to configure our JFrame
		 */
		private void setupFrame() {
			
			this.setTitle("Fundsachen Airport Hotel Basel");
			
			//We get the Display Size
			this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			//Set our JFrame 0,0, display-With, display-Heigh
			this.setBounds(0,0,screenSize.width, screenSize.height);
			
			//Frame fully mazimize
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			
			//Resizable none
			this.setResizable(false);
			
			//Background JPanel
			 this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
			 
			 //setContentPane
			 setContentPane(this.panelWithBackgroundOption);
			 
			 //set Layout Manager
			 this.panelWithBackgroundOption.setLayout(new BorderLayout());
			 
			
//			 closeFundsachen();
			 
			 //add the elements
			 this.addElementsToPanel();
			
		}
		
		
		
		
		public void addElementsToPanel() {
			
			this.topPanel = new JPanel();
			this.topPanel.setOpaque(false);
			this.topPanel.setLayout(new BorderLayout());
			
			this.treasure = new JLabel(setImageJLabel("/img/treasure_350x200.png"));
			this.treasure.setPreferredSize(new Dimension(350,200));
			
			this.topPanel.add(this.treasure, BorderLayout.WEST);
			
			
			
			this.panelWithBackgroundOption.add(this.topPanel, BorderLayout.NORTH);
			
			this.centerPanel = new JPanel(new BorderLayout());
			
			this.panelWithBackgroundOption.add(this.centerPanel);
			
			
			this.btnHome.setPreferredSize(new Dimension(150,70));
			this.btnParking.setPreferredSize(new Dimension(150,70));
			this.btnFitness.setPreferredSize(new Dimension(150,70));
			this.btnPhonebook.setPreferredSize(new Dimension(150,70));
			this.btnLogout.setPreferredSize(new Dimension(150,70));
			

			
			this.containerPanelButton.setOpaque(false);
			this.containerPanelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			this.containerPanelButton.add(centerPanelButtons, BorderLayout.SOUTH);
			
			

			this.centerPanelButtons.add(btnHome);
			this.centerPanelButtons.add(btnParking);
			this.centerPanelButtons.add(btnFitness);
			this.centerPanelButtons.add(btnPhonebook);
			this.centerPanelButtons.add(btnLogout);
			
			
			this.topPanel.add(containerPanelButton, BorderLayout.CENTER);
			
			
			this.loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			this.loginPanel.setBackground(Color.GRAY);
			
			
			this.loginUserLabel = new JLabel("Benutzer: ");
			this.loginUserLabel.setFont(new Font("Verdana", Font.BOLD, 18));
			this.loginUserLabel.setForeground(Color.WHITE);
			
			this.loginPanel.add(this.loginUserLabel);
			
			this.topPanel.add(this.loginPanel, BorderLayout.SOUTH);
			
			
			
		}
		
		
		
		public ImageIcon setImageJLabel(String img) {


			this.myImage = new ImageIcon(getClass().getResource(img)).getImage();


		     return new ImageIcon(this.myImage);

		}
		
		
	
	
	
	

}
