package au.net.iinet.jpoller.poller;

public class NetworkInterface {

    private String name;
    private String inoid;
    private String outoid;
    private int speed;
    private DatapointDatabase inDataPoints;
    private DatapointDatabase outDataPoints;

    public NetworkInterface() {
        this.name = null;
        this.inoid = null;
        this.outoid = null;
        this.speed = 100;
        this.inDataPoints = new DatapointDatabase();
        this.outDataPoints = new DatapointDatabase();
    }

    public NetworkInterface(String name, int speed) {

        this.name = name;

        if(speed > 0) {
            this.speed = speed;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInOid() {
        return inoid;
    }

    public void setInOid(String oid) {
        this.inoid = oid;
    }

    public String getOutOid() {
        return outoid;
    }

    public void setOutOid(String oid) {
        this.outoid = oid;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public DatapointDatabase getInDataPoints() {
        return this.inDataPoints;
    }

    public DatapointDatabase getOutDataPoints() {
        return this.outDataPoints;
    }
}
