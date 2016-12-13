package  com.duanrong.dataAnalysis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("serial")
public class PropUtil extends Properties {
	
	private static PropUtil instance;

	public static PropUtil getInstance() {
		if (instance != null) {
			return instance;
		} else {
			createInstance();
			return instance;
		}
	}

	private static synchronized void createInstance() {
		if (instance == null) {
			instance = new PropUtil();
		}
	}

	private PropUtil() {
		InputStream is = getClass().getResourceAsStream("/config.properties");
			try {
				load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	

}
