import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author R.Q.
 * description:抓包类，包含抓包的方法
 */
public class PacketCapture {
    private final Set<PacketCapturedListener> listeners = new HashSet<>();
    private boolean capturing = false;
    private PcapNetworkInterface nif;
    private PcapHandle handle;

    public PacketCapture(PcapNetworkInterface nif){
        this.nif = nif;
    }

    public void registerListener(PacketCapturedListener packetListener){
        listeners.add(packetListener);
    }

    public void startCapturing() throws PcapNativeException, NotOpenException, InterruptedException {
        if(!capturing){
            capturing = true;
            System.out.println("开始抓包");
            handle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
            PacketListener listener = this::handlePacketReceived;
            handle.loop(-1,listener);
        }
    }

    private void handlePacketReceived(Packet packet) {
        if (capturing) {
            for (PacketCapturedListener listener : listeners) {
                listener.sendPacket(packet);
            }
        }
    }

    /**
     * function:停止抓包
     * @throws NotOpenException 异常
     */
    public void stopCapturing() throws NotOpenException {
        if (capturing) {
            capturing = false;
            System.out.println("停止抓包！");
        }
    }
}
