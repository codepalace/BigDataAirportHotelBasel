package tech.codepalace.utility;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.apple.eawt.Application;

public class SetIconOperatingSystem {
	
	private JFrame jFrame;
	private Image image;
	
	public SetIconOperatingSystem() {
		
		
	}
	
	
	
	
	
	public void setIconJFrame(JFrame jFrame, String imagePath) {
		this.jFrame = jFrame;
		URL url = getClass().getResource(imagePath);
		this.image = new ImageIcon(url).getImage();
		
switch (OperatingSystemCheck.getOparatingSystem()) {
		
		case MAC:
			
			
			this.jFrame.setIconImage(image);
			Application application = Application.getApplication();
			
			application.setDockIconImage(image);
			
//			System.setProperty("apple.laf.useScreenMenuBar", "true");
//	        System.setProperty(
//	            "com.apple.mrj.application.apple.menu.about.name", "Name");
//	        
//	        
//	        System.setProperty("apple.awt.application.name", "My app");
			
			
		
		break;

			
			
default:
	
	 this.jFrame.setIconImage (new ImageIcon(getClass().getResource(imagePath)).getImage());
	break;
	
	 
	
		}

		
	}

	
}
