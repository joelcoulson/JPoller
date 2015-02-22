package au.net.iinet.jpoller.tests;

import au.net.iinet.jpoller.poller.SNMPAgent;
import au.net.iinet.jpoller.poller.SNMPVersion;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SNMPAgentTest {

    public static void main(String[] args) {

        final ExecutorService service;
        final Future<Long> task;

        service = Executors.newFixedThreadPool(1);
        task = service.submit(new SNMPAgent("127.0.0.2", SNMPVersion.v2c, "public", ".1.3.6.1.2.1.2.2.1.16.1", 161, 5000));

        try {

            long val = task.get();
            System.out.println("Interface counter: " + val);

        } catch(final InterruptedException ex) {
            ex.printStackTrace();
        } catch(final ExecutionException ex) {
            ex.printStackTrace();
        }

        service.shutdownNow();
    }

}
