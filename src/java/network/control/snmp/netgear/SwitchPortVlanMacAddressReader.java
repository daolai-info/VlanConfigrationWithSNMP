/*
 * @author í£ëPñæÅiShanming ZhangÅj
 * @version 1.0 
 * @copyright Yamanaka Laboratory, ICS, Keio University 
 */



package java.network.control.snmp.netgear;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SwitchPortVlanMacAddressReader {

	private static final String CONFIG_FOLDER = "./config/";
	private static final String FILE_EXTENSION = ".properties";
	private static final String PORT_STR = "Port";

	public static String[] getPortVlanMacAddress(String fileName,
			String[] vlanPorts) {

		String[] macAddress = new String[vlanPorts.length];

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(CONFIG_FOLDER.concat(fileName).concat(
					FILE_EXTENSION));

			// load a properties file
			prop.load(input);

			for (int index = 0; index < vlanPorts.length; index++) {
				macAddress[index] = prop.getProperty(PORT_STR
						.concat(vlanPorts[index]));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return macAddress;
	}
}
