package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class loadProperty {

	public Properties readPropFile() throws IOException{
		Properties prop = new Properties();
		String propFileName = "config.properties";
		
		InputStream in = getClass().getClassLoader().getResourceAsStream(propFileName);
		if(in!= null){
			prop.load(in);
		}else{
			throw new FileNotFoundException("property file '"+propFileName+"' not found found in class path");
		}
	return prop; 
	}
}
