package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoParkingImpl;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewParking;


/**
 * description Class to Enter a new Parking-Reservation
 * @author tonimacaroni
 *
 */
public class LogicModelNewParking extends LogicModel {
	
	protected UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static NewParking newParking;
	protected ParkingReservation parkingReservation;
	private Loading loading;

	
	
	protected String dbName;
	
	private boolean readyToCloseNewParkingReservation = false;
	
	private String elementHadFocus = "";
	
	private JPanel panelErrorDateFormat = new JPanel(new BorderLayout());

	
	
	private String inputTextBoxName = "";
	
	//Variable to use in case the user enters incorrect data.
	public JDialog errorDateFormat, errorEntries;
	
	private JButton okButtonErrorDateFormat  = new JButton("OK");
	
	private Object[] optionButtonErrorDateFormat = { this.okButtonErrorDateFormat };
	
	private JLabel messageErrorDateFormat;

	
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	//Variable to get the Total days to be payed.
	private long totalDaysToPay;
	
	//LocalDate for the arrival Date and departure date.
	private LocalDate anreiseLocalDate=null, abreiseLocalDate=null;
	
	//LocalDateTime to set the todays Date in LocalDateTime format.
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	//Variable LocalDate to set the todays Date in LocalDate format we need to convert some dates thats why we need both format.
	protected LocalDate nowLocalDate;
	

	//Variable boolean to check if the user is finished entering all the information. 
	private boolean entryCompleted = false;
	
	//Variables to set and check if the Arrival and Departure dates are in the expected format.
	private boolean anreiseOK = false, abreiseOK = false;
	

	
	//Variable for the total sum what the user of Parking has to pay.
	protected double betragTotal;
	
	
	//Variables to save data in the database
	protected String idParking,buchungsname, autokfz;
	protected Date anreisedatum, abreisedatum;
	protected int anzahltagen;
	protected double betragparking;
	protected String buchungskanal, bemerkungen, schluesselinhaus, verkaufer;
	
	
	

	



	
	/**
	 * @param dataBaseGUI
	 * @param newParking
	 * @param userAHB
	 * @description This constructor receives 4 necessary parameters.
	 * <p>DataBaseGUI: Will be used to call the constructor of DaoParkingImpl to add the new ParkingReservation.</p>
	 * <p>NewParking: NewParking GUI.</p>
	 * <p>UserAHB: necessary parameter in almost all the application environment.</p>
	 * <p>Loading: Always expected from the DaoParkingImpl class.</p>
	 */
	public LogicModelNewParking(DataBaseGUI dataBaseGUI, NewParking newParking, UserAHB userAHB, Loading loading) {
		

		this.dataBaseGUI = dataBaseGUI;
		LogicModelNewParking.newParking = newParking;
		this.userAHB = userAHB;
		this.loading = loading;

		//nowLoacalDate for the current date in LocalDate variable
		this.nowLocalDate = now.toLocalDate();
		
		
		//Initialize the erroDateFormat JDialog with initial value
		errorDateFormat = new JOptionPane(panelErrorDateFormat, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, 
				errorImg, optionButtonErrorDateFormat, null).createDialog("Kritische Fehler(" + inputTextBoxName + ")");
		
		
		
	
		//Add ActionListener to the OK Button by the Error Message JDialog.
		this.okButtonErrorDateFormat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				
				//Evaluate the value String variable componentHadfocus located by the Super Class LogicModel
				switch(getComponentHadFocus()) {
					
					//Depending of the cases we requestFocus to the corresponding element by pressing the JButton okButtonErrorDateFormat.
					case "anreiseDatumPlaceHolderTextField":
						
				
						LogicModelNewParking.newParking.anreiseDatumPlaceHolderTextField.requestFocus();
						
						//Reset to the initial value anreiseOK(false), anreiseLocalDate(null).
						setAnreiseOk(false);
						setAnreiseLocalDate(null);
						
						break;
						
					case "abreiseDatumPlaceholderTextField":
						
						LogicModelNewParking.newParking.abreiseDatumPlaceholderTextField.requestFocus();
	
						
						setAbreiseOK(false);
						setAbreiseLocalDate(null);
						
						break;
						
				}

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

				switch(getComponentHadFocus()) {
					
					case "anreiseDatumPlaceHolderTextField":
						
				
						LogicModelNewParking.newParking.anreiseDatumPlaceHolderTextField.requestFocus();
						setAnreiseOk(false);
						setAnreiseLocalDate(null);
						break;
						
					case "abreiseDatumPlaceholderTextField":
						
						LogicModelNewParking.newParking.abreiseDatumPlaceholderTextField.requestFocus();	
						setAbreiseOK(false);
						setAbreiseLocalDate(null);
						break;
						
				}
				errorDateFormat.dispose();

			}
		});
		

		
	}

	
	

	
