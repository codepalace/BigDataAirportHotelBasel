package tech.codepalace.utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * 
 * 
 * @description This class allows you to manage the table and the events performed on it.
 * <p>each cell would be a customizable object</p>
 *
 */
public class CellTableManager extends DefaultTableCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 variable String type, which defines everything that is going to reach the table, in principle it is of type text,
	 */
	private String type="text";

	//I define a default font
	private Font normal = new Font( "Verdana",Font.PLAIN ,12 );
	private Font bold = new Font( "Verdana",Font.BOLD ,12 );
	
	
	
	public CellTableManager(){
		
	}

	/**
	 * Explicit constructor with the type of data that the cell will have
	 * 
	 * @param type
	 */
	public CellTableManager(String type){
		
		/*
		 Me recibe el tipo de la celda en la que voy a trabajar, esta clase tiene la propiedad de que cada evento que yo ejecute sobre la celda 
		 va a ser llamada a la clase, por eso es importante saber cual es el tipo de datos que las celdas mantienen. 
		 */
		this.type=type;
	}

	
	/**
	 * @description
	 * This method controls the whole table, we can get the value it contains
	 * define which cell is selected, the row and column when focusing on it.
	 * 
	 * <p>
	 * Each event on the table will invoke this method.</p>
	 * 
	 * <p>
	 * In this method we can define the logic that we are going to give to my table, if I want for example to recognize numerical data, if I want to recognize 
	 * text data, if I want to recognize graphic components.</p>
	 * 
	 * <p>
	 * Within this method we will also control the logic of the tables,</p>
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
		
		
		
		/*we define default colors that I'm going to work with.
		 
		These colors will allow me to put the desired color when a row is selected, what color I will have when I change from cell to cell.
		 */
		
       Color backgroundColor = null;
       Color defaultBackgroundColor=new Color( 192, 192, 192);
       Color backgroundColorSelection=new Color( 140, 140 , 140);
   	
       /*
        * If the event cell is selected, the default background color for the selection is assigned
        * but if it is not selected then put a white color
        */
       if (selected) {                
           this.setBackground(defaultBackgroundColor );   
       }
       else
       {
       	//For those that are not selected, the background of the cells is painted white
           this.setBackground(Color.white);
       }
               
       //if the data type the cell will have is text
       if( type.equals("text"))
       {
       	//if text type defines the background color of the text and cell as well as the alignment
           if (focused) {
        	   backgroundColor=backgroundColorSelection;
   		}else{
   			backgroundColor= defaultBackgroundColor;
   		}
           this.setHorizontalAlignment( JLabel.LEFT);
           this.setText( (String) value );
           //this.setForeground( (selected)? new Color(255,255,255) :new Color(0,0,0) );   
           //this.setForeground( (selected)? new Color(255,255,255) :new Color(32,117,32) );
           this.setBackground( (selected)? backgroundColor :Color.WHITE);	
           this.setFont(normal);   
           this.setBorder(new EmptyBorder(0,5,0,0)); //dejamos un espacio de 5 a la izquierda Toni
           //this.setFont(bold);
           return this;
       }

       
       //if the data type the cell will have is number
       if( type.equals("number"))
       {           
       	if (focused) {
       		backgroundColor=backgroundColorSelection;
    		}else{
    			backgroundColor=defaultBackgroundColor;
    		}
       	// System.out.println(value);
           this.setHorizontalAlignment( JLabel.CENTER );
           this.setText( (String) value );            
           this.setForeground( (selected)? new Color(255,255,255) :new Color(32,117,32) );    
           this.setBackground( (selected)? backgroundColor :Color.WHITE);
          // this.setBackground( (selected)? colorFondo :Color.MAGENTA);
           this.setFont(bold);            
           return this;   
       }
		
		return this;
		
		
	}

}
