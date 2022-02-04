package tech.codepalace.controller;

import javax.swing.SwingUtilities;

import tech.codepalace.model.LogicModelStartFrame;
import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;

/**
* Runner object for the MVC BigDataAHB application.
* @author Antonio Estevez Gonzalez
* @version Apr 18, 2014 4:2:17 PM
*
*/
public class BigDataAHBRunner {

	/**
	 * Main starter method or entry point for the Java program.
	 * @param args Unused as this is a GUI BigDataAHB app.
	 */
	public static void main(String[] args) {


		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {

				//Instantiate the main window(The principal GUI JFrame class)
				BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame = new BigDataAirportHotelBaselStartFrame();
				
				//Instantiate UserAHB
				UserAHB userAHB = new UserAHB();
				
				
				//Instantiate LogicModelStartFrame
			    LogicModelStartFrame logicModelStartFrame = new LogicModelStartFrame(bigDataAirportHotelBaselStartFrame, userAHB);
			    


						
				//We instantiate the controller class and pass the required arguments to it.
				@SuppressWarnings("unused")
				BigDataAHBStartFrameController bigDataAHBStartFrameController = new BigDataAHBStartFrameController(bigDataAirportHotelBaselStartFrame, userAHB, logicModelStartFrame);
				
				//Set the icon four our JFrame
				bigDataAHBStartFrameController.setMyIcon("/img/iconoHotel.png", bigDataAirportHotelBaselStartFrame);
				
				bigDataAirportHotelBaselStartFrame.setLocationRelativeTo(null);
				
				bigDataAirportHotelBaselStartFrame.setVisible(true);
			}
		});

		
		
	}

}
	