package au.net.iinet.jpoller.console;

import au.net.iinet.jpoller.poller.Poller;

public class Console {

    public static void main(String[] args) {
        Poller poller = new Poller();
        poller.startPolling();
    }

}
