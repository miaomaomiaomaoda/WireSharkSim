package ui;

import main.Main;
import packet.MyPacket;
import packet.PacketList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author R.Q.
 * description:以数据表格展示捕获的包
 */
public class PacketTable {
    private Main parent;
    private PacketList packetList = new PacketList();
    private String filter = null;
    private DefaultTableModel tableModel;
    private final String[] tableColumns = new String[]{"编号", "时间序列", "源地址", "目标地址", "协议", "长度"};

    public PacketTable(Main parent) {
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
                                this.parent.analyzingPacket(packetList.getPacket(rowIndex).getPacket());
                            }
                        }
                    });
        }
    }

    /**
     * function:向表格中追加一行新包
     *
     * @param myPacket 包
     */
    public void addTable(MyPacket myPacket) {
        if (PacketList.isNotBeFiltered(myPacket, filter)) {
            tableModel.addRow(parseRow(myPacket));
            packetList.addPacket(myPacket);
        }
    }

    /**
     * function:更新数据表格内容（整个更新）
     *
     * @param packetList 包列表
     */
    private void updateModel(PacketList packetList) {
        Object[][] rows = packetListToObjectArray(packetList);
        tableModel.setDataVector(rows, tableColumns);
    }

    /**
     * function:将包列表转换为二维对象列表
     *
     * @param packetList 包列表
     * @return 二维列表对象
     */
    private Object[][] packetListToObjectArray(PacketList packetList) {
        Object[][] rows = new Object[packetList.size()][tableColumns.length];
        for (int i = 0; i < rows.length; i++) {
            MyPacket myPacket = packetList.getPacket(i);
            rows[i] = parseRow(myPacket);
        }
        return rows;
    }

    /**
     * function:根据表格结构将包转化为一维数组
     *
     * @param myPacket 包
     * @return 一维对象数组
     */
    private Object[] parseRow(MyPacket myPacket) {
        return new Object[]{myPacket.getOrder(), myPacket.getTimestamp(), myPacket.getSrcAddress(), myPacket.getDestAddress(), myPacket.getPacketType().name(), myPacket.getLength()};
    }

    /**
     * function:设置过滤器，从包类中重新拉取数据
     *
     * @param filter 过滤条件
     */
    public void setFilter(String filter, PacketList allPackets) {
        if (!filter.isEmpty()) {
            this.filter = filter;
            this.packetList = allPackets.getFilteredPackets(filter);
            updateModel(this.packetList);
        } else {
            this.packetList = allPackets;
            updateModel(this.packetList);
        }
    }

    /**
     * function:装载文件
     *
     * @param packetList 包列表
     */
    public void loadFilePacket(PacketList packetList) {
        this.filter = null;
        this.packetList = packetList;
        updateModel(this.packetList);
    }

    /**
     * function:重置数据表格
     */
    public void reset() {
        this.filter = null;
        this.packetList = new PacketList();
        updateModel(this.packetList);
    }
}
