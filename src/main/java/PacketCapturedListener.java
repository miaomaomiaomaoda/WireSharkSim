import org.pcap4j.packet.Packet;

/**
 * @author R.Q.
 */
public interface PacketCapturedListener {
    void sendPacket(Packet packet);
}
