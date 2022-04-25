import org.pcap4j.packet.Packet;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author R.Q.
 */
public class PacketTree {
    private MainUI parent;
    private Packet packet;
    DefaultMutableTreeNode root, child;
    DefaultTreeModel treeModel;

    public PacketTree(MainUI parent, Packet packet) {
        this.parent = parent;
        this.packet = packet;
        if (this.packet != null) {
            //renderTree();
            renderBytes();
        }
    }

    private void renderTree() {
        /*
        treeModel = (DefaultTreeModel) parent.tree.getModel();
        root = (DefaultMutableTreeNode) treeModel.getRoot();
        root.removeAllChildren();
        child = new Branch(packetDetail.frameData).node(); // Frame子节点
        treeModel.insertNodeInto(child, root, 0);
        child = new Branch(packetDetail.ethernetData).node(); // Ethernet II子节点
        treeModel.insertNodeInto(child, root, 0);
        child = new Branch(packetDetail.ipv4Data).node(); //IPV4 子节点
        treeModel.insertNodeInto(child, root, 0);
        child = new Branch(packetDetail.fourthData).node(); //传输层子节点
        treeModel.insertNodeInto(child, root, 0);
        treeModel.reload();

         */
    }

    private void renderBytes() {
        parent.packetBytes.setText("16进制包数据:\n"+PacketInfo.byteArrayToHexString(packet.getRawData()));
    }

    //定义树的分支节点
    class Branch {
        DefaultMutableTreeNode node;

        public Branch(String[] data) {
            node = new DefaultMutableTreeNode(data[0]);
            for (int i = 1; i < data.length; i++) {
                node.add(new DefaultMutableTreeNode(data[i]));
            }
        }

        public DefaultMutableTreeNode node() {// 返回节点
            return node;
        }
    }
}
