package tech.codepalace.view.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class TopPanel Phone Book
 *
 */
@SuppressWarnings("serial")
public class TopPanelTelefonbuch extends JPanel {
	
	//Variables for the different panels.
	private JPanel  centerPanelButtons, containerPanelButton, containerSearchSelect;
	
	//Border for the centerPanelButtons
	private Border etchedBorder;
	
	//JLabel for the HandOver shift transfer informations image(TopPanel image).
	public JLabel telefonImage;
	
	private JButton btnHome,  btnParking, btnFundsachen,  btnFitness, 
	 btnUerbergabe,  btnLogout, reloadDdJButton;

}
