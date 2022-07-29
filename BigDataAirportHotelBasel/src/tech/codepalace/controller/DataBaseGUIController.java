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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
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
public class DataBaseGUIController implements ActionListener, KeyListener, WindowListener, 
TableModelListener, ItemListener, FocusListener, PopupMenuListener {
	

	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	private DataBaseGUI dataBaseGUI;
	private LogicModelParking logicModelParking;
	private LogicModelFundSachen logicModelFundSachen;
	
	private TableModel model;
	

	//Variable to get the value of the selected row in the JTable.
	private int selectedRow=0;
	
	public DataBaseGUIController(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, 
			DataBaseGUI dataBaseGUI, LogicModelParking logicModelParking, LogicModelFundSachen logicModelFundSachen) {
		
		//We set the values to the Objects
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.dataBaseGUI = dataBaseGUI;
		this.logicModelParking = logicModelParking;
		this.logicModelFundSachen = logicModelFundSachen;
		
		
		//Add Listener
		this.dataBaseGUI.addWindowListener(this);
		
		this.dataBaseGUI.addKeyListener(this);
		this.dataBaseGUI.btnHome.addKeyListener(this);
	
		
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
			this.dataBaseGUI.fundsachenTable.addKeyListener(this);

		}
		
		
		//Add KeyListener to the Search Box
		this.dataBaseGUI.searchText.addKeyListener(this);
	    
		this.dataBaseGUI.reloadDdJButton.addActionListener(this);
		
		this.dataBaseGUI.searchJComboBox.addItemListener(this);
		
		
		//Add FocusListener to the elements Fundsachen
		this.dataBaseGUI.btnHome.addFocusListener(this);
		this.dataBaseGUI.btnParking.addFocusListener(this);
		this.dataBaseGUI.btnFitness.addFocusListener(this);
		this.dataBaseGUI.btnPhonebook.addFocusListener(this);
		this.dataBaseGUI.btnLogout.addFocusListener(this);
		this.dataBaseGUI.btnNewFundsachen.addFocusListener(this);
		this.dataBaseGUI.searchText.addFocusListener(this);
		this.dataBaseGUI.searchJComboBox.addFocusListener(this);
		
		this.dataBaseGUI.btnHome.requestFocus();
		
		
		this.dataBaseGUI.deleteItem.addActionListener(this);
		
		this.dataBaseGUI.popupMenu.addPopupMenuListener(this);
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
		
		else if(e.getSource()==this.dataBaseGUI.deleteItem) {
//			 JOptionPane.showMessageDialog(null, "Right-click performed on table and choose DELETE");
	        
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
	        	 
	        	 //We call the deleteRowDataBase Method with the arguments selectedRow and the name of the table we are going to try to delete.
	        	 this.logicModelFundSachen.deleteRowDataBase(this.selectedRow, "FUNDSACHEN", this.model);
	         }
		
		}
		
		
	}

	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	
	@Override
	public void keyPressed(KeyEvent e) {
		

		
		if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode() == 112
				||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 112
				||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 112 
				||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==112)
		
		
		{
			this.bigDataAirportHotelBaselStartFrame.setVisible(true);
			this.dataBaseGUI.dispose();
		}
		
		else
			if(e.getSource()==this.dataBaseGUI.btnHome && e.getKeyCode()== 113
					||e.getSource()==this.dataBaseGUI.searchJComboBox && e.getKeyCode() == 113
					||e.getSource()==this.dataBaseGUI.searchText && e.getKeyCode() == 113 
					||e.getSource()==this.dataBaseGUI.fundsachenTable && e.getKeyCode() ==113)
			
			{
		
				this.logicModelFundSachen.displayParking(bigDataAirportHotelBaselStartFrame, "PARKING");
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
				
				if(!this.logicModelFundSachen.getDataBaseStatus().equalsIgnoreCase("RELOAD")) {
					
					this.logicModelFundSachen.updateFundsachen(selectedRow, model, this.dataBaseGUI);
					
				}
				
				
			}
		}

		
		
	}



	@Override
	public void itemStateChanged(ItemEvent e) {
		
		this.dataBaseGUI.searchText.setText("");
		this.dataBaseGUI.searchText.requestFocus();

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
		
		
	}



	@Override
	public void focusLost(FocusEvent e) {}



	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

		
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
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	

}
