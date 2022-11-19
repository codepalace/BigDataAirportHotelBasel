package tech.codepalace.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFitnessAbo;

public class LogicModelNewFitnessAbo extends LogicModel{
	
	private DataBaseGUI dataBaseGUI;
	private NewFitnessAbo newFitnessAbo;
	private UserAHB userAHB;
	private Loading loading;
	
	//Variable to evaluate if the user are trying to close the GUI. Used to avoid displaying error by loosing the focus by some elements.
	private boolean tryingToCancel = false;
	
	private ImageIcon preventionImage = new ImageIcon(getClass().getResource("/img/prevention.png"));
	
	//Variables to evaluate if by loosing the focus in those elements was found error or not.
	private boolean nameLostFocusWithError = false, startDateLostFocusWithError = false; 
	
	//Variable to evaluate if the start date and the number of month selected has a valid values.
	private boolean startDateAndNumberOfMonthAreValid = false;
	
	//Variables for the start date and end Date of the contract as LocalDate used to manipulate the periods between dates.
	private LocalDate startDateLocalDate, endDateLocalDate;
	
	//Variables for the start date and end Date of the contract as Date used to save in database.
	private Date startDate, endDate;
	
	//Variable for the contract price
	private double betrag = 0.0;
	
	
	
	//dataBaseGUI, newFundsachen, getUserAHB(), loading
	public LogicModelNewFitnessAbo(DataBaseGUI dataBaseGUI, NewFitnessAbo newFitnessAbo, UserAHB userAHB, Loading loading) {
		
		this.dataBaseGUI = dataBaseGUI;
		this.newFitnessAbo = newFitnessAbo;
		this.userAHB = userAHB;
		this.loading = loading;
		
	}
	
	
	/**
	 * @description Method to confirm if the user wants really to close the GUI.
	 */
	public void closeNeueFitnessAbo() {
		
		//Variable below int get the value depending what the user decide to do, if press Yes GUI will be closed.
		int selectedOption = JOptionPane.showConfirmDialog(null, "Möchten Sie das neue Abonnement abbrechen?",
			"Neue Abonnement abbrechen", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, preventionImage);
				
				//if the user is sure he wants to delete the entry. He has pressed Button Yes. 
				if (selectedOption == JOptionPane.YES_OPTION) {
					
					this.newFitnessAbo.dispose();
						
		}else {
			
			//The user finally decided not close the GUI. We set the value tryingToCancel to false.
			this.setTryingToCancel(false);
		}
	}


	/**
	 * @return the tryingToCancel
	 */
	public boolean isTryingToCancel() {
		return tryingToCancel;
	}


	/**
	 * @param tryingToCancel the tryingToCancel to set
	 */
	public void setTryingToCancel(boolean tryingToCancel) {
		this.tryingToCancel = tryingToCancel;
	}


	/**
	 * @return the nameLostFocusWithError
	 */
	public boolean isNameLostFocusWithError() {
		return nameLostFocusWithError;
	}


	/**
	 * @param nameLostFocusWithError the nameLostFocusWithError to set
	 */
	public void setNameLostFocusWithError(boolean nameLostFocusWithError) {
		this.nameLostFocusWithError = nameLostFocusWithError;
	}


	/**
	 * @return the startDateLostFocusWithError
	 */
	public boolean isStartDateLostFocusWithError() {
		return startDateLostFocusWithError;
	}


	/**
	 * @param startDateLostFocusWithError the startDateLostFocusWithError to set
	 */
	public void setStartDateLostFocusWithError(boolean startDateLostFocusWithError) {
		this.startDateLostFocusWithError = startDateLostFocusWithError;
	}


	/**
	 * @return the startDateAndNumberOfMonthAreValid
	 */
	public boolean isStartDateAndNumberOfMonthAreValid() {
		return startDateAndNumberOfMonthAreValid;
	}


