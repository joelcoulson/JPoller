package au.net.iinet.jpoller.filestore;

import au.net.iinet.jpoller.configuration.Configuration;
import au.net.iinet.jpoller.poller.Device;
import au.net.iinet.jpoller.poller.NetworkInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataDAO {

    private Configuration configuration;

    public DataDAO() {
        this.configuration = new Configuration();
    }

    public void add(Device device, NetworkInterface networkInterface, String oid, long value) {

        // the file to be written to has the format of device_interface_oid.csv
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(configuration.getDataDirectory()+"/"+device.getName()+"_"+networkInterface.getName()+"_"+oid + ".csv"), true));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
            bw.write(simpleDateFormat.format(new Date()) + "," + value + "\n");
            bw.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
