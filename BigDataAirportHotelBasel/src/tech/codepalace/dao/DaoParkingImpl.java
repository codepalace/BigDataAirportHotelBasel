package tech.codepalace.dao;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tech.codepalace.controller.NewParkingController;
import tech.codepalace.model.LogicModelNewParking;
import tech.codepalace.model.ParkingReservation;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.AHBParking;
import tech.codepalace.view.frames.NewParking;



/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version v0.1.0 February 12.2022
 *
 */
public class DaoParkingImpl extends ConnectionClass implements DAOParking {
	
	protected ConnectionClass connectionClass;
	
 	protected String[] names = { "TABLE" };
 	protected ResultSet result;
 	protected DatabaseMetaData metadata = null;
 	protected Statement statement = null;
 	protected String table_name = "Parking";
 	
 	
 	public JDialog newParkingDialog;
 	public JButton newParkingReservationJButton;
 	public JButton cancelNewParkingReservationJButton;
 	
 	private Object [] options;
 	
 	private JPanel panelContainerMessage, panelButtons;
	
 	private JLabel messageParking;
 	
 	private ImageIcon imgUserRequestNewParking;
 	
 	private UserAHB userAHB;
 	private AHBParking ahbParking;
 	private NewParking newParking;
 	
 	private ParkingReservation parkingReservation;
 	
 	
 	private DataEncryption dataEncryption;
 	
 	private long count = 0L;
 	
 	

	
	
	public DaoParkingImpl(String urlDB, String dbName, UserAHB userAHB, AHBParking ahbParking, ParkingReservation parkingReservation) {
		super(urlDB, dbName);
		
		this.userAHB = userAHB;
		this.ahbParking = ahbParking;
		this.parkingReservation = parkingReservation;
		
		
		
		
		
	}

	
	
	@Override
	public void checkTableParking() throws DaoException {

		//We connect to the Database
		try {
			this.conect();
			
			metadata = conn.getMetaData();
			result = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
			
			 if(result.next()) {
	            	
	            	System.out.println("The table: " + this.table_name + " exists");
            	 
	            	 this.closeConnection();
	            	 
	            	 displayListParking();
	            }else {
	            	
	            	
	            	System.out.println("The table: " + this.table_name + " does not exist");
	            	
	            	
					 
					
					 //we proceed to create the table
					 String sql = "CREATE TABLE PARKING ( "
							 
							 + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
							 + "idparking VARCHAR(12) NOT NULL,"
							 + "buchungsname VARCHAR(50) NOT NULL,"
							 + "autokfz VARCHAR(8) NOT NULL,"
							 + "anreisedatum DATE NOT NULL,"
							 + "abreisedatum DATE NOT NULL,"
							 + "anzahltagen INTEGER,"
							 + "betragparking decimal(6,2) NOT NULL,"
							 + "buchungskanal VARCHAR(40) NOT NULL,"
							 + "bemerkungen VARCHAR(255) NOT NULL,"
							 + "schluesselinhaus VARCHAR(5) NOT NULL,"
							 +	"verkaufer VARCHAR(20) NOT NULL,"
							 + "CONSTRAINT primary_key PRIMARY KEY (id)"
							 + ")";
			 
					 
					 statement = conn.createStatement();
					 
					 statement.executeUpdate(sql);
					 
					 System.out.println("The Table: " + this.table_name + " was successfully created");
					 
					 statement.close();
				
	           
	            //Very important close conneciton
	             this.closeConnection();
	             
	             /*
	              * now we call the displayListParking method that shows the data of the parking table even if there are no data yet.
	              */
	           
	             displayListParking();
	            }
			 
			 
			 
			 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void createTableParkingDataBase(ParkingReservation parkingReservation) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewParkingReservation(long lenghtParkingTableDataBase) throws DaoException {
		
		this.dataEncryption = new DataEncryption();
		
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				newParking = new  NewParking(ahbParking, true, userAHB, count);

				@SuppressWarnings("unused")
				LogicModelNewParking logicModelNewParking = new LogicModelNewParking(userAHB, ahbParking, newParking);
				
				
				
				@SuppressWarnings("unused")
				NewParkingController newParkingController = new NewParkingController(newParking, logicModelNewParking);
				
				newParking.setLocationRelativeTo(null);
				newParking.setVisible(true);
				
				
			}
			
		});
		
		
		
	}

	
	
	
	
	
	
	
	
	
	@Override
	public List<ParkingReservation> displayListParking() throws DaoException {
		
		List<ParkingReservation>parkingreservations = new ArrayList<ParkingReservation>();

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		
		//Ahora utilizamos el hilo largo para esta tarea de recorrer el contenido de la tabla. 
		try {
			this.conect();
			String sqlString = "SELECT * From PARKING";
			preparedStatement = conn.prepareStatement(sqlString);
			rs = preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				count++;
			}
			
			if(count == 0) { // if equal to 0 then the table is null
				
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						
						imgUserRequestNewParking = new ImageIcon(getClass().getResource("/img/carparking.png"));
						
						newParkingReservationJButton = new JButton("Ja");
						cancelNewParkingReservationJButton = new JButton("Nein");
						
						options = new Object[] {cancelNewParkingReservationJButton, newParkingReservationJButton};
						
						panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
						
						
						
						panelButtons.add(cancelNewParkingReservationJButton);
						panelButtons.add(newParkingReservationJButton);
						
						messageParking = new JLabel("Noch keine Daten in Parking DataBase. Wollen Sie einen neuen Parkplatz reservieren? ");
						
						panelContainerMessage =  new JPanel(new BorderLayout());
						
						panelContainerMessage.add(panelButtons, BorderLayout.NORTH);
						panelContainerMessage.add(messageParking, BorderLayout.CENTER);
						
						newParkingDialog = new JOptionPane(panelContainerMessage, JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION,
								imgUserRequestNewParking, options, null).createDialog("Neue Parkplatzreservierung!");
						
						newParkingReservationJButton.requestFocus();
						
						newParkingReservationJButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								
								try {
									addNewParkingReservation(count);
									newParkingDialog.dispose();
								} catch (DaoException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}
						});
						
						
						newParkingReservationJButton.addKeyListener(new KeyListener() {
							
							@Override
							public void keyTyped(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void keyReleased(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void keyPressed(KeyEvent e) {

								if(e.getKeyCode()==10) {
									try {
										addNewParkingReservation(count);
									} catch (DaoException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									newParkingDialog.dispose();
								}
							}
						});
						
						
						
						
						
						
						cancelNewParkingReservationJButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {

								newParkingDialog.dispose();
							}
						});
						
						
						
						cancelNewParkingReservationJButton.addKeyListener(new KeyListener() {
							
							@Override
							public void keyTyped(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void keyReleased(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void keyPressed(KeyEvent e) {

								if(e.getKeyCode()==10) {
									newParkingDialog.dispose();
								}
							}
						});
						
						newParkingDialog.setVisible(true);
						newParkingDialog.dispose();
						
					}
					
				});
				
				
				

			}
			
			this.closeConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parkingreservations;
	}
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public List<ParkingReservation> displayParkingFoundLikeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkingReservation> displayParkingFoundLikeCarNumber(String carNumer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkingReservation> displayParkingFoundLikeDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkingReservation> displayParkingFoundLikeAnyEntry(String likeAnyEntry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateParkingReservation(ParkingReservation parkingReservation) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteParkingReservation(int id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ParkingReservation> orderby(String orderBy) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
