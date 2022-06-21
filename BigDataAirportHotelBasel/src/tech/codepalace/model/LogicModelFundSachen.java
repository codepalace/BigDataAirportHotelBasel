package tech.codepalace.model;

import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.Fundsachen;

public class LogicModelFundSachen extends LogicModel {
	
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	
	private Fundsachen fundSachen;
	
//	public LogicModelFundSachen(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
//		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
//		
//		
//	}
	
	
	public LogicModelFundSachen(Fundsachen fundSachen) {
		this.fundSachen = fundSachen;
		
		
	}
	
	public LogicModelFundSachen() {}

}
