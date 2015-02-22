package au.net.iinet.jpoller.poller;

// Many thanks to Jinesh Mathew for the excellent example on SNMP4J which has been used in part to form this class
// http://www.jineshmathew.com/2012/11/how-to-get-started-with-snmp4j.html

import org.snmp4j.*;
import org.snmp4j.smi.*;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Callable;

public class SNMPAgent implements Callable<Long> {

    private String host;
    private SNMPVersion version;
    private String community;
    private String oid;
    private int port;
    private int timeout;

    public SNMPAgent() {
        host = "";
        version = SNMPVersion.v2c;
        community = "";
        oid = "";
        port = 161;
        timeout = 500;
    }

    public SNMPAgent(String host, SNMPVersion version, String community, String oid, int port, int timeout) {
        this.host = host;
        this.version = version;
        this.community = community;
        this.oid = oid;
        this.port = port;
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public SNMPVersion getVersion() {
        return version;
    }

    public void setVersion(SNMPVersion snmpVersion) {
        this.version = snmpVersion;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public long getOidValue() {

        try {

            Snmp snmp4j = new Snmp(new DefaultUdpTransportMapping());
            snmp4j.listen();
            Address add = new UdpAddress(this.host + "/" + this.port);

            CommunityTarget target = new CommunityTarget();
            target.setAddress(add);
            target.setTimeout(this.timeout);
            target.setRetries(3);
            target.setCommunity(new OctetString(this.community));

            switch(version) {
                case v1:
                    target.setVersion(SnmpConstants.version1);
                    break;
                case v2c:
                    target.setVersion(SnmpConstants.version2c);
                    break;
                case v3:
                    target.setVersion(SnmpConstants.version3);
                    break;
                default:
                    target.setVersion(SnmpConstants.version2c);
            }

            PDU request = new PDU();
            request.setType(PDU.GET);
            OID oid = new OID(this.oid);
            request.add(new VariableBinding(oid));

            PDU responsePDU = null;
            ResponseEvent responseEvent;
            responseEvent = snmp4j.send(request, target);

            if (responseEvent != null)
            {
                responsePDU = responseEvent.getResponse();
                if ( responsePDU != null)
                {

                    Vector<? extends VariableBinding> tmpv = responsePDU.getVariableBindings();

                    if(tmpv != null)
                    {
                        for(int k = 0; k <tmpv.size();k++)
                        {
                            VariableBinding vb = (VariableBinding) tmpv.get(k);
                            String output = null;
                            if ( vb.isException())
                            {

                                String errorstring = vb.getVariable().getSyntaxString();
                                System.out.println("Error: " + errorstring);
                            }
                            else
                            {
                                String sOid = vb.getOid().toString();
                                Variable var = vb.getVariable();
                                Counter32 counter = new Counter32(var.toLong());
                                return counter.toLong();
                            }

                        }

                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Long call() {
        return getOidValue();
    }

    public static void main(String[] args) {

        try {

            Snmp snmp4j = new Snmp(new DefaultUdpTransportMapping());
            snmp4j.listen();
            Address add = new UdpAddress("127.0.0.1" + "/" + "161");

            CommunityTarget target = new CommunityTarget();
            target.setAddress(add);
            target.setTimeout(500);

            target.setRetries(3);
            target.setCommunity(new OctetString("public"));
            target.setVersion(SnmpConstants.version2c);

            PDU request = new PDU();
            request.setType(PDU.GET);
            OID oid = new OID(".1.3.6.1.2.1.2.2.1.16.1");
            request.add(new VariableBinding(oid));

            PDU responsePDU = null;
            ResponseEvent responseEvent;
            responseEvent = snmp4j.send(request, target);

            if (responseEvent != null)
            {
                responsePDU = responseEvent.getResponse();
                if ( responsePDU != null)
                {

                    Vector<? extends VariableBinding> tmpv = responsePDU.getVariableBindings();

                    if(tmpv != null)
                    {
                        for(int k=0; k <tmpv.size();k++)
                        {
                            VariableBinding vb = (VariableBinding) tmpv.get(k);
                            String output = null;
                            if ( vb.isException())
                            {

                                String errorstring = vb.getVariable().getSyntaxString();
                                System.out.println("Error:"+errorstring);
                            }
                            else
                            {
                                String sOid = vb.getOid().toString();
                                Variable var = vb.getVariable();
                                Counter32 counter = new Counter32(var.toLong());
                                String sVar =  counter.toString();

                                System.out.println("success:"+sVar);
                            }

                        }

                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
