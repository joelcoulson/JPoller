package au.net.iinet.jpoller.tests;

import au.net.iinet.jpoller.configuration.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ConfigurationTest {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();

        System.out.println("Data directory: " + configuration.getDataDirectory());
        ArrayList<Device> devices = configuration.getDevices();

        for(Device device : devices) {
            System.out.println("-------------------------------------");
            showDevice(device);
        }

    }

    public static void showDevice(Device device) {
        System.out.println("Name: " + device.getName() + "\n");
        System.out.println("IP: " + device.getIp());
        System.out.println("SNMP Community: " + device.getSnmpCommunity());
        System.out.println("SNMP Version: " + device.getSnmpVersion() + "\n");

        // get interfaces
        HashMap<String, NetworkInterface> networkInterfaces = device.getNetworkInterfaces();
        Set<String> keys = networkInterfaces.keySet();

        for(String key : keys) {
            System.out.println("Interface Name: " + networkInterfaces.get(key).getName());
            System.out.println("Interface OID: " + networkInterfaces.get(key).getOid());
            System.out.println("Interface Speed: " + networkInterfaces.get(key).getSpeed() + "\n");
        }
    }

}
