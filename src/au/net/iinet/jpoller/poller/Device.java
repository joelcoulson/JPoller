package au.net.iinet.jpoller.poller;

import java.io.Serializable;

public class Device implements Serializable {

    private String name;
    private String ip;
    private String snmpCommunity;
    private String snmpVersion;
    private int port;
    private int interval;
    private int timeout;
    private NetworkInterfaceDAO networkInterfaces;

    public Device() {
        this.name = "";
        this.ip = "";
        this.snmpCommunity = "public";
        this.port = 161;
        this.interval = 60;
        this.timeout = 1000;
        this.networkInterfaces = new NetworkInterfaceDAO();
    }

    public Device(String name, String ip, String snmpCommunity, int pollerInterval, NetworkInterfaceDAO networkInterfaceDatabase) {

        this.name = name;
        this.ip = ip;
        this.snmpCommunity = snmpCommunity;
        this.networkInterfaces = networkInterfaceDatabase;

        if(pollerInterval > 1) {
            this.interval = pollerInterval;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public NetworkInterfaceDAO getNetworkInterfaces() {
        return networkInterfaces;
    }

}
