package presentation.guiModelView;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import javafx.embed.swing.JFXPanel;
import data.mediaDB.MediaContentUploadable;
import data.mediaDB.implementation.konkreteMedien.InteractiveVideoImp;
import controller.MVcontroller;
import userInterface.cliView.View;
import data.producerManager.ProducersManager;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import simulations.ZufaelligErzeuger;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ModelViewTest <T extends MediaContentUploadable> {

    private ZufaelligErzeuger zufaelligErzeuger = new ZufaelligErzeuger();
    private ProducersManager zufallProduzenten = zufaelligErzeuger.getProducers();
    private ModelView<T> modelView = new ModelView<>();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @BeforeEach
    void init(){
        JFXPanel jfxPanel = new JFXPanel();
        ZufaelligErzeuger zufaelligErzeuger = new ZufaelligErzeuger();
        this.zufallProduzenten = zufaelligErzeuger.getProducers();
        this.modelView = new ModelView<>();
        ProducersManager producersManager = new ProducersManager();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(300));
        MediaVerwalter model = new MediaVerwalter(producersManager, memoryManager);
        View view = new View();
        MVcontroller controller = new MVcontroller(view, model);
        this.modelView.setModel(controller);
    }

    private void addProduzent(int index) throws InterruptedException {
        this.modelView.textProperty.set(zufallProduzenten.getProducerList().get(index).getName()); // add a producer with index 0 with name
        this.modelView.hochladen(null);
        Thread.sleep(500);
    }

    private void addMedia(String eingabe) throws InterruptedException {
        this.modelView.textProperty.set(eingabe);
        this.modelView.hochladen(null);
        Thread.sleep(500);
    }

    @Test
    void ADD_PODUZENT() throws InterruptedException {
        this.addProduzent(0);
        this.addProduzent(1);
        assertTrue(this.modelView.outputPropertyProduzent.size() == 2);
        assertTrue(this.modelView.outputPropertyProduzent.get(0).getName().equals(this.zufallProduzenten.getProducerList().get(0).getName()));
        assertTrue(this.modelView.outputPropertyProduzent.get(1).getName().equals(this.zufallProduzenten.getProducerList().get(1).getName()));
    }

    @Test
    void ADD_MEDIA() throws InterruptedException {
        // Add Produzent "Mohamed" zunächst
        this.addProduzent(0);

        // at the first the output list view contains the added producer
        assertTrue(this.modelView.outputPropertyProduzent.size() == 1);

        //aploading a new media object
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        assertTrue(this.modelView.outputProperty.size() == 1);
        assertTrue(((InteractiveVideoImp)(this.modelView.outputProperty.get(0))).getProducerName().equals("Mohamed"));
    }

    @Test
    void MEDIA_LOESCHEN() throws InterruptedException {
        // add two producers
        this.addProduzent(0);
        this.addProduzent(1);

        //aploading 2 new media objects
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        assertTrue(this.modelView.outputProperty.size() == 2);

        this.modelView.textProperty.set(this.modelView.outputProperty.get(0).getAddr());
        this.modelView.loeschen(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.size() == 1);
    }

    @Test
    void MEDIA_ABRUFEN() throws InterruptedException {
        // add two producers
        this.addProduzent(0);
        this.addProduzent(1);

        //aploading 2 new media objects
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("InteractiveVideo Ali Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        assertTrue(this.modelView.outputProperty.size() == 2);

        assertTrue(this.modelView.outputProperty.get(0).getAC() == 0);

        this.modelView.textProperty.set(this.modelView.outputProperty.get(0).getAddr());
        this.modelView.abrufen(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.get(0).getAC() == 1);
    }

    @Test
    void MEDIA_NACH_ABRUFEN_SORTIEREN_TEST() throws InterruptedException {
        // add two producers
        this.addProduzent(0);
        this.addProduzent(1);

        //aploading 2 new media objects
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("InteractiveVideo Ali Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        assertTrue(this.modelView.outputProperty.get(0).getAC() == 0);
        assertTrue(this.modelView.outputProperty.get(1).getAC() == 0);

        this.modelView.textProperty.set(this.modelView.outputProperty.get(0).getAddr());
        this.modelView.abrufen(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.get(0).getAC() == 1);
        assertTrue(this.modelView.outputProperty.get(1).getAC() == 0);

        this.modelView.nachAbrufen(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.get(0).getAC() == 0);
        assertTrue(this.modelView.outputProperty.get(1).getAC() == 1);

    }

    @Test
    void MEDIA_NACH_ADRESS_SORTIEREN_TEST() throws InterruptedException {
        // add two producers
        this.addProduzent(0);
        this.addProduzent(1);

        //aploading 2 new media objects
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("InteractiveVideo Ali Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        this.modelView.outputProperty.get(0).setAdress("xyz");
        this.modelView.outputProperty.get(1).setAdress("abc");

        assertTrue(this.modelView.outputProperty.get(0).getAddr().equals("xyz"));
        assertTrue(this.modelView.outputProperty.get(1).getAddr().equals("abc"));

        this.modelView.nachAdress(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.get(0).getAddr().equals("abc"));
        assertTrue(this.modelView.outputProperty.get(1).getAddr().equals("xyz"));
    }

    @Test
    void MEDIA_AUFLISTE_TYP_TEST() throws InterruptedException {
        // add two producers
        this.addProduzent(0);
        this.addProduzent(1);

        //aploading 3 new media objects
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("InteractiveVideo Ali Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("LicensedVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        assertTrue(this.modelView.outputProperty.size() == 3);

        this.modelView.textProperty.set("InteractiveVideo");
        this.modelView.auflisten(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.size() == 2);

        this.modelView.textProperty.set("LicensedVideo");
        this.modelView.auflisten(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.size() == 1);
    }

    @Test
    void MEDIA_NACH_PRODUZENT_SORTIEREN_TEST() throws InterruptedException {
        // add two producers
        this.addProduzent(0);
        this.addProduzent(1);

        //aploading 3 new media objects
        this.addMedia("InteractiveVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("InteractiveVideo Ali Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");
        this.addMedia("LicensedVideo Mohamed Lifestyle,News 5000 3600 DWT 640 480 Abstimmung");

        assertTrue(this.modelView.outputProperty.get(0).getProducerName().equals("Mohamed"));
        assertTrue(this.modelView.outputProperty.get(1).getProducerName().equals("Ali"));
        assertTrue(this.modelView.outputProperty.get(2).getProducerName().equals("Mohamed"));

        this.modelView.nachProduzent(null);
        Thread.sleep(500);

        assertTrue(this.modelView.outputProperty.get(0).getProducerName().equals("Ali"));
        assertTrue(this.modelView.outputProperty.get(1).getProducerName().equals("Mohamed"));
        assertTrue(this.modelView.outputProperty.get(2).getProducerName().equals("Mohamed"));
    }

}