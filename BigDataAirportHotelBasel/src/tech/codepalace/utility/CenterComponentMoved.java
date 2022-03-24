package tech.codepalace.utility;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;


/**
 * 
 * @author tonimacaroni
 * @description class util to center a component or for example class JFrame jdialog to the center of the screen.
 * 
 * <p>This class has the property that when we move the element, for example a modal JDialog window, when we move it, it returns to the center again.</p>
 * 
 * <p>This will be useful when we block the JFrame below with a JFrame o JDialog class above. for example when we use a login dialog box or to set some value without closing the JFrame below.</p>
 */
@SuppressWarnings("serial")
public class CenterComponentMoved extends JFrame implements ComponentListener {
	
	//Component that called this class
	private Component component;
	
	public CenterComponentMoved(Component component) {
		
		this.component = component;
		this.component.addComponentListener(this);
		
	}

	@Override
	public void componentResized(ComponentEvent e) {}

	
	/*
	 * We use this method when we moved the Component or Element to center back.
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		((Window) this.component).setLocationRelativeTo(null);
		
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

}
