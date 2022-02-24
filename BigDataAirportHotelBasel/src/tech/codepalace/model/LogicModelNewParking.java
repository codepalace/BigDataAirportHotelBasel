package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import tech.codepalace.view.frames.AHBParking;
import tech.codepalace.view.frames.NewParking;

public class LogicModelNewParking {
	
	protected UserAHB userAHB;
	protected AHBParking ahbParking;
	protected NewParking newParking;
	
	
	
	private JDialog errorDateFormatAnreise, errorDateFormatAbreise, errorEntries;
	
	private JButton okButtonAnreiseError = new JButton("OK"), okButtonAbreiseError  = new JButton("OK"), okButtonEntriesError  = new JButton("OK");
	
	private JLabel messageAnreiseError, messageAbreiseError, messageEntriesError;
	
	private JPanel myPanelDialogAnreise = new JPanel(new BorderLayout()),  myPanelDialogAbreise = new JPanel(new BorderLayout()), myPanelDialogEntries = new JPanel(new BorderLayout());
	
	private Object[] optionButtonsAnreise = { this.okButtonAnreiseError }, optionButtonsAbreise = { this.okButtonAbreiseError }, optionButtonEntries = { this.okButtonEntriesError };
	
	
	
	
	private LocalDate anreiseLocalDate, abreiseLocalDate;
	
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	protected LocalDate nowLocalDate;
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	private boolean readyToSaveArrival=false, readyToSaveDepature = false;
	
	private boolean entryCompleted = false;
	
	private boolean anreiseOK = false, abreiseOK = false;
	
	
	
	
	protected double betragTotal;
	
	
	
