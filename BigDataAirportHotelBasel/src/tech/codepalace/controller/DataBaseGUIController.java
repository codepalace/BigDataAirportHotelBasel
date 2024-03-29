package tech.codepalace.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import tech.codepalace.model.LogicModelFitnessAbo;
import tech.codepalace.model.LogicModelFundSachen;
import tech.codepalace.model.LogicModelParking;
import tech.codepalace.model.LogicModelTelefonbuch;
import tech.codepalace.model.LogicModelUebergabe;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;


/**
 * 
 * 
 * @description Controller Class for DataBaseGUI.
 * <p>This class receives as an argument BigDataAirportHotelBaselStartFrame Object so we can call this object anyTime back.</p>
 * <p>The DataBaseGUI Object is the class will be controlled by this one.</p>
 * <p>The LogicModelParking so we can call anyTime for creating a new Parking-Reservation for example.</p>
 * <p>The TableModelListener will be used to interact with the changes of the cells by any JTable.</p>
 * <p>The ItemListener will be used in this class to interact with the itemStateChanged by JComboBox. 
 * So inside the itemStateChanged Method we give the Focus to searchText JTextBox.</p>
 * <p>FocusListener for the focusGained and focusLost.</p>
 * <p>PopupMenuListener for the PopUp Menu.</p>
 */
