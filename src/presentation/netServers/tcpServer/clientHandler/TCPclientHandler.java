package presentation.netServers.tcpServer.clientHandler;

import controller.MVcontroller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class TCPclientHandler extends Thread implements PropertyChangeListener {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket socket;
    private Object respond;
    private String request;
    private MVcontroller model;
    private String mode;

    private String myThreadName;

    public TCPclientHandler(Socket socket, MVcontroller model){
        this.socket = socket;
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    @Override
    public void run() {
        this.myThreadName = currentThread().getName();
        try {
            this.os = new ObjectOutputStream(this.socket.getOutputStream());
            this.is = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int x = 2;
        while (true) {
            try {
                this.request = (String) this.is.readObject();
                System.out.println("recieved from client@"+socket.getInetAddress()+":"+socket.getPort() + "-> " + this.request);
                if (this.request.equalsIgnoreCase("exit")){
                    System.out.println("AbstractClient " + this.socket + " requested to close the connection");
                    this.socket.close();
                    System.out.println("the connection has been closed");
                    break;
                } else if (this.request.equalsIgnoreCase(":config")) {
                    this.os.writeObject("This mode is not supported in Server and cliet mode");
                    this.os.writeObject(null);
                } else {
                    synchronized (this.model) {
                        this.model.setCurrentMode(this.mode);
                        this.model.start(this.request);
                        this.mode = this.model.getCurrentMode();
                    }
                    sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("client@"+socket.getInetAddress()+":"+socket.getPort()+" disconnected");
    }

    @Override  // das ist Beobachter auf mein Model , wenn was sich ZB Hochladen ... in Model geändert hat
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getOldValue().equals(this.myThreadName)) {
            this.respond = evt.getPropertyName();
            try {
                this.os.writeObject(this.respond);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.respond = evt.getNewValue();
            if (this.respond != null) {
                try {
                    List list = new LinkedList();
                    list.addAll((List)(evt.getNewValue()));
                    this.os.writeObject(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.os.writeObject(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
