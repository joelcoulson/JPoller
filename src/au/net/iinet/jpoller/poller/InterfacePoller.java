package au.net.iinet.jpoller.poller;

import au.net.iinet.jpoller.filestore.DataDAO;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InterfacePoller implements Runnable {

    private Device device;

    public InterfacePoller() {
        this.device = new Device();
    }

    public InterfacePoller(Device device) {
        this.device = device;
    }

    public void poll() {

        NetworkInterfaceDAO interfaces = device.getNetworkInterfaces();
        Set<String> interfaceKeys = interfaces.keySet();

        // poll each interface every interval seconds
        for(String interfaceKey : interfaceKeys) {

            NetworkInterface networkInterface = interfaces.get(interfaceKey);
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

            // write the values to disk
            DataDAO dataDAO = new DataDAO();
            dataDAO.add(device, networkInterface, networkInterface.getInOid(), snmpAgentIn.getOidValue());
            dataDAO.add(device, networkInterface, networkInterface.getOutOid(), snmpAgentOut.getOidValue());

        }

    }

    @Override
    public void run() {
        poll();
    }
}
