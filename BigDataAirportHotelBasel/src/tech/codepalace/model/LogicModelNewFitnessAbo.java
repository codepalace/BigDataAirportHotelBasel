package tech.codepalace.model;

import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFitnessAbo;

public class LogicModelNewFitnessAbo extends LogicModel{
	
	private DataBaseGUI dataBaseGUI;
	private NewFitnessAbo newFitnessAbo;
	private UserAHB userAHB;
	private Loading loading;
	
	//dataBaseGUI, newFundsachen, getUserAHB(), loading
	public LogicModelNewFitnessAbo(DataBaseGUI dataBaseGUI, NewFitnessAbo newFitnessAbo, UserAHB userAHB, Loading loading) {
		
		this.dataBaseGUI = dataBaseGUI;
		this.newFitnessAbo = newFitnessAbo;
		this.userAHB = userAHB;
		this.loading = loading;
		
	}

}
