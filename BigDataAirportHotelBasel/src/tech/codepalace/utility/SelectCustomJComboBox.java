package tech.codepalace.utility;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;


/**
 * 
 * @description Class to create a Custom JComboBox extended BasicComboBoxUI
 *
 */
public class SelectCustomJComboBox extends BasicComboBoxUI{
	
	Color color = Color.GRAY;
	
	private static String imageArrow, itemIcon;
	
	
	
	//Very important method to visualize the JComboBox
	public static ComboBoxUI createUI(JComponent jComponent, String imageArrow, String itemIcon) {
		
		SelectCustomJComboBox.imageArrow = imageArrow;
		SelectCustomJComboBox.itemIcon = itemIcon;
		
		return new SelectCustomJComboBox();
		
	}
	
	
	/**
	 * JButton from the JComboBox
	 */
	@Override
	protected JButton createArrowButton() {
		
		//JButton of the JComboBox
		JButton dropDownJButton = new JButton();
		
		//We use one image for the JButton
		dropDownJButton.setIcon(new ImageIcon(getClass().getResource("/img/" + imageArrow)));
		
		//Set a LineBorder to let space to the JButton image
		dropDownJButton.setBorder(BorderFactory.createLineBorder(color, 3));
		
		//Transparency for the JButton
		dropDownJButton.setContentAreaFilled(false);
		
		//return our JButton
		return dropDownJButton;
		
		
	}

	

	
	@SuppressWarnings({ "serial", "rawtypes" })
	@Override
	protected ListCellRenderer createRenderer() {
		
		return new DefaultListCellRenderer() {
			
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
				//Background Color for the Selected Item 
				list.setSelectionBackground(Color.DARK_GRAY);
				
				//If you want to avoid to put one image to the first element you can uncomment the if instruction.
//				if(index !=-1) {
				
					//We set one image to the items
					setIcon(new ImageIcon(getClass().getResource("/img/" + itemIcon)));
//				}
				return this;
			}
		};
	}
	
	
	
	


}
