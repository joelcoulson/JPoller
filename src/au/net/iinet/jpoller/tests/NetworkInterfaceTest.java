package au.net.iinet.jpoller.tests;

import au.net.iinet.jpoller.poller.NetworkInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class NetworkInterfaceTest {

    public static void main(String[] args) {

        NetworkInterface networkInterface = new NetworkInterface();

        // set some data points
        try {
            for (int i = 1; i <= 10; i++) {
                networkInterface.getInDataPoints().put(new Date(), (long) i);
                networkInterface.getOutDataPoints().put(new Date(), (long)(i+10));
                Thread.sleep(1000);
            }
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }

        // get some data points
        Set<Date> inDates = networkInterface.getInDataPoints().keySet();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        for(Date date : inDates) {
            System.out.println("In(" + dateFormat.format(date) + "): " + networkInterface.getInDataPoints().get(date));
            System.out.println("Out(" + dateFormat.format(date) + "): " + networkInterface.getOutDataPoints().get(date) + "\n");
        }
    }

}
