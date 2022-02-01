package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tech.codepalace.view.panels.PanelWithBackgroundOption;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version 1.0.0 Jan 30.2022 10:40PM
 * 
 * @description Class for the application of parking databases
 *
 */
public class AHBParking extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	
	//Variable to get the Display Size
	private Dimension screenSize;
	
	private JPanel topPanel, centerPanel, southPanel;
	
	private JLabel cadillac;
	
	private Image myImage;
	
	public ImageIcon exitParkingImageIcon;
	
	//Dialog Box To use when the user presses on the cross close the application.
	public JDialog exitParkingDialog, noExitParkingDialog;
	
	//Option Buttons
	public JButton exitParking, noExitParking;
	
	//Array to include the options Jbuttons for the custom Dialog Box
	private Object [] options;
	
	//JPanels for the Custom Dialog Box and for the option buttons.
	private JPanel panelContainerMessage, panelButtons; 
	
	//Panel for the Message
	private JPanel panelTextCenter;
	
	//JLabel for the alert message.
	private JLabel message; 
	
	
	
	public AHBParking() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		this.exitParking = new JButton("Ja");
		this.noExitParking = new JButton("Nein");
		//new ImageIcon(getClass().getResource("/img/iconoHotel.png")
		//File configurationFile = new File(projectDirectoryString + File.separator + "config.properties");
		
		
		setupFrame();
		
		
	}
	
	
	/**
	 * @description method to configure our JFrame
	 */
	private void setupFrame() {
		
		this.setTitle("Langzeit Parking Airport Hotel Basel");
		
		//We get the Display Size
		this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Set our JFrame 0,0, display-With, display-Heigh
		this.setBounds(0,0,screenSize.width, screenSize.height);
		
		//Frame fully mazimize
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		//Resizable none
		this.setResizable(false);
		
		//Background JPanel
		 this.panelWithBackgroundOption.setImage("/img/texture_blue.jpg");
		 
		 //setContentPane
		 setContentPane(this.panelWithBackgroundOption);
		 
		 //set Layout Manager
		 this.panelWithBackgroundOption.setLayout(new BorderLayout());
		 
		
		 closeParking();
		
	}

	
	
	
	/**
	 * @description method that will be called when the user presses on the cross close window
	 */
	private void closeParking() {
		
		try {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					confirmClose();
				}
			});
//			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void confirmClose() {
		
		 this.exitParkingImageIcon = new ImageIcon(
					"src" + File.separator + "img" + File.separator + "exit_parking.png");
		 
		 this.options = new Object[] {this.exitParking, this.noExitParking};
			
			this.panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			this.panelButtons.add(this.exitParking);
			this.panelButtons.add(this.noExitParking);
			
			this.panelTextCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
			this.message = new JLabel("Sind Sie sicher, dass Sie die Langzeitparken schließen möchten?");
			
			this.panelTextCenter.add(message);
			
			this.panelContainerMessage = new JPanel(new BorderLayout());
			
			this.panelContainerMessage.add(this.panelButtons, BorderLayout.NORTH);
			this.panelContainerMessage.add(this.panelTextCenter, BorderLayout.CENTER);
			
			
			this.exitParkingDialog = new JOptionPane(this.panelContainerMessage, JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION,
					this.exitParkingImageIcon, this.options, null).createDialog("Langzeit Parken Verlassen!");
			
			//Make noExitParking having the Focus.
			this.noExitParking.requestFocus();
			
			this.exitParkingDialog.setVisible(true);
			this.exitParkingDialog.dispose();
		
	}


}
