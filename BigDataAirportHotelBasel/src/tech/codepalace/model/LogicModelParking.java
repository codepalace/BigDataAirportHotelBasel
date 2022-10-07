package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import tech.codepalace.controller.NewParkingController;
import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoParkingImpl;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewParking;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version 1.0.0 Jan 30.2022 10:40PM
 * @description Class Logic algorithm for the application of parking databases
 *
 */
public class LogicModelParking extends LogicModel {

	
	
	private static DataBaseGUI dataBaseGUI;
	private Loading loading;
	
	protected DataEncryption dataEncryption;
	
	//Variable where should be stored the URL or Directory where our database is located
	protected String urlDataBase;
	
	//Variable used to generate the current DataBase name.
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	//Variable to store the Database Name
	protected String dbName;
	
	
	protected File urlDataBaseFile;
	
	protected ParkingReservation parkingReservation;
	
	private String  abkuerzungMA = "";
	
	private int tableCounter=0;
	
	private DAOParking daoParking = null;
	
	
	//Variable to use in case the user enters incorrect data.
	public JDialog errorDateFormat, errorEntries;
		
	private JButton okButtonErrorDateFormat  = new JButton("OK");
		
	private Object[] optionButtonErrorDateFormat = { this.okButtonErrorDateFormat };
		
	private JLabel messageErrorDateFormat;
	
	private JPanel panelErrorDateFormat = new JPanel(new BorderLayout());

		
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	private static int selectedRow=0;
	private static int selectedColumn=0;
	
