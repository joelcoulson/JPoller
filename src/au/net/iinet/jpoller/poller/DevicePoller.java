package au.net.iinet.jpoller.poller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DevicePoller implements Runnable{

    private Device device;

    public DevicePoller() {}

    public DevicePoller(Device device) {
        this.device = device;
    }

    public void run() {

        try {

            Runnable task = new InterfacePoller(device);
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            while(true) {

                // poll the device's interfaces
                executorService.submit(task);

                // pause before polling again
                Thread.sleep(device.getInterval()*1000);

            }

        }catch(InterruptedException ie){
            ie.printStackTrace();
        }

    }

}
