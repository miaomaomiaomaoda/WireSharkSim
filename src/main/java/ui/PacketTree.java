package ui;

import main.Main;
import org.pcap4j.packet.Packet;
import packet.MyPacket;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Objects;

/**
 * @author R.Q.
 * description:以树结构展示分析包协议
 */
public class PacketTree {
    private final Main parent;
    private final Packet packet;
    DefaultMutableTreeNode root;
    DefaultTreeModel treeModel;

    public PacketTree(Main parent, Packet packet) {
        this.parent = parent;
        this.packet = packet;
        if (this.packet != null) {
            renderBytes();
            renderTree(packet);
        }
    }

    private void renderTree(Packet packet) {
        treeModel = (DefaultTreeModel) parent.tree.getModel();
        root = (DefaultMutableTreeNode) treeModel.getRoot();
        root.removeAllChildren();
        String packetInfo = packet.toString();
        String[] lines = packetInfo.split("\\r?\\n");
        DefaultMutableTreeNode titleNode = null;
        for (String line : lines) {
            if (line.charAt(0) == '[') {
                titleNode = new DefaultMutableTreeNode(line);
                root.add(titleNode);
            } else {
                DefaultMutableTreeNode contentNode = new DefaultMutableTreeNode(line);
                Objects.requireNonNull(titleNode).add(contentNode);
            }
        }
        treeModel.reload();
    }

    private void renderBytes() {
        parent.packetBytes.setText("16进制包数据:\n" + MyPacket.byteArrayToHexString(packet.getRawData()));
    }
}
