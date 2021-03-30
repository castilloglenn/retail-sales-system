import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.Base64;

import java.io.FileOutputStream;
import java.awt.Font;
import java.io.FileInputStream;
import java.util.Properties;

public class Utility {
	
	Document dom;
	
	Utility() {
		setupXMLConfigurations();
	}
	
	private void setupXMLConfigurations() {
		try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("./config/config.xml");
        } catch (ParserConfigurationException | 
                 SAXException | IOException e) {
            e.printStackTrace();
        }
	}
	
	public String getConfig(String tag) {
		try {
	        Element doc = dom.getDocumentElement();
	        NodeList nl = doc.getElementsByTagName(tag);
	        return nl.item(0).getFirstChild().getNodeValue();
		} catch (DOMException | NullPointerException e) {
			return "";
		}
    }
	
	public boolean setDatabaseProperties(String user, String pass) {
		Properties p = new Properties();
        p.put(encodeData("username"), encodeData(user));
        p.put(encodeData("password"), encodeData(pass));
        try {
            FileOutputStream fos = new FileOutputStream("./config/setup.properties");
            p.store(fos, "Important system property, do not change.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public String getDatabaseProperty(String property) {
		try {
            FileInputStream fis = new FileInputStream("./config/setup.properties");
            Properties p = new Properties();
            p.load(fis);
            return p.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	
    public static String encodeData(String data) {
    	String value = (data.equals("")) ? "emptypassword" : data;
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(value.getBytes());
    }

    public static String decodeData(String data) {
        Base64.Decoder dec = Base64.getDecoder();
        return new String(dec.decode(data));
    }

    public static String hashData(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(data.getBytes());
            BigInteger bi = new BigInteger(1, messageDigest);
            String hashedText = bi.toString(16);
            while (hashedText.length() < 32) {
                hashedText = "0" + hashedText;
            }
            return hashedText;
        } catch (NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
        return null;
    }
	
	public void adjustFont(JComponent comp, JPanel panel, double minWidth, int minSize) {
		// ratio between panel's width divided by minimum font size
		double adaptiveWidth = panel.getSize().width / (minWidth / minSize);
		comp.setFont(new Font(
			"Tahoma", (comp.getFont().isBold()) ? Font.BOLD : Font.PLAIN,
			(int) adaptiveWidth)
		);
	}

}
