package presentation.netServers.tcpServer;

import controller.MVcontroller;
import presentation.netServers.tcpServer.clientHandler.TCPclientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private ServerSocket serverSocket;
    private Socket socket;
    private MVcontroller model;

    private Thread tcpClientHandler;

    public TcpServer(MVcontroller model) {
        this.model = model;
    }



    public void startServerTCP(){
        try {
            this.serverSocket = new ServerSocket(5056);
            System.out.println("tcp server started port : 5056");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                this.socket = this.serverSocket.accept();
                System.out.println("new client@"+socket.getInetAddress()+":"+socket.getPort());

                this.tcpClientHandler = new TCPclientHandler(this.socket, this.model);
                this.tcpClientHandler.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
