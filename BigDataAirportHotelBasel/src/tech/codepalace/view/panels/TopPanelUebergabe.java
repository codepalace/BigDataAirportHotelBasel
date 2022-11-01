package tech.codepalace.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import tech.codepalace.utility.RoundJTextField;
import tech.codepalace.utility.SelectCustomJComboBox;
import tech.codepalace.utility.SimpleRoundBorder;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class TopPanel HandOver or Shift transfer informations
 *
 */
@SuppressWarnings("serial")
public class TopPanelUebergabe extends JPanel {
	
	private JPanel  centerPanelButtons, containerPanelButton, containerSearchSelect;
	public JPanel containerMonthSelect;
	
	//Border for the centerPanelButtons
	private Border etchedBorder;
	
	//JLabel for the HandOver shift transfer informations image(TopPanel image).
	public JLabel uebergabeImage; 
	
	private JButton btnHome,  btnParking, btnFundsachen,  btnFitness, 
	 btnPhonebook,  btnLogout, reloadDdJButton;
	
	// JTextField with rounded Border for the Search Entries
	private RoundJTextField searchText;

	// Object container Panel with background picture to add the searchText object.
	private PanelWithBackgroundOption containerSearch;

	// JComboBox to select how we search the result
	public JComboBox<String> searchJComboBox;
	
	
	
	public TopPanelUebergabe(JButton btnHome, JButton btnParking, JButton btnFundsachen, JButton btnFitness,
			JButton btnPhonebook, JButton btnLogout, 
			RoundJTextField searchText, JComboBox<String> searchJComboBox, JButton reloadDdJButton) {
		
		this.btnHome = btnHome;
		this.btnParking = btnParking;
		this.btnFundsachen = btnFundsachen;
		this.btnFitness = btnFitness;
		this.btnPhonebook = btnPhonebook;
		this.btnLogout = btnLogout;
		this.searchText = searchText;
		this.searchJComboBox = searchJComboBox;
		this.reloadDdJButton = reloadDdJButton;
		
		//Initialize the elements calling the init Method.
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
				this.containerPanelButton.add(centerPanelButtons, BorderLayout.NORTH);
				
				this.centerPanelButtons.add(this.btnHome);
				this.centerPanelButtons.add(this.btnParking);
				this.centerPanelButtons.add(this.btnFundsachen);
				this.centerPanelButtons.add(this.btnFitness);
				this.centerPanelButtons.add(this.btnPhonebook);
				this.centerPanelButtons.add(this.btnLogout);
				
				
				this.uebergabeImage = new JLabel(new ImageIcon(getClass().getResource("/img/office_woman.png")));

				this.setLayout(new BorderLayout());
				
				this.setOpaque(false);
				
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
						
						
						this.searchJComboBox.setFont(new Font("Verdana", Font.BOLD, 16));
						
						
						//We make setUI for a Custom JComboBox
						this.searchJComboBox.setUI(SelectCustomJComboBox.createUI(this, "down_black_arrow.png", "pink_arrow_right.png"));
						
						this.searchJComboBox.setPreferredSize(new Dimension(350, 40));
					
						
						//Background for the JComboBox
						this.searchJComboBox.setBackground(Color.LIGHT_GRAY);
						
					
						//We set Round Border to the JComboBox
						this.searchJComboBox.setBorder(new SimpleRoundBorder());
						

						//Add JComboBox to our GUI
						this.containerSearchSelect.add(searchJComboBox);
						
						
						//Add reloadDdJButton to our GUI 
						this.containerSearchSelect.add(reloadDdJButton);
				
						this.add(this.uebergabeImage, BorderLayout.WEST);
						this.add(containerPanelButton, BorderLayout.CENTER);
				
	}
	
	
	

}
