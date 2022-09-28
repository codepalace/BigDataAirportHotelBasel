package tech.codepalace.controller;

import tech.codepalace.model.LogicModelDateChronologyCorrection;
import tech.codepalace.view.frames.DateChronologyCorrection;

public class DateChronologyCorrectionController {
	
	private DateChronologyCorrection dateChronologyCorrection;
	LogicModelDateChronologyCorrection logicDateChronologyCorrection;
	
	public DateChronologyCorrectionController(DateChronologyCorrection dateChronologyCorrection, 
									LogicModelDateChronologyCorrection logicModelDateChronologyCorrection ) {
		
		this.dateChronologyCorrection = dateChronologyCorrection;
		this.logicDateChronologyCorrection = logicModelDateChronologyCorrection;
	}

}
