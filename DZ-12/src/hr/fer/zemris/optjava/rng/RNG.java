package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RNG {
	private static IRNGProvider rngProvider;

	static {
		Properties properties = new Properties();
		ClassLoader classLoader = RNG.class.getClassLoader();
		InputStream inStream = classLoader.getResourceAsStream("rng-config.properties");
		try {
			properties.load(inStream);
		} catch (IOException e) {
			System.err.println("Could not load properties.");
			System.exit(1);
		}

		String rngProviderName = properties.getProperty("rng-provider");
		try {
			rngProvider = (IRNGProvider) classLoader.loadClass(rngProviderName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.err.println("Could not load RNG provider.");
			System.exit(1);
		}
	}

	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}
