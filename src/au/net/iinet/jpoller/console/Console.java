package au.net.iinet.jpoller.console;

import au.net.iinet.jpoller.poller.DevicePoller;

public class Console {

    public static void main(String[] args) {
        DevicePoller poller = new DevicePoller();
        poller.startPolling();
    }

}
