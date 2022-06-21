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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tech.codepalace.dao.DAOParking;
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
	private NewParking newParking;
	protected ParkingReservation parkingReservation;
	private Loading loading;

	
	
	protected String dbName;
	
	
	//Variable to use in case the user enters incorrect data.
	public JDialog errorDateFormatAnreise, errorDateFormatAbreise, errorDateFormat, errorEntries;
	
	private JButton okButtonAnreiseError = new JButton("OK"), okButtonAbreiseError  = new JButton("OK"), okButtonErrorDateFormat  = new JButton("OK"),  okButtonEntriesError  = new JButton("OK");
	
	private JLabel messageAnreiseError, messageAbreiseError, messageErrorDateFormat, messageEntriesError;
	
	private JPanel  myPanelDialogAnreise = new JPanel(new BorderLayout()),  myPanelDialogAbreise = new JPanel(new BorderLayout()), myPanelDialogEntries = new JPanel(new BorderLayout());
	
	private Object[] optionButtonsAnreise = { this.okButtonAnreiseError }, optionButtonsAbreise = { this.okButtonAbreiseError }, optionButtonEntries = { this.okButtonEntriesError };
	
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	//Variable to get the Total days to be payed.
	private long totalDaysToPay;
	
	//LocalDate for the arrival Date and departure date.
	private LocalDate anreiseLocalDate, abreiseLocalDate;
	
	//LocalDateTime to set the todays Date in LocalDateTime format.
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	//Variable LocalDate to set the todays Date in LocalDate format we need to convert some dates thats why we need both format.
	protected LocalDate nowLocalDate;
	
	//DateTimeFormatter to set the Date Format we need.
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	
	//Variable boolean to check if the user is finished entering all the information. 
	private boolean entryCompleted = false;
	
	//Variables to set and check if the Arrival and Departure dates are in the expected format.
	private boolean anreiseOK = false, abreiseOK = false;
	
	//Variable to check if the user has pressed clothing NewParkingReservation you will find more information in the code below.
	private boolean closingNewParkingReservation = false;
	
	//Variables to display error, format error in case of Arrival date or departure date. You will find more information in the code below.
	public boolean errorDateDepartureMessageDelivered = false, errorDateArrivalMessageDelivered = false;
	
	//Variable to set or to check Arrival Date Focus.
	private boolean anreiseFocus = false;
	
	
	
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
		this.newParking = newParking;
		this.userAHB = userAHB;
		this.loading = loading;

		//nowLoacalDate for the current date in LocalDate variable
		this.nowLocalDate = now.toLocalDate();
		
		
		//We initialize the error message for the departure date. In case error this will be displayed.
		this.errorDateFormatAbreise = new JOptionPane(this.myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
				this.optionButtonsAbreise, null).createDialog("kritischer Fehler (Abreisedatum)");
		
		
		//We initialize the error message for the Arrival date. In case error this will be displayed.
		this.errorDateFormatAnreise = new JOptionPane(this.myPanelDialogAnreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
				this.optionButtonsAnreise, null).createDialog("kritischer Fehler (Anreisedatum)");
		
		
	
		//Add ActionListener to the OK Button by the Error Message JDialog.
		this.okButtonErrorDateFormat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				errorDateFormat.dispose();
				messageErrorDateFormat.setText("");
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

				errorDateFormat.dispose();
				messageErrorDateFormat.setText("");
			}
		});
		
		
		
		
		//The same for the buttons below.
		this.okButtonAnreiseError.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				errorDateFormatAnreise.dispose();
				messageAnreiseError.setText("");
				setErrorDateArrivalMessageDelivered(false);
				setErrorDateDepartureMessageDelivered(true);
				setAnreiseFocus(false);
			}
		});
		
		this.okButtonAnreiseError.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {

				errorDateFormatAnreise.dispose();
				messageAnreiseError.setText("");
				setErrorDateArrivalMessageDelivered(false);
				setErrorDateDepartureMessageDelivered(true);
				setAnreiseFocus(false);
				
			}
		});
		
		
		
		
		
		
		this.okButtonAbreiseError.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				errorDateFormatAbreise.dispose();
				messageAbreiseError.setText("");
				setErrorDateDepartureMessageDelivered(false);
			}
		});
		
		
		this.okButtonAbreiseError.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {

				errorDateFormatAbreise.dispose();
				messageAbreiseError.setText("");
				setErrorDateDepartureMessageDelivered(false);
			}
		});
		
		
		
		
		
