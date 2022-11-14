package tech.codepalace.model;

import java.awt.EventQueue;

import tech.codepalace.controller.NewFitnessAboController;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFitnessAbo;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @derscription Logic Class for the Fitness Subscription.
 *
 */
public class LogicModelFitnessAbo extends LogicModel {
	
	private static DataBaseGUI dataBaseGUI;
	
	private String  abkuerzungMA = "";
	
	
 public LogicModelFitnessAbo() {}
	
 
 public LogicModelFitnessAbo(DataBaseGUI dataBaseGUI) {
	 
	 
	 LogicModelFitnessAbo.dataBaseGUI = dataBaseGUI;
	 
	 this.dataEncryption = new DataEncryption();
 }
 
 
 /**
  * @description Method to create a new Fitness-Subscription
  * @param dataBaseGUI
  */
 public void createNewFitnessSubscription(DataBaseGUI dataBaseGUI) {
	 
	 //Set the value from our DataBaseGUI
	 LogicModelFitnessAbo.dataBaseGUI = dataBaseGUI;
	 
	 try {
		this.abkuerzungMA = this.dataEncryption.decryptData(getUserAHB().getAbkuerzungMA());
	} catch (Exception e) {
		e.printStackTrace();
	}
	 
	 //New Thread to display NewFitnessAbo GUI.
	 EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
	 
				//New NewFitnessAbo Instance declared and initialized here by the Thread
				NewFitnessAbo newFitnessAbo = new NewFitnessAbo(LogicModelFitnessAbo.dataBaseGUI, true, abkuerzungMA);
	 
				//New LogicModelNewFitnessAbo declared and initialized here by the Thread
				LogicModelNewFitnessAbo logicModelNewFitnessAbo = new LogicModelNewFitnessAbo(dataBaseGUI, newFitnessAbo, getUserAHB(), new Loading(dataBaseGUI, true));
	 
				//New instance of NewFitnessAboController
				new NewFitnessAboController(newFitnessAbo, logicModelNewFitnessAbo);
	 
				//Center the GUI NewFitnessAbo to the screen
				newFitnessAbo.setLocationRelativeTo(null);
	 
				//Finally we make it visible.
				newFitnessAbo.setVisible(true);
	 
			}
		});
	 
 }
	

}
