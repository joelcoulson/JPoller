package au.net.iinet.jpoller.configuration;

import java.util.HashMap;

public class Device {

    private String name;
    private String ip;
    private String snmpCommunity;
    private String snmpVersion;
    private int pollerInterval;
    private HashMap<String, NetworkInterface> networkInterfaces;

    public Device() {
        this.name = "";
        this.ip = "";
        this.snmpCommunity = "public";
        this.pollerInterval = 60;
        this.networkInterfaces = new HashMap<>();
    }

    public Device(String name, String ip, String snmpCommunity, int pollerInterval, HashMap<String, NetworkInterface> networkInterfaces) {

        this.name = name;
        this.ip = ip;
        this.snmpCommunity = snmpCommunity;
        this.networkInterfaces = networkInterfaces;

        if(pollerInterval > 1) {
            this.pollerInterval = pollerInterval;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSnmpCommunity() {
        return snmpCommunity;
    }

    public void setSnmpCommunity(String snmpCommunity) {
        this.snmpCommunity = snmpCommunity;
    }

    public String getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(String snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public int getPollerInterval() {
        return pollerInterval;
    }

    public void setPollerInterval(int pollerInterval) {
        this.pollerInterval = pollerInterval;
    }

    public HashMap<String, NetworkInterface> getNetworkInterfaces() {
        return networkInterfaces;
    }

    public void addInterface(NetworkInterface networkInterface) {
        this.networkInterfaces.put(networkInterface.getName(), networkInterface);
    }
}
