/*
 * @author í£ëPñæÅiShanming ZhangÅj
 * @version 1.0 
 * @copyright Yamanaka Laboratory, ICS, Keio University 
 */

package java.network.control.snmp.netgear;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

public class SnmpUtils {

	private final static String OID_FOR_VLAN_CREATE = ".1.3.6.1.2.1.17.7.1.4.3.1.5.";
	private final static String OID_FOR_VLAN_NAME = ".1.3.6.1.2.1.17.7.1.4.3.1.1.";
	private final static String OID_FOR_VLAN_U_TAG = ".1.3.6.1.2.1.17.7.1.4.3.1.2.";
	private final static String OID_FOR_VLAN_T_TAG = ".1.3.6.1.2.1.17.7.1.4.3.1.4.";
	private final static String OID_FOR_VLAN_DELETE = ".1.3.6.1.2.1.17.7.1.4.3.1.5.";

	private final static String COMMAND_FLAG_CREATE = "i 4";
	private final static String COMMAND_FLAG_NAME = "s";
	private final static String COMMAND_FLAG_ASSIGN = "x";
	private final static String COMMAND_FLAG_TAG = "x";
	private final static String COMMAND_FLAG_DELETE = "i 6";

	private final static String SNMPSET_VLAN_COMMAND_CREATE = "snmpset -c private -v2c";
	private final static String SNMPSET_VLAN_COMMAND_NAME = "snmpset -c private -v2c";
	private final static String SNMPSET_VLAN_COMMAND_ASSIGN = "snmpset -c private -v 2c";
	private final static String SNMPSET_VLAN_COMMAND_TAG = "snmpset -v 2c -c private";
	private final static String SNMPSET_VLAN_COMMAND_DELETE = "snmpset -c private -v2c";

	private final static String SNMPSET_VLAN_STR = "Vlan";

	public static int executeCommand(String cmdLine) {

		DefaultExecutor executor = new DefaultExecutor();
		int exitValue = 0;
		try {
			executor.setExitValue(0);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(30000);

			executor.setWatchdog(watchdog);
			exitValue = executor.execute(CommandLine.parse(cmdLine.toString()));

		} catch (ExecuteException ex) {
			exitValue = 1;
			ex.printStackTrace();
		} catch (IOException ex) {
			exitValue = 1;
			ex.printStackTrace();
		}

		return exitValue;
	}

	public static String getSnmpsetCommand(String commandType, String ip,
			String oid, String flag, String lastValue) {

		String command = commandType.concat(" ").concat(ip).concat(" ")
				.concat(oid).concat(" ").concat(flag);

		if (lastValue != null) {
			command = command.concat(" ").concat(lastValue);
		}

		return command;
	}

	public static int setPortVlanWithSnmpSet(String switchIP, String vlanID,
			String assignPortsMacAddress) {

		int flag = 1;
		// Create Command
		String createCmdLine = getSnmpsetCommand(SNMPSET_VLAN_COMMAND_CREATE,
				switchIP, OID_FOR_VLAN_CREATE.concat(vlanID),
				COMMAND_FLAG_CREATE, null);
		if (0 == executeCommand(createCmdLine)) {
			System.out
					.println(" Vlan " + vlanID + "  is created successfully ");
			flag = 0;
		}

		// Name Vlan
		String nameCmdLine = getSnmpsetCommand(SNMPSET_VLAN_COMMAND_NAME,
				switchIP, OID_FOR_VLAN_NAME.concat(vlanID), COMMAND_FLAG_NAME,
				SNMPSET_VLAN_STR.concat(vlanID));
		if (0 == executeCommand(nameCmdLine)) {
			System.out.println("Vlan " + vlanID + "  is named successfully ");
			flag = 0;
		}

		// Assign Vlan to port
		String assignCmdLine = getSnmpsetCommand(SNMPSET_VLAN_COMMAND_ASSIGN,
				switchIP, OID_FOR_VLAN_U_TAG.concat(vlanID),
				COMMAND_FLAG_ASSIGN, assignPortsMacAddress);
		if (0 == executeCommand(assignCmdLine)) {
			System.out.println("Vlan " + vlanID
					+ "  is assigned to port successfully ");
			flag = 0;
		}

		return flag;
	}

	public static int setTagVlanWithSnmpSet(String switchIP, String vlanID,
			String assignedPortsMacAddress, String tagedPortsMacAddress) {

		int flag = 1;
		setPortVlanWithSnmpSet(switchIP, vlanID, assignedPortsMacAddress);

		String tagCmdLine = getSnmpsetCommand(SNMPSET_VLAN_COMMAND_TAG,
				switchIP, OID_FOR_VLAN_T_TAG.concat(vlanID), COMMAND_FLAG_TAG,
				tagedPortsMacAddress);

		if (0 == executeCommand(tagCmdLine)) {
			flag = 0;
			System.out.println(" Vlan " + vlanID
					+ "  is taged in port  successfully ");
		}

		return flag;
	}

	public static int deleteVlanWithSnmpSet(String switchIP, String vlanID) {

		int flag = 1;

		String tagCmdLine = getSnmpsetCommand(SNMPSET_VLAN_COMMAND_DELETE,
				switchIP, OID_FOR_VLAN_DELETE.concat(vlanID),
				COMMAND_FLAG_DELETE, null);

		if (0 == executeCommand(tagCmdLine)) {
			flag = 0;
			System.out.println(" Vlan " + vlanID
					+ "  is deleted   successfully ");
		}

		return flag;
	}

	public static void getVlanWithSnmp() {

	}

	public static void setVlanWithSnmp() {

	}

}
