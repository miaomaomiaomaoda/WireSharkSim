package packet;

import main.Main;
import org.pcap4j.packet.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * @author R.Q.
 * description:对packet的封装，包含了packet和packet的一些简要内容
 */
public class MyPacket implements Serializable {
    private final int order;
    private final String timestamp;
    private String srcAddress;
    private String destAddress;
    private PacketType packetType;
    private int length;
    Packet packet;

    public MyPacket(Packet packet,Timestamp timestamp){
        this.packet = packet;
        this.order = Main.packetOrder;
        Main.packetOrder++;
        parsePacket(packet);
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        this.timestamp = dateTimeFormatter.format(timestamp.toLocalDateTime());
    }

    /**
     * function:加载packet信息
     * @param packet 包
     */
    public void parsePacket(Packet packet){
        this.length = packet.length();
        if (packet.contains(IpPacket.class)){
            //1.IP包
            IpPacket ipPacket = packet.get(IpPacket.class);
            this.srcAddress = ipPacket.getHeader().getSrcAddr().toString();
            this.destAddress = ipPacket.getHeader().getDstAddr().toString();
            this.packetType = PacketType.IP;
            if(ipPacket.contains(TcpPacket.class)){
                this.packetType = PacketType.TCP;
            }else if(ipPacket.contains(UdpPacket.class)){
                this.packetType = PacketType.UDP;
            }
            if(ipPacket.contains(DnsPacket.class)){
                this.packetType = PacketType.DNS;
            }
            if(ipPacket.contains(IcmpV4CommonPacket.class)||ipPacket.contains(IcmpV6CommonPacket.class)){
                this.packetType = PacketType.ICMP;
            }
        }
        else if(packet.contains(ArpPacket.class)){
            ArpPacket arpPacket = packet.get(ArpPacket.class);
            this.srcAddress = arpPacket.getHeader().getSrcProtocolAddr().toString();
            this.destAddress = arpPacket.getHeader().getDstProtocolAddr().toString();
            this.packetType = PacketType.ARP;
        }
        this.ipFormat();
    }

    /**
     * function:字节数组转换为16进制字符串
     * @param bytes 字节
     * @return 返回16进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        int i=0;
        for (byte b : bytes) {
            builder.append(String.format("%02x", b & 0xff));
            i++;
            switch (i) {
                case 8:
                    builder.append("   ");
                    break;
                case 16:
                    i=0;
                    builder.append("\n");
                    break;
                default:
                    builder.append(" ");
                    break;
            }
        }
        return builder.toString();
    }

    /**
     * function:删除ip前的/
     */
    private void ipFormat(){
        this.srcAddress =  this.srcAddress.substring(1);
        this.destAddress = this.destAddress.substring(1);
    }

    public int getOrder() {
        return order;
    }
    public Packet getPacket() {
        return packet;
    }
    public String getSrcAddress() {return srcAddress;}
    public String getDestAddress() {return destAddress;}
    public PacketType getPacketType() {return packetType;}
    public String getTimestamp() {return timestamp;}
    public int getLength() {
        return this.length;
    }
    public enum PacketType {IP,UDP,TCP,ARP,ICMP,DNS}
}
