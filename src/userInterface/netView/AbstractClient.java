package userInterface.netView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class AbstractClient {
    protected ObjectInputStream is;
    protected ObjectOutputStream os;
    protected InetAddress serverAdress;
    protected int ServerPort;
    protected String tosend;
    protected String name;

    public AbstractClient(String host, int ServerPort, String name) {
        try {
            this.serverAdress = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.ServerPort = ServerPort;
        this.name = name;
    }

    public abstract void startClient();
}
