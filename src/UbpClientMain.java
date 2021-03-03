import userInterface.netView.AbstractClient;
import userInterface.netView.UdpClient;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UbpClientMain {
    public static void main(String[] args) {
        try {
            AbstractClient udpAbstractClient = new UdpClient("localhost", 5056, "Ali", new DatagramSocket(5057));
            udpAbstractClient.startClient();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
