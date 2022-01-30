package tech.codepalace.controller;

import tech.codepalace.model.LogicModelParking;
import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.AHBParking;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version 1.0.0 Jan 30.2022 10:40PM
 * @description Class Controller for the application of parking databases
 *
 */
public class AHBParkingController {
	
	private AHBParking ahbParking;
	private UserAHB userAHB;
	private LogicModelParking logicModelParking;
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	private BigDataAHBStartFrameController bigDataAHBStartFrameController;
	
	
	
	public AHBParkingController(AHBParking ahbParking, UserAHB userAHB, LogicModelParking logicModelParking, BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, BigDataAHBStartFrameController bigDataAHBStartFrameController ) {
		
		this.ahbParking = ahbParking;
		this.userAHB = userAHB;
		this.logicModelParking = logicModelParking;
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.bigDataAHBStartFrameController = bigDataAHBStartFrameController;
		
		
	}
		

}
