package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import tech.codepalace.view.frames.AHBParking;
import tech.codepalace.view.frames.NewParking;

public class LogicModelNewParking {
	
	protected UserAHB userAHB;
	protected AHBParking ahbParking;
	protected NewParking newParking;
	protected ParkingReservation parkingReservation;
	protected String urlDB, dbName;
	
	
	
	public JDialog errorDateFormatAnreise, errorDateFormatAbreise, errorDateFormat, errorEntries;
	
	private JButton okButtonAnreiseError = new JButton("OK"), okButtonAbreiseError  = new JButton("OK"), okButtonErrorDateFormat  = new JButton("OK"),  okButtonEntriesError  = new JButton("OK");
	
	private JLabel messageAnreiseError, messageAbreiseError, messageErrorDateFormat, messageEntriesError;
	
	private JPanel  myPanelDialogAnreise = new JPanel(new BorderLayout()),  myPanelDialogAbreise = new JPanel(new BorderLayout()), myPanelDialogEntries = new JPanel(new BorderLayout());
	
	private Object[] optionButtonsAnreise = { this.okButtonAnreiseError }, optionButtonsAbreise = { this.okButtonAbreiseError }, optionButtonEntries = { this.okButtonEntriesError };
	
	
	private long totalDaysToPay;
	
	private LocalDate anreiseLocalDate, abreiseLocalDate;
	
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	protected LocalDate nowLocalDate;
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	private boolean entryCompleted = false;
	
	private boolean anreiseOK = false, abreiseOK = false;
	
	private boolean closingNewParkingReservation = false;
	
	public boolean errorDateDepartureMessageDelivered = false, errorDateArrivalMessageDelivered = false;
	
	private boolean anreiseFocus = false;
	
	
	
	
	protected double betragTotal;
	
	
	//Variables to save data in the database
	protected String idParking,buchungsname, autokfz;
	protected Date anreisedatum, abreisedatum;
	protected int anzahltagen;
	protected double betragparking;
	protected String buchungskanal, bemerkungen, schluesselinhaus, verkaufer;
	
	
	
	public LogicModelNewParking(UserAHB userAHB, AHBParking ahbParking, NewParking newParking, ParkingReservation parkingReservation, String urlDB, String dbName) {
		
		this.userAHB = userAHB;
		this.ahbParking = ahbParking;
		this.newParking = newParking;
		this.parkingReservation = parkingReservation;
		this.urlDB = urlDB;
		this.dbName = dbName;
//		JOptionPane.showMessageDialog(null, "LogicaModelNewParking recibe urldatabase: " + this.urlDB);
//		JOptionPane.showMessageDialog(null, "LogicaModelNewParking recibe dbName: " + this.dbName);
		
		//nowLoacalDate for the current date in LocalDate variable
		this.nowLocalDate = now.toLocalDate();
		
		
		this.errorDateFormatAbreise = new JOptionPane(this.myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
				this.optionButtonsAbreise, null).createDialog("kritischer Fehler (Abreisedatum)");
		
		this.errorDateFormatAnreise = new JOptionPane(this.myPanelDialogAnreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
				this.optionButtonsAnreise, null).createDialog("kritischer Fehler (Anreisedatum)");
		
		
	
		
		this.okButtonErrorDateFormat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				errorDateFormat.dispose();
				messageErrorDateFormat.setText("");
			}
		});
		
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
				} else if(abreiseLocalDate.isBefore(nowLocalDate)) {
					
					

					
					if(!closingNewParkingReservation && !this.anreiseFocus) {
						
					   if(!this.errorDateDepartureMessageDelivered) {
						   
						   SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								messageAbreiseError.setText("Abreisedatum kann nicht früher als heutiges Datum sein!");
								
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
					
					
					
					
				}else {
					
					
					
					
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
				
			
				
				
				//Anreise no es ok procedemos a lanzar un mensaje de error
				
				this.messageAbreiseError.setText("Anreisdatum darf nicht leer sein. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy)");
				
				
				if(!this.errorDateFormatAnreise.isVisible()) {
					
					if(!closingNewParkingReservation && !this.anreiseFocus) {
						
						if(!this.errorDateDepartureMessageDelivered) {
							
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
 * @description This method will check if all required entries are completed correctly
 */
public void checkAllEntries() {
	
	
	if(this.newParking.buchungsNameJTextField.getText().equalsIgnoreCase("") ||
			this.newParking.buchungsNameJTextField.getText().length()< 3 ) {
		
		this.entryCompleted=false;
		this.newParking.buchungsNameJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
	}else {
		this.entryCompleted=true;
	}
	
	
	
	if(this.newParking.autoKFZJTextField.getText().equalsIgnoreCase("") ||
			this.newParking.autoKFZJTextField.getText().length()<3) {
		this.entryCompleted=false;
		this.newParking.autoKFZJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
	}else {
		this.entryCompleted= true;
	}
	
	
	
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
			addNewParkingReservationToDataBase();
		}else {
			
			
			
			if(!this.anreiseOK) {
//				this.messageErrorDateFormat = new JLabel("") a seguir trabajando desde aqui
			}
			
		}
	}
		
}



/**
 * @description Method to add the new Parking Reservation to DataBase Table parking
 */
protected void addNewParkingReservationToDataBase() {
	

	
	this.idParking = this.newParking.idParkingGenerated.getText();
	this.buchungsname = this.newParking.buchungsNameJTextField.getText();
	this.autokfz = this.newParking.autoKFZJTextField.getText();
	
	SimpleDateFormat simpeDateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	
	this.anreisedatum = Date.valueOf(this.anreiseLocalDate);
	this.abreisedatum = Date.valueOf(this.abreiseLocalDate);
	
	this.anzahltagen = (int) this.totalDaysToPay;
	
	this.betragparking = this.betragTotal;
	
	this.buchungskanal = this.newParking.buchunsKanalJTextField.getText();
	this.bemerkungen = this.newParking.bemerkungenJTextField.getText();
	this.schluesselinhaus = this.newParking.schluesselBox.getSelectedItem().toString();
	
	this.verkaufer = this.newParking.abkuerzungMAGeneratedJLabel.getText();
	
	
	
	String dateToFormat = simpeDateFormat.format(this.anreisedatum);

	JOptionPane.showMessageDialog(null, "fecha localdate en sql.Date: " +this.anreisedatum);
	JOptionPane.showMessageDialog(null, "fecha convertida en String: " + dateToFormat);
	
	JOptionPane.showMessageDialog(null, "Schluesel in haus: " + this.schluesselinhaus);
	
	JOptionPane.showMessageDialog(null, "Total days to pay: " + this.anzahltagen);
	
	JOptionPane.showMessageDialog(null, "Betragparking: " + this.betragparking);
	
	
	/*
	 * DAOParking daoParking = new DaoParkingImpl(urlDataBase, dbName, userAHB, ahbParking, parkingReservation);
	 */
	
	DAOParking daoParking = new DaoParkingImpl(this.urlDB, this.dbName, this.userAHB, this.ahbParking, this.parkingReservation);

}



	

}
