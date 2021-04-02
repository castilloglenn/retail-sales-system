package utils;

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
import java.util.Calendar;
import java.io.FileOutputStream;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Utility {
	
	private Document dom;
	
	public Utility() {
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
	
	public boolean setDatabaseProperties(String url, String database, String user, String pass) {
		Properties p = new Properties();
        p.put(encodeData("url"), encodeData(url));
        p.put(encodeData("database"), encodeData(database));
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
	
    public String encodeData(String data) {
    	String value = (data.equals("")) ? "emptypassword" : data;
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(value.getBytes());
    }

    public String decodeData(String data) {
        Base64.Decoder dec = Base64.getDecoder();
        return new String(dec.decode(data));
    }

    public String hashData(String data) {
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
	
	public void adjustFont(JComponent comp, JPanel panel, int minWidth, int minSize) {
		int maxSize = minSize + 7;
		int ratio = minWidth / minSize;
		int adaptiveWidth = panel.getSize().width / ratio;

		if (adaptiveWidth > maxSize) adaptiveWidth = maxSize;
		if (adaptiveWidth < minSize) adaptiveWidth = minSize;
		
		comp.setFont(new Font(
			"Tahoma", (comp.getFont().isBold()) ? Font.BOLD : Font.PLAIN,
			adaptiveWidth)
		);
	}
	
	public String getTermsAndConditions() {
		try {
			File myObj = new File("./config/terms.txt");
			Scanner myReader = new Scanner(myObj);
			
			StringBuilder sb = new StringBuilder();
			while (myReader.hasNextLine()) {
				sb.append(myReader.nextLine());
				sb.append("\n");
			}
			myReader.close();
			return sb.toString();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public void copyToClipboard(String text) {
		StringSelection ss = new StringSelection(text);
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(ss, null);
	}
	
	public String getFromClipboard() {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = cb.getContents(null);
		if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				return (String) t.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Employee ID Format: <br>
	 * example:  51210325001 <br>
	 * 		5-1-21-03-25-001 <p>
	 * 
	 *  5 = Employee Code <br>
	 *  1 = Level of Access <br>
	 *        { 0 : Store Assistant <br>
	 *        	1 : Inventory Clerk <br>
	 *        	2 : Cashier <br>
	 *        	3 : Junior Supervisor <br>
	 *        	4 : Senior Supervisor <br>
	 *        	5 : Manager/Owner } <br>
	 *  21 = Year <br>
	 *  03 = Month <br>
	 *  25 = Day <br>
	 *  001 = auto-increment first is 1, second is 2, etc. <p>
	 *  
	 */
	public long generateEmployeeID(long lastID, int level) {
		StringBuilder markup = new StringBuilder("5");
		
		markup.append(Integer.toString(level));
		
		Calendar c = Calendar.getInstance();
		markup.append(Integer.toString(c.get(Calendar.YEAR)).substring(2));
		
		int month = c.get(Calendar.MONTH) + 1;
		if (month < 10) markup.append("0");
		markup.append(Integer.toString(month));
		
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day < 10) markup.append("0");
		markup.append(Integer.toString(day));
		
		long latest = lastID;
		if (latest == -1) {
			markup.append("001");
		} else {
			String lastNum = Long.toString(latest).substring(8);
			int increment = Integer.parseInt(lastNum) + 1;
			markup.append(String.format("%03d", increment));
		}
		
		return Long.parseLong(markup.toString());
	}
	
	/**
	 * Log ID Format: <br>
	 * example:  14100000001 <br>
	 * 		1-4-0000001 <p>
	 * 
	 *  1 = Log Code <br>
	 *  4 = Log Type <br>
	 *  1 = Log Status <br>
	 *  00000001 = auto increment <br>
	 *  0 = Message Part <p>
	 *  
	 *  @param isSubMessage = determines if the log transaction will be incremented
	 *  	by 1, because if not, it will remain the same as the latest id which would be
	 *  	the message head by the normal process.
	 */
	public long generateLogID(long lastID, int type,
			int part, boolean isSubMessage) {
		StringBuilder markup = new StringBuilder("1");
		
		markup.append(Integer.toString(type));
		
		// all new logs will be unread initially by the manager.
		markup.append("1");
		
		long latest = lastID;
		if (latest == -1) {
			markup.append("00000001");
		} else {
			String lastNum = Long.toString(latest).substring(3, 8);
			int value;
			if (isSubMessage) {
				markup.append(lastNum);
			} else {
				value = Integer.parseInt(lastNum) + 1;
				markup.append(String.format("%08d", value));
			}
		}
		
		// message part
		markup.append(Integer.toString(part));
		
		return Long.parseLong(markup.toString());
	}
}
