package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFitnessAbo;

public class LogicModelNewFitnessAbo extends LogicModel{
	
	private DataBaseGUI dataBaseGUI;
	private NewFitnessAbo newFitnessAbo;
	private UserAHB userAHB;
	private Loading loading;
	
	private ImageIcon preventionImage = new ImageIcon(getClass().getResource("/img/prevention.png"));
	
	//dataBaseGUI, newFundsachen, getUserAHB(), loading
	public LogicModelNewFitnessAbo(DataBaseGUI dataBaseGUI, NewFitnessAbo newFitnessAbo, UserAHB userAHB, Loading loading) {
		
		this.dataBaseGUI = dataBaseGUI;
		this.newFitnessAbo = newFitnessAbo;
		this.userAHB = userAHB;
		this.loading = loading;
		
	}
	
	
	public void closeNeueFitnessAbo() {
		
		//Variable below int get the value depending what the user decide to do, if press Yes GUI will be closed.
		int selectedOption = JOptionPane.showConfirmDialog(null, "Möchten Sie das neue Abonnement abbrechen?",
			"Neue Abonnement abbrechen", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, preventionImage);
				
				//if the user is sure he wants to delete the entry. He has pressed Button Yes. 
				if (selectedOption == JOptionPane.YES_OPTION) {
					
					this.newFitnessAbo.dispose();
						
		}
	}

}
