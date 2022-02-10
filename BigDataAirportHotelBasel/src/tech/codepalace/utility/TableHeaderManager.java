package tech.codepalace.utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version v.0.1.0
 * @description class that allows you to customize the table header to define the color that the columns will have.
 * <p>This class will allow me to customize the header thanks to the implementation of the TableCellRenderer class</p>
 *
 */
public class TableHeaderManager implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		
		/*
		 JComponent que va a permitir la personalizacion del encabezado.
		 */
		JComponent jcomponent = null;
		
		if( value instanceof String ) {
			
			//Creamos un componente JLabel que recibe el value de la instanceof
           jcomponent = new JLabel((String) value);
           
           //Centramos el texto del JLabel
           ((JLabel)jcomponent).setHorizontalAlignment( SwingConstants.CENTER );
           
           //Establecemos un alto definido de 30 y un ancho recuperado del contenido del componente
           ((JLabel)jcomponent).setSize( 30, jcomponent.getWidth() );   
           
           
           ((JLabel)jcomponent).setPreferredSize( new Dimension(6, jcomponent.getWidth())  );
       }         
  
       //jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(221, 211, 211)));
       jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(255, 255, 255)));
       jcomponent.setOpaque(true);
       //jcomponent.setBackground( new Color(236,234,219) );
       jcomponent.setBackground( new Color(65,65,65) );
       jcomponent.setToolTipText("Parking Table");
       jcomponent.setForeground(Color.white); //Color del texto del encabezado
       
       return jcomponent;
	}
	

}
