package au.net.iinet.jpoller.configuration;

public class NetworkInterface {

    private String name;
    private String oid;
    private int speed;

    public NetworkInterface() {
        this.name = null;
        this.oid = null;
        this.speed = 100;
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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
