package userInterface.netView;

import userInterface.cliView.display.Display;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Scanner;

public class UdpClient extends AbstractClient {

    private DatagramSocket socket;
    private int messageId;

    public UdpClient(String host, int port, String name, DatagramSocket socket) {
        super(host, port, name);
        this.socket = socket;
    }

    @Override
    public void startClient() {
        try {
            this.init();
            ObjectInputStream ois = this.receive();
            Object respond = ois.readObject();
            int returnedMessageId = ois.readInt();
            int sessionId = ois.readInt();
            if (returnedMessageId == this.messageId) {
                System.out.println("respond -> " + respond);
                System.out.println("My session id -> " + sessionId);
                System.out.println("message id echo -> " + returnedMessageId);
            } else {
                System.out.println("wrong message id returned: "+ returnedMessageId);
            }
            Display display = new Display();
            while (true) {
                System.out.println(display.getEinleitung());
                System.out.println("enter 'exit' to exit the session");
                Scanner scn = new Scanner(System.in);
                String request;
                request = scn.nextLine();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                if (request.equalsIgnoreCase("exit")) {
                    oos.writeChar('E');
                    oos.writeInt(++this.messageId);
                    oos.writeInt(sessionId);
                    oos.flush();
                    this.sendMessage(bos.toByteArray());
                    oos.close();
                    bos.close();
                    break;
                } else {
                    oos.writeChar('N');
                    oos.writeInt(++this.messageId);
                    oos.writeInt(sessionId);
                    oos.writeObject(request);
                    oos.flush();
                    oos.close();
                    bos.close();
                    this.sendMessage(bos.toByteArray());

                    ois = this.receive();
                    System.out.println(ois.readObject());
                    ois = this.receive();
                    System.out.println(ois.readObject());
                    ois = this.receive();
                    Object o = ois.readObject();
                    LinkedList list;
                    if (o != null) {
                        if (o instanceof String)
                            System.out.println(o);
                        else {
                            list = (LinkedList) o;
                            if (list.size() == 0)
                                System.out.println(o);
                            else {
                                for (Object itr : list)
                                    System.out.println("[" + itr + "]");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("AbstractClient is exiting ..");
    }

    public String init() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeChar('I');
        oos.writeInt(++this.messageId);
        oos.close();
        bos.close();
        this.sendMessage(bos.toByteArray());
        return this.name;
    }

    private void sendMessage(byte[] message) {
        DatagramPacket packetOut = new DatagramPacket(message, message.length, this.serverAdress, this.ServerPort);
        try {
            this.socket.send(packetOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream receive() throws IOException {
        byte[] buffer=new byte[5000];
        DatagramPacket packetIn = new DatagramPacket(buffer,buffer.length);
        this.socket.receive(packetIn);
        ObjectInputStream dis=new ObjectInputStream(new ByteArrayInputStream(packetIn.getData()));
        return dis;
    }
}