/**
 * @description method to establish whether the arrival date, entry is correct or not
 * @param anreiseOk
 */
public void setAnreiseOk(boolean anreiseOk) {
	this.anreiseOK = anreiseOk;
}




/**
 * @return the anreiseOK
 */
public boolean isAnreiseOK() {
	return anreiseOK;
}






/**
 * @return the abreiseOK
 */
public boolean isAbreiseOK() {
	return abreiseOK;
}

/**
 * @param abreiseOK the abreiseOK to set
 */
public void setAbreiseOK(boolean abreiseOK) {
	this.abreiseOK = abreiseOK;
}



/**
 * @description This method will check if all required entries are completed correctly.
 * 
 * if all the inputs are correct, it will call the method addNewParkingReservation in the DaoParkingImpl Class.
 */
public void checkAllEntries() {
	
	/*
	 * If the JTextField it's empty: entryCompleted = false and we paint a Red border to the JTextField.
	 */
	if(newParking.buchungsNameJTextField.getText().equalsIgnoreCase("") ||
			newParking.buchungsNameJTextField.getText().length()< 3 ) {
		
		this.entryCompleted=false;
		newParking.buchungsNameJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		
	}else {
		this.entryCompleted = true;
	}
		
		
		if(newParking.autoKFZJTextField.getText().equalsIgnoreCase("") ||
			newParking.autoKFZJTextField.getText().length()<3) {
		this.entryCompleted=false;
		newParking.autoKFZJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
	} else {
		
		this.entryCompleted = true;
	}
	

		if(!isAnreiseOK()) {
			checkAnreiseDate();
		}else {
			errorDateFormat.dispose();
		}
		
		if(!isAbreiseOK()) {
			checkAbreiseDate();
		}else {
			errorDateFormat.dispose();
		}
	
		

		if(newParking.buchunsKanalJTextField.getText().equalsIgnoreCase("") ||
				newParking.buchunsKanalJTextField.getText().length()<3) {
			
			this.entryCompleted = false;
			newParking.buchunsKanalJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			
		}else {
			this.entryCompleted = true;
		}
		
		
		
		
		
		if(this.entryCompleted & isAnreiseOK() & isAbreiseOK()) {
//			JOptionPane.showMessageDialog(null, "Estamos listos para guardar Parking");
		
			/*
			 * Every entries is OK then we call to addNewParkingReservationToDataBase Method.
			 */
			addNewParkingReservationToDataBase();
		}else {
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null, "Bitte füllen Sie die rot markierten Daten korrekt aus",  "Kritische Fehler Dateneingabe", JOptionPane.OK_OPTION, errorImg);
					
					
				}
			});
			
		}
		
}






/**
 * @return the readyToCloseNewParkingReservation
 */
public boolean isReadyToCloseNewParkingReservation() {
	return readyToCloseNewParkingReservation;
}





/**
 * @param readyToCloseNewParkingReservation the readyToCloseNewParkingReservation to set
 */
public void setReadyToCloseNewParkingReservation(boolean readyToCloseNewParkingReservation) {
	this.readyToCloseNewParkingReservation = readyToCloseNewParkingReservation;
}



/**
 * @return the elementHadFocus
 */
public String getElementHadFocus() {
	return elementHadFocus;
}




/**
 * @param elementHadFocus the elementHadFocus to set
 */
public void setElementHadFocus(String elementHadFocus) {
	this.elementHadFocus = elementHadFocus;
}



