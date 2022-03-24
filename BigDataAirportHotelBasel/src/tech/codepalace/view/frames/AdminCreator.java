package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tech.codepalace.utility.CenterComponentMoved;
import tech.codepalace.utility.JTextFieldLimit;
import tech.codepalace.utility.SetIconOperatingSystem;
import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

@SuppressWarnings("serial")
public class AdminCreator extends JDialog {
	
	private SetIconOperatingSystem setIconOperatingSystem;
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	private JPanel northPanel, soutPanel;
	
	private JLabel adminJLabel, passwordJLabel, abkuerzungMAJLabel;
	
	public JTextField adminJTextField, abkuerzungJTextField; 
	
	public JPasswordField passwordField;
	
	public JButton saveAdminButton, exitApplicationButton;
	
	private GridBagLayout gridBagLayout;
	private GridBagConstraints gridBagConstraints;
	
	
	private String urldDataBase = "", urlDataBaseBackup ="";

	
	
	public AdminCreator(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, boolean modal) {
		super(bigDataAirportHotelBaselStartFrame, modal);
		
		
		this.saveAdminButton = new  MyButton("/img/btnspeicherdb.png");
		this.saveAdminButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));
		this.exitApplicationButton = new MyButton("/img/abbrechendb.png");
		this.exitApplicationButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
		
		this.abkuerzungJTextField = new JTextField(20);
		this.abkuerzungJTextField.setFont(new Font("Verdana", Font.BOLD, 16));
		this.adminJTextField = new JTextField(20);
		this.adminJTextField.setFont(new Font("Verdana", Font.BOLD, 16));
		this.passwordField = new JPasswordField(20);
		this.passwordField.setFont(new Font("Verdana", Font.BOLD, 16));
		
		this.gridBagLayout = new GridBagLayout();
		this.gridBagConstraints = new GridBagConstraints();
		
		init();
	}
	
	
	
	
	
	private void init() {
		
		this.setSize(580, 200);
		this.setLocationRelativeTo(new CenterComponentMoved(this));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.setTitle("Erstellen einen neuen Admin-Benutzer");
		
		this.setIconOperatingSystem = new SetIconOperatingSystem();
		this.setIconImage(new ImageIcon("/img/iconoHotel.png").getImage());
		
		this.panelWithBackgroundOption =  new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		
		this.setContentPane(this.panelWithBackgroundOption);
		
		
		addElements();
		
		
		
		
		
		
	}
	
	
	
	
	private void addElements() {
		
		this.adminJLabel = new JLabel("Admin:");
		this.adminJLabel.setPreferredSize(new Dimension(140, 20));
		this.adminJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.adminJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.passwordJLabel = new JLabel("Password:");
		this.passwordJLabel.setPreferredSize(new Dimension(140, 20));
		this.passwordJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.abkuerzungMAJLabel = new JLabel("Abkürzung MA:");
		this.abkuerzungMAJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.abkuerzungMAJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.abkuerzungMAJLabel.setPreferredSize(new Dimension(140, 20));
		this.abkuerzungMAJLabel.setOpaque(false);
		
		this.abkuerzungJTextField.setDocument(new JTextFieldLimit(4)); //We limit the number of characters that can be entered to 4
		
		//Creamos el panel norte donde iran los elementos a insertar informacion
		this.northPanel = new JPanel();
		
		this.northPanel.setLayout(gridBagLayout);
		this.northPanel.setOpaque(false);
		
		
		//add the elements to the panel GridBagLayout
		this.gridBagConstraints.gridx = 0; 
		this.gridBagConstraints.gridy = 0; 
		this.gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		this.gridBagLayout.setConstraints(adminJLabel, gridBagConstraints);
		this.northPanel.add(adminJLabel);
		
		this.gridBagConstraints.gridx = 1;
		this.gridBagConstraints.gridy = 0;
		this.gridBagConstraints.insets = new Insets(10, 5, 0, 0);
		this.gridBagLayout.setConstraints(this.adminJTextField, gridBagConstraints);
		this.northPanel.add(adminJTextField);
		
		
		this.gridBagConstraints.gridx = 0; 
		this.gridBagConstraints.gridy = 1; 
		this.gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		this.gridBagLayout.setConstraints(passwordJLabel, gridBagConstraints);
		this.northPanel.add(passwordJLabel);
		
		this.gridBagConstraints.gridx = 1;
		this.gridBagConstraints.gridy = 1; 
		this.gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		this.gridBagLayout.setConstraints(passwordField, gridBagConstraints);
		this.northPanel.add(passwordField);
		
		
		this.gridBagConstraints.gridx = 0;
		this.gridBagConstraints.gridy = 2; 
		this.gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		this.gridBagLayout.setConstraints(abkuerzungMAJLabel, gridBagConstraints);
		this.northPanel.add(abkuerzungMAJLabel);
		   
		   
		this.gridBagConstraints.gridx = 1;
		this.gridBagConstraints.gridy = 2; 
		this.gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		this.gridBagLayout.setConstraints(abkuerzungJTextField, gridBagConstraints);
		this.northPanel.add(abkuerzungJTextField);
		
		
		
		this.soutPanel = new JPanel(new BorderLayout());
		this.soutPanel.setOpaque(false);
		this.soutPanel.add(this.saveAdminButton, BorderLayout.EAST);
		this.soutPanel.add(exitApplicationButton, BorderLayout.WEST);
		
		this.panelWithBackgroundOption.add(this.northPanel, BorderLayout.NORTH);
		this.panelWithBackgroundOption.add(this.soutPanel, BorderLayout.SOUTH);
		
		
		
		
	}

}
