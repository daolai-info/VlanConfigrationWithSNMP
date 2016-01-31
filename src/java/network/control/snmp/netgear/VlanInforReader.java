/*
 * @author í£ëPñæÅiShanming ZhangÅj
 * @version 1.0 
 * @copyright Yamanaka Laboratory, ICS, Keio University 
 */

package java.network.control.snmp.netgear;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class VlanInforReader {

	private static final String VLAN_INFO_FILE_CREATE = "./data/CreateVlanInfo.data";
	private static final String VLAN_INFO_FILE_DELETE = "./data/DeleteVlanInfo.data";
	private static final String ITEM_SEPARATOR = "\t";

	private static ArrayList<String[]> getVlanInfoFromFile(String filePath) {

		ArrayList<String[]> vlanInfo = new ArrayList<String[]>();

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				String[] info = line.split(ITEM_SEPARATOR);
				vlanInfo.add(info);
			}

			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return vlanInfo;
	}

	public static ArrayList<String[]> getDeleteVlanInfo() {

		ArrayList<String[]> deleteVlanInfo = getVlanInfoFromFile(VLAN_INFO_FILE_DELETE);
		return deleteVlanInfo;
	}

	public static ArrayList<String[]> getCreateVlanInfo() {
		ArrayList<String[]> createVlanInfo = getVlanInfoFromFile(VLAN_INFO_FILE_CREATE);
		return createVlanInfo;
	}

}
