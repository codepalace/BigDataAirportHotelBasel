package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Login class. This class will block the main GUI(BigDataAiportHotelBaselStartFrame). 
 * 
 * <p>
 * Until the user does not login with their correct data they will not allow the main class to be unlocked.
 * </p>
 * 
 * <p>
 * If this class is closed, the entire application is closed.
 * </p>
 *
 */
public class LoginUser extends JDialog  {
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	private JPanel mainJPanel, entriesPanel, containerButtonPanel;
	
	private JLabel userNameJLabel, passwordJLabel; 
	
	//Image for the modal Window
	private JLabel imgModal;
	
	public MyButton loginButton, cancelLoginButton;
	
	public JTextField userLolingJTextField;
	public JPasswordField passwordField;

	private GridBagLayout mainGBL, gbl;
	private GridBagConstraints mainGBC, gbc;
	
	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 
	 * @param bigDataAirportHotelBaselStartFrame
	 * @param modal
	 * @description 
	 * the constructor receives a BigDataAirportHotelBaselStartFrame class as a parameter and a boolean for the modal window.
	 */
	public LoginUser(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, boolean modal) {
		
		
		
		//we pass the data to the super class. Our locked class and the modal value.
		super(bigDataAirportHotelBaselStartFrame, modal);
		

		//we initialize the elements
		init();
		
		
		setContentPane(this.panelWithBackgroundOption);
		
		
		
		
		//addComponentListener to center the window again.
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
		
	
	

		
				
			
		
	}
	
	
	
	private void init() {
		
		setSize(500, 180);
		setTitle("Bitte geben Sie Ihren Benutzernamen und Ihr Passwort ein.");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		
		//mainJPanel will be added to the panelWithBckgroundOption and it will be added to this JPanel the other elements.
		this.mainJPanel = new JPanel();
		
		this.imgModal = new JLabel();
		this.imgModal.setOpaque(false);
		
		this.imgModal.setIcon(new ImageIcon(getClass().getResource("/img/face90x90.png")));
		
		// GridBagLayout for the mainJPanel
		this.mainGBL = new GridBagLayout();

		// GridBagConstraints for the mainJPanel
		this.mainGBC = new GridBagConstraints();
		
		
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		
		// setLayout to the mainJPanel the GridBagLayout
		this.mainJPanel.setLayout(mainGBL);
		this.mainJPanel.setOpaque(false);
		
		this.entriesPanel =  new JPanel(gbl);
		this.entriesPanel.setOpaque(false);
		
		this.userNameJLabel = new JLabel("Benutzer:");
		this.userNameJLabel.setPreferredSize(new Dimension(110,20));
		this.userNameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.userNameJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		this.passwordJLabel = new JLabel("Password:");
		this.passwordJLabel.setPreferredSize(new Dimension(110,20));
		this.passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.passwordJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		
		this.userLolingJTextField = new JTextField(20);
		this.userLolingJTextField.setText("");
		
		this.passwordField = new JPasswordField(20);
		this.passwordField.setText("");
		
		
		//MyButton and containerButtonPanel
		this.loginButton = new MyButton("/img/login_login_gui.png");
		this.cancelLoginButton = new MyButton("/img/abbrechen_login_gui.png");
		
		this.containerButtonPanel = new JPanel(new BorderLayout());
		this.containerButtonPanel.setOpaque(false);
		
		
		
		//Add all elements to the GUI
		addElementsToGUI();
		
	}
	
	
	
	private void addElementsToGUI() {
		
		//Setting the elements GridBagConstraints and add to entriesPanel with GridBagLayout
		
		this.gbc.insets = new Insets(10, 0, 10, 0);
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.entriesPanel.add(this.userNameJLabel, gbc);
		

		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.gbc.insets = new Insets(0, 5, 0, 0);
		this.entriesPanel.add(this.userLolingJTextField, gbc);
		
		
		this.gbc.gridx = 0; 
		this.gbc.gridy = 1; 
		this.gbc.insets = new Insets(5, 0, 0, 0);
		this.entriesPanel.add(this.passwordJLabel, gbc);
		

		this.gbc.gridx = 1;
		this.gbc.gridy = 1; 
		this.gbc.insets = new Insets(5, 5, 0, 0);
		this.entriesPanel.add(this.passwordField, gbc);
		

	
		
		//We add the entriesPanel to the mainJPanel
		this.mainJPanel.add(this.entriesPanel);
		
		
		//Add the Buttons to the containerButtonPanel
		this.containerButtonPanel.add(this.loginButton, BorderLayout.EAST);
		this.containerButtonPanel.add(this.cancelLoginButton, BorderLayout.WEST);
		
		
		
		//Setting the elements GridBagConstraints and add to mainJPanel with GridBagLayout
		this.mainGBC.insets = new Insets(10, 0, 0, 0);
		mainGBC.gridx = 0;
		mainGBC.gridy = 0;
		mainGBL.setConstraints(imgModal, mainGBC);
		this.mainJPanel.add(imgModal);
		
		mainGBC.gridx = 1;
		mainGBC.gridy = 0;
		mainGBL.setConstraints(this.entriesPanel, mainGBC);
		
		
		
		mainGBC.gridx = 0;
		mainGBC.gridy = 1;
		mainGBC.fill = GridBagConstraints.HORIZONTAL;
		mainGBC.gridwidth = 2;
		mainGBL.setConstraints(this.containerButtonPanel, mainGBC);
		
		//We add the JButton s to the mainJPanel
		this.mainJPanel.add(containerButtonPanel);
		
		this.panelWithBackgroundOption.add(this.mainJPanel, BorderLayout.NORTH);
		
		
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

		
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/dialogo.png"));
		
	
		JFrame frame = new JFrame();
		JOptionPane optionPane = new JOptionPane();
		 optionPane.setMessage("Sind Sie sicher, dass Sie das Programm beenden möchten?");
		 optionPane.setMessageType(JOptionPane.OK_CANCEL_OPTION);
		 optionPane.setIcon(icon);
		 JButton noExitLogin = new JButton("Abbrechen");
		 JButton exitProgram = new JButton("Beenden");
		 JDialog dialog = optionPane.createDialog(frame, "Programm beenden?");
		 
		 
		 noExitLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Close JDialog and continue loging GUI
				userLolingJTextField.requestFocus();
				dialog.dispose();
				
				
			}
		});
		 
		 exitProgram.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					//Exit program
					System.exit(1);
					
				}
			});
		 
		 
		  noExitLogin.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==10) {
					dialog.dispose();
					
				}
				
			}
		});
		  
		  
		  
		  exitProgram.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			
			@Override
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode()==10) {
					
					//Call for the confirmation
//					confirmClose();
					dialog.dispose();
					System.exit(0);
				}
				
			}
		});
		  
		  
		 
		 
		 optionPane.setOptions(new Object[] {noExitLogin, exitProgram});
		 
		
		 dialog.setVisible(true);
		 

			
					
			
			
			
	}
	
	
	/**
	 * @description we get the UserName entered by the User
	 * @return
	 */
	public String getUserNameEntered() {
		return this.userLolingJTextField.getText();
	}
	
	
	
	/**
	 * @descripion we get the password entered by the User.
	 * @return
	 */
	public String getPasswordEntered() {
		
		String pwd = new String(this.passwordField.getPassword());
		return pwd;
	}

	


	

}
