package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tech.codepalace.utility.CenterComponentMoved;
import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;



/**
 * 
 * @author tonimacaroni
 * @description class to set database path and database backup path
 *
 */
@SuppressWarnings("serial")
public class ConfigurationDirectory extends JDialog {


	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	private JPanel northPanel, southPanel;
	
	private JLabel dataBaseLocalJLabel, backupDataBaseJLabel; 

	public JLabel dataBaseSelectedJLabel, backupDataBaseSelectedJLabel;
	
	public JButton selectDatabaseLocalJButton, selectBackupDataBaseJButton, savePathsJButton, cancelSavePathsJButton, loadConfig;

	private GridBagLayout gridBagLayoutNorth;
	private GridBagConstraints gridBagConstraintsNorth;
	
	
	//Elements for closing the configuration GUI
	public JDialog exitDialog; 
	public JButton exitJButton, cancelJButton;
	public Object[] option;
	public JPanel dialogJPanel, jButtonsPanel;
	public JLabel messageExit;
	public ImageIcon imageDialogExit;
	
	
	
	/**
	 * @description ConfigurationDirectory extends from JDialog to block the BigDataAirportHotelBaselStartFrame GUI until the user has proceeded with the configuration that must be set.
	 * <p>When the value modal is true, BigDataAirportHotelBaselStartFrame will be blocked in background. 
	 * @param bigDataAirportHotelBaselStartFrame
	 * @param modal
	 */
	public ConfigurationDirectory(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, boolean modal) {
		super(bigDataAirportHotelBaselStartFrame, modal);
		
		
		
		
		init();
		
	}
	
	
	private void init() {
		
		this.setSize(480, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("Datenbankpfad und Datenbankbackuppfad festlegen");
		this.setIconImage(new ImageIcon("/img/iconoHotel.png").getImage());
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		
		this.setContentPane(this.panelWithBackgroundOption);

		
		this.setLocationRelativeTo(new CenterComponentMoved(this));
		
		this.northPanel = new JPanel();
		this.southPanel = new JPanel(new BorderLayout());
		
		this.gridBagLayoutNorth = new GridBagLayout();
	
		
		this.gridBagConstraintsNorth = new GridBagConstraints();
	
		
		
		this.northPanel.setLayout(gridBagLayoutNorth);
		this.northPanel.setOpaque(false);
	
		
		
		this.dataBaseLocalJLabel = new JLabel("Datenbankpfad wählen:");
		this.dataBaseLocalJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.dataBaseLocalJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.dataBaseLocalJLabel.setPreferredSize(new Dimension(290,20));
		
		this.backupDataBaseJLabel = new JLabel("Backup Datenbankpfad wählen:");
		this.backupDataBaseJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.backupDataBaseJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.backupDataBaseJLabel.setPreferredSize(new Dimension(290,20));
		
		this.selectDatabaseLocalJButton = new MyButton("/img/dbselect.png"); 
		this.selectBackupDataBaseJButton = new MyButton("/img/backupdbselected.png");
		this.savePathsJButton = new MyButton("/img/btnspeicherdb.png");
		this.savePathsJButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));
		this.cancelSavePathsJButton = new MyButton("/img/abbrechendb.png");
		this.cancelSavePathsJButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
		
		this.loadConfig = new JButton("Load Config");
		this.loadConfig.setPreferredSize(new Dimension(100, 20)); //will be change the design in next.
		
		
		this.dataBaseSelectedJLabel = new JLabel();
		this.dataBaseSelectedJLabel.setPreferredSize(new Dimension(420,20));
		this.dataBaseSelectedJLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		this.backupDataBaseSelectedJLabel = new JLabel();
		this.backupDataBaseSelectedJLabel.setPreferredSize(new Dimension(420,20));
		this.backupDataBaseSelectedJLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		this.gridBagConstraintsNorth.gridx = 0;
		this.gridBagConstraintsNorth.gridy = 0;
		this.gridBagConstraintsNorth.insets = new Insets(5, 0, 0, 0);
		this.northPanel.add(this.dataBaseLocalJLabel, gridBagConstraintsNorth);
		
