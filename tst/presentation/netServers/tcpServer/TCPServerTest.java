package presentation.netServers.tcpServer;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import controller.MVcontroller;
import userInterface.cliView.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.producerManager.ProducersManager;
import presentation.netServers.tcpServer.clientHandler.TCPclientHandler;
import simulations.ZufaelligErzeuger;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TCPServerTest {
    private MVcontroller model;
    private ZufaelligErzeuger zufaelligErzeuger;
    private LinkedList mediaList;

    @BeforeEach
    void init(){
        ProducersManager producersManager = new ProducersManager();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(300));
        MediaVerwalter model = new MediaVerwalter(producersManager, memoryManager);
        View view = new View();
        this.model = new MVcontroller(view, model);
        this.zufaelligErzeuger = new ZufaelligErzeuger();
        this.mediaList = this.zufaelligErzeuger.getMediaList();
    }

    @Test
    public void HOCHLADEN_TCP_SERVER_TEST()  {
        Socket mockSocket = mock(Socket.class);
        TCPclientHandler tcPclientHandler = new TCPclientHandler(mockSocket, this.model);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            this.hochladen(oos);
            oos.writeObject("exit");

            when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(bos.toByteArray()));
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            when(mockSocket.getOutputStream()).thenReturn(out);

            tcPclientHandler.start();
            tcPclientHandler.join();
            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));

            LinkedList respond = new LinkedList();
            for (int i = 0; i < 10; i++) {
                respond.add(ois.readObject());
            }
            this.checkHochladen(respond, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AUFLISTEN_TCP_SERVER_TEST() {
        Socket mockSocket = mock(Socket.class);
        TCPclientHandler tcPclientHandler = new TCPclientHandler(mockSocket, this.model);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            this.hochladen(oos);
            this.aufListen(oos);
            oos.writeObject("exit");

            when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(bos.toByteArray()));
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            when(mockSocket.getOutputStream()).thenReturn(out);

            tcPclientHandler.start();
            tcPclientHandler.join();
            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));

            LinkedList respond = new LinkedList();
            for (int i = 0; i < 18; i++) {
                respond.add(ois.readObject());
            }
            this.checkHochladen(respond, 0);
            this.checkAuflisten(respond, 10, 2, 1, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void LÖSCHEN_TCP_SERVER_TEST() {
        Socket mockSocket = mock(Socket.class);
        TCPclientHandler tcPclientHandler = new TCPclientHandler(mockSocket, this.model);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            this.hochladen(oos);
            this.löschen(oos);
            this.aufListen(oos);
            oos.writeObject("exit");

            when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(bos.toByteArray()));
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            when(mockSocket.getOutputStream()).thenReturn(out);

            tcPclientHandler.start();
            tcPclientHandler.join();
            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));

            LinkedList respond = new LinkedList();
            for (int i = 0; i < 28; i++) {
                respond.add(ois.readObject());
            }
            this.checkHochladen(respond, 0);
            this.checkLöschen(respond, 10);
            this.checkAuflisten(respond, 20, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hochladen(ObjectOutputStream oos) {
        try {
            oos.writeObject(":c");
            oos.writeObject("Ali");
            oos.writeObject("Mohamed");
            oos.writeObject("LicensedVideo Ali Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
            oos.writeObject("InteractiveVideo Ali Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private void checkHochladen(LinkedList respond, int start) {
        assertEquals("current mode::c", respond.get(start));
        assertEquals("Producer added", respond.get(start+2));
        assertEquals("Producer added", respond.get(start+4));
        assertEquals("HochLaden -> success", respond.get(start+6));
        assertEquals("HochLaden -> success", respond.get(start+8));
    }

     private void aufListen(ObjectOutputStream oos) {
        try {
            oos.writeObject(":r");
            oos.writeObject("uploader");
            oos.writeObject("content LicensedVideo");
            oos.writeObject("content InteractiveVideo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private void checkAuflisten(LinkedList respond, int start, int uploaderAnz, int licensedAnz, int interactiveAnz) {
         assertEquals("current mode::r", respond.get(start));
         assertEquals("uploader", respond.get(start+2));
         assertEquals(uploaderAnz, ((List)respond.get(start+3)).size());
         assertEquals("nachTypListen", respond.get(start+4));
         assertEquals(licensedAnz, ((List)respond.get(start+5)).size());
         assertEquals("nachTypListen", respond.get(start+6));
         assertEquals(interactiveAnz, ((List)respond.get(start+7)).size());
     }

     private void löschen(ObjectOutputStream oos) {
        try {
            oos.writeObject(":d");
            oos.writeObject("Addr1");
            oos.writeObject("Addr2");
            oos.writeObject("Ali");
            oos.writeObject("Mohamed");
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

     private void checkLöschen(LinkedList respond, int start) {
         assertEquals("current mode::d", respond.get(start));
         assertEquals("delete", respond.get(start+2));
         assertEquals("delete", respond.get(start+4));
         assertEquals("deleteProducer", respond.get(start+6));
         assertEquals("deleteProducer", respond.get(start+8));
     }


}