this.okButtonEntriesError.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				errorEntries.dispose();
				messageEntriesError.setText("");
			}
		});
		
		
		this.okButtonEntriesError.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {

				errorEntries.dispose();
				messageEntriesError.setText("");
			}
		});
		
		
		
		
		
		
		
	}
	
	/**
	 * @description method to evaluate that the date format corresponds to the desired one
	 */
	public void checkAnreiseDateFormat() {
		
		//Regex Format to evaluate the date Format.
		String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$";
		
		
		//Error message in case a wrong expected Date format. 
		this.messageAnreiseError = new JLabel(
				"Sie haben ein falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy)");
		
		
		
		this.myPanelDialogAnreise.add(messageAnreiseError, BorderLayout.CENTER);
		
		
		//in case it does not correspond to the date format
		if (!Pattern.matches(formatDateRegex, this.newParking.anreiseDatumPlaceHolderTextField.getText())) {
			
			if(!closingNewParkingReservation) {
				if(!this.errorDateFormatAbreise.isVisible()) {
					
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							
							errorDateFormatAnreise = new JOptionPane(myPanelDialogAnreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
									optionButtonsAnreise, null).createDialog("Falsches Datumsformat Anreisedatum!");
							
							
							
							errorDateFormatAnreise.setAlwaysOnTop(true);
							errorDateFormatAnreise.setVisible(true);
							errorDateFormatAnreise.dispose();
							anreiseOK=false;
							messageAnreiseError.setText("");
							newParking.tagenGeneratedJLabel.setText("0");
							newParking.betragGeneratedJLabel.setText("0.00 EUR");
							errorDateArrivalMessageDelivered = true;
							newParking.anreiseDatumPlaceHolderTextField.requestFocus();
							
						}
					});
					
						
					
			
				
			}
			}
			
			

			
		} else {

			
			/*
			 * The date format entered is correct, we replace the (.) by (/), that way we can pass an appropriate value to the variable of type LocalDate.
			 */
			String replaceCharacter = this.newParking.anreiseDatumPlaceHolderTextField.getText().replace('.', '/');

			//We pass an appropriate value to anreiseLocalDate using dateTimeFormatter.
			this.anreiseLocalDate = LocalDate.parse(replaceCharacter, dateTimeFormatter);
			
			//we indicate that everything concerning the arrival date is correct
			this.anreiseOK = true;
			

			
		}
		
		
	}
	
	
	
	
	
	
	
	
	/**
	 * @description method to evaluate that the date format corresponds to the desired one
	 */
	public void checkAbreiseDateFormat() {
		
		
		
		//Some of the instructions are the same as in the above method. We save time commenting double
		
		
		//Regex Format to evaluate the date Format.
		String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$";

		
		this.messageAbreiseError = new JLabel(
				"Sie haben ein falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy)");
		

		
		this.myPanelDialogAbreise.add(this.messageAbreiseError, BorderLayout.CENTER);
		
		//in case it does not correspond to the date format
		if (Pattern.matches(formatDateRegex, this.newParking.abreiseDatumPlaceholderTextField.getText())) {
			
			

			String replaceCharacter = this.newParking.abreiseDatumPlaceholderTextField.getText().replace('.', '/');
			this.abreiseLocalDate = LocalDate.parse(replaceCharacter, dateTimeFormatter);

			this.messageAbreiseError.setText("Abreisedatum kann nicht früher als Anreisedatum sein!");
			
			/*
			 * We check if the arrival date is ok to check without errors the departure date if it is earlier than the arrival date.
				That's why we need to do this check first to be sure before proceeding to  
			check between the two dates that the previous date has been processed correctly and 
			in the right format.
			 */
			
			
			
			
			if(this.anreiseOK) {
				
				//if the departure date is earlier than the arrival date
				if (this.abreiseLocalDate.isBefore(anreiseLocalDate)) {

					
					//if the user does not intend to close the entry Jdialog box and the arrival date doesn't have the focus
					if(!closingNewParkingReservation && !this.anreiseFocus) {
						
						//If have not thrown an error message Departure Date Message Error
						if(!this.errorDateDepartureMessageDelivered) {
							
							
							//Then we call all the instructions below
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {

									errorDateFormatAbreise = new JOptionPane(myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
											optionButtonsAbreise, null).createDialog("kritischer Fehler (Abreisedatum)");

									
									errorDateFormatAbreise.setAlwaysOnTop(true);
									errorDateFormatAbreise.setVisible(true);
									errorDateFormatAbreise.dispose();
									abreiseOK=false;
									messageAbreiseError.setText("");
									newParking.tagenGeneratedJLabel.setText("0");
									newParking.betragGeneratedJLabel.setText("0.00 EUR");
									newParking.abreiseDatumPlaceholderTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
									newParking.abreiseDatumPlaceholderTextField.requestFocus();
									errorDateDepartureMessageDelivered = false;
									
								}
							});
							
							
							
							
							
						}
						
					}

					
					
					
		
					
					//If the departure date is earlier than the current date, it is not correct
				} else {
					
					
					
					
					/*
					 * The dates have a correct format and the date of departure is not earlier than the date of arrival, we give value to the variable totalDays.
					 * 
					 * for this we use the chronology between both dates.
					 */
					long totalDays = ChronoUnit.DAYS.between(anreiseLocalDate, abreiseLocalDate);


					//totalDaysToPay receives the value of totalDays + 1 because it is also charged the day of delivery of the carl.(The parking Date).
					this.totalDaysToPay = totalDays + 1; 

					this.newParking.tagenGeneratedJLabel.setText(String.valueOf(totalDaysToPay));

					/*
					 * If the total days are from 1 to 3 it costs 30CHF, if more than 3 days at 10CHF per day.
					 */
					if (this.totalDaysToPay <= 3) {
						this.betragTotal = 30d;
						this.newParking.betragGeneratedJLabel.setText(String.valueOf(betragTotal + " CHF"));
					} else {
						//Rate per day 10CHF
						this.betragTotal = this.totalDaysToPay * 10;
						this.newParking.betragGeneratedJLabel.setText(String.valueOf(betragTotal + " CHF"));
					}
					
					this.abreiseOK = true;
					
					

				}
				
				
				
			}else {
				
			
				
				
				//Arrival date is not OK we display an error message.
				
				this.messageAbreiseError.setText("Anreisdatum darf nicht leer sein. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy)");
				
				
				//We check if errorDateFormatAnreise is visible and we continue evaluate inside the block.
				//All of this is needed to play with the focus Lost and Get Focus.
				if(!this.errorDateFormatAnreise.isVisible()) {
					
					//if the user has not decided to close the NewParking GUI and Arrival-JTextFiled  has not the Focus.
					if(!closingNewParkingReservation && !this.anreiseFocus) {
						
						//If we did not displayed any Error of Departure Date format.
						if(!this.errorDateDepartureMessageDelivered) {
							
							
							//Invoke a new Thread with the errorDateFormatAbreise(Error Departure Date).
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									

									errorDateFormatAbreise = new JOptionPane(myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
											optionButtonsAbreise, null).createDialog("kritischer Fehler (Anreisedatum)");
									

									errorDateFormatAbreise.setAlwaysOnTop(true);
									errorDateFormatAbreise.setVisible(true);
									errorDateFormatAbreise.dispose();
									abreiseOK=false;
									messageAbreiseError.setText("");
									newParking.tagenGeneratedJLabel.setText("0");
									newParking.betragGeneratedJLabel.setText("0.00 EUR");
									newParking.anreiseDatumPlaceHolderTextField.requestFocus();
									
								}
							});
						}
						
					}
						
							
						
				
					
				}
				
				
				
				
			}
			

			
	
		} else { 
			

			//The operation of this code is the same principle as the code above.
			if(!this.errorDateFormatAnreise.isVisible()) {
				
				
						
						
						if(!closingNewParkingReservation) {
							
							if(!errorDateDepartureMessageDelivered && !this.anreiseFocus) {
								
								SwingUtilities.invokeLater(new Runnable() {
									
									@Override
									public void run() {
										
										errorDateFormatAbreise = new JOptionPane(myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
												optionButtonsAbreise, null).createDialog("kritischer Fehler (Abreisedatum)");
										

										errorDateFormatAbreise.setAlwaysOnTop(true);
										errorDateFormatAbreise.setVisible(true);
										errorDateFormatAbreise.dispose();
										abreiseOK=false;
										messageAbreiseError.setText("");
										newParking.tagenGeneratedJLabel.setText("0");
										newParking.betragGeneratedJLabel.setText("0.00 EUR");
										newParking.abreiseDatumPlaceholderTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
										newParking.abreiseDatumPlaceholderTextField.requestFocus();
										errorDateDepartureMessageDelivered = false;
									}
								});
								
							}
				
						}

						
				
				
				
			}
			
			
			
			
		
		}
				

	}

	
	
