package tech.codepalace.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import tech.codepalace.utility.RoundJTextField;

@SuppressWarnings("serial")
public class TopPanelFundSachen extends JPanel {
	
	private JPanel  centerPanelButtons, containerPanelButton, containerSearchSelect;
	
	//Border for the centerPanelButtons
	private Border etchedBorder;
		
	public JLabel fundsachenImage;
	
	private JButton btnHome,  btnParking,  btnFitness,
	 btnPhonebook,  btnLogout, btnNewFundsachen;
	
    //JTextField with rounded Border for the Search Entries
	private RoundJTextField searchText;
	
	//Object container Panel with background picture to add the searchText object.
	private PanelWithBackgroundOption containerSearch;
	
	public TopPanelFundSachen(JButton btnHome, JButton btnParking, JButton btnFitness,
			JButton btnPhonebook, JButton btnLogout, JButton btnNewFundsachen) {
		
		this.btnHome = btnHome;
		this.btnParking = btnParking;
		this.btnFitness = btnFitness;
		this.btnPhonebook = btnPhonebook;
		this.btnLogout = btnLogout;
		this.btnNewFundsachen = btnNewFundsachen;
		
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
		this.containerPanelButton.add(centerPanelButtons, BorderLayout.NORTH);

		this.centerPanelButtons.add(this.btnHome);
		this.centerPanelButtons.add(this.btnParking);
		this.centerPanelButtons.add(this.btnFitness);
		this.centerPanelButtons.add(this.btnPhonebook);
		this.centerPanelButtons.add(this.btnLogout);
		this.centerPanelButtons.add(this.btnNewFundsachen);
		

		this.fundsachenImage = new JLabel(new ImageIcon(getClass().getResource("/img/treasure_350x200.png")));
		
		this.setLayout(new BorderLayout());
		
		this.setOpaque(false);
		
		//Initialize the searchText object 14 Column
		this.searchText = new RoundJTextField(14);
		this.searchText.setFont(new Font("Verdana", Font.BOLD, 18));
		this.searchText.setBackground(Color.GRAY);
		
		//Initialize the containerSearch Object
		this.containerSearch = new PanelWithBackgroundOption();
		
		//set the image for the background to this container.
		this.containerSearch.setImage("/img/lupe_black_gray.png");
		
		//Setting the size to the containerSearch
		this.containerSearch.setPreferredSize(new Dimension(340, 50));
		
		//Container containerSearchSelect initialize with the FlowLayout align Left.
		this.containerSearchSelect = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.containerSearchSelect.setOpaque(false);
		
		//Set Empty Border to push the searchText in the position what we want.
		this.containerSearch.setBorder(BorderFactory.createEmptyBorder(4, 32, 0, 0));
		this.containerSearch.setOpaque(false);

		//Add the searchText to the containerSearch
		this.containerSearch.add(searchText);
		
		//And add the containerSearch to the containerSearchSelect
		this.containerSearchSelect.add(containerSearch);
		
		//And add the containerSearchSelect to the containerPanelButton position Center.
		this.containerPanelButton.add(containerSearchSelect, BorderLayout.CENTER);
		
		this.add(this.fundsachenImage, BorderLayout.WEST);
		this.add(containerPanelButton, BorderLayout.CENTER);
		
		
				
				
				
				
				
	}

}
