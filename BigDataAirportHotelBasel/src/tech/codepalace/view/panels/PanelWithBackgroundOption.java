package tech.codepalace.view.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description Class extends JPanel and it will be used to set a Background to the JPanel. 
 *
 */
public class PanelWithBackgroundOption extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	//Variable for the panel background image
	private Image background = null;


	public PanelWithBackgroundOption() {


	}

   	

	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, 0, 0, this);
    }



	public void setImage(String image) {


	   	if(image!=null) {
	   		
	   		//set the image background. We receive the Path by the argument(image).
	   		background = new ImageIcon(getClass().getResource(image)).getImage();
	   		

	   		 
	   	}

	   }
	

}
