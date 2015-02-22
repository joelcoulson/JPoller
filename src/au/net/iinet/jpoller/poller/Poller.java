package au.net.iinet.jpoller.poller;

import au.net.iinet.jpoller.configuration.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Poller {

    private Configuration configuration;

    public Poller() {
        configuration = new Configuration();
    }

    private void startPolling() {

        // start a separate polling process for each device
        DeviceDatabase devices = configuration.getDevices();
        Set<String> keys = devices.keySet();

        for(String key : keys) {
            pollDevice(devices.get(key));
        }
    }

    private void pollDevice(Device device) {

        // use the executer service here

    }

}
