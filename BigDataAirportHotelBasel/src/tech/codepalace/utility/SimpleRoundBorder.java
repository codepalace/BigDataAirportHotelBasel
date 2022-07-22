package tech.codepalace.utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

/**
 * @description Class to set Round Border to the Element. 
 * 
 *
 */
@SuppressWarnings("serial")
public class SimpleRoundBorder extends AbstractBorder {
	
	Color bgColor = Color.GRAY;
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
    {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D)g).setColor( bgColor );
        //drawRoundRect you can play with the numbers to see the result. where -2 change for -8 and you will see
        //it goes up.
        //the 12, 12 is who round will be
        ((Graphics2D)g).drawRoundRect(x, y, width-2, height-2, 12, 12); 
        
    }

    /**
     * Method for setting the border space for the element
     */
    public Insets getBorderInsets(Component c) {
    	
    	
        return new Insets(3, 3, 3, 3);
    }

    public Insets getBorderInsets(Component c, Insets insets) 
    {
        insets.top =  insets.left = insets.bottom = insets.right = 3;
        return insets;
    }
}

