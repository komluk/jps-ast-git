package pl.edu.pjwstk.jps.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JpsProperties {
	private static final Logger log = LoggerFactory.getLogger(JpsProperties.class);
	private static final JpsProperties instance = new JpsProperties();
	
	private final Properties prop = new Properties();
	
	private JpsProperties() {
		try {
			URL u = JpsProperties.class.getResource("/jps.properties");
			if(u == null) {
				System.err.println("NULL");
			} else {
				System.out.println(u.toString());
			}
			prop.load(JpsProperties.class.getResourceAsStream("/jps.properties"));
			log.debug("read keys: {}", prop.keySet());
		} catch (IOException e) {
			log.warn("Unable to read application properties. Using defaults.", e);
		}
	}
	
	public boolean isQResInDebug() {
		return Boolean.parseBoolean(prop.getProperty("qres.debug", "false"));
	}
	
	public static JpsProperties getInstance() {
		return instance;
	}
}
