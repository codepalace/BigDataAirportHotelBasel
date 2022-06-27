package tech.codepalace.view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class TopPanelFundSachen extends JPanel {
	
	private JPanel  centerPanelButtons, containerPanelButton;
	
	//Border for the centerPanelButtons
	private Border etchedBorder;
		
	public JLabel fundsachenImage;
	
	private JButton btnHome,  btnParking,  btnFitness,
	 btnPhonebook,  btnLogout;
	
	public TopPanelFundSachen(JButton btnHome, JButton btnParking, JButton btnFitness,
			JButton btnPhonebook, JButton btnLogout) {
		
		this.btnHome = btnHome;
		this.btnParking = btnParking;
		this.btnFitness = btnFitness;
		this.btnPhonebook = btnPhonebook;
		this.btnLogout = btnLogout;
		
		init();
	}
	
	private void init() {
		
		// We initialize the panel of the buttons
		this.centerPanelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// Initialize the Border
		this.etchedBorder = BorderFactory.createEtchedBorder();

		this.centerPanelButtons.setBorder(etchedBorder);

		this.centerPanelButtons.setOpaque(false);

		this.containerPanelButton = new JPanel(new BorderLayout());
		this.containerPanelButton.setOpaque(false);
		this.containerPanelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.containerPanelButton.add(centerPanelButtons, BorderLayout.SOUTH);

		this.centerPanelButtons.add(this.btnHome);
		this.centerPanelButtons.add(this.btnParking);
		this.centerPanelButtons.add(this.btnFitness);
		this.centerPanelButtons.add(this.btnPhonebook);
		this.centerPanelButtons.add(this.btnLogout);

		this.fundsachenImage = new JLabel(new ImageIcon(getClass().getResource("/img/treasure_350x200.png")));
		
		this.setLayout(new BorderLayout());
		
		this.setOpaque(false);
		
		this.add(this.fundsachenImage, BorderLayout.WEST);
		this.add(containerPanelButton, BorderLayout.CENTER);
		
		
				
				
				
				
				
	}

}
