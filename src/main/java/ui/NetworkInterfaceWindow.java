package ui;

import main.Main;
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
        container.setLayout(null);

        JLabel nifLabel = new JLabel("网口:");
        JComboBox<String> nifJComboBox = new JComboBox<>();
        nifLabel.setBounds(40,90,40,30);
        nifLabel.setFont(new Font("黑体",Font.PLAIN,15));
        nifJComboBox.setBounds(100,90,320,30);
        nifJComboBox.setFont(new Font("黑体",Font.PLAIN,15));
        nifJComboBox.addItem("没有发现网卡");
        initNifComboBox(nifJComboBox);
        container.add(nifLabel);
        container.add(nifJComboBox);

        JButton ok_button = new JButton("确定");
        ok_button.setFont(new Font("黑体",Font.PLAIN,15));
        ok_button.setBounds(200,160,80,40);
        ok_button.addActionListener(e -> {
            nif = allDev.get(nifJComboBox.getSelectedIndex());
            Main.updateNif(nif);
            System.out.println("nif.toString() = " + nif.toString());
            dispose();
        });
        container.add(ok_button);
    }

    /**
     * function:加载下拉框数据
     * @param nifJComboBox 下拉框
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