		this.gridBagConstraintsNorth.gridx = 1; 
		this.gridBagConstraintsNorth.gridy = 0; 
		this.gridBagConstraintsNorth.insets = new Insets(5, 15, 0, 0);
		this.northPanel.add(this.selectDatabaseLocalJButton, gridBagConstraintsNorth);
		
		
		this.gridBagConstraintsNorth.gridx = 0; 
		this.gridBagConstraintsNorth.gridy = 1; 
		this.gridBagConstraintsNorth.gridwidth = 2;
		this.gridBagConstraintsNorth.insets = new Insets(10, 0, 0, 0);
		
		
		this.dataBaseSelectedJLabel.setOpaque(true);
		this.dataBaseSelectedJLabel.setBackground(Color.GRAY);
		this.northPanel.add(this.dataBaseSelectedJLabel, gridBagConstraintsNorth);
		
		
		
		this.gridBagConstraintsNorth.gridx = 0; 
		this.gridBagConstraintsNorth.gridy = 2; 
		this.gridBagConstraintsNorth.insets = new Insets(15, 0, 0, 0);
		this.gridBagConstraintsNorth.gridwidth = 1;
		this.northPanel.add(this.backupDataBaseJLabel, gridBagConstraintsNorth);
		
		this.gridBagConstraintsNorth.gridx = 1;
		this.gridBagConstraintsNorth.gridy = 2; 
		this.gridBagConstraintsNorth.insets = new Insets(15, 15, 0, 0);
		this.northPanel.add(this.selectBackupDataBaseJButton, gridBagConstraintsNorth);
		
		
		this.gridBagConstraintsNorth.gridx = 0; 
		this.gridBagConstraintsNorth.gridy = 3; 
		this.gridBagConstraintsNorth.gridwidth = 2;
		this.gridBagConstraintsNorth.insets = new Insets(10, 0, 0, 0);
		
		this.backupDataBaseSelectedJLabel.setOpaque(true);
		this.backupDataBaseSelectedJLabel.setBackground(Color.GRAY);
		this.northPanel.add(this.backupDataBaseSelectedJLabel, gridBagConstraintsNorth);
		
		
		
		
		
		
		this.panelWithBackgroundOption.add(this.northPanel, BorderLayout.NORTH);
		
		this.southPanel.setOpaque(false);
		this.southPanel.add(cancelSavePathsJButton, BorderLayout.WEST);
		this.southPanel.add(this.loadConfig, BorderLayout.CENTER);
		this.southPanel.add(savePathsJButton, BorderLayout.EAST);
		
		this.panelWithBackgroundOption.add(this.southPanel);
		
		
		
		
		
		this.exitDialog = new JDialog();
		this.exitJButton = new JButton("Beenden");
		this.cancelJButton = new JButton("Abbrechen");
		
		this.option = new Object[] {this.cancelJButton, this.exitJButton};
		
		this.dialogJPanel = new JPanel(new BorderLayout());
		this.messageExit = new JLabel("Wollen Sie das Programm verlassen?");
		
		this.jButtonsPanel = new JPanel(new BorderLayout());
		this.jButtonsPanel.add(exitJButton, BorderLayout.EAST);
		this.jButtonsPanel.add(this.cancelJButton, BorderLayout.WEST);
		
		this.dialogJPanel.add(this.messageExit, BorderLayout.CENTER);
		this.dialogJPanel.add(this.jButtonsPanel, BorderLayout.SOUTH);
		
		this.imageDialogExit = new ImageIcon(getClass().getResource("/img/prevention.png"));
		
		
		
		
		
		
		
	}
	
	
	
	
	public JLabel getDataBaseSelectedPath() {
		return this.dataBaseSelectedJLabel;
	}
	
	
	
	
	public JLabel getBackupDatabaseSelectedPath() {
		
		return this.backupDataBaseSelectedJLabel;
		
	}
	
	
	
	public void confirmClose() {
		this.exitDialog = new JOptionPane(this.dialogJPanel, JOptionPane.OK_OPTION, 
				JOptionPane.NO_OPTION, this.imageDialogExit, this.option, null).createDialog("Programm Verlassen");
		
		this.exitDialog.setVisible(true);
		
	}
	
	
	
	

}
