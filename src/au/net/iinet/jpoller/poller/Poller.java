package au.net.iinet.jpoller.poller;

import au.net.iinet.jpoller.configuration.Configuration;

import java.util.ArrayList;
import java.util.Set;

public class Poller {

    private Configuration configuration;

    public Poller() {
        configuration = new Configuration();
    }

    public void startPolling() {

        // start a separate polling process for each device
        DeviceDAO devices = configuration.getDevices();
        Set<String> deviceKeys = devices.keySet();
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for(String deviceKey : deviceKeys) {

            threads.add(new Thread(new DevicePoller(devices.get(deviceKey))));
            threads.get(threads.size()-1).start();

        }
    }

}
