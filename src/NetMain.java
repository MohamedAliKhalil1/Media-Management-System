import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import controller.MVcontroller;
import userInterface.cliView.View;
import data.producerManager.ProducersManager;
import presentation.netServers.tcpServer.TcpServer;
import presentation.netServers.udpServer.UdpServer;

import java.math.BigDecimal;
import java.net.DatagramSocket;
import java.util.Scanner;

public class NetMain {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        long size = 0;
        boolean flag = true;
        while (flag) {
            System.out.println("Bitte die größe von Speicher in Giga bytes eingeben");
            try {
                size = Long.parseLong(scn.nextLine());
                flag = false;
            } catch (Exception e){
                System.out.println("falsche Angaben !!");
            }
        }

        ProducersManager producersManager = new ProducersManager();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(size*1024));
        MediaVerwalter CRUD = new MediaVerwalter(producersManager, memoryManager);
        View view = new View();
        MVcontroller model = new MVcontroller(view, CRUD);
        model.setViewAnzeigen(false);
        flag = true;
        while (flag) {
            System.out.println("Bitte den Protokoll eingeben (udp , tcp)");
            try {
                String protocol = scn.nextLine();

                if (protocol.equalsIgnoreCase("udp")){
                    DatagramSocket datagramSocket = new DatagramSocket(5056);
                    UdpServer udpServer = new UdpServer(datagramSocket,model);
                    udpServer.startServerUDP();
                } else if(protocol.equalsIgnoreCase("tcp")){
                    TcpServer server = new TcpServer(model);
                    server.startServerTCP();
                } else {
                    System.out.println("falsche Angaben !!");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
