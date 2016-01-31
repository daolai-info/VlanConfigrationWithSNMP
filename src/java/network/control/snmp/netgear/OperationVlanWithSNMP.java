/*
 * @author í£ëPñæÅiShanming ZhangÅj
 * @version 1.0 
 * @copyright Yamanaka Laboratory, ICS, Keio University 
 */


package java.network.control.snmp.netgear;

import java.util.ArrayList;
import java.util.Iterator;

public class OperationVlanWithSNMP {

	public static int DeleteVlan() {
		int flag = 0;

		ArrayList<String[]> vlanInfo = VlanInforReader.getDeleteVlanInfo();
		Iterator<String[]> iterator = vlanInfo.iterator();

		while (iterator.hasNext()) {
			String[] items = iterator.next();
			String switchIP = null;
			String vlanID = null;

			for (int index = 0; index < items.length; index++) {

				if (index == 0) {
					switchIP = items[index];
				}

				if (index == 1) {
					vlanID = items[index];
				}

			}

			try {

				if (0 == SnmpUtils.deleteVlanWithSnmpSet(switchIP, vlanID)) {
					System.out.println("The Vlan ".concat(vlanID).concat(
							" is deleted in switch [" + switchIP
									+ "] successfully. "));
					flag = 0;
				}

				Thread.sleep(3000);
			} catch (Exception e) {
				flag = 1;
				System.out.println("The Vlan ".concat(vlanID).concat(
						" is deleted in switch [" + switchIP
								+ "] unsuccessfully. "));

				e.printStackTrace();
			}
		}

		return flag;
	}

	public static int CreateVlan(String type) {
		int flag = 1;
		ArrayList<String[]> vlanInfo = VlanInforReader.getCreateVlanInfo();
		Iterator<String[]> iterator = vlanInfo.iterator();

		while (iterator.hasNext()) {
			String[] items = iterator.next();
			String[] vlanPorts = new String[items.length - 2];
			String[] vlanPortsMacAddresses = new String[items.length - 2];
			String switchIP = null;
			String vlanID = null;
			String assignedPortsMacAddress = null;
			String tagedPortsMacAddress = null;

			for (int index = 0; index < items.length; index++) {

				if (index == 0) {
					switchIP = items[index];
				} else if (index == 1) {
					vlanID = items[index];
				} else {
					vlanPorts[index - 2] = items[index];
				}
			}

			try {

				vlanPortsMacAddresses = SwitchPortVlanMacAddressReader
						.getPortVlanMacAddress(switchIP, vlanPorts);

				assignedPortsMacAddress = CalculatorForSwitchVlanIfIndex
						.getVlanUTagMacAddress(vlanPortsMacAddresses);

				switch (type) {
				case "PortVlan":
					if (0 == SnmpUtils
							.setPortVlanWithSnmpSet(
									switchIP,
									vlanID,
									CalculatorForSwitchVlanIfIndex
											.getHexMacAddressFromBinAddress(assignedPortsMacAddress))) {
						System.out.println("The PortVlan ".concat(vlanID)
								.concat(" is created in ports ["
										+ vlanPorts.toString()
										+ "] successfully. "));
						flag = 0;
					}
					break;

				case "TagVlan":
					tagedPortsMacAddress = CalculatorForSwitchVlanIfIndex
							.getVlanTTagMacAddress(assignedPortsMacAddress);

					if (0 == SnmpUtils
							.setTagVlanWithSnmpSet(
									switchIP,
									vlanID,
									CalculatorForSwitchVlanIfIndex
											.getHexMacAddressFromBinAddress(assignedPortsMacAddress),
									CalculatorForSwitchVlanIfIndex
											.getHexMacAddressFromBinAddress(tagedPortsMacAddress))) {
						System.out.println("The Tag Vlan".concat(vlanID)
								.concat(" is created in ports ["
										+ vlanPorts.toString()
										+ "] successfully. "));
						flag = 0;
					}
					break;
				}
				Thread.sleep(3000);
			} catch (Exception e) {
				flag = 1;
				System.out.println("The Vlan".concat(vlanID).concat(
						" is created unsuccessfully. "));

				e.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 1) {
			System.out
					.println("Please input operation type( Delete or Create). ");

			System.out
					.println("   Example:  java -jar OperationVlanWithSNMP.jar Del/Create ");
			return;
		}

		if (args.length == 1 && args[0].equals("Delete")) {
			if (0 == DeleteVlan()) {
				System.out.println(" The Vlans is deleted successfully ");
			}
			return;
		}

		if (args.length == 2 && args[0].equals("Create")
				&& (args[1].equals("PortVlan") || args[1].equals("TagVlan"))) {
			if (0 == CreateVlan(args[1])) {
				System.out.println(" The Vlans is created successfully ");
			}
			return;
		}

		System.out
				.println("Please read README.txt for using OperationVlanWithSNMP tool.");
	}
}
