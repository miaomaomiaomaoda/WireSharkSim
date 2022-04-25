package packet;

/**
 * @author R.Q.
 * description:抓包类接口
 */
public interface PacketCapturedListener {
    /**
     * function:抓包类接口，用于PacketCapture类
     * @param packet 包
     */
    void sendMyPacket(MyPacket packet);
}