	private LocalDate arrivalLocalDate = null, departureLocalDate = null;
	
	
	//Variables for the ParkingReservation Object
	private int id = 0;
	private String idParking = "";
	private String buchungsname = "";
	private String autokfz = "";
	private Date anreisedatum = null;
	private Date abreisedatum = null;
	private int anzahltagen = 0;
	private double betragparking = 0.0;
	private String buchungskanal = "";
	private String bemerkungen = "";
	private String schluesselinhaus = "";
	private String verkaufer = "";
	

	
	public LogicModelParking(DataBaseGUI dataBaseGUI, Loading loading) {
		

		LogicModelParking.dataBaseGUI = dataBaseGUI;
		this.loading = loading;

		//We create a new Instance fo DataEncryption, needed to decrypt some Data we need.
		this.dataEncryption = new DataEncryption();
		
		
		//Add ActionListener to the OK Button by the Error Message JDialog.
		this.okButtonErrorDateFormat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

//				int selectedRow = LogicModelParking.dataBaseGUI.parkingTable.getSelectedRow();
//				LogicModelParking.dataBaseGUI.parkingTable.getModel().setValueAt(getDateAsStringToBeModified(), selectedRow, 4);
				errorDateFormat.dispose();

			}
		});
		
		
		//Add KeyListener to the OK Button by the Error Message JDialog.
		this.okButtonErrorDateFormat.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {

//				int selectedRow = LogicModelParking.dataBaseGUI.parkingTable.getSelectedRow();
//				LogicModelParking.dataBaseGUI.parkingTable.getModel().setValueAt(getDateAsStringToBeModified(), selectedRow, 4);
				errorDateFormat.dispose();

			}
		});
		
		
	}

	

	
	

	/**
	 * @description method that calls the GUI NewParking to create a new parking reservation entry in the database
	 */
	public void createNewParkingReservation(DataBaseGUI dataBaseGUI) {
		
		LogicModelParking.dataBaseGUI = dataBaseGUI;

	
		try {
			/*
			 * we give value to the variable calling the method getDataRowCounter belonging to Class DaoParkingImpl.
			 * 
			 * it will count how many entries we have in the database. Exactly within the table parking present in the Database. 
			 * 
			 * After counting and get the value we add +1 so we can generate with this value a new ParkingID.
			 */
			

			
			this.daoParking = new DaoParkingImpl(getUserAHB(), LogicModelParking.dataBaseGUI, loading);
			tableCounter = daoParking.getDataRowCounter() + 1;
			
		} catch (DaoException e) {
			
			e.printStackTrace();
		}

		
		
		try {
			/* With the help of the dataEncryption object we set the value to the variable abkuerzungMA, calling to decryptData and for that we 
			 * also call getUserAHB().getAbbkuerzungMA() to get the value we need. 
			 * 
			 * AbbkuerzungMA is the Team user who is typing the new entries in the database.
			 *
			 * 
			 */
		    abkuerzungMA = this.dataEncryption.decryptData(getUserAHB().getAbkuerzungMA());
		    
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		
		//Invoke a new Thread
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				/*
				 * We create a new NewParking object. we pass the required parameters. Object AHBParking, false to block the GUI below the NewParking GUI, 
				 * tableCouter(used to set the value IDParking in the NewParking GUI and also to save this value to the Database, abkuerzungMA the user who is typing 
				 * the new Parking.
				 */
				
				NewParking newparking = new NewParking(LogicModelParking.dataBaseGUI, true, tableCounter, abkuerzungMA);
				
				
				/*
				 * We create a LogicModelNewParking and passing also the required parameters. Nothing special to explain in this part.
				 */
				LogicModelNewParking logicModelNewParking = new LogicModelNewParking(LogicModelParking.dataBaseGUI, newparking, getUserAHB(), loading);

					
				//New Instance of NewParkingController to control the newParking GUI.
				new NewParkingController(newparking, logicModelNewParking);
				
				//Center the GUI to the Screen
				newparking.setLocationRelativeTo(null);
				
				//And finally we setVisible.
				newparking.setVisible(true);
				
			}
		});
		
		
		
	}

	
	
	
	
	
	
	@Override
	public void searchResultsInDataBase(DataBaseGUI dataBaseGUI) {
		//we call the super Method
		super.searchResultsInDataBase(dataBaseGUI);
		
		LogicModelParking.dataBaseGUI = dataBaseGUI;
		
		//Block switch conditional to evaluate what we are going to search in database
		switch (getSearchSelected()) {
			
			case "Suchen nach ID-Parking":
				
				suchenNachIDParking(getToSearch());
				
				break;
				
			case "Suchen nach Buchungsname":
				
				suchenNachBuchungsName(getToSearch());
				
				break;
				
			case "Suchen nach Auto-KFZ":
				
				suchenByAutoNr(getToSearch());
				break;
				
			case "Suchen nach Anreisedatum":

				if(checkDateFormatToSearchInDataBase()) {

					
					//Set the value of loading object, First argument the GUI in Background and true to block it.
					this.loading = new Loading(dataBaseGUI, true);
					
					//Instance of DAOFunsachen
//					DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), LogicModelFundSachen.dataBaseGUI, loading, this);
					DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, loading);
					
					
					
					try {
						//Now we are ready to call searchByDateFundschen Method by the DAO Object.
						daoParking.searchByArrivalDate(getDateToSearchInDataBase());
					} catch (DaoException e1) {
						e1.printStackTrace();
					}
				}
				
				break;
		}
		
	}
	
	
	
	
	/**
	 * @description Method to call to search in DataBase as ID-Parking(idParking Column in Table Parking).
	 * @param IDParking
	 */
	private void suchenNachIDParking(String IDParking) {
		
		
		DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true));
		
		try {
			daoParking.searchByIDParking(IDParking);
		} catch (DaoException e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	
	/**
	 * @description Method to search by booker name. call to the DAO Object to search in Database
	 * @param buchungsname
	 */
	private void suchenNachBuchungsName(String buchungsname) {
		
		DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true));
		
		try {
			daoParking.suchenNachBuchungsName(buchungsname);
		} catch (DaoException e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	/**
	 * @description Method to search by Car Number
	 * @param autokfz
	 */
	private void suchenByAutoNr(String autokfz) {
		
		DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true));
		
		try {
			daoParking.suchenByAutoNr(autokfz);
		} catch (DaoException e) {
			e.printStackTrace();
			
		}
	}
	
	
	
	
	/**
	 * @description Method to reload or refresh the DataBase Table PARKING.
	 * @param dataBaseGUI
	 * @param loading
	 */
	public void reloadParkingDataBase(DataBaseGUI dataBaseGUI, Loading loading) {
		
		DAOParking daoParking = new DaoParkingImpl(getUserAHB(), LogicModelParking.dataBaseGUI, loading);
		
		try {
			
			daoParking.reloadParkingData();
			
		}catch (DaoException e) {
			e.printStackTrace();
		
		}
		
	}
	

	
	/**
	 * @description Method that receives the Data to be update in the Database Table Parking throw DAO Object.
	 * @param selectedRow
	 * @param model
	 * @param dataBaseGUI
	 * @throws DaoException
	 */
	public void updateParking(int selectedRow, int selectedColumn, TableModel model, DataBaseGUI dataBaseGUI) {
		
		
		//We set the value of the dataBaseGUI
		LogicModelParking.dataBaseGUI = dataBaseGUI;
		
		/*
		 * We set the value of selectedRow. It's going to be needed mostly if case not correct Date Format.
		 */
		LogicModelParking.selectedRow = selectedRow;
		
		//We set the value of selectedColumn. 
		LogicModelParking.selectedColumn = selectedColumn;
		
		//If a row was selected
		if(LogicModelParking.selectedRow!=-1) {
			
			/*
			 * We evaluate selectedColumn and depending of the value we check if Date format is correct from selected row, column. I mean exact the cell we
			 * want to modify in the JTable.
			 */
			switch (LogicModelParking.selectedColumn) {
				
				//In case of the columns number below we apply the instructions inside. 
				case 1:
				case 2:
				case 3:
				
				case 9:
				case 10:
					
					try {
					
					//The arrivalLocalDate value will be set and departureLocalDate
					setDepartureLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 5).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
					
					setArrivalLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 4).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
				

						//We do not need to calculate Dates also call only the updateAllParkingChanges
						updateAllParkingChanges(model);
					}catch (ArrayIndexOutOfBoundsException e) {
					
					}
						break;
						
				
				case 8: //In case of modification of the Column 8 Sales Channel
					
					try {
						
						//Set the value of arrivalLocalDate
						setArrivalLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 4).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
						
						
						//set the value of departureLocalDate
						setDepartureLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 5).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
					
							//After having the values of Arrival and Departure LocalDate Objects. Now is time to call for checking if Chronology of Dates is ok.
							checkChronologyOfDates(model);
					
									//Variable String to store temporary the value of the column 8 Bucungskanal(Sales Channel).
									String salesChannel = (String)model.getValueAt(selectedRow, 8);

									//In case of value is Park, Sleep & Fly is free of charge(NO extras because is a package included with the room reservation).
									if(salesChannel.equalsIgnoreCase("Park, Sleep & Fly")) {

											//Store the value of Column 5 Temporary to evaluate before changing
											String betragString = (String)model.getValueAt(selectedRow, 7);
											
											//We evaluate the value of betragString variable(Cost for Parking) if not yet 0.0 € we set the column with the value "0.0 €" as String.
											if(!betragString.equalsIgnoreCase("0.0 €")) {
												model.setValueAt("0.0 €", selectedRow, 7); //setting the value "0.0 €"
											}

									
											
											
									}else { //In case salesChannel value is not "Park, Sleep & Fly"
										
										//Store the total of date calculating the Local Dates variables values + 1 because the same day when costumer Park the Card it count.
										anzahltagen = (int) calculateDatesPlus(getArrivalLocalDate(), getDepartureLocalDate()) + 1;
										
										//If anzahltagen(total days are <=3 
										if(anzahltagen<=3) {
											betragparking = 30d; //double value 30.00
											model.setValueAt(String.valueOf(betragparking) + " €" , selectedRow, 7);
											
										}else {
											betragparking = (double)anzahltagen * 10; //multiply anzahltagen(total days) * 10 for a result in double 
											model.setValueAt(String.valueOf(betragparking) + " €" , selectedRow, 7);
										}
									}

									//call the updateAllParkingChanges to update all modified informations and sending to the DAOParking for saving in the DataBase.
									updateAllParkingChanges(model);
					
					}catch (ArrayIndexOutOfBoundsException e) {
						
					}
					
					
					break;						
						
				case 4:
					
					/*
					 * 
					 * We check if the entered date has a correct format.
					 * 
					 * - First argument is the value entered by the user. Value taken from the selectedRow, Column 4 for Arrival date(Anreisedatum)
					 * - Second argument is the inputTextBox or Cell name what we give manually in this case Anreisedatum for in case error to display information where is the error. 
					 * - Third argument is the component had focus. In this case was one of the JTable Cell Anreisedatum. We have give manually the name Anreisedatum Parking. 
					 * this will be used to evaluate by the checkDateFormatBeforeSaveData Method the value we send to this method and depending of the value, in case of wrong date format 
					 * we write back to the cell it has lost the focus the value it had before.
					 */
					
					if(checkDateFormatBeforeSaveData((String)model.getValueAt(selectedRow, 4).toString(), "Anreisedatum", "Anreisedatum Parking")) { 
						
						try {
						
						//if checkDAteFormatBeforeSaveData return true. Date Format Regex(Expression language for date is correct). 
						
						//We set the LocalDate value to be modified getting the value from the selected row and the selecting cell. Replacing character(.) to (/). 
						
						setArrivalLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 4).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
						
						
						//We set the value of departureLocalDate
						setDepartureLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 5).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
					
					
							checkChronologyOfDates(model);
						
					
						}catch (ArrayIndexOutOfBoundsException e) {
							
						}		
							
					
					}
			
					
					break;
					
				case 5: 
					
					
					//The same procedure for Departure
					
						if(checkDateFormatBeforeSaveData((String)model.getValueAt(selectedRow, 5).toString(), "Abreisedatum", "Abreisedatum Parking")) { 
						
						//if checkDAteFormatBeforeSaveData return true. Date Format Regex(Expression language for date is correct). 
						
						//We set the LocalDate value to be modified getting the value from the selected row and the selecting cell. Replacing character(.) to (/). 
						
							try {
						
							//We set the value of departureLocalDate
							setDepartureLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 5).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
							
							setArrivalLocalDate(LocalDate.parse((String)model.getValueAt(selectedRow, 4).toString().replace('.', '/'), getDateTimeFormatterForSavingDataBase()));
						
						
							
								checkChronologyOfDates(model);
							}catch (ArrayIndexOutOfBoundsException e) {
								
							}		
					
					
					}
					break;
					

			}
			
		}


		
	}

	
	
	
	
	
	
	
	
	/*
	 * We Override this Method in case the Date Modified in any Date Cell is not correct so we can  display error message.
	 * 
	 * We also return the Focus back to the column.
	 */
	@Override
	public boolean checkDateFormatBeforeSaveData(String dateEnteredByUser, String inputTextBoxName,
			String componentHadFocus) {

		
		setComponentHadFocus(componentHadFocus);
		
			//Initialize the erroDateFormat JDialog with initial value
				errorDateFormat = new JOptionPane(panelErrorDateFormat, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, 
						errorImg, optionButtonErrorDateFormat, null).createDialog("Kritische Fehler(" + inputTextBoxName + ")");
				
			if(!errorDateFormat.isVisible()) {
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						messageErrorDateFormat = new JLabel(
								"Sie haben eine falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy)");
						
						panelErrorDateFormat = new JPanel(new BorderLayout());
						
						// We Center the Error Messsage to the JPanel
						panelErrorDateFormat.add(messageErrorDateFormat, BorderLayout.CENTER);
						
						errorDateFormat = new JOptionPane(panelErrorDateFormat, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, 
								errorImg, optionButtonErrorDateFormat, null).createDialog("Kritische Fehler(" + inputTextBoxName + ")");
						
						if(!isDateFormatCorrect()) {

							errorDateFormat.setVisible(true);
							messageErrorDateFormat.setText("");

							switch (getComponentHadFocus()) {
								case "Anreisedatum Parking":
									
									
									// If Date format is not correct we set back the value for the Column 4 (Arrival)
									
									LogicModelParking.dataBaseGUI.parkingTable.getModel().setValueAt(getDateAsStringToBeModified()[0], LogicModelParking.selectedRow, 4);
									break;
									
								case "Abreisedatum Parking": 
									
									
									 // If Date format is not correct we set back the value for the Column 5 (Departure)
									 
									LogicModelParking.dataBaseGUI.parkingTable.getModel().setValueAt(getDateAsStringToBeModified()[1], LogicModelParking.selectedRow, 5);
									break;

							}
							
						}
						

						

						
					}
				});
			}
		
		return super.checkDateFormatBeforeSaveData(dateEnteredByUser, inputTextBoxName, componentHadFocus);
	}






	/**
	 * @return the arrivalLocalDate
	 */
	public LocalDate getArrivalLocalDate() {
		return arrivalLocalDate;
	}






	/**
	 * @param arrivalLocalDate the arrivalLocalDate to set
	 */
	public void setArrivalLocalDate(LocalDate arrivalLocalDate) {
		this.arrivalLocalDate = arrivalLocalDate;
	}






	/**
	 * @return the departureLocalDate
	 */
	public LocalDate getDepartureLocalDate() {
		return departureLocalDate;
	}






	/**
	 * @param departureLocalDate the departureLocalDate to set
	 */
	public void setDepartureLocalDate(LocalDate departureLocalDate) {
		this.departureLocalDate = departureLocalDate;
	}
	
	
	
	
	
	/**
	 * @description method for checking the chronology of dates
	 * @param model
	 */
	private void checkChronologyOfDates(TableModel model) {

		//In case Arrival date is after than Departure date
		if(getArrivalLocalDate().isAfter(getDepartureLocalDate())) {
	
			//We store the message we want to display for the user.
			String message = "<html>Das Abreisedatum darf nicht vor dem Anreisedatum sein. <br/>Bitte ändern Sie das Abreisedatum: </html>";
			
			//We display the error JDialog to request the user to enter a new departure date
			displayRequestDateCorrection(selectedRow, selectedColumn, message, "Abreisedatum ändern");
			
			
			
		}else { //else Dates Chronology is OK.
		
			
			
			
			
			//We set the values Casting the Type calling model and getValueAt(the selected Row and the Column).
			id = (int)model.getValueAt(selectedRow, 0);
			idParking = (String)model.getValueAt(selectedRow, 1);
			buchungsname = (String)model.getValueAt(selectedRow, 2);
			autokfz = (String)model.getValueAt(selectedRow, 3);
			
			//We set the value for the Date(anreisedatum). Value needed to save in our ParkingReservation object to sending to the DAO object to save in Database with the new value.
			anreisedatum = Date.valueOf(getArrivalLocalDate());
			
			//We set the value for the Date(abreisedatum). Value needed to save in our ParkingReservation object to sending to the DAO object to save in Database with the new value.
			abreisedatum = Date.valueOf(getDepartureLocalDate());
			
			
			//We have to calculate the total of days. It will be continue
			anzahltagen = (int) calculateDatesPlus(getArrivalLocalDate(), getDepartureLocalDate()) + 1;

			

			/*
			 * In this double object we call the Method parseDouble getting the value of the String in the column 7.
			 * 
			 * To this value we call the replaceAll(We replace the € symbol for empty String) so we can get only a double value.
			 */
			betragparking = Double.parseDouble(String.valueOf(model.getValueAt(selectedRow, 7)).replaceAll("€", ""));
			
			//If anzahltagen(total days are <=3 
			if(anzahltagen<=3) {
				betragparking = 30d; //double value 30.00
			}else {
				betragparking = (double)anzahltagen * 10; //multiply anzahltagen(total days) * 10 for a result in double 
			}
			
			
			
			
			buchungskanal = (String)model.getValueAt(selectedRow, 8	);
			bemerkungen = (String)model.getValueAt(selectedRow, 9);
			schluesselinhaus = (String)model.getValueAt(selectedRow, 10);
			verkaufer = (String)model.getValueAt(selectedRow, 11);
			
			
			
			
			//Initialize the parkingReservation Object.
			parkingReservation = new ParkingReservation();
			
			parkingReservation.setId(id);
			parkingReservation.setIdParking(idParking);
			parkingReservation.setBuchungsname(buchungsname);
			parkingReservation.setAutoKFZ(autokfz);
			parkingReservation.setAnreiseDatum(anreisedatum);
			parkingReservation.setAbreiseDatum(abreisedatum);
			parkingReservation.setAnzahlTagen(anzahltagen);
			parkingReservation.setBetragParking(betragparking);
			parkingReservation.setBuchungsKanal(buchungskanal);
			parkingReservation.setBemerkungen(bemerkungen);
			parkingReservation.setSchluesselInHaus(schluesselinhaus);
			parkingReservation.setAbkuerzungMA(verkaufer);
			
			
			
			//Initialize the DAOParking Object
			DAOParking daoParking = new DaoParkingImpl(getUserAHB(), LogicModelParking.dataBaseGUI, new Loading(LogicModelParking.dataBaseGUI, true));
			
			
			
			try {
				
										
				//We call for the updateParkingReservation Method with the parkingReservation argument.
				daoParking.updateParkingReservation(parkingReservation);
				
				
				
				
			}catch (DaoException e) {
				
				e.printStackTrace();
			
			}
			
			
			LogicModelParking.dataBaseGUI.parkingTable.setCellSelectionEnabled(true);
			LogicModelParking.dataBaseGUI.parkingTable.changeSelection(selectedRow, 6, false, false);
			LogicModelParking.dataBaseGUI.parkingTable.requestFocus();

			//We set the value for the column 6 Total Days anzahltagen
			model.setValueAt(anzahltagen, LogicModelParking.selectedRow, 6);
			
			//We set the values for the betragparking Column 7(Cost for parking and adding also Euro Symbol).
			model.setValueAt(betragparking + " €", LogicModelParking.selectedRow, 7);
			
			
		}
		

	}
	
	
	
	
	/**
	 * @description Method to update All Parking changes but special without modifying any Dates(Arrival or Departure).
	 * <p>We do not modify here in this Method the anzahltagen(Total Days).</p>
	 * <p>And also it will be not calculate the value of betragParking(Price for parking). 
	 * @param model
	 */
	private void updateAllParkingChanges(TableModel model){
		
		if(LogicModelParking.selectedRow!=-1) {
			
			//We set the values Casting the Type calling model and getValueAt(the selected Row and the Column).
			id = (int)model.getValueAt(selectedRow, 0);
			idParking = (String)model.getValueAt(selectedRow, 1);
			buchungsname = (String)model.getValueAt(selectedRow, 2);
			autokfz = (String)model.getValueAt(selectedRow, 3);
			
			//We set the value for the Date(anreisedatum). Value needed to save in our ParkingReservation object to sending to the DAO object to save in Database with the new value.
			anreisedatum = Date.valueOf(getArrivalLocalDate());
			
			//We set the value for the Date(abreisedatum). Value needed to save in our ParkingReservation object to sending to the DAO object to save in Database with the new value.
			abreisedatum = Date.valueOf(getDepartureLocalDate());
			
			
			/*
			 * In this Method we do not need to calculateDatePlus to change the value because the value of the column 6 stay unmodified
			 */
			anzahltagen = (int)model.getValueAt(selectedRow, 6);

			

			/*
			 * In this double object we call the Method parseDouble getting the value of the String in the column 7.
			 * 
			 * To this value we call the replaceAll(We replace the € symbol for empty String) so we can get only a double value.
			 */
			betragparking = Double.parseDouble(String.valueOf(model.getValueAt(selectedRow, 7)).replaceAll("€", ""));
			
			
			/*
			 * The comments below is to show you the different between how it works in the checkChronologyOfDates Method and how does it works in
			 * this Method. 
			 * 
			 *  We do not need to calculate Total days and change any value by betragParking(Price of Parking) because this value stay unmodified.
			 *  
			 *  We keep this value and send it to DAOParking object to save the same value unmodified in the Database.
			 */
			
			//If anzahltagen(total days are <=3 
//			if(anzahltagen<=3) {
//				betragparking = 30d; //double value 30.00
//			}else {
//				betragparking = (double)anzahltagen * 10; //multiply anzahltagen(total days) * 10 for a result in double 
//			}
//			
			
			
			
			buchungskanal = (String)model.getValueAt(selectedRow, 8	);
			bemerkungen = (String)model.getValueAt(selectedRow, 9);
			schluesselinhaus = (String)model.getValueAt(selectedRow, 10);
			verkaufer = (String)model.getValueAt(selectedRow, 11);
			
			
			
			
			//Initialize the parkingReservation Object.
			parkingReservation = new ParkingReservation();
			
			parkingReservation.setId(id);
			parkingReservation.setIdParking(idParking);
			parkingReservation.setBuchungsname(buchungsname);
			parkingReservation.setAutoKFZ(autokfz);
			parkingReservation.setAnreiseDatum(anreisedatum);
			parkingReservation.setAbreiseDatum(abreisedatum);
			parkingReservation.setAnzahlTagen(anzahltagen);
			parkingReservation.setBetragParking(betragparking);
			parkingReservation.setBuchungsKanal(buchungskanal);
			parkingReservation.setBemerkungen(bemerkungen);
			parkingReservation.setSchluesselInHaus(schluesselinhaus);
			parkingReservation.setAbkuerzungMA(verkaufer);
			
			
			
			//Initialize the DAOParking Object
			DAOParking daoParking = new DaoParkingImpl(getUserAHB(), LogicModelParking.dataBaseGUI, new Loading(LogicModelParking.dataBaseGUI, true));
			
			
			
			try {
				
										
				//We call for the updateParkingReservation Method with the parkingReservation argument.
				daoParking.updateParkingReservation(parkingReservation);
				
				
				
				
			}catch (DaoException e) {
				
				e.printStackTrace();
			
			}
		}
		
		
		
		
	
	}


}
