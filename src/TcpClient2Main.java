import userInterface.netView.AbstractClient;
import userInterface.netView.TcpClient;

public class TcpClient2Main {
    public static void main (String[] args){
        AbstractClient abstractClient = new TcpClient("localhost", 5056, "Ali");
        System.out.println("Starting .. enter modus:");
        abstractClient.startClient();
    }
}
