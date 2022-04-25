import org.pcap4j.packet.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author R.Q.
 * description：包装类，用于提取Packet里面的部分信息
 */
public class PacketInfo {
    private String timestamp;
    private String srcAddress;
    private String destAddress;
    private PacketType packetType;
    private int length;

    /**
     * function:构造函数
     */
    public PacketInfo(){
    }

    /**
     * function:包装packet,返回packetinfo对象
     * @param packet 包
     * @return packetInfo包装类
     */
    public static PacketInfo parsePacket(Packet packet){
        PacketInfo packetInfo = new PacketInfo();
        packetInfo.length = packet.length();
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        packetInfo.timestamp = dateTimeFormatter.format(LocalDateTime.now());

        if (packet.contains(IpPacket.class)){
            //1.IP包
            IpPacket ipPacket = packet.get(IpPacket.class);
            packetInfo.srcAddress = ipPacket.getHeader().getSrcAddr().toString();
            packetInfo.destAddress = ipPacket.getHeader().getDstAddr().toString();
            if(ipPacket.contains(TcpPacket.class)){
                packetInfo.packetType = PacketType.TCP;
            }else if(ipPacket.contains(UdpPacket.class)){
                packetInfo.packetType = PacketType.UDP;
            }
            if(ipPacket.contains(DnsPacket.class)){
                packetInfo.packetType = PacketType.DNS;
            }
            if(ipPacket.contains(IcmpV4CommonPacket.class)||ipPacket.contains(IcmpV6CommonPacket.class)){
                packetInfo.packetType = PacketType.ICMP;
            }
        }
        else if(packet.contains(ArpPacket.class)){
            ArpPacket arpPacket = packet.get(ArpPacket.class);
            packetInfo.srcAddress = arpPacket.getHeader().getSrcProtocolAddr().toString();
            packetInfo.destAddress = arpPacket.getHeader().getDstProtocolAddr().toString();
            packetInfo.packetType = PacketType.ARP;
        }
        packetInfo.ipFormat();
        return packetInfo;
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
    private void ipFormat(){
        this.srcAddress =  this.srcAddress.substring(1);
        this.destAddress = this.destAddress.substring(1);
    }
    public String getSrcAddress() {return srcAddress;}
    public String getDestAddress() {return destAddress;}
    public PacketType getPacketType() {return packetType;}
    public String getTimestamp() {return timestamp;}
    public int getLength() {
        return this.length;
    }
    public enum PacketType {UDP,TCP,ARP,ICMP,DNS}
}
