package au.net.iinet.jpoller.tests;

import au.net.iinet.jpoller.configuration.*;
import au.net.iinet.jpoller.poller.Device;
import au.net.iinet.jpoller.poller.DeviceDAO;
import au.net.iinet.jpoller.poller.NetworkInterface;

import java.util.HashMap;
import java.util.Set;

public class ConfigurationTest {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();

        System.out.println("Data directory: " + configuration.getDataDirectory());
        System.out.println("Timezone: " + configuration.getTimezone());
        DeviceDAO devices = configuration.getDevices();
        Set<String> keys = devices.keySet();

        for(String key: keys) {
            System.out.println("-------------------------------------");
            showDevice(devices.get(key));
        }

    }

    public static void showDevice(Device device) {
        System.out.println("Name: " + device.getName() + "\n");
        System.out.println("IP: " + device.getIp());
        System.out.println("Port: " + device.getPort());
        System.out.println("Timeout: " + device.getTimeout());
        System.out.println("SNMP Community: " + device.getSnmpCommunity());
        System.out.println("SNMP Version: " + device.getSnmpVersion() + "\n");

        // get interfaces
        HashMap<String, NetworkInterface> networkInterfaces = device.getNetworkInterfaces();
        Set<String> keys = networkInterfaces.keySet();

        for(String key : keys) {
            System.out.println("Interface Name: " + networkInterfaces.get(key).getName());
            System.out.println("Interface In OID: " + networkInterfaces.get(key).getInOid());
            System.out.println("Interface Out OID: " + networkInterfaces.get(key).getOutOid());
            System.out.println("Interface Speed: " + networkInterfaces.get(key).getSpeed() + "\n");
        }
    }

}
