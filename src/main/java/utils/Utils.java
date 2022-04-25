package utils;

import packet.PacketList;

import java.io.*;

/**
 * @author R.Q.
 * description:工具类，包含着一些读写方法
 */
public class Utils {
    public static void write(String filePath, PacketList allPackets) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(allPackets);
            out.close();
            fileOut.close();
            System.out.print("Serialized data is saved in" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PacketList read(String filePath) {
        System.out.println("read filePath = " + filePath);
        PacketList allPackets = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            allPackets = (PacketList) in.readObject();
            in.close();
            fileIn.close();
            return allPackets;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allPackets;
    }
}
