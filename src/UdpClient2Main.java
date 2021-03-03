import userInterface.netView.AbstractClient;
import userInterface.netView.UdpClient;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpClient2Main {
    public static void main(String[] args) {
        try {
            AbstractClient udpAbstractClient = new UdpClient("localhost", 5056, "m", new DatagramSocket(5058));
            udpAbstractClient.startClient();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
