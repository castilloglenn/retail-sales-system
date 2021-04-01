package utils;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

public class Gallery {
	
	// =================== DARK THEME COLORS =======================
	public final Color DFONT = new Color(255, 255, 255);
	public final Color DFONT_HOVER = new Color(105, 162, 176);
	public final Color DFRAME_BACKGROUND = new Color(51, 51, 51);
	public final Color DPANEL_BACKGROUND = new Color(22, 27, 51);
	public final Color DCOMP_BACKGROUND = new Color(0, 0, 0);
	public final Color DCOMP_FOREGROUND = new Color(255, 255, 255);

	// =================== LIGHT THEME COLORS =======================
	public final Color LFONT = new Color(0, 0, 0);
	public final Color LFONT_HOVER = new Color(224, 82, 99);
	public final Color LFRAME_BACKGROUND = new Color(200, 200, 200);
	public final Color LPANEL_BACKGROUND = new Color(255, 255, 255);
	public final Color LCOMP_BACKGROUND = new Color(255, 255, 255);
	public final Color LCOMP_FOREGROUND = new Color(0, 0, 0);
	
	
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
	
	public void adjustTheme(JComponent[] components) {
		for (JComponent c : components) {
			if (c instanceof JPanel) {
				if (isDark) {
					c.setBackground(DPANEL_BACKGROUND);
				} else {
					c.setBackground(LPANEL_BACKGROUND);
				}
			} else if (c instanceof JScrollPane) {
				if (isDark) {
					c.setBackground(DPANEL_BACKGROUND);
				} else {
					c.setBackground(LPANEL_BACKGROUND);
				}
			} else if (c instanceof JCheckBox) {
				if (isDark) {
					c.setBackground(DPANEL_BACKGROUND);
					c.setForeground(DFONT);
				} else {
					c.setBackground(LPANEL_BACKGROUND);
					c.setForeground(LFONT);
				}
			} else if (c instanceof JTextField ||
					   c instanceof JTextArea ||
					   c instanceof JPasswordField ||
					   c instanceof JButton ||
					   c instanceof JToggleButton) {
				if (isDark) {
					c.setBackground(DCOMP_BACKGROUND);
					c.setForeground(DCOMP_FOREGROUND);
				} else {
					c.setBackground(LCOMP_BACKGROUND);
					c.setForeground(LCOMP_FOREGROUND);
				}
			} else {
				if (isDark) {
					c.setForeground(DFONT);
				} else {
					c.setForeground(LFONT);
				}
			}
		}
	}

}
