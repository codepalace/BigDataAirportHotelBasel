package tech.codepalace.model;

import javax.swing.JOptionPane;

import tech.codepalace.view.frames.DataBaseGUI;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @derscription Logic Class for the Fitness Subscription.
 *
 */
public class LogicModelFitnessAbo extends LogicModel {
	
	private static DataBaseGUI dataBaseGUI;
	
	
 public LogicModelFitnessAbo() {}
	
 
 public LogicModelFitnessAbo(DataBaseGUI dataBaseGUI) {
	 
	 
	 LogicModelFitnessAbo.dataBaseGUI = dataBaseGUI;
 }
 
 
 /**
  * @description Method to create a new Fitness-Subscription
  * @param dataBaseGUI
  */
 public void createNewFitnessSubscription(DataBaseGUI dataBaseGUI) {
	 
	 //Set the value from our DataBaseGUI
	 LogicModelFitnessAbo.dataBaseGUI = dataBaseGUI;
	 
	 JOptionPane.showMessageDialog(null, "Time to create new Fitness subscription");
 }
	

}
