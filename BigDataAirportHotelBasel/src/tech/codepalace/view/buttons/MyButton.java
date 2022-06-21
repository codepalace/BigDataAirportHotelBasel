package tech.codepalace.view.buttons;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description Custom button, with photo as button, opacity options and effects when hovering the mouse over the button.
 *
 */

public class MyButton extends JButton {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//variable for button opacity
    private float opacity = 0.8f;

    //Image for the button
    private Image img;
    
    
 
    public MyButton(){
        super();
       /*
        we call the addMouseListener method of the JButton class
      
        the nested class we have created we passed as argument the class for the mouse event
        */
       addMouseListener(new EventButton());

    }
    
    
    
    /*
     * The second constructor receives an argument with the path where the image is going to have the button
     */
    
    public MyButton(String imageIcon) {







   	//We set the icon of our button, recovering the value of the variable imageIcon
    	
   	try {
		img = ImageIO.read(getClass().getResource(imageIcon));
		setIcon(new ImageIcon(img));
	} catch (IOException e) {
	
		System.out.println(e);
	}
   	

   	//set the mouse cursor
   	setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

   	/*
   	set the ContentAreaFilled of the Button to false, and Layout(null)
   	 */
   	setContentAreaFilled(false);
   	setLayout(null);


   	setSize(getPreferredSize());


	//No Border
   	setBorder(null);


   	//add Mouse Event Listener
   	addMouseListener(new EventButton());


    }
    
    
    
    //Setter method to set the opacity value
    public void setOpacity(float opacity){
        this.opacity = opacity;

        
        //the component is updated
        repaint();
    }

    
    
    //Getter method to retrieve opacity value
    public float getOpacity(){
        return this.opacity;
    }
    
    

    
   //Overrride paintComponent method
    
    @Override
    public void paintComponent(Graphics g){
       
       Graphics2D g2 = (Graphics2D) g;

       
       g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

      
       super.paintComponent(g);


    } 
    
    
    
    
    
    
    /*
    we create a class that allows interaction with the events of the Mouse, it will be a nested class. 
    This class what goes to do is to represent every action of MyButton. The events of the mouse and
    for this you will have to extend the MouseAdapter
    */
    public class EventButton extends MouseAdapter{
       
            @Override
            public void mouseExited(MouseEvent e) {
                efectHover(1f, 0.6f, 0.03f, 10, false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	
                //we implement our logic
            	
               /*0.5 is the default button opacity the first value indicates what opacity it is currently at
                * 
                the second value means until what opacity I want it to reach.
                
               the third value is from start to finish opacity
                how much I want to add to the startup opacity
                
                the fourth value the milliseconds I want to delay each iteration
                and the boolean at the end is to signal when I want it to join and when I want
                let it subtract. as in the case of false when I want it to be subtracted.
                */
            	
              efectHover(0.5f, 1f, 0.03f, 10, true);

            }

            @Override
            public void mouseClicked(MouseEvent e) {
               efectHover(1f, 0.5f, 0.03f, 10, false);
            }
            
            
        /*
        We write a new method where we will write all the logic for each
         one of those events. We will pass as parameters 5 Variables

          */
            
     private void efectHover(float index, float range, float cont, int sleep, boolean event){
    	 
         //we create a new thread,
         new Thread(new Runnable(){
        	 
           @Override
           public void run(){
           /*
            we implement a boucle for with several parameters inside
           float i = index; the parameter we pass to the efectHover method
           inside by parameter we evaluate event, the boolean variable that we pass to the method
           then ternary operator ? i if true i< = range: and if false i > = range;
           in the counter i+cont in false case i-cont.
               */
           for(float i = index; (event) ? i <= range : i>= range; i = (event) ? i + cont : i - cont){
        	   
               //We call our setOpacity method and as parameter the variable i.
               setOpacity(i);

             
              try{
                 Thread.sleep(sleep);
              }catch(InterruptedException e){
                  e.printStackTrace();
              }
           }
           }
         })/*We start the Thread */.start();
     }


    }







    
    
    
    
    
    
    

}