public class DataBaseGUIController implements ActionListener, KeyListener, WindowListener, 
TableModelListener, ItemListener, FocusListener, MouseListener, PopupMenuListener {
	

	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	private DataBaseGUI dataBaseGUI;
	private LogicModelParking logicModelParking;
	private LogicModelFundSachen logicModelFundSachen;
	private LogicModelFitnessAbo logicModelFitnessAbo;
	private LogicModelUebergabe logicModelUebergabe;
	private LogicModelTelefonbuch logicModelTelefonbuch;
	
	
	private TableModel model;
	

	//Variables to get the value of the selected row and column in the JTable.
	private int selectedRow=0;
	private int selectedColumn=0;
	
	private String[]dates;
	
	protected String privilege;
	
	private DataEncryption dataEncryption;
	
	private ImageIcon imgError = new ImageIcon(getClass().getResource("/img/error.png"));
	
	public DataBaseGUIController(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, 
			DataBaseGUI dataBaseGUI, LogicModelParking logicModelParking, LogicModelFundSachen logicModelFundSachen, 
			LogicModelFitnessAbo logicModelFitnessAbo, LogicModelUebergabe logicModelUebergabe, LogicModelTelefonbuch logicModelTelefonbuch) {
		
		//We set the values to the Objects
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.dataBaseGUI = dataBaseGUI;
		this.logicModelParking = logicModelParking;
		this.logicModelFundSachen = logicModelFundSachen;
		this.logicModelFitnessAbo = logicModelFitnessAbo;
		this.logicModelUebergabe = logicModelUebergabe;
		this.logicModelTelefonbuch = logicModelTelefonbuch;
		
		this.dataEncryption = new DataEncryption();

		
		
		
		//Add WindowListener Listener
		this.dataBaseGUI.addWindowListener(this);
		
		
		//Add the ActionListener to our elements
		this.dataBaseGUI.btnHome.addActionListener(this);
		
		this.dataBaseGUI.btnExitDBGUI.addActionListener(this);
		
		this.dataBaseGUI.btnNoExitDBGUI.addActionListener(this);
		
		this.dataBaseGUI.btnParking.addActionListener(this);
		
		this.dataBaseGUI.btnNewParking.addActionListener(this);
		
		this.dataBaseGUI.btnNewFundsachen.addActionListener(this);
		
		this.dataBaseGUI.btnLogout.addActionListener(this);
		
		this.dataBaseGUI.btnFundsachen.addActionListener(this);
		
		this.dataBaseGUI.btnFitness.addActionListener(this);
		
		this.dataBaseGUI.btnUerbergabe.addActionListener(this);
		
		this.dataBaseGUI.btnPhonebook.addActionListener(this);
		
		//Add ActionListener to the MenutItem of the popupMenu
		this.dataBaseGUI.deleteItem.addActionListener(this);
		
		this.dataBaseGUI.btnNewFitnessAbo.addActionListener(this);
		
		
		
		
		
		
		
		//Add the KeyListener to the elements
		this.dataBaseGUI.btnHome.addKeyListener(this);
		
		this.dataBaseGUI.btnNewParking.addKeyListener(this);
		
		this.dataBaseGUI.btnNewFundsachen.addKeyListener(this);

		this.dataBaseGUI.searchText.addKeyListener(this);
		
		this.dataBaseGUI.reloadDdJButton.addActionListener(this);
		
		this.dataBaseGUI.btnFundsachen.addKeyListener(this);
		
		this.dataBaseGUI.btnFitness.addKeyListener(this);
		
		this.dataBaseGUI.btnUerbergabe.addKeyListener(this);
		
		this.dataBaseGUI.btnPhonebook.addKeyListener(this);
		
		this.dataBaseGUI.btnNewFitnessAbo.addKeyListener(this);	
		
		
		
		
		
		//Add FocusListener to the elements
		this.dataBaseGUI.btnHome.addFocusListener(this);
				
		this.dataBaseGUI.btnParking.addFocusListener(this);
		
		this.dataBaseGUI.btnFundsachen.addFocusListener(this);
				
		this.dataBaseGUI.btnFitness.addFocusListener(this);
				
		this.dataBaseGUI.btnUerbergabe.addFocusListener(this);
				
		this.dataBaseGUI.btnPhonebook.addFocusListener(this);
				
		this.dataBaseGUI.btnLogout.addFocusListener(this);
				
		this.dataBaseGUI.btnNewFundsachen.addFocusListener(this);
				
		this.dataBaseGUI.searchText.addFocusListener(this);
				
		this.dataBaseGUI.searchJComboBox.addFocusListener(this);
		
		this.dataBaseGUI.btnNewFitnessAbo.addFocusListener(this);
		
		
		
		
		//Add ItemListener
		this.dataBaseGUI.searchJComboBox.addItemListener(this);
		
		

		
		/*
		 * depending on the table that is visible it will be add TableModelListener. 
		 * 
		 * So we can know if some cell in the JTable has been modified.
		 * 
		 * For that we go to the Method tableChanged
		 */
		if(this.dataBaseGUI.parkingTable != null) {
			this.dataBaseGUI.parkingTable.getModel().addTableModelListener(this);
			this.dataBaseGUI.parkingTable.addKeyListener(this);
			this.dataBaseGUI.parkingTable.addMouseListener(this);
			this.dataBaseGUI.parkingTable.addFocusListener(this);
			
			//Add ItemListener to the schluesselInHausJComboBox
			this.dataBaseGUI.schluesselInHausJComboBox.addItemListener(this);
			this.dataBaseGUI.buchungskanalJComboBox.addItemListener(this);
		}
		
		else if(this.dataBaseGUI.fundsachenTable != null) {
			this.dataBaseGUI.fundsachenTable.getModel().addTableModelListener(this);
			this.dataBaseGUI.fundsachenTable.addKeyListener(this);
			this.dataBaseGUI.fundsachenTable.addMouseListener(this);
			
			//Add ItemListener to the kisteNummerJComboBox
			this.dataBaseGUI.kisteNummerJComboBox.addItemListener(this);
			
			this.dataBaseGUI.displayMothLostAndFoundJComboBox.addItemListener(this);
		}
		
		else if(this.dataBaseGUI.fitnessAboTable !=null) {
			
			this.dataBaseGUI.fitnessAboTable.getModel().addTableModelListener(this);
			this.dataBaseGUI.fitnessAboTable.addKeyListener(this);
			this.dataBaseGUI.fitnessAboTable.addMouseListener(this);
			this.dataBaseGUI.fitnessAboTable.addFocusListener(this);
			
		}
		
		else if(this.dataBaseGUI.uebergabeTable !=null) {
			
			this.dataBaseGUI.uebergabeTable.getModel().addTableModelListener(this);
			this.dataBaseGUI.uebergabeTable.addKeyListener(this);
			this.dataBaseGUI.uebergabeTable.addMouseListener(this);
			this.dataBaseGUI.uebergabeTable.addFocusListener(this);
		}
		
		
		else if(this.dataBaseGUI.kontakteTable !=null) {
			
			this.dataBaseGUI.kontakteTable.getModel().addTableModelListener(this);
			this.dataBaseGUI.kontakteTable.addKeyListener(this);
			this.dataBaseGUI.kontakteTable.addMouseListener(this);
			this.dataBaseGUI.kontakteTable.addFocusListener(this);
			
		}
		

		/*  btnHome request the Focus so we call the KeyCode keys better when the focus is always by btnHome */
		this.dataBaseGUI.btnHome.requestFocus();


	}
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==this.dataBaseGUI.btnHome) {
		
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.dispose();
			
		} 
		
		else if(e.getSource()== this.dataBaseGUI.btnExitDBGUI) {
			
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.exitDBGUI.dispose();
			this.dataBaseGUI.dispose();
			
		} 
		
		else if(e.getSource()==this.dataBaseGUI.btnParking) {	
		     this.logicModelParking.displayParking(bigDataAirportHotelBaselStartFrame);

		}
		
		else if(e.getSource()==this.dataBaseGUI.btnLogout) {
			
			this.dataBaseGUI.dispose();
			
			this.logicModelFundSachen.logoutApplication();
		}
		
		else if (e.getSource()== this.dataBaseGUI.btnNoExitDBGUI) {
			
			this.dataBaseGUI.exitDBGUI.dispose();
		
		} 
		
		
		
		else if(e.getSource()==this.dataBaseGUI.btnNewParking) {
			
			this.logicModelParking.createNewParkingReservation(dataBaseGUI);
		
		} 
		
		
		
		else if(e.getSource()==this.dataBaseGUI.btnNewFundsachen) {
			
			this.logicModelFundSachen.enterNewFoundsachenEntries(dataBaseGUI);
		}
		
		
		
		
		else if(e.getSource()==this.dataBaseGUI.reloadDdJButton) {
			
			
		 if(this.dataBaseGUI.fundsachenTable!=null) 
		 {	
			 /* Time to reload the Lost And Found Data and display in JTable the Results. 
			  * 
			  * As argument we pass dataBaseGUI object and a new Loading Object that receives the first argument the 
			  * GUI in background that should be blocked and true to be blocked.
			  */
			  this.logicModelFundSachen.reloadFundsachen(dataBaseGUI, new Loading(this.dataBaseGUI, true));
		  
		 }
		 
		 else if(this.dataBaseGUI.parkingTable!=null) 
		 
		 {
			 this.logicModelParking.reloadParkingDataBase(dataBaseGUI, new Loading(this.dataBaseGUI, false));
		}
		 
		 
		}
		
		
		else if(e.getSource()==this.dataBaseGUI.btnFundsachen) {
			
			
			this.logicModelParking.displayFundSachen(bigDataAirportHotelBaselStartFrame);
			
		}
		
		else if(e.getSource()==this.dataBaseGUI.btnFitness) {
			
			this.logicModelParking.displayFitnessAbo(bigDataAirportHotelBaselStartFrame);
		}
		
		
		
		else if(e.getSource()==this.dataBaseGUI.btnUerbergabe) {
			
			this.logicModelParking.displayUebergabe(bigDataAirportHotelBaselStartFrame);

		}
		
		
		else if(e.getSource()==this.dataBaseGUI.btnPhonebook) {
			
			this.logicModelParking.displayPhoneBook(bigDataAirportHotelBaselStartFrame);
		}
		
		
		else if(e.getSource()==this.dataBaseGUI.deleteItem) {

			/*
			 * we evaluate which is the table that currently exists within our GUI dataBaseGUI.
			 * 
			 * depending on the table that exists we call the delete method.
			 */
	         if(this.dataBaseGUI.fundsachenTable!=null) {
	        	 
	        	 //We set the value of selectedRow calling the getSelectedRow Method of the fundsachenTable !=null in this case.
	        	 this.selectedRow = this.dataBaseGUI.fundsachenTable.getSelectedRow();
	        	 
	        	 //We get the TableModel of the JTable
	        	 this.model = this.dataBaseGUI.fundsachenTable.getModel();
	        	 
	        	 //Only if any Row was selected 
	        	 if(this.selectedRow== -1) {
	        		//We invoke a new Thread with the error message.
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Wählen Sie zuerst den zu löschenden Eintrag aus"
								   , "Es wurde kein Eintrag ausgewählt", JOptionPane.ERROR_MESSAGE, this.imgError));
	        	 }else {
	        		 //We call the deleteRowDataBase Method with the arguments selectedRow and the name of the table we are going to try to delete.
		        	 this.logicModelFundSachen.deleteRowDataBase(this.selectedRow, "FUNDSACHEN", this.model);
	        	 }
	        	 
	        	
	         
	         }
	         
	         
		
		}
		
		
		
		else if(e.getSource()== this.dataBaseGUI.btnNewFitnessAbo) {
			
			//We call the method createNewFitnessSubscription
			this.logicModelFitnessAbo.createNewFitnessSubscription(dataBaseGUI);
			
		}

		 
		
		
		
	}

	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	
	@Override
	public void keyPressed(KeyEvent e) {
		

		//KeyPressed F1
		if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode() == 112
				||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 112
				||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 112 
				||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==112
				||e.getSource()==this.dataBaseGUI.parkingTable && e.getKeyCode() ==112
				||e.getSource()==this.dataBaseGUI.fitnessAboTable && e.getKeyCode() ==112
				||e.getSource()==this.dataBaseGUI.uebergabeTable && e.getKeyCode() ==112
				||e.getSource()==this.dataBaseGUI.kontakteTable && e.getKeyCode() ==112
				
				)
		
		
		{
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.dispose();
		}
		
		
		
		
		else //KeyPressed F2
			if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 113
					||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 113
					||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 113 
					||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==113
					||e.getSource()==this.dataBaseGUI.fitnessAboTable && e.getKeyCode() ==113
					||e.getSource()==this.dataBaseGUI.uebergabeTable && e.getKeyCode() ==113
					||e.getSource()==this.dataBaseGUI.kontakteTable && e.getKeyCode() ==113
					)
			
			{
		
				this.logicModelParking.displayParking(bigDataAirportHotelBaselStartFrame);
			}
		
		
		
		
		
			else //KeyPressed F3
				
				if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 114
				||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 114
				||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 114 
				||e.getSource()==this.dataBaseGUI.parkingTable && e.getKeyCode() ==114
				||e.getSource()==this.dataBaseGUI.fitnessAboTable && e.getKeyCode() ==114
				||e.getSource()==this.dataBaseGUI.uebergabeTable && e.getKeyCode() ==114
				||e.getSource()==this.dataBaseGUI.kontakteTable && e.getKeyCode() ==114
						) 
				
				{
					this.logicModelParking.displayFundSachen(bigDataAirportHotelBaselStartFrame);
					
				}
		
		
		
			//KeyPressed F4
			else if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 115
					||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 115
					||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 115 
					||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==115
					||e.getSource()==this.dataBaseGUI.parkingTable && e.getKeyCode() ==115
					||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==115
					||e.getSource()==this.dataBaseGUI.kontakteTable && e.getKeyCode() ==115
					)
				
			{
				
			
					this.logicModelParking.displayFitnessAbo(bigDataAirportHotelBaselStartFrame);
				
			
				
				
			}
		
		
			//KeyPressed F6
			else if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 117
					||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 117
					||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 117 
					||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==117
					||e.getSource()==this.dataBaseGUI.parkingTable && e.getKeyCode() ==117
					||e.getSource()==this.dataBaseGUI.fitnessAboTable && e.getKeyCode() ==117
					||e.getSource()==this.dataBaseGUI.kontakteTable && e.getKeyCode() ==117
					)
				
			{
				
		
					this.logicModelParking.displayUebergabe(bigDataAirportHotelBaselStartFrame);
		
				
				
			}
		
			//KeyPressed F7
			else if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 118
					||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 118
					||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 118 
					||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==118
					||e.getSource()==this.dataBaseGUI.parkingTable && e.getKeyCode() ==118
					||e.getSource()==this.dataBaseGUI.fitnessAboTable && e.getKeyCode() ==118
					||e.getSource()==this.dataBaseGUI.uebergabeTable && e.getKeyCode() ==118
					
					) {
				
				this.logicModelParking.displayPhoneBook(bigDataAirportHotelBaselStartFrame);
			}
		
		
		
		
			
		
		
			else	//KeyPressed F8
				
			if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 119
					||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 119
					||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 119 
					||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==119
					||e.getSource()==this.dataBaseGUI.parkingTable && e.getKeyCode() ==119
					||e.getSource()==this.dataBaseGUI.fitnessAboTable && e.getKeyCode() ==119
					||e.getSource()==this.dataBaseGUI.uebergabeTable && e.getKeyCode() ==119
					||e.getSource()==this.dataBaseGUI.kontakteTable && e.getKeyCode() ==119
					) 
			
			{
				
				
					this.dataBaseGUI.dispose();
				
				this.logicModelFundSachen.logoutApplication();
				
			}
	
		
			else //KeyPressed F9
				
				if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 120
						||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 120
						||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 120 
						||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==120
						||e.getSource()==this.dataBaseGUI.parkingTable && e.getKeyCode() ==120
						||e.getSource()==this.dataBaseGUI.fitnessAboTable && e.getKeyCode() ==120
						
						) 
				
				{
					
					if(this.dataBaseGUI.fundsachenTable!=null) {
						this.logicModelFundSachen.enterNewFoundsachenEntries(dataBaseGUI);
					
					} else if(this.dataBaseGUI.parkingTable!=null) {
						this.logicModelParking.createNewParkingReservation(dataBaseGUI);
					
					} else if(this.dataBaseGUI.fitnessAboTable !=null) {
						
						//We call the method createNewFitnessSubscription
						this.logicModelFitnessAbo.createNewFitnessSubscription(dataBaseGUI);
					}
					
					
					
					
				}
		
		
			
	
	
	else
		
		
			
		
		
	
		if(e.getSource()==this.dataBaseGUI.btnExitDBGUI && e.getKeyCode() ==10) {
			
		}
		
		else
		
			/*
			 * In case the user type Enter by the searchText object we call the
			 * searchResultsInDataBase Method passing argument dataBaseGUI to avoid 
			 * NullPointerException and for having available all the elements from the GUI DataBaseGUI.
			 */
			if(e.getSource()==this.dataBaseGUI.searchText  && e.getKeyCode() ==10) {
				
				
				/*
				 * We check with a conditional if else which JTable is visible
				 */
				if(this.dataBaseGUI.fundsachenTable!=null) {
					this.logicModelFundSachen.searchResultsInDataBase(this.dataBaseGUI);
				
				}
				
				else 
					if(this.dataBaseGUI.parkingTable!=null) {
						
						//case parkingTable searchResultsInDataBase called through  logicModelParking
						this.logicModelParking.searchResultsInDataBase(dataBaseGUI);
					}
			
				
		}
		
			else if(e.getSource()==this.dataBaseGUI.parkingTable) {
				
				int selectedRow = dataBaseGUI.parkingTable.getSelectedRow();


				if(this.dataBaseGUI.parkingTable.getSelectedColumn()==4) {

					TableModel model = this.dataBaseGUI.parkingTable.getModel();

			
					dates = new String[] {model.getValueAt(selectedRow, 4).toString(), model.getValueAt(selectedRow, 5).toString()};
					
					
					/*
					 we save the arrival and departure dates in an array so that we can access the old value in case of an error 
					 and return old value to the table as they were.
					 */
					this.logicModelParking.setDateAsStringToBeModified(dates);
				}
				

			}
		

		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	
	
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		
		this.bigDataAirportHotelBaselStartFrame.dispose();
		
		//Bring to front our GUI
		this.dataBaseGUI.toFront();
		//this.fundsachenTable.setComponentPopupMenu(popupMenu);	
		
		try {
			this.privilege = this.dataEncryption.decryptData(this.logicModelFundSachen.getUserAHB().getPrivilege());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(this.dataBaseGUI.fundsachenTable!=null && this.privilege.equalsIgnoreCase("ADMIN")) {
			this.dataBaseGUI.fundsachenTable.setComponentPopupMenu(this.dataBaseGUI.popupMenu);
		}


	}

	
	
	
	
	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}



	@Override
	public void tableChanged(TableModelEvent e) {

		

		/*
		 * depending on the table that is visible we get the selected Row calling the 
		 * Method getSelectedRow().
		 */
		if(this.dataBaseGUI.parkingTable != null) {
			
			selectedRow = this.dataBaseGUI.parkingTable.getSelectedRow();
			selectedColumn = this.dataBaseGUI.parkingTable.getSelectedColumn();
			
		}else if(this.dataBaseGUI.fundsachenTable != null) {
			
			selectedRow = this.dataBaseGUI.fundsachenTable.getSelectedRow();
			selectedColumn = this.dataBaseGUI.fundsachenTable.getSelectedColumn();

			
		}
		
		
		
		//TableMode will received the value of the current JTable displayed.
		model = (TableModel)e.getSource();

		
		/* To avoid one ArrayIndexOutOfBoundsException i use one conditional if 
		 * to evaluate if selectedRow =!-1 of course we can use a try-catch
		 */
		if(selectedRow!=-1) {
		

			//if parkingTable is displayed we call to update the data for Parking
			if(this.dataBaseGUI.parkingTable != null) {
				
				/*
				 * We call the Method updateFundsachen by LogicModelFundsachen. 
				 * 
				 * - First Parameter  of this Method is the selected Row
				 * - Second Parameter the TableModel needed to get all value and generated values for the Instance ParkingReservation by the Logic Class. So we can send to the DAOParking
				 * - Third Parameter the Object DataBaseGUI to be able to send it in the same way to the DAO object. 
				 */
				
				if(!this.logicModelParking.getDataBaseStatus().equalsIgnoreCase("RELOAD")) {
					
					
					
					
						this.logicModelParking.updateParking(selectedRow, selectedColumn, model, this.dataBaseGUI);
				
					
				}
				
		
			//if fundsachenTable is displayed we call to update the data for FUNDSACHEN(Lost and found)
			}else if(this.dataBaseGUI.fundsachenTable != null) {
			
		
				/*
				 * If status is not REALOAD. We call the Method updateFundsachen by LogicModelFundsachen. 
				 * 
				 * - First Parameter  of this Method is the selected Row
				 * - Second Parameter the TableModel needed to get all value and generated values for the Instance Fundgegenstand by the Logic Class.
				 * - Third Parameter the Object DataBaseGUI to be able to send it in the same way to the DAO object. 
				 */
				
				if(!this.logicModelFundSachen.getDataBaseStatus().equalsIgnoreCase("RELOAD")) {
					
					this.logicModelFundSachen.updateFundsachen(selectedRow, model, this.dataBaseGUI);
					
				}
				
				
			}
		}

		
		
	}



	@Override
	public void itemStateChanged(ItemEvent e) {
		
		//searchJComboBox
		if(e.getSource()== this.dataBaseGUI.searchJComboBox) {
			this.dataBaseGUI.searchText.setText("");
			this.dataBaseGUI.searchText.requestFocus();
		}
		
		else if(e.getSource()==this.dataBaseGUI.schluesselInHausJComboBox) {
			
				this.logicModelParking.updateParking(selectedRow, selectedColumn, model, dataBaseGUI);
		
		}
		
		//In case Parking changing the Sales Channel
		else if(e.getSource()==this.dataBaseGUI.buchungskanalJComboBox) {
			
			this.logicModelParking.updateParking(selectedRow, selectedColumn, model, dataBaseGUI);
		}
		
		else if(e.getSource()==this.dataBaseGUI.kisteNummerJComboBox) {
			
			this.logicModelFundSachen.updateFundsachen(selectedRow, model, dataBaseGUI);
		}
		
		
		else if(e.getSource()==this.dataBaseGUI.displayMothLostAndFoundJComboBox) {
		
			 //if item was selected
			 if(e.getStateChange() == ItemEvent.SELECTED) {
			
			 //set the value String of monthSelected
			 String monthSelected = this.dataBaseGUI.displayMothLostAndFoundJComboBox.getSelectedItem().toString();
			 
			//Evaluate monthSelected is not "Zeigen Nach Monat"
			if(!monthSelected.equals("Zeigen Nach Monat")) {
				  //We call the logicModelFundSachen.displayMonthSelected Method.
				this.logicModelFundSachen.displayMonthSelected(monthSelected, dataBaseGUI, new Loading(dataBaseGUI, false));
	             }
			}
			
		  

		}
		

	}



	@Override
	public void focusGained(FocusEvent e) {
	
		//It will be returned the Focus to btnHombe so we can play with the KeyCode Pressed Key
		if (e.getSource() == this.dataBaseGUI.btnHome) {
			this.dataBaseGUI.btnHome.requestFocus();

		} else if (e.getSource() == this.dataBaseGUI.btnParking) {

			this.dataBaseGUI.btnHome.requestFocus();
		}

		else if (e.getSource() == this.dataBaseGUI.btnFitness) {
			this.dataBaseGUI.btnHome.requestFocus();
		}

		else if (e.getSource() == this.dataBaseGUI.btnPhonebook) {
			this.dataBaseGUI.btnHome.requestFocus();

		}

		else if (e.getSource() == this.dataBaseGUI.btnLogout) {
			this.dataBaseGUI.btnHome.requestFocus();
		}

		else if (e.getSource() == this.dataBaseGUI.btnNewFundsachen) {
			this.dataBaseGUI.btnHome.requestFocus();
		}
		
		else if(e.getSource()== this.dataBaseGUI.btnFundsachen) {
			this.dataBaseGUI.btnHome.requestFocus();
		}
		
		
		else if(e.getSource()==this.dataBaseGUI.btnNewParking) {
			this.dataBaseGUI.btnHome.requestFocus();
		}
		
		else if(e.getSource()==this.dataBaseGUI.searchText) {
			
			if(this.dataBaseGUI.parkingTable!=null) {
				this.dataBaseGUI.parkingTable.getSelectionModel().clearSelection();
			}
			
			else if(this.dataBaseGUI.fundsachenTable!=null) {

				this.dataBaseGUI.fundsachenTable.getSelectionModel().clearSelection();
			}
		}
		
		else if(e.getSource()==this.dataBaseGUI.btnNewFitnessAbo) {
			
			this.dataBaseGUI.btnHome.requestFocus();
		}

	}



	@Override
	public void focusLost(FocusEvent e) {}


	@Override
	public void mouseClicked(MouseEvent e) {
	}



	@Override
	public void mousePressed(MouseEvent e) {
	if(this.dataBaseGUI.parkingTable!=null) {
			
		
		
			
			if(e.getSource()==this.dataBaseGUI.parkingTable) {


				if(e.getClickCount()==2 && this.dataBaseGUI.parkingTable.getSelectedRow() !=-1) {
					TableModel model = this.dataBaseGUI.parkingTable.getModel();
					
					int selectedRow = this.dataBaseGUI.parkingTable.getSelectedRow();
					int selectedColumn = this.dataBaseGUI.parkingTable.getSelectedColumn();
					
					if(selectedColumn == 4) {
											
						dates = new String[] {model.getValueAt(selectedRow, 4).toString(), model.getValueAt(selectedRow, 5).toString()};
						
						
						/*
						 we save the arrival and departure dates in an array so that we can access the old value in case of an error 
						 and return old value to the table as they were.
						 */
						this.logicModelParking.setDateAsStringToBeModified(dates);
						
						
					}
					
					else if(selectedColumn == 5) {
						
						//aqui vamos a guardar de forma provicional el valor de esta coloumn en caso de que halla que volver a devolverlo.
//						this.logicModelParking.setDateAsStringToBeModified(model.getValueAt(selectedRow, selectedColumn).toString());
					
						dates = new String[] {model.getValueAt(selectedRow, 4).toString(), model.getValueAt(selectedRow, 5).toString()};
						this.logicModelParking.setDateAsStringToBeModified(dates);
						
					}
				}
			}
		}
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {}



	@Override
	public void mouseEntered(MouseEvent e) {}



	@Override
	public void mouseExited(MouseEvent e) {}



	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		
		/*
		 * if the JTable active is fundsachenTable by click right PopupMenu will be active and marking the selecting row to give the option to delete.
		 */
		if(this.dataBaseGUI.dataBaseApplication.equalsIgnoreCase("FUNDSACHEN")) {
			SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int rowAtPoint = dataBaseGUI.fundsachenTable.rowAtPoint(SwingUtilities.convertPoint(dataBaseGUI.popupMenu, new Point(0, 0), dataBaseGUI.fundsachenTable));
                    if (rowAtPoint > -1) {
                    	dataBaseGUI.fundsachenTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                    }
                }
            });
		
		
		}
		

	}



	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}



	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {}


	
	

}
