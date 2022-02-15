package tech.codepalace.model;

import tech.codepalace.view.frames.AHBParking;

public class LogicModelNewParking {
	
	protected UserAHB userAHB;
	protected AHBParking ahbParking;
	protected ParkingReservation parkingReservation;
	
	
	//DAOParking daoParking = new DaoParkingImpl(urlDataBase, dbName, userAHB, ahbParking, parkingReservation);
	public LogicModelNewParking(UserAHB userAHB, AHBParking ahbParking, ParkingReservation parkingReservation) {
		
		this.userAHB = userAHB;
		this.ahbParking = ahbParking;
		this.parkingReservation = parkingReservation;
		
		
		
		
		
	}
	

}
