package tech.codepalace.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import tech.codepalace.model.LogicModelFundSachen;
import tech.codepalace.model.LogicModelParking;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;


/**
 * 
 * @author tonimacaroni
 * @description Controller Class for DataBaseGUI.
 * <p>This class receives as an argument BigDataAirportHotelBaselStartFrame Object so we can call this object anyTime back.</p>
 * <p>The DataBaseGUI Object is the class will be controlled by this one.</p>
 * <p>The LogicModelParking so we can call anyTime for creating a new Parking-Reservation for example.</p>
 * <p>The TableModelListener will be used to interact with the changes of the cells by any JTable.</p>
 */
public class DataBaseGUIController implements ActionListener, KeyListener, WindowListener, TableModelListener {
	

	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	private DataBaseGUI dataBaseGUI;
	private LogicModelParking logicModelParking;
	private LogicModelFundSachen logicModelFundSachen;
	
	private TableModel model;

	
	public DataBaseGUIController(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, 
			DataBaseGUI dataBaseGUI, LogicModelParking logicModelParking, LogicModelFundSachen logicModelFundSachen) {
		
		//We set the values to the Objects
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.dataBaseGUI = dataBaseGUI;
		this.logicModelParking = logicModelParking;
		this.logicModelFundSachen = logicModelFundSachen;
		
		
		//Add Listener
		this.dataBaseGUI.addWindowListener(this);
		
		this.dataBaseGUI.btnHome.addActionListener(this);
		
		this.dataBaseGUI.btnExitDBGUI.addActionListener(this);
		this.dataBaseGUI.btnNoExitDBGUI.addActionListener(this);
		
		this.dataBaseGUI.btnNewParking.addActionListener(this);
		this.dataBaseGUI.btnNewParking.addKeyListener(this);
		
		this.dataBaseGUI.btnNewFundsachen.addActionListener(this);
		this.dataBaseGUI.btnNewFundsachen.addKeyListener(this);
		
		
		/*
		 * depending on the table that is visible it will be add TableModelListener. 
		 * 
		 * So we can know if some cell in the JTable has been modified.
		 * 
		 * For that we go to the Method tableChanged
		 */
		if(this.dataBaseGUI.parkingTable != null) {
			this.dataBaseGUI.parkingTable.getModel().addTableModelListener(this);
		}
		else if(this.dataBaseGUI.fundsachenTable != null) {
			this.dataBaseGUI.fundsachenTable.getModel().addTableModelListener(this);

		}
		
		
		//Add KeyListener to the Search Box
		this.dataBaseGUI.searchText.addKeyListener(this);
		
		this.dataBaseGUI.reloadDdJButton.addActionListener(this);
		

		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==this.dataBaseGUI.btnHome) {
		
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.dispose();
			
		} else if(e.getSource()== this.dataBaseGUI.btnExitDBGUI) {
			
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.exitDBGUI.dispose();
			this.dataBaseGUI.dispose();
			
		} else if (e.getSource()== this.dataBaseGUI.btnNoExitDBGUI) {
			
			this.dataBaseGUI.exitDBGUI.dispose();
		
		} else if(e.getSource()==this.dataBaseGUI.btnNewParking) {
			
			this.logicModelParking.createNewParkingReservation(dataBaseGUI);
		
		} else if(e.getSource()==this.dataBaseGUI.btnNewFundsachen) {
			
			this.logicModelFundSachen.enterNewFoundsachenEntries(dataBaseGUI);
		}
		
		else if(e.getSource()==this.dataBaseGUI.reloadDdJButton) {
			
		 /* Time to reload the Lost And Found Data and display in JTable the Results. 
		  * 
		  * As argument we pass dataBaseGUI object and a new Loading Object that receives the first argument the 
		  * GUI in background that should be blocked and true to be blocked.
		  */
		  this.logicModelFundSachen.reloadFundsachen(dataBaseGUI, new Loading(this.dataBaseGUI, true));
		
		}
		
		
	}

	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource()==this.dataBaseGUI.btnExitDBGUI && e.getKeyCode() ==10) {
			
		}
		
		else
		
			/*
			 * In case the user type Enter by the searchText object we call the
			 * searchResultsInDataBase Method passing argument dataBaseGUI to avoid 
			 * NullPointerException and for having available all the elements from the GUI DataBaseGUI.
			 */
			if(e.getSource()==this.dataBaseGUI.searchText  && e.getKeyCode() ==10) {
			
				this.logicModelFundSachen.searchResultsInDataBase(this.dataBaseGUI);
		}

		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	
	
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		
		this.bigDataAirportHotelBaselStartFrame.dispose();

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

		//Variable to get the value of the selected row in the JTable.
		int selectedRow=0;

		/*
		 * depending on the table that is visible we get the selected Row calling the 
		 * Method getSelectedRow().
		 */
		if(this.dataBaseGUI.parkingTable != null) {
			
			selectedRow = this.dataBaseGUI.parkingTable.getSelectedRow();
			
		}else if(this.dataBaseGUI.fundsachenTable != null) {
			
			selectedRow = this.dataBaseGUI.fundsachenTable.getSelectedRow();

			
		}
		
		
		
		//TableMode will received the value of the current JTable displayed.
		model = (TableModel)e.getSource();

		
		/* To avoid one ArrayIndexOutOfBoundsException i use one conditional if 
		 * to evaluate if selectedRow =!-1 of course we can use a try-catch
		 */
		if(selectedRow!=-1) {
		

			//if parkingTable is displayed we call to update the data for Parking
			if(this.dataBaseGUI.parkingTable != null) {
				
				//@TODO update PARKING Table
				
		
			//if fundsachenTable is displayed we call to update the data for FUNDSACHEN(Lost and found)
			}else if(this.dataBaseGUI.fundsachenTable != null) {
			
		
				/*
				 * We call the Method updateFundsachen by LogicModelFundsachen. 
				 * 
				 * - First Parameter  of this Method is the selected Row
				 * - Second Parameter the TableModel needed to get all value and generated values for the Instance Fundgegenstand by the Logic Class.
				 * - Third Parameter the Object DataBaseGUI to be able to send it in the same way to the DAO object. 
				 */
				
				this.logicModelFundSachen.updateFundsachen(selectedRow, model, this.dataBaseGUI);
				
			}
		}

		
		
	}


	
	

}
