package packet;

import filter.Filter;
import filter.Filters;
import filter.InvalidFilterException;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author R.Q.
 * description:数据包列表，一组数据包数据和对包的操作
 */
public class PacketList implements Serializable {
    private final ArrayList<MyPacket> packetList;
    @Serial
    private static final long serialVersionUID = -5809782578272943999L;

    public PacketList() {
        this.packetList = new ArrayList<>();
    }

    public PacketList(List<MyPacket> packetList){
        this.packetList = (ArrayList<MyPacket>) packetList;
    }

    /**
     * function:向列表中加入包
     * @param myPacket 包
     */
    public void addPacket(MyPacket myPacket) {
        if (myPacket == null) {
            throw new IllegalArgumentException("试图向列表中加入空包！");
        }
        packetList.add(myPacket);
    }

    /**
     * function:获取过滤后的包
     * @param filterString 过滤表达式
     * @return 过滤后的包
     */
    public PacketList getFilteredPackets(String filterString) {
        //包含=的情况
        if (filterString.contains("=")) {
            try {
                Filter filter = Filters.parseFilter(filterString);
                return new PacketList(packetList.stream()
                        .filter(filter::matches).collect(Collectors.toList()));
            } catch (InvalidFilterException e) {
                JOptionPane.showMessageDialog(null, "错误提示",
                        "过滤器语法错误", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        //模糊匹配
        return new PacketList(packetList.stream().filter(p -> anyFieldContains(p, filterString)).collect(Collectors.toList()));
    }

    /**
     * function:判断包是否被过滤
     * @param myPacket 包
     * @param filterString 过滤条件字符串
     * @return 是否被过滤
     */
    public static boolean isNotBeFiltered(MyPacket myPacket,String filterString){
        if(filterString==null){
            return true;
        }

        if (filterString.contains("=")){
            try{
                Filter filter = Filters.parseFilter(filterString);
                return !filter.matches(myPacket);
            } catch (InvalidFilterException e) {
                e.printStackTrace();
            }
        }else{
            return PacketList.anyFieldContains(myPacket,filterString);
        }
        return true;
    }

    /**
     * function:模糊匹配
     * @param myPacket 包
     * @param filter 匹配字符串
     * @return 是否包含
     */
    private static boolean anyFieldContains(MyPacket myPacket, String filter) {
        return myPacket.getSrcAddress().contains(filter) || myPacket.getDestAddress().contains(filter) || myPacket.getPacketType().name().contains(filter);
    }

    public int size(){
        return packetList.size();
    }

    public MyPacket getPacket(int index){
        return packetList.get(index);
    }
}
