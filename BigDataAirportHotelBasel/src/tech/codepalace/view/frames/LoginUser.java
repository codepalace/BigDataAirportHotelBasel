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

import javax.swing.BorderFactory;
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
import javax.swing.border.Border;

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
	private JPanel centerPanel, centerPanelWest, centerPanelEast, southPanel, containerButtonPanel;
	
	private JLabel userNameJLabel, passwordJLabel; 
	
	//Image for the modal Window
	private JLabel imgModal;
	
	public JButton loginButton, cancelLoginButton;
	
	public JTextField userLolingJTextField;
	public JPasswordField passwordField;
	
	//Border for the centerPanelButtons
	private Border etchedBorder;
	
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	
	
	


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
		

		
		setSize(500, 180);
		setTitle("Bitte geben Sie Ihren Benutzernamen und Ihr Passwort ein.");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		
		
		this.centerPanel = new JPanel(new BorderLayout());
		this.centerPanel.setOpaque(false);
		
		this.centerPanelWest = new JPanel(new BorderLayout());
		this.centerPanelWest.setOpaque(false);
		this.centerPanelWest.setBorder(BorderFactory.createEmptyBorder(0,10,0, 0));
		
		this.imgModal = new JLabel();
		this.imgModal.setOpaque(false);
		
		this.imgModal.setIcon(new ImageIcon(getClass().getResource("/img/face90x90.png")));
		
		this.centerPanelWest.add(this.imgModal);
	
		
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
		

		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();

		this.centerPanelEast = new JPanel();
		this.centerPanelEast.setOpaque(false);
		this.centerPanelEast.setBorder(BorderFactory.createEmptyBorder(0,0,0, 10));
		this.centerPanelEast.setLayout(gbl);
		
		
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.centerPanelEast.add(this.userNameJLabel, gbc);
		

		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.gbc.insets = new Insets(0, 5, 0, 0);
		this.centerPanelEast.add(this.userLolingJTextField, gbc);
		
		
		this.gbc.gridx = 0; 
		this.gbc.gridy = 1; 
		this.gbc.insets = new Insets(5, 0, 0, 0);
		this.centerPanelEast.add(this.passwordJLabel, gbc);
		

		this.gbc.gridx = 1;
		this.gbc.gridy = 1; 
		this.gbc.insets = new Insets(5, 5, 0, 0);
		this.centerPanelEast.add(this.passwordField, gbc);
		
		
		
		
		this.centerPanel.add(this.centerPanelWest, BorderLayout.WEST);
		this.centerPanel.add(centerPanelEast, BorderLayout.EAST);
		
		
		
		this.southPanel = new JPanel(new BorderLayout());
		this.southPanel.setOpaque(false);
		
		//Initialize the Border
		this.etchedBorder = BorderFactory.createEtchedBorder();
		
//		this.southPanel.setBorder(etchedBorder);
		
		this.loginButton = new JButton("Login");
		this.cancelLoginButton = new JButton("Abbrechen");
		
		this.containerButtonPanel = new JPanel(new BorderLayout());
		this.containerButtonPanel.setOpaque(false);
		
		this.containerButtonPanel.add(this.loginButton, BorderLayout.EAST);
		this.containerButtonPanel.add(this.cancelLoginButton, BorderLayout.WEST);
		
		this.containerButtonPanel.setBorder(etchedBorder);
		
		this.southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.southPanel.add(containerButtonPanel);
		
		
		this.panelWithBackgroundOption.add(centerPanel, BorderLayout.CENTER);
		this.panelWithBackgroundOption.add(this.southPanel, BorderLayout.SOUTH);
		

		
		setContentPane(this.panelWithBackgroundOption);
		
		
		
		
		//addComponentListener to center the window again.
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
		
	
	

		
				
			
		
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
					confirmClose();
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
