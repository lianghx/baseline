package drizzt.sf.app;

import java.io.InputStream;
import java.util.Properties;

public class ReadProperty {

	public String getParameter(String paraName){
		Properties prop = new Properties();
		try {
			InputStream is = getClass().getResourceAsStream("config.property");
			prop.load(is);
			if (is != null)
				is.close();
		} catch (Exception e) {
			System.out.println(e + "file config.property not found");
		}
		return prop.getProperty(paraName);
	}
}
