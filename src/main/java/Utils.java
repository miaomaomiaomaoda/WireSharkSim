import java.io.*;

/**
 * @author R.Q.
 * function:工具类，包含着一些工具方法
 */
public class Utils {
    public static void write(String filePath, PacketList allPackets) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(allPackets);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in" + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PacketList read(String filePath) {
        System.out.println("read filePath = " + filePath);
        PacketList allPackets = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            allPackets = (PacketList) in.readObject();
            in.close();
            fileIn.close();
            return allPackets;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allPackets;
    }
}
