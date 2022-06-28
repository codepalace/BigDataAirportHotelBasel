package tech.codepalace.controller;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tech.codepalace.model.LogicModelStartFrame;
import tech.codepalace.utility.OperatingSystemCheck;
import tech.codepalace.utility.SetIconOperatingSystem;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;

/**
* Runner object for the MVC BigDataAHB application.
* @author Antonio Estevez Gonzalez
* @version Apr 18, 2014 4:2:17 PM
*
*/
public class BigDataAHBRunner {
	
	private static SetIconOperatingSystem setIconOperatingSystem;
	

	/**
	 * Main starter method or entry point for the Java program.
	 * @param args Unused as this is a GUI BigDataAHB app.
	 */
	public static void main(String[] args) {

		
		/*
		 * We create an instance of the class. 
		 * 
		 * This class is useful to set the icon of our JFrame depending on the operating system where we are running our application.
		 * 
		 * This will be useful for us especially when we run the application on the macOS operating system. 
		 * 
		 * In this way we will be able to put the application icon in the Dock. 
		 */
		setIconOperatingSystem = new SetIconOperatingSystem();
		

		
		//We evaluate which operating system we are running
		switch (OperatingSystemCheck.getOparatingSystem()) {
		
		//In the case of running a macOs operating system
		case MAC: 
			
			
			//we set some properties for our macos operating system
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "BigData Airport Hotel Basel");
			
			
			//We start with a new thread
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					

					try {
						//get Default System LookAndFeel
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}	


					//Instantiate the main window(The principal GUI JFrame class)
					BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame = new BigDataAirportHotelBaselStartFrame();
					

					//Instantiate LogicModelStartFrame
				    LogicModelStartFrame logicModelStartFrame = new LogicModelStartFrame(bigDataAirportHotelBaselStartFrame);
				    

				    
					/* We instantiate the controller class and pass the required arguments to it.
					 * 
					 */
				    new BigDataAHBStartFrameController(bigDataAirportHotelBaselStartFrame, logicModelStartFrame);

					
				    //Center the GUI to the Screen.
					bigDataAirportHotelBaselStartFrame.setLocationRelativeTo(null);
					
					
					/*
					 * We call to set the Application Icon depending on the operating system we are running. 
					 * 
					 * The first parameter it receives is the JFrame that is going to set the icon. 
					 * The second parameter the path with the image that is in our resources folder and inside a package named img.
					 */
					setIconOperatingSystem.setIconJFrame(bigDataAirportHotelBaselStartFrame, "/img/iconoHotel.png");
					
					//We setVisible our GUI
					bigDataAirportHotelBaselStartFrame.setVisible(true);
				}
			});

			
			break;
			

	
			//In the case of running a another operating system	
		default:
			
		
			
EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					

					try {
						
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}	


					//Instantiate the main window(The principal GUI JFrame class)
					BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame = new BigDataAirportHotelBaselStartFrame();
					
					
					//Instantiate LogicModelStartFrame
				    LogicModelStartFrame logicModelStartFrame = new LogicModelStartFrame(bigDataAirportHotelBaselStartFrame);
				    

					
				    new BigDataAHBStartFrameController(bigDataAirportHotelBaselStartFrame, logicModelStartFrame);
					
					//Set the icon four our JFrame
					bigDataAirportHotelBaselStartFrame.setLocationRelativeTo(null);
					
					//We set the JFrame icon calling the setIconImage Method.
					bigDataAirportHotelBaselStartFrame.setIconImage (new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
					
					
					bigDataAirportHotelBaselStartFrame.setVisible(true);
				}
			});
			
			
			break;
		
		
		}
		
		
		
		
		
	}

}

//TEST git
	