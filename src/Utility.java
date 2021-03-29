import java.io.IOException;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class Utility {
	
	Document dom;
	
	Utility() {
		try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("config.xml");
        } catch (ParserConfigurationException | 
                 SAXException | IOException e) {
            e.printStackTrace();
        }
	}
	
	public String getConfig(String tag) {
        Element doc = dom.getDocumentElement();
        NodeList nl = doc.getElementsByTagName(tag);
        String value = nl.item(0).getFirstChild().getNodeValue();
        System.out.println(value);
        return value;
    }

}
