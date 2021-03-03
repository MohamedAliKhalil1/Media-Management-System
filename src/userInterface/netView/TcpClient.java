package userInterface.netView;

import userInterface.cliView.display.Display;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class TcpClient extends AbstractClient {

    private Socket socket;
    private Object recieved;

    public TcpClient(String host, int port, String name) {
        super(host, port, name);
    }

    @Override
    public void startClient() {
        Scanner scn = new Scanner(System.in);
        try {
            this.socket = new Socket(this.serverAdress, this.ServerPort);

            this.os = new ObjectOutputStream(this.socket.getOutputStream());
            this.is = new ObjectInputStream(this.socket.getInputStream());
            Display display = new Display();
            while (true){
                System.out.println(display.getEinleitung());
                System.out.println("Enter request, new mode or exit to terminate");
                this.tosend = scn.nextLine();
                this.os.writeObject(this.tosend);

                if (this.tosend.equalsIgnoreCase("exit")){
                    System.out.println(this.name + " is closing the connection");
                    this.socket.close();
                    System.out.println(this.name + " has closed");
                    break;
                } else {
                    this.recieved = this.is.readObject();
                    System.out.println(this.recieved);
                    this.recieved = this.is.readObject();
                    if (this.recieved != null){
                        LinkedList list = (LinkedList) this.recieved;
                        if (list.size() == 0)
                            System.out.println(list);
                        else
                            for (Object itr : list)
                            System.out.println(itr);
                    }
                }
            }
            scn.close();
            this.os.close();
            this.os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
