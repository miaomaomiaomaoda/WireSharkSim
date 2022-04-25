import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.util.List;

/**
 * @author R.Q.
 */
public class CapturePacket {
    public static void main(String[] args) throws PcapNativeException, NotOpenException, InterruptedException, IOException {
        List<PcapNetworkInterface> allDev = Pcaps.findAllDevs();
        System.out.println("allDev.size() = " + allDev.size());
        for (PcapNetworkInterface networkInterface : allDev) {
            System.out.println("networkInterface.toString() = " + networkInterface.toString());
        }
        capture(allDev.get(0));
    }

    public static void capture(PcapNetworkInterface nif) throws PcapNativeException, NotOpenException, InterruptedException, IOException {
        nif = new NifSelector().selectNetworkInterface();
        final PcapHandle handle = nif.openLive(65536,PcapNetworkInterface.PromiscuousMode.PROMISCUOUS,10);
        PacketListener listener = packet -> {
            System.out.println("handle.getTimestamp() = " + handle.getTimestamp());
            System.out.println("packet.toString() = " + packet.toString());
        };
        handle.loop(-1,listener);
        /*
        Packet packet = null;
        while(true) {
            packet = handle.getNextPacket();
            if(packet!=null&&packet.contains(TcpPacket.class)){
                break;
            }
        }

        System.out.println("handle.getTimestamp() = " + handle.getTimestamp());
        System.out.println("packet.toString() = " + packet);
        System.out.println("\nAnalysis");
        Packet packet1 = packet;
        while(packet1!=null){
            System.out.println("handle.getTimestamp() = " + handle.getTimestamp());
            System.out.println("packet1.toString() = " + packet1.toString());
            System.out.println("packet1.getClass() = " + packet1.getClass());
            packet1 = packet1.getPayload();
        }
        */
        /*
        System.out.println("handle.getTimestamp() = " + handle.getTimestamp());
        EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
        System.out.println("ethernetPacket.toString() = " + ethernetPacket.getHeader().toString());

        System.out.println("handle.getTimestamp() = " + handle.getTimestamp());
        IpPacket ipPacket = packet.get(IpPacket.class);
        System.out.println("ipPacket.toString() = " + ipPacket.getHeader().toString());

        System.out.println("handle.getTimestamp() = " + handle.getTimestamp());
        if (packet.contains(TcpPacket.class)){
            TcpPacket tcpPacket = packet.get(TcpPacket.class);
            System.out.println("tcpPacket.toString() = " + tcpPacket.getHeader().toString());
        }

        if (packet.contains(UdpPacket.class)){
            UdpPacket udpPacket = packet.get(UdpPacket.class);
            System.out.println("udpPacket.toString() = " + udpPacket.getHeader().toString());
            System.out.println("udpPacket.getRawData() = " + udpPacket.getRawData());
            System.out.println("udpPacket.getPayload().toString() = " + udpPacket.getPayload().toString());
        }

        System.out.println("handle.getTimestamp() = " + handle.getTimestamp());
        Packet packet1 = packet.getPayload();
        System.out.println("packet1.toString() = " + packet1.toString());
         */
    }
}
