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


/*
 * Para comprobar cuando el foco lo tenga abreise si Anreise format es ok. 
 * 
 * Aqui en esta GUI tenemos solo dos fechas que comprobar, si tuvieramos varias fechas que comprobar tuvieramos que 
 * recorrer la comprobacion de todas las fechas anteriores a donde la fecha que se encuentra el cursor para poder establecer el cursor en las fechas
 * anteriores.
 */

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
			JOptionPane.showMessageDialog(null, "Estamos listos para guardar Parking");
		
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
	
//	JOptionPane.showMessageDialog(null, "Ready to close: " + logicModelNewParking.isReadyToCloseNewParkingReservation());
//	logicModelNewParking.checkDateFormatBeforeSaveData(this.newParking.anreiseDatumPlaceHolderTextField.getText(), "AnreiseDatum");
	
	
//	this.logicModelNewParking.setReadyToCloseNewParkingReservation(false);
	
	
	
	
	
	if(!isReadyToCloseNewParkingReservation() !=isAbreiseOK()) {
		
		
		
		if(checkDateFormatBeforeSaveData(newParking.anreiseDatumPlaceHolderTextField.getText(), "Anreisedatum", "anreiseDatumPlaceHolderTextField")) {
//			JOptionPane.showMessageDialog(null, "Fecha correcta");
//			this.logicModelNewParking.setAnreiseOk(true);
			
			/*
			 * The date format entered is correct, we replace the (.) by (/), that way we can pass an appropriate value to the variable of type LocalDate.
			 */
			String replaceCharacter = newParking.anreiseDatumPlaceHolderTextField.getText().replace('.', '/');
		
			
			try {
				setAnreiseLocalDate(LocalDate.parse(replaceCharacter, getDateTimeFormatterForSavingDataBase()));
				
			
			}catch (DateTimeParseException ex) {
				
				//No hacemos nada. Es solo en el caso de lostfocus y no se ha introducido fecha correcta.

				
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
//			this.entryCompleted = true;
			
		}else {
//			JOptionPane.showMessageDialog(null, "Fecha incorrecta");
			setAnreiseOk(false);
			setDateFormatCorrect(false);
			setAnreiseLocalDate(null);
//			this.entryCompleted = false;
			
			
		}
		

	}
	
}



public void checkAbreiseDate() {
	
	String replaceCharacter = newParking.abreiseDatumPlaceholderTextField.getText().replace('.', '/');
	
	if(!isReadyToCloseNewParkingReservation() && isAnreiseOK()) {
		
		if(checkDateFormatBeforeSaveData(newParking.abreiseDatumPlaceholderTextField.getText(), "Abreisedatum", "abreiseDatumPlaceholderTextField")) {

			
			try {
				setAbreiseLocalDate(LocalDate.parse(replaceCharacter, getDateTimeFormatterForSavingDataBase()));
				
				
				//Aqui cuando pierde el foco la fecha que tiene que calcular la cronologia podemos llamar a un metodo que la calcule y de el resultado
				//Al Textbox. Nos quedamos aqui programando.
			
			}catch (DateTimeParseException ex) {
				//No hacemos nada. Es solo en el caso de lostfocus y no se ha introducido fecha correcta.
				
			}
			
			setAbreiseOK(true);
//			//Las dos fechas son correctas mandamos a calcularlas
//			if(this.logicModelNewParking.isAnreiseOK() && this.logicModelNewParking.isAbreiseOK()) {
//				this.logicModelNewParking.calculateTotalDays();
//			}
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
	// TODO Auto-generated method stub
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
	
	
	
	
	
	
	//Incluir un boton de ok que se pueda gestionar los listener
	
	
//JOptionPane.showMessageDialog(null, panelErrorDateFormat,  "Kritische Fehler Toto(" + inputTextBoxName + ")", JOptionPane.OK_OPTION, errorImg);
	
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








}