	public LogicModelNewParking(UserAHB userAHB, AHBParking ahbParking, NewParking newParking) {
		
		this.userAHB = userAHB;
		this.ahbParking = ahbParking;
		this.newParking = newParking;
		
		//nowLoacalDate for the current date in LocalDate variable
		this.nowLocalDate = now.toLocalDate();
		
		
		
		this.okButtonAnreiseError.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				errorDateFormatAnreise.dispose();
				messageAnreiseError.setText("");
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
			}
		});
		
		
		
		
		
		
		this.okButtonAbreiseError.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				errorDateFormatAbreise.dispose();
				messageAbreiseError.setText("");
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
			
			

			this.errorDateFormatAnreise = new JOptionPane(this.myPanelDialogAnreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
					this.optionButtonsAnreise, null).createDialog("Falsches Datumsformat Anreisedatum!");
			
			
			
			
			//if we are ready to save or process the information
			if(this.readyToSaveArrival) {
				
				//we throw the error message since the date format does not correspond to the desired one.
				this.errorDateFormatAnreise.setAlwaysOnTop(true);
				this.errorDateFormatAnreise.setVisible(true);
				this.errorDateFormatAnreise.dispose();
				setReadyToSaveArrival(false);
				this.anreiseOK=false;
				
				//Reset all the time messgeAnreiseError text to ""
				this.messageAnreiseError.setText("");
				this.newParking.tagenGeneratedJLabel.setText("0");
				this.newParking.betragGeneratedJLabel.setText("0.00 EUR");
			}else {
				
				this.newParking.anreiseDatumPlaceHolderTextField.setSize(310, 30);
				
				/*
				 * otherwise we only paint a red border to indicate that there is an error in the date. 
				 * We do this to avoid repeating the error message and collapsing with the error message of the arrival date and departure date.
				 */
				this.newParking.anreiseDatumPlaceHolderTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				this.anreiseOK=false;
				
				//Reset values
				this.newParking.tagenGeneratedJLabel.setText("0");
				this.newParking.betragGeneratedJLabel.setText("0.00 EUR");
			}

			this.messageAnreiseError.setText("");
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
		if (!Pattern.matches(formatDateRegex, this.newParking.abreiseDatumPlaceholderTextField.getText())) {
			
			

	
			this.errorDateFormatAbreise = new JOptionPane(this.myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
					this.optionButtonsAbreise, null).createDialog("Falsches Datumsformat! Abreisedatum");
			


			if(this.readyToSaveDepature) {
				this.errorDateFormatAbreise.setAlwaysOnTop(true);
				this.errorDateFormatAbreise.setVisible(true);
				this.errorDateFormatAbreise.dispose();
				setReadyToSaveDeparture(false);
				this.abreiseOK=false;
				this.messageAbreiseError.setText("");
				
					
			}else {
				this.newParking.abreiseDatumPlaceholderTextField.setSize(310,30);
				this.newParking.abreiseDatumPlaceholderTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				this.abreiseOK=false;
				
			}
			//Reset values
			this.messageAbreiseError.setText("");
			this.newParking.tagenGeneratedJLabel.setText("0");
			this.newParking.betragGeneratedJLabel.setText("0.00 EUR");
			
		} else {
			String replaceCharacter = this.newParking.abreiseDatumPlaceholderTextField.getText().replace('.', '/');
			this.abreiseLocalDate = LocalDate.parse(replaceCharacter, dateTimeFormatter);

			this.messageAbreiseError.setText("Abreisedatum kann nicht früher als Anreisedatum sein!");
			
		
			//If the departure date is earlier than the arrival date, it is not correct
			if (this.abreiseLocalDate.isBefore(anreiseLocalDate)) {

				
				this.errorDateFormatAbreise = new JOptionPane(this.myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
						this.optionButtonsAbreise, null).createDialog("kritischer Fehler (Abreisedatum)");


				this.errorDateFormatAbreise.setAlwaysOnTop(true);
				this.errorDateFormatAbreise.setVisible(true);
				this.errorDateFormatAbreise.dispose();
				this.abreiseOK=false;
				this.messageAbreiseError.setText("");
				this.newParking.tagenGeneratedJLabel.setText("0");
				this.newParking.betragGeneratedJLabel.setText("0.00 EUR");
				
	
				
				//If the departure date is earlier than the current date, it is not correct
			} else if(abreiseLocalDate.isBefore(nowLocalDate)) {
				this.messageAbreiseError.setText("Abreisedatum kann nicht früher als heutiges Datum sein!");
				
				this.errorDateFormatAbreise = new JOptionPane(this.myPanelDialogAbreise, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg,
						this.optionButtonsAbreise, null).createDialog("kritischer Fehler (Abreisedatum)");
				

				
				
				this.errorDateFormatAbreise.setAlwaysOnTop(true);
				this.errorDateFormatAbreise.setVisible(true);
				this.errorDateFormatAbreise.dispose();
				this.abreiseOK=false;
				this.messageAbreiseError.setText("");
				this.newParking.tagenGeneratedJLabel.setText("0");
				this.newParking.betragGeneratedJLabel.setText("0.00 EUR");
			}
			
			else {
				
				
				if(!this.anreiseOK) {
					this.newParking.tagenGeneratedJLabel.setText("0");
					this.newParking.betragGeneratedJLabel.setText("0.00 EUR");
				}else {
				
				
						
						/*
						 * The dates have a correct format and the date of departure is not earlier than the date of arrival, we give value to the variable totalDays.
						 * 
						 * for this we use the chronology between both dates.
						 */
						long totalDays = ChronoUnit.DAYS.between(anreiseLocalDate, abreiseLocalDate);


						//totalDaysToPay receives the value of totalDays + 1 because it is also charged the day of delivery of the carl.(The parking Date).
						long totalDaysToPay = totalDays + 1; 

						this.newParking.tagenGeneratedJLabel.setText(String.valueOf(totalDaysToPay));

						/*
						 * If the total days are from 1 to 3 it costs 30CHF, if more than 3 days at 10CHF per day.
						 */
						if (totalDaysToPay <= 3) {
							this.betragTotal = 30d;
							this.newParking.betragGeneratedJLabel.setText(String.valueOf(betragTotal + " CHF"));
						} else {
							//Rate per day 10CHF
							this.betragTotal = totalDaysToPay * 10;
							this.newParking.betragGeneratedJLabel.setText(String.valueOf(betragTotal + " CHF"));
						}
				}
				
				
			
				
				
				
				
				

				
				this.abreiseOK = true;
			}

		}

		
	}
	
	
	
	
	
public void setReadyToSaveArrival(boolean readyToSaveArrival) {
	this.readyToSaveArrival = readyToSaveArrival;
}

public void setReadyToSaveDeparture(boolean readyToSaveDepature) {
	this.readyToSaveDepature = readyToSaveDepature;
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
			System.out.println("Now we can save the data!");
		}
	}
		
}



/**
 * @description Method to add the new Parking Reservation to DataBase Table parking
 */
protected void addNewParkingReservationToDataBase() {
	
}




	

}
