package au.net.iinet.jpoller.configuration;

// kudos to MyKong for the info found at http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import au.net.iinet.jpoller.poller.Device;
import au.net.iinet.jpoller.poller.DeviceDAO;
import au.net.iinet.jpoller.poller.NetworkInterface;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Configuration {

    private final File configFile = new File("./config/config.xml");
    private String dataDirectory;
    private String timezone;
    private DeviceDAO devices;

    public Configuration() {
        this.dataDirectory = "";
        this.timezone = "UTC";
        this.devices = new DeviceDAO();
        parseConfiguration();
    }

    public String getDataDirectory() {
        return this.dataDirectory;
    }

    public String getTimezone() {
        return this.timezone;
    }

    private void parseConfiguration() {

        try {

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(configFile);

            if (doc.hasChildNodes()) {

                // get the top layer of child nodes
                NodeList topLevelNodes = doc.getChildNodes().item(0).getChildNodes();

                for(int i = 0; i < topLevelNodes.getLength(); i++) {

                    // get the data directory
                    if (topLevelNodes.item(i).getNodeName().equals("datafiles")) {
                        this.dataDirectory = topLevelNodes.item(i).getChildNodes().item(0).getNodeValue();
                    }

                    // get the timezone
                    if (topLevelNodes.item(i).getNodeName().equals("timezone")) {
                        this.timezone = topLevelNodes.item(i).getChildNodes().item(0).getNodeValue();
                    }

                    // parse the device
                    if (topLevelNodes.item(i).getNodeName().equals("device")) {
                        Device device = parseDevice(topLevelNodes.item(i));
                        devices.put(device.getName(), device);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public DeviceDAO getDevices() {
        return this.devices;
    }

    private Device parseDevice(Node node) {

        NodeList nodeList = node.getChildNodes();
        Device device = new Device();

        // get the name
        device.setName(node.getAttributes().getNamedItem("name").getNodeValue());

        for(int i = 0; i < nodeList.getLength(); i++) {

            Node nNode = nodeList.item(i);

            // get the ip
            if(nNode.getNodeName().equals("ip")) {
                device.setIp(nNode.getChildNodes().item(0).getNodeValue());
            }

            // get the poller port
            if(nNode.getNodeName().equals("port")) {
                device.setPort(Integer.parseInt(nNode.getChildNodes().item(0).getNodeValue()));
            }

            // get the poller interval
            if(nNode.getNodeName().equals("pollerinterval")) {
                device.setInterval(Integer.parseInt(nNode.getChildNodes().item(0).getNodeValue()));
            }

            // get the poller timeout
            if(nNode.getNodeName().equals("timeout")) {
                device.setTimeout(Integer.parseInt(nNode.getChildNodes().item(0).getNodeValue()));
            }

            // get the snmp values
            if(nNode.getNodeName().equals("snmp")) {

                NodeList iNodeList = nNode.getChildNodes();

                for(int j = 0; j < iNodeList.getLength(); j++) {

                    Node iNode = iNodeList.item(j);
                    NetworkInterface networkInterface = new NetworkInterface();

                    if(iNode.getNodeName().equals("version")) {
                        device.setSnmpVersion(iNode.getChildNodes().item(0).getNodeValue());
                    }

                    if(iNode.getNodeName().equals("community")) {
                        device.setSnmpCommunity(iNode.getChildNodes().item(0).getNodeValue());
                    }
                }
            }

            // get the interfaces
            if(nNode.getNodeName().equals("interface")) {

                NodeList iNodeList = nNode.getChildNodes();
                NetworkInterface networkInterface = new NetworkInterface();

                for(int j = 0; j < iNodeList.getLength(); j++) {

                    Node iNode = iNodeList.item(j);

                    if(iNode.getNodeName().equals("name")) {
                        networkInterface.setName(iNode.getChildNodes().item(0).getNodeValue());
                    }

                    if(iNode.getNodeName().equals("inoid")) {
                        networkInterface.setInOid(iNode.getChildNodes().item(0).getNodeValue());
                    }

                    if(iNode.getNodeName().equals("outoid")) {
                        networkInterface.setOutOid(iNode.getChildNodes().item(0).getNodeValue());
                    }

                    if(iNode.getNodeName().equals("speed")) {
                        networkInterface.setSpeed(Integer.parseInt(iNode.getChildNodes().item(0).getNodeValue()));
                    }

                }

                device.getNetworkInterfaces().put(networkInterface.getName(), networkInterface);
            }
        }

        return device;
    }
}
