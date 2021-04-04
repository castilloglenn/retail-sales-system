package utils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;


/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Gallery {
	
	// =================== DARK THEME COLORS =======================
	public final Color DFONT = new Color(255, 255, 255);
	public final Color DFONT_HOVER = new Color(105, 162, 176);
	public final Color DFRAME_BACKGROUND = new Color(51, 51, 51);
	public final Color DPANEL_BACKGROUND = new Color(22, 27, 51);
	public final Color DCOMP_BACKGROUND = new Color(0, 0, 0);
	public final Color DCOMP_FOREGROUND = new Color(255, 255, 255);
	public final Color DHEADER_TABLE_BACKGROUND = new Color(255, 255, 255);

	// =================== LIGHT THEME COLORS =======================
	public final Color LFONT = new Color(0, 0, 0);
	public final Color LFONT_HOVER = new Color(224, 82, 99);
	public final Color LFRAME_BACKGROUND = new Color(200, 200, 200);
	public final Color LPANEL_BACKGROUND = new Color(255, 255, 255);
	public final Color LCOMP_BACKGROUND = new Color(255, 255, 255);
	public final Color LCOMP_FOREGROUND = new Color(0, 0, 0);
	public final Color LHEADER_TABLE_BACKGROUND = new Color(0, 0, 0);
	
	public Image loginIcon, businessLogo;
	public ImageIcon darkLogo, lightLogo; 
	public ImageIcon darkShow, darkHide;
	public ImageIcon lightShow, lightHide;
	public ImageIcon darkSymbol, lightSymbol;
	public ImageIcon darkEmployee, lightEmployee;
	
	public boolean isDark = true;
	
	public Gallery() {
		designOptionPanes();
		
		loginIcon = new ImageIcon("images/icon.png").getImage();
		businessLogo = new ImageIcon("images/logo.png").getImage();
		
		darkLogo = resizeImage("images/dark/logo.png", 64, 64);
	    darkShow = resizeImage("images/dark/show.png", 24, 24);
	    darkHide = resizeImage("images/dark/hide.png", 24, 24);
	    darkSymbol = resizeImage("images/dark.png", 24, 24);
	    darkEmployee = resizeImage("images/dark/employee.png", 64, 64);
	    
	    lightLogo = resizeImage("images/light/logo.png", 64, 64);
	    lightShow = resizeImage("images/light/show.png", 24, 24);
	    lightHide = resizeImage("images/light/hide.png", 24, 24);
	    lightSymbol = resizeImage("images/light.png", 24, 24);
	    lightEmployee = resizeImage("images/light/employee.png", 64, 64);
	}
	
	private ImageIcon resizeImage(String path, int width, int height) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
			return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return null;
	}
	
	public void designOptionPanes() {
		if (isDark) {
			UIManager.put("OptionPane.background", DPANEL_BACKGROUND);
			UIManager.put("OptionPane.messageForeground", DFONT);
			UIManager.put("Panel.background", DPANEL_BACKGROUND);
		} else {
			UIManager.put("OptionPane.background", LPANEL_BACKGROUND);
			UIManager.put("OptionPane.messageForeground", LFONT);
			UIManager.put("Panel.background", LPANEL_BACKGROUND);
		}
	}
	
	public void getAllComponentsChangeTheme(Container container, int depth) {
		if (depth != 0) {
			for (Component component : container.getComponents()) {
				if (component instanceof Container) {
					adjustTheme(component);
					getAllComponentsChangeTheme((Container) component, depth - 1);
				} else {
					adjustTheme(component);
				}
			}
		}
	}
	
	public void adjustTheme(Component component) {
		if (component instanceof JFrame) {
			if (isDark) {
				component.setBackground(DFRAME_BACKGROUND);
			} else {
				component.setBackground(LFRAME_BACKGROUND);
			}
		} else if (component instanceof JPanel) {
			if (component.getName() == null) {
				if (isDark) {
					component.setBackground(DPANEL_BACKGROUND);
				} else {
					component.setBackground(LPANEL_BACKGROUND);
				}
			} else {
				if (isDark) {
					component.setBackground(DFRAME_BACKGROUND);
				} else {
					component.setBackground(LFRAME_BACKGROUND);
				}
			}
		} else if (component instanceof JRadioButton) {
			if (isDark) {
				component.setForeground(DFONT);
				component.setBackground(DPANEL_BACKGROUND);
			} else {
				component.setForeground(LFONT);
				component.setBackground(LPANEL_BACKGROUND);
			}
		} else if (component instanceof JSpinner) {
			JTextField tf = (JTextField) ((JSpinner) component).getEditor().getComponent(0);
			if (isDark) {
				tf.setForeground(DFONT);
				tf.setBackground(DPANEL_BACKGROUND);
			} else {
				tf.setForeground(LFONT);
				tf.setBackground(LPANEL_BACKGROUND);
			}
			
		} else if (component instanceof JTextField || component instanceof JTextArea ||
				component instanceof JPasswordField || component instanceof JButton ||
				component instanceof JToggleButton) {
			if (isDark) {
				component.setBackground(DCOMP_BACKGROUND);
				component.setForeground(DCOMP_FOREGROUND);
			} else {
				component.setBackground(LCOMP_BACKGROUND);
				component.setForeground(LCOMP_FOREGROUND);
			}
		} else if (component instanceof JScrollPane) {
			JScrollBar sb = ((JScrollPane) component).getVerticalScrollBar();
			if (isDark) {
				((JScrollPane) component).getViewport().setBackground(DPANEL_BACKGROUND);
				sb.setBackground(DCOMP_BACKGROUND);
			} else {
				((JScrollPane) component).getViewport().setBackground(LPANEL_BACKGROUND);
				sb.setBackground(LCOMP_BACKGROUND);
			}
		} else if (component instanceof JTable) {
			JTable table = ((JTable) component);
			JTableHeader tableheader = table.getTableHeader();
			if (isDark) {
				tableheader.setBackground(DCOMP_BACKGROUND);
				table.setBackground(DPANEL_BACKGROUND);
				table.setForeground(DFONT);
			} else {
				tableheader.setBackground(LCOMP_BACKGROUND);
				table.setBackground(LPANEL_BACKGROUND);
				table.setForeground(LFONT);
			}
		} else if (component instanceof JCheckBox ||
				component instanceof JComboBox) {
			if (isDark) {
				component.setBackground(DPANEL_BACKGROUND);
				component.setForeground(DFONT);
			} else {
				component.setBackground(LPANEL_BACKGROUND);
				component.setForeground(LFONT);
			}
		} else {
			if (isDark) {
				component.setForeground(DFONT);
			} else {
				component.setForeground(LFONT);
			}
		}
	}

}
