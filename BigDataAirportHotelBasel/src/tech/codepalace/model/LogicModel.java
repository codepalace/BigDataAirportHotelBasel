package tech.codepalace.model;

import javax.swing.JFrame;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description abstract class. it contains the common methods that all other classes are going to be using them.
 * 
 * I have included several methods for opening and closing JFrames
 * 
 * I could create a single method to open one JFrame and close the other using a switch statement to evaluate which JFrame is open and which JFrame must be closed, 
 * but for the moment I have preferred to create independent methods for closing and opening windows
 *
 */
public abstract class LogicModel {
	
	
	
	/**
	 * @description logoutApplication it will be called from any JFrame. The first argument is the JFrame we have to close.
	 * <p/>The second argument is the JFrame we have to make visible to makes possible the login again.
	 * @param jframeToDispose
	 * @param jframeToVisible
	 */
	public abstract void logoutApplication();
	
	
	/**
	 * @description Method to display the Parking JFrame
	 * @param parkingFrameToVisible
	 */
	public abstract void displayParking(JFrame parkingFrameToVisible, JFrame jframeToClose);
	
	
	/**
	 * @description Method to display the FundSache JFrame
	 * @param fundSachenToVisible
	 */
	public abstract void displayFundSachen(JFrame fundSachenToVisible, JFrame JframeToClose);
	
	
	/**
	 * @description Method to display FitnessAbo JFrame
	 * @param fitnessAboToVisible
	 */
	public abstract void displayFitnessAbo(JFrame fitnessAboToVisible, JFrame JframeToClose);
	
	
	/**
	 * Method to display Uebergabe JFrame
	 * @param uebergabeToVisible
	 */
	public abstract void displayUebergabe(JFrame uebergabeToVisible, JFrame JframeToClose);
	
	
	/**
	 * Method to display PhoneBook JFrame
	 * @param phoneBookToVisible
	 */
	public abstract void displayPhoneBook(JFrame phoneBookToVisible, JFrame JframeToClose);

}
