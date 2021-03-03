package presentation.netServers.udpServer;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import controller.MVcontroller;
import userInterface.cliView.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import data.producerManager.ProducersManager;
import simulations.ZufaelligErzeuger;

import java.io.*;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UDPServerTest {

    private MVcontroller model;
    private ZufaelligErzeuger zufaelligErzeuger;
    private LinkedList mediaList;
    MediaVerwalter mediaVerwalter;
    private DatagramSocket mockSocket;
    private ArgumentCaptor<DatagramPacket> captor;
    private UdpServer server;
    private InetAddress address;
    private int port;
    private ByteArrayOutputStream bos;
    private ObjectOutputStream oos;

    @BeforeEach
    void init() throws IOException {
        ProducersManager producersManager = new ProducersManager();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(1000));
        this.mediaVerwalter = new MediaVerwalter(producersManager, memoryManager);
        View view = new View();
        this.model = new MVcontroller(view, this.mediaVerwalter);
        this.zufaelligErzeuger = new ZufaelligErzeuger();
        this.mediaList = this.zufaelligErzeuger.getMediaList();

        this.mockSocket = mock(DatagramSocket.class);
        this.captor = ArgumentCaptor.forClass(DatagramPacket.class);
        this.server = new UdpServer(mockSocket, this.model);
        this.address = mock(InetAddress.class);
        this.port = 5057;
        this.bos = new ByteArrayOutputStream(5000);
        this.oos = new ObjectOutputStream(bos);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                DatagramPacket d = invocation.getArgument(0);
                d.setAddress(address);
                d.setPort(port);
                d.setData(bos.toByteArray());
                return null;
            }
        }).when(mockSocket).receive(any(DatagramPacket.class));
    }

    @Test
    public void NEW_SESSION_UDP_SERVER_TEST() throws IOException, ClassNotFoundException {
        this.makeNewSession();
        this.oos.flush();
        server.processMessage();
        this.verifySocket();
        assertEquals(address.getHostAddress(),captor.getValue().getAddress().getHostAddress());
        assertEquals(port,captor.getValue().getPort());
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(captor.getValue().getData()))) {
            assertEquals("ok", ois.readObject());
            this.assertRespondId(ois, 1, 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MEDIA_HOCHLADEN_UDP_SERVER_TEST() throws IOException, ClassNotFoundException {
        server.creatSession(1, ":c");
        server.setSessionId(1);
        this.model.setCurrentMode(":c");
        this.model.start("Mohamed"); // add a producer at the first.
        this.mediaHochladen();
        this.oos.flush();
        server.processMessage();
        this.verifySocket();
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(captor.getValue().getData()))) {
            assertEquals(this.mediaVerwalter.auflisten().toString(), ois.readObject().toString());
            this.assertRespondId(ois, 4, 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AUFLISTEN_UDP_SERVER_TEST() throws IOException, ClassNotFoundException {
        //zuerst 2 Medien hochladen
        this.model.setCurrentMode(":c");
        this.server.setSessionId(1);
        this.server.creatSession(1, ":r");
        this.model.start("Mohamed");
        this.model.start("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.model.start("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        //ab hier gibt es 2 Media Objekte schon hochgeleden
        this.mediaAuflisten();
        this.oos.flush();
        server.processMessage();
        this.verifySocket();
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(captor.getValue().getData()))) {
            assertEquals(2, ((List) ois.readObject()).size());
            this.assertRespondId(ois, 5, 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void LÖSCHEN_UDP_SERVER_TEST() throws IOException, ClassNotFoundException {
        //zuerst 2 Medien hochladen
        this.server.creatSession(1, ":d");
        this.server.setSessionId(1);
        this.model.setCurrentMode(":c");
        this.model.start("Mohamed");
        this.model.start("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.model.start("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        //ab hier gibt es 2 Media Objekte schon hochgeleden
        this.mediaLöschen();
        this.oos.flush();
        server.processMessage();
        this.verifySocket();
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(captor.getValue().getData()))) {
            assertEquals(this.mediaVerwalter.auflisten().toString(), ois.readObject().toString());
            this.assertRespondId(ois, 6, 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void verifySocket() throws IOException {
        verify(mockSocket, atLeast(1)).send(captor.capture());
    }

    private void assertRespondId(ObjectInputStream ois, int mId, int sId) throws IOException, ClassNotFoundException {
        assertEquals(mId, (Integer) ois.readInt());
        assertEquals(sId, (Integer) ois.readInt());
    }

    private void makeNewSession() throws IOException {
        this.oos.writeChar('I');
        this.oos.writeInt(1);
    }

    private void setModeRequest() throws IOException {
        this.oos.writeChar('N');
        this.oos.writeInt(2);
        this.oos.writeInt(1);
        this.oos.writeObject(":c");
    }

    private void mediaHochladen() throws IOException {
        this.oos.writeChar('N');
        this.oos.writeInt(4);
        this.oos.writeInt(1);
        this.oos.writeObject("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
    }

    private void mediaAuflisten() throws IOException {
        this.oos.writeChar('N');
        this.oos.writeInt(5);
        this.oos.writeInt(1);
        this.oos.writeObject("content InteractiveVideo");
    }

    private void mediaLöschen() throws IOException {
        this.oos.writeChar('N');
        this.oos.writeInt(6);
        this.oos.writeInt(1);
        this.oos.writeObject("Addr1");
    }




}