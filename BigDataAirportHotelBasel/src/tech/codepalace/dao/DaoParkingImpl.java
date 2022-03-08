package tech.codepalace.dao;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.DatabaseMetaData;
import java.sql.Date;
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
import javax.swing.SwingWorker;

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
 	
 	private String urlDB, dbName;
 	
 	private	PreparedStatement preparedStatement = null;
 	private	ResultSet rs = null;
 	
 	

	
	
	public DaoParkingImpl(String urlDB, String dbName, UserAHB userAHB, AHBParking ahbParking) {
		super(urlDB, dbName);
		
		this.userAHB = userAHB;
		this.ahbParking = ahbParking;
//		this.parkingReservation = parkingReservation;
		this.urlDB = urlDB;
		this.dbName = dbName;
//		JOptionPane.showMessageDialog(null, "url DaoParkingImp: " + this.urlDB);
//		JOptionPane.showMessageDialog(null, "dbName DaoParkingImp: " + this.dbName);
		
		
		
		
		
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
	public void createNewParkingReservation() throws DaoException {
		
this.dataEncryption = new DataEncryption();
		
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				newParking = new  NewParking(ahbParking, true, userAHB, count);

				@SuppressWarnings("unused")
				LogicModelNewParking logicModelNewParking = new LogicModelNewParking(userAHB, ahbParking, newParking, parkingReservation, urlDB, dbName);
				
				
				
				@SuppressWarnings("unused")
				NewParkingController newParkingController = new NewParkingController(newParking, logicModelNewParking);
				
				newParking.setLocationRelativeTo(null);
				newParking.setVisible(true);
				
				
			}
			
		});
		
	}
	
	
	
	
	
	
	
	
	@Override
	public void addNewParkingReservation(ParkingReservation parkingReservation) throws DaoException {
		
//			JOptionPane.showMessageDialog(null, "URL-Base de datos: " + this.urlDB);
//		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			this.conect();
//			JOptionPane.showMessageDialog(null, "Connexion: " + this.conn);
//			connection = getConnection();
			preparedStatement = conn.prepareStatement("insert into parking(idparking, buchungsname, autokfz, anreisedatum, abreisedatum, anzahltagen, betragparking, buchungskanal, bemerkungen, schluesselinhaus, verkaufer) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			preparedStatement.setString(1, parkingReservation.getIdParking());
			preparedStatement.setString(2, parkingReservation.getBuchungsname());
			preparedStatement.setString(3, parkingReservation.getAutoKFZ());
			preparedStatement.setDate(4, parkingReservation.getAnreiseDatum());
			preparedStatement.setDate(5, parkingReservation.getAbreiseDatum());
			preparedStatement.setInt(6, parkingReservation.getAnzahlTagen());
			preparedStatement.setDouble(7, parkingReservation.getBetragParking());
			preparedStatement.setString(8, parkingReservation.getBuchungsKanal());
			preparedStatement.setString(9, parkingReservation.getBemerkungen());
			preparedStatement.setString(10, parkingReservation.getSchluesselInHaus());
			preparedStatement.setString(11, parkingReservation.getAbkuerzungMA());
			
			preparedStatement.executeUpdate();
			
			conn.commit();
			closeConnection();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();

		
			
		}
		
		
		displayListParking();
	}
	
	
	
	
	
	
	
	@Override
	public void displayListParking() throws DaoException {
		
		

		
		
		
		
		try {
			this.conect(); //We contect to Database
			
			//SQL SELECT Statement to select all the entries in Data base
			String sqlString = "SELECT * From PARKING";
			
			
			
			
			
			//We use SwingWorker in case we have many entries in Data base so that the threads execution work in background
			SwingWorker<Void, Void> worker1 = new SwingWorker<Void, Void>(){

				@Override
				protected Void doInBackground() throws Exception { //we run all processes in background

					//PreparedStatement to execute the SQL Query
					preparedStatement = conn.prepareStatement(sqlString);
					
					//ResultSet execute PreparedStatement SQL Query
					rs = preparedStatement.executeQuery();
					
					
					//as long as we have entries in the database
					while(rs.next()) {
						
						//add 1 to count
						count++;
					}
					
					
					if(count == 0) { // if equal to 0 then the table is null(empty)
						
						
						//if the table PARKING is empty, we run a new thread invokLater
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								
								/*
								 * within the run method, we create our dialog box elements 
								 * that will launch the message if the user wants to create a new parking reservation
								 */
								
								//image for the JDialgo
								imgUserRequestNewParking = new ImageIcon(getClass().getResource("/img/carparking.png"));
								
								//JButtons to confirm o cancel the creation of a new parking reservation 
								newParkingReservationJButton = new JButton("Ja");
								cancelNewParkingReservationJButton = new JButton("Nein");
								
								//Array Object to add the JButtons for the JDialog
								options = new Object[] {cancelNewParkingReservationJButton, newParkingReservationJButton};
								
								//Panel for the JButton
								panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
								
								
								//We add the JButtons to de JPanel
								panelButtons.add(cancelNewParkingReservationJButton);
								panelButtons.add(newParkingReservationJButton);
								
								//message asking the user if they want to create a new reservation
								messageParking = new JLabel("Noch keine Daten in Parking DataBase. Wollen Sie einen neuen Parkplatz reservieren? ");
								
								//JPanel to add the JLabel messageParking
								panelContainerMessage =  new JPanel(new BorderLayout());
								
								//JPanel container to add both JPanels(panelButtons and messageParking).
								panelContainerMessage.add(panelButtons, BorderLayout.NORTH);
								panelContainerMessage.add(messageParking, BorderLayout.CENTER);
								
								//We create the new JDialgo = JOptionPane with all the elemenents we need.
								newParkingDialog = new JOptionPane(panelContainerMessage, JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION,
										imgUserRequestNewParking, options, null).createDialog("Neue Parkplatzreservierung!");
								
								//requestFocus
								newParkingReservationJButton.requestFocus();
								
								//ActionListener
								newParkingReservationJButton.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										
										
										try {
											/*
											 * the user decides to create a new reservation, then we call the createNewParkingReservation Method.
											 */
											createNewParkingReservation();
											
											//and then we closed the JDialog
											newParkingDialog.dispose();
										} catch (DaoException e1) {
											e1.printStackTrace();
										}
										
									}
								});
								
								
								
								//all procedures within this method are the same as in the actionPerformed Method above.
								newParkingReservationJButton.addKeyListener(new KeyListener() {
									
									@Override
									public void keyTyped(KeyEvent e) {}
									
									@Override
									public void keyReleased(KeyEvent e) {}
									
									@Override
									public void keyPressed(KeyEvent e) {

										if(e.getKeyCode()==10) {
											try {
												createNewParkingReservation();
											} catch (DaoException e1) {
												e1.printStackTrace();
											}
											newParkingDialog.dispose();
										}
									}
								});
								
								
								
								
								
								
								//to cancel, we just close the JDialog
								cancelNewParkingReservationJButton.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {

										newParkingDialog.dispose();
									}
								});
								
								
								
								cancelNewParkingReservationJButton.addKeyListener(new KeyListener() {
									
									@Override
									public void keyTyped(KeyEvent e) {}
									
									@Override
									public void keyReleased(KeyEvent e) {}
									
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
					
					
					
					
					else { //there are data in the Parking table within our database

						
						//Wec call to conect to the Database
						conect();
						
						
						/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
						 *																												   *
						 * We use SwingWorker in case we have many entries in Data base so that the threads execution work in background   *
						 * 																												   *
						 * by SwingWorker 																								   *
						 *																												   *
						 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
					
						/*
						 * El primer Void es el tipo de retorno que nos dara el SwingWorker. 
						 * Si tenemos como primer tipo Void con V mayuscula tenemos que retorna null, de lo contrario obtenemos un error. 
						 * 
						 * The first type Void can be changed to another value that we want to return or another class of our own object for example.
						 * Just if we want to return a value.
						 * 
						 * the second type of object(Template) we could also not return anything, but in this case we will return an object of type ParkingReservation.
						 * this second type will be returned during the process.
						 * 
						 * Now if we want to update in our GUI through the process we will have to make use of the second type of tamplate. 
						 * 
						 * an example could be to return a String while we are updating the process and this could be displayed in a JLabel updating the Text in the GUI.
						 * 
						 * In our case we are going to use a value of type ParkingReservation, to go adding each object of type ParkingReservation readed from Database to an ArrayList.
						 * 
						 * 
						 * For the update of the values that are executed in the background process we have to make use of the publish method.
						 * 
						 * 
						 * 
						 */
						
						
						
						SwingWorker<Void, ParkingReservation> worker = new SwingWorker<Void, ParkingReservation>(){

							//in background we execute all the instructions we need
							@Override
							protected Void doInBackground() throws Exception {
								
								/*
								 * we create an ArrayList type ParkingReservation to add each ParkingReservation object  that we find inside the table Parking in our DataBase
								 */
								List<ParkingReservation> parkingReservation = new ArrayList<ParkingReservation>();

								//PreparedStatement to execute the SQL Query
								preparedStatement = conn.prepareStatement(sqlString);
								
								//ResultSet for our result
								rs = preparedStatement.executeQuery();
								
					
								//as long as there are entries in the table
								while (rs.next()) {
									
									/*We create the necessary variables of the type we need according to the data stored in the database
									 * 
									 * The values are retrieved from each result found in the table and of course from the corresponding column in the table Parking.
									 */
									
									String idParking = rs.getString("idparking");
									String buchungsName = rs.getString("buchungsname");
									String autokfz = rs.getString("autokfz");
									Date anreisedatum = rs.getDate("anreisedatum");
									Date abreisedatum = rs.getDate("abreisedatum");
									int anzahltagen = rs.getInt("anzahltagen");
									double betragparking = rs.getDouble("betragparking");
									String buchungskanal = rs.getString("buchungskanal");
									String bemerkungen = rs.getString("bemerkungen");
									String schluesselinhaus = rs.getString("schluesselinhaus");
									String verkaufer = rs.getString("verkaufer");
									
									
									//We create an object type ParkingReservation
									ParkingReservation parkingReservation2 = new ParkingReservation();
									
									//We set the values of this Object type ParkingReservation 
									parkingReservation2.setIdParking(idParking);
									parkingReservation2.setBuchungsname(buchungsName);
									parkingReservation2.setAutoKFZ(autokfz);
									parkingReservation2.setAnreiseDatum(anreisedatum);
									parkingReservation2.setAbreiseDatum(abreisedatum);
									parkingReservation2.setAnzahlTagen(anzahltagen);
									parkingReservation2.setBetragParking(betragparking);
									parkingReservation2.setBuchungsKanal(buchungskanal);
									parkingReservation2.setBemerkungen(bemerkungen);
									parkingReservation2.setSchluesselInHaus(schluesselinhaus);
									parkingReservation2.setAbkuerzungMA(verkaufer);
									
									
									//add the Object ParkingReservation(parkingReservation2) to the ArrayList parkingReservation
									parkingReservation.add(parkingReservation2);
									
									
									//run a foreach loop and for each index inside parkingReservation ArrayList
									for(ParkingReservation index : parkingReservation) {
										
										//We call publish method with the parameter current index.
										publish(index);
										
									}

								}
								
								return null;
							}

							
							
							
							
							
							
							/*
							 *method that will serve me to update the gui with the data or fill the list with the data from the database.
							 *
							 *This method receive a List<T> type of the second type or template declared in the SwingWorker(chunks is the name of the list giving by default).
							 */
							@Override
							protected void process(List<ParkingReservation> chunks) {
								
								/*
								 * Object type ParkingReservation = chunks value. size of chunks -1 for the Last value, to avoid out of index value from the ArrayList.
								 */
								ParkingReservation parkingReservation = chunks.get(chunks.size() -1);
								
								
								/*
								 * at the moment we only print the values. Later we will present it directly in a JTable
								 */
								System.out.println("Id: " + parkingReservation.getId() + " IdParking: " + parkingReservation.getIdParking() + " Buchungsname: " 
								+ parkingReservation.getBuchungsname() + " Autokfz: " + parkingReservation.getAutoKFZ() + " Anreisedatum: " + parkingReservation.getAnreiseDatum() +
								" Abreisedatum: " +  parkingReservation.getAbreiseDatum() + " Anzahltagen: " + parkingReservation.getAnzahlTagen() + " Tagen" + " Betrag: " +
								parkingReservation.getBetragParking() + "CHF" + " Buchungskanal: " + parkingReservation.getBuchungsKanal() + " Bemerkungen: " + 
								parkingReservation.getBemerkungen() + " Schluesselinhause: " + parkingReservation.getSchluesselInHaus() + " Abkürzung Mitarbeiter: " + 
								parkingReservation.getAbkuerzungMA());
								
								
							}








							/*
							 * This method will be called when the thread has finished its task.
							 * 
							 * Within this done method we can safely update our user interface.
							 * 
							 * Here we could for example close a JFrame, open another, modify a graphic element after finishing the process etc.
							 * 
							 * In our case we will close the connection to the database
							 * 
							 */
							@Override
							protected void done() {
						
								try {
									closeConnection();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
							
							
							
							
						};
						
						worker.execute(); //Execute the Thread by else instruccion
						
						
						
					}
					
					return null;
				}
				
				
				
			};
			
			worker1.execute(); //Execute the first Thread 
			
			
			
			

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
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
