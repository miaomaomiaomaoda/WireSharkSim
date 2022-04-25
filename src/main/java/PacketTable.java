import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author R.Q.
 */
public class PacketTable {
    private MainUI parent;
    private PacketList packetList = new PacketList();
    private String filter = null;
    private DefaultTableModel tableModel;
    private final String[] tableColumns = new String[]{"编号", "时间序列", "源地址", "目标地址", "协议", "长度"};

    public PacketTable(MainUI parent) {
        if (parent != null) {
            this.parent = parent;
            //初始化表格
            tableModel = (DefaultTableModel) this.parent.table.getModel();
            updateModel(this.packetList);
            this.parent.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.parent.table.getSelectionModel()
                    .addListSelectionListener((e) -> {
                        if (packetList.size() > 0) {
                            int rowIndex = this.parent.table.getSelectedRow();
                            if (rowIndex == -1) {
                                return;
                            }
                            if (packetList.size() > rowIndex) {
                                this.parent.analyzingPacket(packetList.getPacket(rowIndex));
                            }
                        }
                    });
        }
    }

    /**
     * function:向表格中追加一行新包
     *
     * @param packet 包
     */
    public void addTable(Packet packet) {
        if (!PacketList.isBeFiltered(packet, filter)) {
            tableModel.addRow(parseRow(PacketInfo.parsePacket(packet)));
            packetList.addPacket(packet);
        }
    }

    /*
    //根据过滤规则更新过滤包列表和表格数据模型
    public void filterPacketList(String filter) {
        this.filter = filter;
        this.packetList = packetList.getFilteredPackets(filter);

        if (filter.isEmpty()) {
            packetList = packetList;
        } else {
            //packetList = packetList.filter(filter);
        }
        if (packetList!=null) {
            updateModel(packetList);
        }
    }*/

    /**
     * function:更新表格内容，整个更新
     * @param packetList 包
     */
    private void updateModel(PacketList packetList) {
        Object[][] rows = packetListToObjectArray(packetList);
        tableModel.setDataVector(rows, tableColumns);
    }

    /**
     * function:将包列表转换为二维对象列表
     * @param packetList 包列表
     * @return 二维列表对象
     */
    private Object[][] packetListToObjectArray(PacketList packetList) {
        Object[][] rows = new Object[packetList.size()][tableColumns.length];
        for (int i = 0; i < rows.length; i++) {
            Packet packet = packetList.getPacket(i);
            rows[i] = parseRow(PacketInfo.parsePacket(packet));
        }
        return rows;
    }

    /**
     * function:根据表格结构将包转化为一维数组
     * @param packetInfo 包信息
     * @return 一维数组对象
     */
    private Object[] parseRow(PacketInfo packetInfo) {
        MainUI.packetOrder++;
        return new Object[]{MainUI.packetOrder, packetInfo.getTimestamp(), packetInfo.getSrcAddress(), packetInfo.getDestAddress(), packetInfo.getPacketType().name(), packetInfo.getLength()};
    }

    /**
     * function:设置过滤器，从包类中重新拉取数据
     * @param filter 过滤条件
     */
    public void setFilter(String filter, PacketList allPackets) {
        this.filter = filter;
        this.packetList = allPackets.getFilteredPackets(filter);
        updateModel(this.packetList);
    }

    public void loadFilePacket(PacketList packetList){
        this.packetList = packetList;
        updateModel(packetList);
    }

    public void reset(){
        this.packetList = new PacketList();
        updateModel(this.packetList);
    }
}
