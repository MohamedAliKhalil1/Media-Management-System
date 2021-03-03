import userInterface.netView.AbstractClient;
import userInterface.netView.TcpClient;

public class TcpClient1Main {
    public static void main (String[] args){
        AbstractClient abstractClient = new TcpClient("localhost", 5056, "mohamed");
        System.out.println("Starting ...");
        abstractClient.startClient();
    }
}


