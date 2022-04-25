import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author R.Q.
 * description：网络接口选择窗口
 */
public class NetworkInterfaceWindow extends JFrame {
    private List<PcapNetworkInterface> allDev;
    PcapNetworkInterface nif;

    public NetworkInterfaceWindow(){
        setTitle("选择希望抓包的网络接口");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        windowInit();
        setVisible(true);
        setBounds(400,300,500,300);
    }

    public void windowInit(){
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(5,5));

        JPanel selectPanel = new JPanel(new FlowLayout());
        JLabel nifLabel = new JLabel("网口:");
        JComboBox<String> nifJComboBox = new JComboBox<>();
        nifJComboBox.addItem("没有发现网卡");
        initNifComboBox(nifJComboBox);
        selectPanel.add(nifLabel);
        selectPanel.add(nifJComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton ok_button = new JButton("确定");
        ok_button.addActionListener(e -> {
            nif = allDev.get(nifJComboBox.getSelectedIndex());
            MainUI.updateNif(nif);
            System.out.println("nif.toString() = " + nif.toString());
            dispose();
        });
        buttonPanel.add(ok_button);
        container.add(selectPanel,BorderLayout.CENTER);
        container.add(buttonPanel,BorderLayout.SOUTH);
    }

    /**
     * description:加载v下拉框数据
     * @param nifJComboBox 下拉框
     * @throws PcapNativeException 异常
     */
    public void initNifComboBox(JComboBox<String> nifJComboBox){
        nifJComboBox.removeAllItems();
        try {
            allDev = Pcaps.findAllDevs();
            for (PcapNetworkInterface networkInterface : allDev) {
                nifJComboBox.addItem(networkInterface.getDescription());
            }
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
    }
}
