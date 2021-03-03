package presentation.netServers.udpServer;

import controller.MVcontroller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UdpServer implements PropertyChangeListener {

    private class Session {
        public int id;
        public String modus;
    }
    private MVcontroller model;

    private byte[] inBuffer;
    private DatagramSocket datagramSocket;
    private HashMap<Integer, Session> sessions;
    private int sessionCounter;

    private InetAddress clientAddr;
    private int clientPort;
    private int sessionId;
    private int messageid;

    public UdpServer(DatagramSocket datagramSocket,MVcontroller model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.datagramSocket = datagramSocket;

        this.inBuffer = new byte[5000];
        this.sessions=new HashMap<>();
        this.sessionCounter = 0;

    }

    public void startServerUDP(){
        System.out.println("UDP server started .. ");
        while (true) {
            try {
                this.processMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void processMessage() throws IOException {
        DatagramPacket packetIn = new DatagramPacket(this.inBuffer, this.inBuffer.length);
        this.datagramSocket.receive(packetIn);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(packetIn.getData()))) {
            this.processMessage(ois, packetIn.getAddress(), packetIn.getPort());
            this.clientAddr = packetIn.getAddress();
            this.clientPort = packetIn.getPort();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(ObjectInputStream ois, InetAddress address, int port) throws IOException, ClassNotFoundException {
        char request=ois.readChar();
        int messageId=ois.readInt();
        this.messageid = messageId;
        switch (request){
            case 'I':
                Session session=new Session();
                session.id=++this.sessionCounter;
                this.sessions.put(session.id,session);
                System.out.println("open session "+session.id+" for "+address+":"+port);
                this.sendResponse("ok", messageId, session.id, address, port);
                break;

            case 'N':
                int sessionId = ois.readInt();
                this.sessionId = sessionId;
                String anfrage = (String) ois.readObject();
                this.sendResponse("ok", messageId, sessionId, address, port);
                if (this.model.isMode(anfrage)) {
                    sessions.get(sessionId).modus = anfrage;
                    this.model.setCurrentMode(anfrage);
                    this.sendResponse("current mode : " + sessions.get(sessionId).modus, messageId, sessionId, address, port);
                    this.sendResponse(null, messageId, sessionId, address, port);
                } else {
                    this.model.setCurrentMode(sessions.get(sessionId).modus);
                    this.model.start(anfrage);
                }
                break;
            case 'E':
                this.sessionId = ois.readInt();
                sessions.remove(this.sessionId);
                System.out.println("remove session "+this.sessionId+" of "+address+":"+port);
                break;
            default:
                break;
        }
    }

    private void sendResponse(Object response, int messageId, int sessionId, InetAddress address, int port) {
        try(ByteArrayOutputStream bos=new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(response);
                oos.writeInt(messageId);
                if(0<sessionId) oos.writeInt(sessionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] out=bos.toByteArray();
            DatagramPacket packetOut = new DatagramPacket(out,out.length,address,port);
            this.datagramSocket.send(packetOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.sendResponse(evt.getPropertyName() + "\ncurrent mode : " + sessions.get(this.sessionId).modus, this.messageid, this.sessionId, this.clientAddr, this.clientPort);
        if (evt.getNewValue() != null) {
            List list = new LinkedList();
            list.addAll((List)(evt.getNewValue()));
            this.sendResponse(list, this.messageid, this.sessionId, this.clientAddr, this.clientPort);
        } else
            this.sendResponse(evt.getPropertyName(), this.messageid, this.sessionId, this.clientAddr, this.clientPort);
    }

    public void creatSession(int id, String modus) { //nur für tests verwendet
        Session se = new Session();
        se.modus = modus;
        this.sessions.put(id, se);
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
