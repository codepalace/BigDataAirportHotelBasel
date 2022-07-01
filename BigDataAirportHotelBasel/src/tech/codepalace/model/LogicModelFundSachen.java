package tech.codepalace.model;

import javax.swing.JOptionPane;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFundsachen;

public class LogicModelFundSachen extends LogicModel {
	
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	

	
	private DataBaseGUI dataBaseGUI;
	private Loading loading;
	
	private String  abkuerzungMA = "";
	
	private DataEncryption dataEncryption;
	
//	public LogicModelFundSachen(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
//		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
//		
//		
//	}
	
	
	public LogicModelFundSachen(DataBaseGUI dataBaseGUI, Loading loading) {
		this.dataBaseGUI = dataBaseGUI;
		this.loading = loading;
		
		//We create a new Instance fo DataEncryption, needed to decrypt some Data we need.
		this.dataEncryption = new DataEncryption();
		
		
	}
	
	public LogicModelFundSachen() {
		
		//We create a new Instance fo DataEncryption, needed to decrypt some Data we need.
		this.dataEncryption = new DataEncryption();
				
	}
	
	
	
	
	public void enterNewFoundsachenEntries(DataBaseGUI dataBaseGUI) {
		
		
		
		this.dataBaseGUI = dataBaseGUI;
		
		
		try {
			/* With the help of the dataEncryption object we set the value to the variable abkuerzungMA, calling to decryptData and for that we 
			 * also call getUserAHB().getAbbkuerzungMA() to get the value we need. 
			 * 
			 * AbbkuerzungMA is the Team user who is typing the new entries in the database.
			 *
			 * 
			 */
		    abkuerzungMA = this.dataEncryption.decryptData(getUserAHB().getAbkuerzungMA());
		    
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		//	public NewFundsachen(DataBaseGUI dataBaseGUI, boolean modal, String abkuerzungMA) {
		NewFundsachen newFundsachen = new NewFundsachen(dataBaseGUI, true, abkuerzungMA);
		
		newFundsachen.setLocationRelativeTo(null);
		
		newFundsachen.setVisible(true);
		
		
		
	}

}
