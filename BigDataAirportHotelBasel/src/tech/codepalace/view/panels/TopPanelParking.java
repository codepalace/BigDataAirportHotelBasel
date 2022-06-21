package tech.codepalace.view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


/**
 * 
 * @author tonimacaroni
 *
 *@description Class that only imports the JPanel to the DataBaseGUI class corresponding to the parking application.
 *
 *<p>This class has the JPanel with the Menu buttons and the Image corresponding to the Parking.</p>
 */
@SuppressWarnings("serial")
public class TopPanelParking extends JPanel {
	
	private JPanel  centerPanelButtons, containerPanelButton;
	
	//Border for the centerPanelButtons
	private Border etchedBorder;
	
	public JLabel parkingImage;
	
	private JButton btnHome,  btnFundsachen,  btnFitness,
	 btnPhonebook,  btnLogout,  btnNewParking;
	


	
	public TopPanelParking(JButton btnHome, JButton btnFundsachen, JButton btnFitness,
			JButton btnPhonebook, JButton btnLogout, JButton btnNewParking) {
		
		this.btnHome = btnHome;
		this.btnFundsachen = btnFundsachen;
		this.btnFitness = btnFitness;
		this.btnPhonebook = btnPhonebook;
		this.btnLogout = btnLogout;
		this.btnNewParking = btnNewParking;
		
		init();
		
		
	}
	
	private void init() {
		
		
		//We initialize the panel of the buttons
		this.centerPanelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		//Initialize the Border
		this.etchedBorder = BorderFactory.createEtchedBorder();
				
		this.centerPanelButtons.setBorder(etchedBorder);
				
		this.centerPanelButtons.setOpaque(false);
				
		this.containerPanelButton = new JPanel(new BorderLayout());
		this.containerPanelButton.setOpaque(false);
		this.containerPanelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.containerPanelButton.add(centerPanelButtons, BorderLayout.SOUTH);
		
		this.centerPanelButtons.add(this.btnHome);
		this.centerPanelButtons.add(this.btnFundsachen);
		this.centerPanelButtons.add(this.btnFitness);
		this.centerPanelButtons.add(this.btnPhonebook);
		this.centerPanelButtons.add(this.btnLogout);
		this.centerPanelButtons.add(this.btnNewParking);
		
		this.parkingImage = new JLabel(new ImageIcon(getClass().getResource("/img/cadillac_oldtimer350x200.png")));

		this.setLayout(new BorderLayout());
		
		this.setOpaque(false);
		
		
		
		this.add(this.parkingImage, BorderLayout.WEST);
		this.add(containerPanelButton, BorderLayout.CENTER);
		
		
	}


}
