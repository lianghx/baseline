package drizzt.sf.app;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtils {
	private static ApplicationContext ctx;

	static {
		Properties props = new Properties();
		try {
			props
					.load(ClassLoader
							.getSystemResourceAsStream("drizzt/sf/log4j/log4j.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);

	}

	public static ApplicationContext getApplicationContext() {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(
					"drizzt/sf/context/main/spring.xml");
		}
		return ctx;
	}
}
