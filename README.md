# VlanConfigrationWithSNMP
The library implements the dynamic configuration of VLAN [1] for Netgear L2-Switch, and can assist other network control logic programs to realize complext network configurations dynamically. The library is implemented based on SNMP (Simple Network Management Protocol)[2] and Java program language 

The usage is as following
  1. Collect VLAN mac addresses of physical ports of Netgear switch by SNMP commands. And then, update ./config/<Switch Management IP Address >.properties
  2. Sava your VLAN configuration information in ./data/CreateVlanInfo.data or ./data/DeleteVlanInfo.data by specified format.
  3. Perform the OperationVlanWithSNMP.jar

The example was based on NetgearGSM7328 switch property data.


[1]. VLAN(Virtual Local Area Network): https://en.wikipedia.org/wiki/Virtual_LAN.
[2]. SNMP(Simple Network Management Protocol): https://en.wikipedia.org/wiki/Simple_Network_Management_Protocol. 