public void checkAnreiseDate() {
	

	
	if(!isReadyToCloseNewParkingReservation() !=isAbreiseOK()) {
		
		
		
		if(checkDateFormatBeforeSaveData(newParking.anreiseDatumPlaceHolderTextField.getText(), "Anreisedatum", "anreiseDatumPlaceHolderTextField")) {

			/*
			 * The date format entered is correct, we replace the (.) by (/), that way we can pass an appropriate value to the variable of type LocalDate.
			 */
			String replaceCharacter = newParking.anreiseDatumPlaceHolderTextField.getText().replace('.', '/');
		
			
			try {
				
				//Set the Localdatevalue for anreiseLocalDate variable
				setAnreiseLocalDate(LocalDate.parse(replaceCharacter, getDateTimeFormatterForSavingDataBase()));
				
				//Set Date value to the anreisedatum Date variable getting the value from the anreiseLocalDate.
				//This Date value will be saved in the Database Table Parking
				this.anreisedatum = Date.valueOf(getAnreiseLocalDate());
				
				
				
			}catch (DateTimeParseException ex) {
				
				//We don't do anything. It is only in the case of lostfocus and no correct date has been entered.

				
			}

			
			/*
			 * The date format entered is correct, we replace the (.) by (/), that way we can pass an appropriate value to the variable of type LocalDate.
			 */
			//String replaceCharacter = newParking.anreiseDatumPlaceHolderTextField.getText().replace('.', '/');
			//
			////We pass an appropriate value to anreiseLocalDate using dateTimeFormatter.
			//this.anreiseLocalDate = LocalDate.parse(replaceCharacter, dateTimeFormatter);
			//
			////we indicate that everything concerning the arrival date is correct
			//this.anreiseOK = true;
			
			setAnreiseOk(true);

			
		}else {

			//in case not correct Date Format Expresion Regular. Date entered by the user wrong.
			setAnreiseOk(false);
			setDateFormatCorrect(false);
			setAnreiseLocalDate(null);

			
			
		}
		

	}
	
}



public void checkAbreiseDate() {
	
	String replaceCharacter = newParking.abreiseDatumPlaceholderTextField.getText().replace('.', '/');
	
	if(!isReadyToCloseNewParkingReservation() && isAnreiseOK()) {
		
		if(checkDateFormatBeforeSaveData(newParking.abreiseDatumPlaceholderTextField.getText(), "Abreisedatum", "abreiseDatumPlaceholderTextField")) {


			try {
				//Set the Localdatevalue for abreiseLocalDate variable
				setAbreiseLocalDate(LocalDate.parse(replaceCharacter, getDateTimeFormatterForSavingDataBase()));
				
				//Set Date value to the anreisedatum Date variable getting the value from the abreiseLocalDate.
				//This Date value will be saved in the Database Table Parking
				this.abreisedatum = Date.valueOf(abreiseLocalDate);
			
			}catch (DateTimeParseException ex) {
				//We don't do anything. It is only in the case of lostfocus and no correct date has been entered.
				
			}
			
			setAbreiseOK(true);
		//The two dates are correct we send to calculate them

			calculateTotalDays();
		}else {
			setAbreiseOK(false);
			setDateFormatCorrect(false);
			
			
			setAbreiseLocalDate(null);
			
		}
		
	
	}
}



@Override
public boolean checkDateFormatBeforeSaveData(String dateEnteredByUser, String inputTextBoxName,
		String componentHadFocus) {

	this.inputTextBoxName = inputTextBoxName;
	

	setComponentHadFocus(componentHadFocus);
	
	
		
		if(!errorDateFormat.isVisible()) {
			
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					messageErrorDateFormat = new JLabel(
							"Sie haben eine falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy)");
					
					panelErrorDateFormat = new JPanel(new BorderLayout());
					
//					// We Center the Error Messsage to the JPanel
					panelErrorDateFormat.add(messageErrorDateFormat, BorderLayout.CENTER);
					
					errorDateFormat = new JOptionPane(panelErrorDateFormat, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, 
							errorImg, optionButtonErrorDateFormat, null).createDialog("Kritische Fehler(" + inputTextBoxName + ")");
					
					if(!isDateFormatCorrect()) {

						errorDateFormat.setVisible(true);
						messageErrorDateFormat.setText("");
						newParking.tagenGeneratedJLabel.setText("0");
						newParking.betragGeneratedJLabel.setText("0.00 EUR");
						
					}
					

					

					
				}
			});
		
		
	
	}
	
	
	return super.checkDateFormatBeforeSaveData(dateEnteredByUser, this.inputTextBoxName, componentHadFocus);
}



/**
 * @return the anreiseLocalDate
 */
public LocalDate getAnreiseLocalDate() {
	return anreiseLocalDate;
}

/**
 * @param anreiseLocalDate the anreiseLocalDate to set
 */
public void setAnreiseLocalDate(LocalDate anreiseLocalDate) {
	this.anreiseLocalDate = anreiseLocalDate;
	
}

/**
 * @return the abreiseLocalDate
 */
public LocalDate getAbreiseLocalDate() {
	return abreiseLocalDate;
}

/**
 * @param abreiseLocalDate the abreiseLocalDate to set
 */
public void setAbreiseLocalDate(LocalDate abreiseLocalDate) {
	this.abreiseLocalDate = abreiseLocalDate;
}



