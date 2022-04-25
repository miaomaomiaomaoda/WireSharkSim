package packet;

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
    private final PcapNetworkInterface nif;
    private PcapHandle handle;

    public PacketCapture(PcapNetworkInterface nif){
        this.nif = nif;
    }

    /**
     * function:注册监听器
     * @param packetListener 抓包类监听器
     */
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

    /**
     * function:处理抓到的包
     * @param packet 包
     */
    private void handlePacketReceived(Packet packet) {
        if (capturing) {
            MyPacket myPacket = new MyPacket(packet,handle.getTimestamp());
            for (PacketCapturedListener listener : listeners) {
                listener.sendMyPacket(myPacket);
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
