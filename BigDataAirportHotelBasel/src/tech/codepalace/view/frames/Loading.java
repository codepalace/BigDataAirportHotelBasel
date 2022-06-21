package tech.codepalace.view.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * 
 * @author tonimacaroni
 * @description This class is going to be used to show any progress within the application.
 *
 */
@SuppressWarnings("serial")
public class Loading extends JDialog {
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	public JProgressBar progressBar;
	
	private boolean jprogressBarIndeterminate = false;
	

	
	/**
	 * @description In this constructor the first parameter jframeInBackground, it's going to be the GUI from where our Loading class is going to be called.
	 * <p>modal is to block the GUI which is located in the background.</p>
	 * @param jframeInBackground
	 * @param modal
	 */
	public Loading(JFrame jframeInBackground, boolean modal) {
		
		//we pass the data to the super class. Our locked class and the modal value.
		super(jframeInBackground, modal);
		
		init();

		
	}
	
	
	public Loading(JFrame jframeInBackground, boolean modal, boolean jprogressBarIndeterminate) {
		
		
		
		//we pass the data to the super class. Our locked class and the modal value.
		super(jframeInBackground, modal);
		
		this.jprogressBarIndeterminate = jprogressBarIndeterminate;
		
		init();

		
	}
	
	
	
  public void init(){
	  
	  setSize(640,213);
		setLocationRelativeTo(null);
		
		//We remove the decoration
		setUndecorated(true);
		
		//Backgraund JPanel
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		//LayoutManager FlowLayout with the components centered. We are going to have only one element(the JProgressBar).
		this.panelWithBackgroundOption.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 106));
		
		//We set an appropriate background image to our GUI application
		this.panelWithBackgroundOption.setImage("/img/bigdata_bg.jpg");
		
		
		this.progressBar = new JProgressBar();
		this.progressBar.setMinimum(0);
		
		
		setContentPane(this.panelWithBackgroundOption);
		
		//We set the size ouf our progressBar
		this.progressBar.setPreferredSize(new Dimension(500, 20));
		
		//setBorder to our progressBar
		this.progressBar.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		
		if(this.jprogressBarIndeterminate) {
			
//			JOptionPane.showMessageDialog(null, "Estamos en el constructor indetermidado");
			
			//We set a new UI BasicProgressBarUI so we can modify our JProgressBar as we want.
			this.progressBar.setUI(new BasicProgressBarUI() {

			  
				
				
			});
			
			
			this.progressBar.setForeground(Color.BLUE);
			
			
			
			
			
		
		}else {
//			JOptionPane.showMessageDialog(null, "Estamos en el constructor determinado");
			
			//We set a new UI BasicProgressBarUI so we can modify our JProgressBar as we want.
			this.progressBar.setUI(new BasicProgressBarUI() {
				
			  
				//import the paintDeterminate Method to modify our JprogressBar
				@Override
				protected void paintDeterminate(Graphics g, JComponent c) {
					
					//New Object Graphics2D and we convert g to Graphics2d or Casting.
					Graphics2D g2d = (Graphics2D)g;
					
					//We get the With and Height from our JProgressBar
					int width = progressBar.getWidth();
					int height = progressBar.getHeight();
					
					//Variable for the Space between the Border
					int spaceWidth = width - 20;
					int spaceHeight = height - 20;
					
					//We get the percentage value
					double percentageProgress = progressBar.getPercentComplete();
					
					//we increase the JProgress 
					spaceWidth = (int)(spaceWidth*percentageProgress);
					
					//We set the Color for the JProgressBar
					g2d.setColor(Color.BLUE);
					
					//Object Rectangle to draw our JProgress value
					Rectangle progress1 = new Rectangle(5,5, spaceWidth+10, spaceHeight+10);
					
					//We fill the Color blue to the progressBar.
					g2d.fill(progress1);
					
					

					
				}
				
			});
			
		}
		
		//Add the JProgressBar to the JPanel.
		this.panelWithBackgroundOption.add(progressBar);
		
		
	}
  
  
	

}
