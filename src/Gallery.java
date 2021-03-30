import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Gallery {
	
	// =================== DARK THEME COLORS =======================
	public final Color DFONT = new Color(255, 255, 255);
	public final Color DFONT_HOVER = new Color(105, 162, 176);
	public final Color DFRAME_BACKGROUND = new Color(51, 51, 51);
	public final Color DPANEL_BACKGROUND = new Color(22, 27, 51);
	public final Color DCOMP_BACKGROUND = new Color(255, 255, 255);

	// =================== LIGHT THEME COLORS =======================
	public final Color LFONT = new Color(0, 0, 0);
	public final Color LFONT_HOVER = new Color(224, 82, 99);
	public final Color LFRAME_BACKGROUND = new Color(200, 200, 200);
	public final Color LPANEL_BACKGROUND = new Color(255, 255, 255);
	public final Color LCOMP_BACKGROUND = new Color(255, 255, 255);
	
	public Image loginIcon, businessLogo;
	public ImageIcon resizedDarkLogo, resizedLightLogo; 
	public ImageIcon resizedShow, resizedHide;
	public ImageIcon resizedDark, resizedLight;
	
	public boolean isDark = true;
	
	Gallery() {
		loginIcon = new ImageIcon("images/icon.png").getImage();
		businessLogo = new ImageIcon("images/logo.png").getImage();
		
		resizedDarkLogo = resizeImage("images/dark/logo.png", 64, 64);
	    resizedLightLogo = resizeImage("images/light/logo.png", 64, 64);
	    resizedShow = resizeImage("images/show.png", 24, 24);
	    resizedHide = resizeImage("images/hide.png", 24, 24);
	    resizedDark = resizeImage("images/dark.png", 24, 24);
	    resizedLight = resizeImage("images/light.png", 24, 24);
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

}
