package au.net.iinet.jpoller.poller;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

public class DatapointDatabase extends LinkedHashMap<Date, Long> implements Serializable {

    public DatapointDatabase() {}

}
