/*
 * @author í£ëPñæÅiShanming ZhangÅj
 * @version 1.0 
 * @copyright Yamanaka Laboratory, ICS, Keio University 
 */


package java.network.control.snmp.netgear;

import java.util.HashMap;
import java.util.Map;

public class CalculatorForSwitchVlanIfIndex {

	private static final int VLAN_IF_MAC_ADDRESS_LENGTH = 632;
	private static final String HEX_STR = "0x";

	private static Map<String, String> HEX_TO_BIN = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("0", "0000");
			put("1", "0001");
			put("2", "0010");
			put("3", "0011");
			put("4", "0100");
			put("5", "0101");
			put("6", "0110");
			put("7", "0111");
			put("8", "1000");
			put("9", "1001");
			put("A", "1010");
			put("B", "1011");
			put("C", "1100");
			put("D", "1101");
			put("E", "1110");
			put("F", "1111");
		}
	};

	private static Map<String, String> BIN_TO_HEX = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("0000", "0");
			put("0001", "1");
			put("0010", "2");
			put("0011", "3");
			put("0100", "4");
			put("0101", "5");
			put("0110", "6");
			put("0111", "7");
			put("1000", "8");
			put("1001", "9");
			put("1010", "A");
			put("1011", "B");
			put("1100", "C");
			put("1101", "D");
			put("1110", "E");
			put("1111", "F");
		}
	};

	public static char[] changeHexToBin(char[] hex) {

		char[] bin = new char[VLAN_IF_MAC_ADDRESS_LENGTH];
		String binStr = new String();

		for (int index = 0; index < hex.length; index++) {

			binStr = binStr.concat(HEX_TO_BIN.get(Character
					.toString(hex[index])));
		}

		bin = binStr.toCharArray();

		return bin;
	}

	public static String getHexMacAddressFromBinAddress(String binAddress) {

		String hexStr = new String();
		String subStr = new String();

		for (int index = 0; index < binAddress.length(); index++) {
			subStr = subStr
					.concat(Character.toString(binAddress.charAt(index)));
			if (subStr.length() == 4) {
				hexStr = hexStr.concat(BIN_TO_HEX.get(subStr));
				subStr = new String();
			}
		}

		return HEX_STR.concat(hexStr);
	}

	public static String getVlanBinMacAddressFromBinAddressesOfPorts(
			char[][] addrBits) {

		String binStr = new String();

		for (int col = 0; col < VLAN_IF_MAC_ADDRESS_LENGTH; col++) {
			char flag = '0';
			for (int row = 0; row < addrBits.length; row++) {
				if (addrBits[row][col] == '1') {
					flag = '1';
					break;
				}
			}
			binStr = binStr.concat(Character.toString(flag));
		}
		return binStr;
	}

	public static String getVlanUTagMacAddress(String[] portAddresses) {
		String binMacAddress = null;

		char[][] addresses = new char[portAddresses.length][VLAN_IF_MAC_ADDRESS_LENGTH];

		for (int index = 0; index < portAddresses.length; index++) {
			addresses[index] = changeHexToBin(portAddresses[index]
					.toCharArray());
		}

		binMacAddress = getVlanBinMacAddressFromBinAddressesOfPorts(addresses);

		return binMacAddress;
	}

	public static String getVlanTTagMacAddress(String binMacAddress) {
		String macAddress = new String();

		for (int index = 0; index < binMacAddress.length(); index++) {
			if ('1' == binMacAddress.charAt(index)) {
				macAddress = macAddress.concat("0");
			} else {
				macAddress = macAddress.concat("1");
			}
		}
		return macAddress;
	}
}
