package au.net.iinet.jpoller.poller;

import au.net.iinet.jpoller.filestore.DataDAO;

import java.util.Set;

public class DevicePoller implements Runnable {

    private Device device;

    public DevicePoller() {
        this.device = new Device();
    }

    public DevicePoller(Device device) {
        this.device = device;
    }

    @Override
    public void run() {

        NetworkInterfaceDAO interfaces = device.getNetworkInterfaces();
        Set<String> interfaceKeys = interfaces.keySet();

        // run forever
        try {

            while (true) {

                // poll each interface every interval seconds
                for(String interfaceKey : interfaceKeys) {

                    NetworkInterface networkInterface = interfaces.get(interfaceKey);

                    //SNMPAgent snmpAgent = new SNMPAgent("127.0.0.2", SNMPVersion.v2c, "public", ".1.3.6.1.2.1.2.2.1.16.1", 161, 1000);
                    SNMPVersion snmpVersion;

                    switch(device.getSnmpCommunity()) {
                        case "v1":
                            snmpVersion = SNMPVersion.v1;
                            break;
                        case "v2c":
                            snmpVersion = SNMPVersion.v2c;
                            break;
                        case "3":
                            snmpVersion = SNMPVersion.v3;
                            break;
                        default:
                            snmpVersion = SNMPVersion.v2c;
                    }

                    SNMPAgent snmpAgentIn = new SNMPAgent(device.getIp(), snmpVersion, device.getSnmpCommunity(), networkInterface.getInOid(), device.getPort(), device.getTimeout());
                    SNMPAgent snmpAgentOut = new SNMPAgent(device.getIp(), snmpVersion, device.getSnmpCommunity(), networkInterface.getOutOid(), device.getPort(), device.getTimeout());

                    // write the values back to disk
                    DataDAO dataDAO = new DataDAO();
                    dataDAO.add(device, networkInterface, networkInterface.getInOid(), snmpAgentIn.getOidValue());
                    dataDAO.add(device, networkInterface, networkInterface.getOutOid(), snmpAgentOut.getOidValue());

                }

                Thread.sleep(device.getInterval()*1000);
            }

        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
