import org.pcap4j.packet.Packet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author R.Q.
 * description:数据包列表，表示一系列的数据包和对包的操作
 */
public class PacketList implements Serializable {
    private final ArrayList<Packet> packetList;

    public PacketList() {
        this.packetList = new ArrayList<>();
    }

    public PacketList(ArrayList<Packet> packetList){ this.packetList = packetList; }

    public void addPacket(Packet packet) {
        if (packet == null) {
            throw new IllegalArgumentException("试图向列表中加入空包！");
        }
        packetList.add(packet);
    }

    public int size(){
        return packetList.size();
    }

    public Packet getPacket(int index){
        return packetList.get(index);
    }

    public PacketList getFilteredPackets(String filter) {
        ArrayList<Packet> filteredPacket = packetList;
        if(filter==null){
            return new PacketList(filteredPacket);
        }

        for (int i = 0; i < packetList.size(); i++) {
            if(PacketList.isBeFiltered(packetList.get(i),filter)){
                filteredPacket.remove(i);
            }
        }
        return new PacketList(filteredPacket);
    }

    /**
     * function:是否被过滤
     * @param packet 包信息
     * @param filter 过滤条件
     * @return 是否被过滤
     */
    public static boolean isBeFiltered(Packet packet,String filter){
        if(filter==null||filter.equals("")){
            return false;
        }

        //测试 eg.filter:src=192.168.1.1
        String ip = filter.substring(filter.indexOf('=')+1);
        return !PacketInfo.parsePacket(packet).getSrcAddress().equals(ip);
    }
}
