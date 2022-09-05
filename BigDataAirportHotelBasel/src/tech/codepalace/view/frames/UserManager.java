package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.PropertiesReader;
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
	
	private JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	private DefaultListModel<String> model = new DefaultListModel<>();
	
	private JList<String> list;
	
	//New Instance PropertiesReader
	private PropertiesReader propertiesReader;
	
	//Instance Properties to store our Properties
	private Properties properties;
	
	//Variables to store the key and the value of the Properties
	private String key = "";
	private String value = "";
	
	//Variable to store the users when we iterate with the Properties.
	private String user = "";
	
	//Instance of DataEncryption to decrypt the data
    private	DataEncryption dataEncryption;
	
	
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
	  
	//addComponentListener to center the window again.
			this.addComponentListener(new java.awt.event.ComponentAdapter() {
	            public void componentMoved(java.awt.event.ComponentEvent evt) {
	                formComponentMoved(evt);
	            }
	        });
			
	
    //Initialize the dataEncryption Object
	this.dataEncryption = new DataEncryption();
	
	//Initialize the JList
	this.list = new JList<String>(model);
	
	//adding all elements to container
	addElementsToGUI();
	  
	  
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
	
	
	/**
	 * @description Method to add all the elements to the container.
	 */
	private void addElementsToGUI() {
		
		//We set the properties to the JList
		this.list.setPreferredSize(new Dimension(400, 100));
		this.list.setBackground(Color.PINK);
		this.list.setFont(new Font("Verdana", Font.BOLD, 14));
		this.list.setFixedCellHeight(30);
	
//		list.setBorder(new EmptyBorder(10,10, 10, 10));
	

		//Initialize the instance PropertiesReader
		this.propertiesReader = new PropertiesReader();
		
		this.properties = propertiesReader.readPropertiesData();
		
	
		@SuppressWarnings("unchecked")
		Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();
		
		//Iterate the Enumeration and until we have elements.
	    while (enums.hasMoreElements()) {
	   
	    	//set the value of key returning the element.
	    	this.key = enums.nextElement();
	    	
	    	//set the value for the value variable calling properties and Method getProperty(As argument the key).
	    	this.value = properties.getProperty(key);
//			System.out.println("key:" +key + " value: " + value );
		
		   try {
			
			//set value for each user present in properties (supposed user because we store all the saved properties). 
			user = this.dataEncryption.decryptData(key);
		} catch (Exception e) {

			e.printStackTrace();
		}
		  
	      
		   //Here then we evaluate if the decrypted user contains "db.user" then we know is an user.
	      if(user.contains("db.user")) {
	    	  
	    	  
	    	  //then we add the element to the model
	    	  model.addElement(user.replace("db.user.", ""));
	    	  
	      }
	  
	    }
		
		//Finally we add the list to the JPanel(listPanel).
		this.listPanel.add(list);
		
		//And the lisPanel to the panelWithBackgroundOption.
		this.panelWithBackgroundOption.add(listPanel, BorderLayout.NORTH);
	}
 
 
 

}