	/**
	 * @param startDateAndNumberOfMonthAreValid the startDateAndNumberOfMonthAreValid to set
	 */
	public void setStartDateAndNumberOfMonthAreValid(boolean startDateAndNumberOfMonthAreValid) {
		this.startDateAndNumberOfMonthAreValid = startDateAndNumberOfMonthAreValid;
	}
	
	
	/**
	 * @description Method to calculate the cost of the Fitness contract depending how many month was selected by the User.
	 */
	public void calculateFitnessCost() {

		
		//Set the value for the String dateCharSequence replacing the (.) by (/).
		String dateCharSequence = this.newFitnessAbo.eintrittsdatumPlaceHolderTextField.getText().replace('.', '/');
		
		//private DateTimeFormatter dateTimeFormatterForSavingDataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		setStartDateLocalDate(LocalDate.parse(dateCharSequence, getDateTimeFormatterForSavingDataBase()));
		
		//Set the value for the startDate(This date will be used to be saved in the DataBase)
		setStartDate(Date.valueOf(startDateLocalDate));
		
		/*
		 * Block switch to evaluate the value of the selected index anzahlDerMonat(JComboBox).
		 *
		 * Depending of the value we set the cost for the Fitness contract.
		 */
		switch (this.newFitnessAbo.anzahlDerMonat.getSelectedIndex()) {
			case 1:
				
				
				/* Set the LocalDate value for the endDateLocalDate variable adding one month from the startDateLocalDate Value. */
				setEndDateLocalDate(getStartDateLocalDate().plusMonths(1));
				
				/* Set the endDate value getting the value from endDateLocalDate */
				setEndDate(Date.valueOf(endDateLocalDate));
				
				//In this case the cost will be 30.0
				setBetrag(30d);
				
				/* generate the value for the betragGeneratedJLabel variable, getting the String.valueOf(double(betrag variable)).
				 * We add at the end (CHF) for the currency.  */
				this.newFitnessAbo.betragGeneratedJLabel.setText(String.valueOf(getBetrag() + " CHF"));
				break;

				
				//For all the next cases is the same. Only change the cost for the contract depending how many Month is the contract valid.
			case 2:
				
				setEndDateLocalDate(getStartDateLocalDate().plusMonths(3));
				setEndDate(Date.valueOf(endDateLocalDate));
				setBetrag(90d);
				this.newFitnessAbo.betragGeneratedJLabel.setText(String.valueOf(getBetrag() + " CHF"));
				break;
				
			case 3:
				
				setEndDateLocalDate(getStartDateLocalDate().plusMonths(6));
				setEndDate(Date.valueOf(endDateLocalDate));
				setBetrag(175d);
				this.newFitnessAbo.betragGeneratedJLabel.setText(String.valueOf(getBetrag() + " CHF"));
				break;
			
			case 4:
				
				setEndDateLocalDate(getStartDateLocalDate().plusMonths(12));
				setEndDate(Date.valueOf(endDateLocalDate));
				setBetrag(350d);
				this.newFitnessAbo.betragGeneratedJLabel.setText(String.valueOf(getBetrag() + " CHF"));
				break;
		}

		
	}

	
	

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the startDateLocalDate
	 */
	public LocalDate getStartDateLocalDate() {
		return startDateLocalDate;
	}


	/**
	 * @param startDateLocalDate the startDateLocalDate to set
	 */
	public void setStartDateLocalDate(LocalDate startDateLocalDate) {
		this.startDateLocalDate = startDateLocalDate;
	}


	/**
	 * @return the endDateLocalDate
	 */
	public LocalDate getEndDateLocalDate() {
		return endDateLocalDate;
	}


	/**
	 * @param endDateLocalDate the endDateLocalDate to set
	 */
	public void setEndDateLocalDate(LocalDate endDateLocalDate) {
		this.endDateLocalDate = endDateLocalDate;
	}


	/**
	 * @return the betrag
	 */
	public double getBetrag() {
		return betrag;
	}


	/**
	 * @param betrag the betrag to set
	 */
	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}
	
	
	
	
	
	
	
	
	

}