public void calculateTotalDays() {
	

		/*
		 * I case the Departure Date is before the Arrival Date we display the error Message JDialog. 
		 */
		if (getAbreiseLocalDate().isBefore(getAnreiseLocalDate())) {
			
	
			
		if(!errorDateFormat.isVisible()) {
				
			
			
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						
						messageErrorDateFormat = new JLabel("Abreisedatum kann nicht früher als Anreisedatum sein!");
						
						panelErrorDateFormat = new JPanel(new BorderLayout());
						
						panelErrorDateFormat.add(messageErrorDateFormat, BorderLayout.CENTER);
	
						errorDateFormat = new JOptionPane(panelErrorDateFormat, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, 
								errorImg, optionButtonErrorDateFormat, null).createDialog("Kritische Fehler datum Chronologie");
						
						

							errorDateFormat.setVisible(true);
				
							/*
							 * Because the departure date is before than arrival date we indicate that the element had Focus was abreiseDatumPlaceholderTextFiel
							 * 
							 */
						    setComponentHadFocus("abreiseDatumPlaceholderTextField");
						
						
						
					}
				});
		
			
		}
		
		
		}else {
			

			/*
			 * The dates have a correct format and the date of departure is not earlier than the date of arrival, we give value to the variable totalDays.
			 * 
			 * for this we use the chronology between both dates.
			 */
//			long totalDays = ChronoUnit.DAYS.between(anreiseLocalDate, abreiseLocalDate);


			//totalDaysToPay receives the value of totalDays + 1 because it is also charged the day of delivery of the carl.(The parking Date).
			this.totalDaysToPay = calculateDatesPlus(anreiseLocalDate, abreiseLocalDate) + 1;
				

			newParking.tagenGeneratedJLabel.setText(String.valueOf(totalDaysToPay));

			/*
			 * If the total days are from 1 to 3 it costs 30CHF, if more than 3 days at 10CHF per day.
			 */
			if (this.totalDaysToPay <= 3) {
				this.betragTotal = 30d;
				newParking.betragGeneratedJLabel.setText(String.valueOf(betragTotal + " CHF"));
			} else {
				//Rate per day 10CHF
				this.betragTotal = this.totalDaysToPay * 10;
				newParking.betragGeneratedJLabel.setText(String.valueOf(betragTotal + " CHF"));
			}
			
			
		}
	
	

}




/**
 * @return the entryCompleted
 */
public boolean isEntryCompleted() {
	return entryCompleted;
}





/**
 * @param entryCompleted the entryCompleted to set
 */
public void setEntryCompleted(boolean entryCompleted) {
	this.entryCompleted = entryCompleted;
}



protected void addNewParkingReservationToDataBase() {

	//First we initialize our instance as a new object of ParkingReservation

	this.parkingReservation = new ParkingReservation();



	//We set the first three values to the fields, variables of the ParkingReservation object.

	this.parkingReservation.setIdParking(LogicModelNewParking.newParking.idParkingGenerated.getText());

	this.parkingReservation.setBuchungsname(LogicModelNewParking.newParking.buchungsNameJTextField.getText());
	
	this.parkingReservation.setAutoKFZ(LogicModelNewParking.newParking.autoKFZJTextField.getText());
	
	this.parkingReservation.setAnreiseDatum(anreisedatum);

	this.parkingReservation.setAbreiseDatum(abreisedatum);



	//We set the total Dates the user going to park by us.

	this.parkingReservation.setAnzahlTagen(Integer.parseInt(LogicModelNewParking.newParking.tagenGeneratedJLabel.getText()));



	//We set how much he has to pay or he has payed.

	this.parkingReservation.setBetragParking(this.betragTotal);



	//We set how was the Parking booked

	this.parkingReservation.setBuchungsKanal(LogicModelNewParking.newParking.buchunsKanalJTextField.getText());



	//Special comments

	this.parkingReservation.setBemerkungen(LogicModelNewParking.newParking.bemerkungenJTextField.getText());



	//leave the key or not

	this.parkingReservation.setSchluesselInHaus(LogicModelNewParking.newParking.schluesselBox.getSelectedItem().toString());



	//And who proceeded to treat the reservation(Team by the Hotel).

	this.parkingReservation.setAbkuerzungMA(LogicModelNewParking.newParking.abkuerzungMAGeneratedJLabel.getText());
	
	//We create a new DAOParking object passing the arguments needed.

	DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, loading);



	//We call for addNewParkingReservation

	try {
		daoParking.addNewParkingReservation(parkingReservation, userAHB);
	} catch (DaoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	//We close the GUI
    newParking.dispose();
	
}








}
