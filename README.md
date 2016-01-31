# VlanConfigrationWithSNMP
The library can be used for dynamic VLAN configration of Netgear L2 switch based on SNMP (Simple Network Management Protocol). 

The usage is as following
  1. Collect VLAN mac addresses of physical ports of Netgear switch by SNMP commands. And then, update ./config/<Switch Management IP Address >.properties
  2. Sava your VLAN configuration information in ./data/CreateVlanInfo.data or ./data/DeleteVlanInfo.data by specified format.
  3. Perform the OperationVlanWithSNMP.jar

The example was based on NetgearGSM7328 switch property data.