/**
 * @description method to establish whether the arrival date, entry is correct or not
 * @param anreiseOk
 */
public void setAnreiseOk(boolean anreiseOk) {
	this.anreiseOK = anreiseOk;
}


/**
 * @description method to set whether the user intends to close or not the NewParkingReservation
 * @param closingNewParkingReservation
 */
public void setClosingNewParkingReservation(boolean closingNewParkingReservation) {
	
	this.closingNewParkingReservation = closingNewParkingReservation;
	
	
}

/**
 * @description method to indicate whether the error message was delivered or not(Departure)
 * @param messageDelivered
 */
public void setErrorDateDepartureMessageDelivered(boolean messageDelivered) {
	this.errorDateDepartureMessageDelivered = messageDelivered;
}


/**
 * @description method to indicate whether the error message was delivered or not(Arrival)
 * @param messageDelivered
 */
public void setErrorDateArrivalMessageDelivered(boolean messageDelivered) {
	this.errorDateArrivalMessageDelivered = messageDelivered;
}



/**
 * @description Method to indicate if Arrival has the focus
 * @param anreiseFocus
 */
public void setAnreiseFocus(boolean anreiseFocus) {
	this.anreiseFocus = anreiseFocus;
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
	if(this.newParking.buchungsNameJTextField.getText().equalsIgnoreCase("") ||
			this.newParking.buchungsNameJTextField.getText().length()< 3 ) {
		
		this.entryCompleted=false;
		this.newParking.buchungsNameJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
	}else {
		
		//If everything is correctly entered we set the value to the entryCompleted as true.
		this.entryCompleted=true;
	}
	
	
	
	//This code do the same as the code above.
	if(this.newParking.autoKFZJTextField.getText().equalsIgnoreCase("") ||
			this.newParking.autoKFZJTextField.getText().length()<3) {
		this.entryCompleted=false;
		this.newParking.autoKFZJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
	}else {
		this.entryCompleted= true;
	}
	
	
	//This code do the same as the code above.
	if(this.newParking.buchunsKanalJTextField.getText().equalsIgnoreCase("") ||
			this.newParking.buchunsKanalJTextField.getText().length()<3) {
		
		this.entryCompleted = false;
		this.newParking.buchunsKanalJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
	}else {
		this.entryCompleted = true;
	}
	
	
	//if the necessary JTextBoxes of the form are not filled in correctly.
	if(!this.entryCompleted) {
	
		
		this.messageEntriesError = new JLabel("Bitte geben Sie die erforderlichen Daten ein und nicht weniger als 3 Schriftzeichen");
		this.myPanelDialogEntries.add(this.messageEntriesError);
		
		this.errorEntries = new JOptionPane(this.myPanelDialogEntries, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
				this.optionButtonEntries, null).createDialog("Unvollständiges Formular!");
		
		
		
		this.errorEntries.setAlwaysOnTop(true);
		this.errorEntries.setVisible(true);
		this.errorEntries.dispose();
		
		
	}else {
		
		//If everything is correctly filled in we precede saving the data.
		if(this.anreiseOK && this.abreiseOK && this.entryCompleted) {
			try {

				//First we initialize our instance as a new object of ParkingReservation
				this.parkingReservation = new ParkingReservation();
				
				//We set the first three values to the fields, variables of the ParkingReservation object.
				this.parkingReservation.setIdParking(this.newParking.idParkingGenerated.getText());
				this.parkingReservation.setBuchungsname(this.newParking.buchungsNameJTextField.getText());
				this.parkingReservation.setAutoKFZ(this.newParking.autoKFZJTextField.getText());
				
				//We use a DateTimeFormatter to set the Date Format we need. 
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				
				//We set the value to the LocalDate variables using the LocalDate.parse method and as parameters the Date inside the PlaceHolderTextField
				//as second argument the DateTimeFormatter with the format we need dd.MM.yyyy for the LocalDate.
				this.anreiseLocalDate = LocalDate.parse(this.newParking.anreiseDatumPlaceHolderTextField.getText(), formatter);
				this.abreiseLocalDate = LocalDate.parse(this.newParking.abreiseDatumPlaceholderTextField.getText(), formatter);
				
				//Now for the Date variables we get the value using the Date.valueOf Method and as argument we pass the 
				//LocalDate Variables.
				this.anreisedatum = Date.valueOf(this.anreiseLocalDate);
				this.abreisedatum = Date.valueOf(this.abreiseLocalDate);
				
				//Now finally we set the value of the variables Date for Arrival and Departure.
				this.parkingReservation.setAnreiseDatum(anreisedatum);
				this.parkingReservation.setAbreiseDatum(abreisedatum);
				
				//We set the total Dates the user going to park by us.
				this.parkingReservation.setAnzahlTagen(Integer.parseInt(this.newParking.tagenGeneratedJLabel.getText()));
				
				//We set how much he has to pay or he has payed.
				this.parkingReservation.setBetragParking(this.betragTotal);
				
				//We set how was the Parking booked
				this.parkingReservation.setBuchungsKanal(this.newParking.buchunsKanalJTextField.getText());
				
				//Special comments
				this.parkingReservation.setBemerkungen(this.newParking.bemerkungenJTextField.getText());
				
				//leave the key or not
				this.parkingReservation.setSchluesselInHaus(this.newParking.schluesselBox.getSelectedItem().toString());
				
				//And who proceeded to treat the reservation(Team by the Hotel).
				this.parkingReservation.setAbkuerzungMA(this.newParking.abkuerzungMAGeneratedJLabel.getText());
				

				//We create a new DAOParking object passing the arguments needed.
				DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, loading);
				
				//We call for addNewParkingReservation
				daoParking.addNewParkingReservation(parkingReservation, userAHB);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
}






	

}
