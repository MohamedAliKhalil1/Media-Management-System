package geschäftslogikTest;

import data.mediaDB.LicensedVideo;
import data.mediaDB.MediaContentUploadable;
import data.mediaDB.implementation.UploaderBeschreibung;
import data.mediaDB.implementation.konkreteMedien.LicensedVideoImp;
import data.memorymanagement.MemoryManager;
import data.producerManager.ProducersManager;
import geschäftslogik.MediaVerwalter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulations.ZufaelligErzeuger;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MediaVerwalterTest <T extends MediaContentUploadable> {

    private ProducersManager producersManager;
    private MemoryManager memoryManager;
    private MediaVerwalter model;
    private ZufaelligErzeuger zufaelligErzeuger;

    @BeforeEach
    void init() {
        this.zufaelligErzeuger = new ZufaelligErzeuger();
        this.producersManager = this.zufaelligErzeuger.getProducers();
        this.memoryManager = new MemoryManager(new BigDecimal(300));
        this.model = new MediaVerwalter(producersManager, memoryManager);
    }

    @Test
    void HOCHLADEN_MEMORY_OVERFLOW_SHOULDBE_FALSE() {
        this.model = new MediaVerwalter(this.producersManager, new MemoryManager(new BigDecimal(0)));
        assertEquals(false, this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(0)));
    }

    @Test
    void HOCHLADEN_GLEICHE_ADRESSE_SHOULDBE_FALSE() {
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(0));
        ((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(1)).setAdress("Addr1");
        assertEquals(false, this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(0)));
    }

    @Test
    void HOCHLADEN_MEDIA_VON_NICHT_VORHANDENEN_PRODUZENT_SHOULDBE_FALSE() {
        this.model.removeProducer(((T)this.zufaelligErzeuger.getMediaList().get(0)).getProducerName());
        assertEquals(false, this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(0)));
    }

    @Test
    void Auflisten_MIT_TYP() {
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(0)); // LicensedVideo
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(1)); // LicensedVideo
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(3)); // InteractiveVideo

        List list = this.model.auflisten("LicensedVideo");
        List richtigAudioList = new LinkedList<>();
        richtigAudioList.add((T)this.zufaelligErzeuger.getMediaList().get(0));
        richtigAudioList.add((T)this.zufaelligErzeuger.getMediaList().get(1));
        assertEquals(true, list.size() == 2);
        assertEquals(true, list.equals(richtigAudioList));

        list = this.model.auflisten("InteractiveVideo");
        List richtigVideoList = new LinkedList<>();
        richtigVideoList.add((T)this.zufaelligErzeuger.getMediaList().get(3));
        assertEquals(true, list.size() == 1);
        assertEquals(true, list.equals(richtigVideoList));
    }

    @Test
    void UPDATE_MIT_ADRESS() {
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(0)); // LicensedVideo
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(1)); // LicensedVideo
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(3)); // InteractiveVideo
        assertEquals(true, ((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(0)).getAC() == 0);
        assertEquals(true, ((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(1)).getAC() == 0);
        assertEquals(true, ((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(3)).getAC() == 0);
        this.model.update(((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(0)).getAddr());
        this.model.update(((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(1)).getAddr());
        this.model.update(((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(1)).getAddr());
        this.model.update(((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(3)).getAddr());
        this.model.update(((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(3)).getAddr());
        this.model.update(((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(3)).getAddr());
        assertEquals(true,((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(0)).getAC() == 1 );
        assertEquals(true,((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(1)).getAC() == 2 );
        assertEquals(true,((MediaContentUploadable) this.zufaelligErzeuger.getMediaList().get(3)).getAC() == 3 );

    }

    @Test
    void UPDATE_MIT_NICHT_VORHANDEN_ADRESS() {
        assertEquals(false, this.model.update("aaaaa"));
    }

    @Test
    void HOCHLADEN_SHOULBE_TRUE (){
        LicensedVideo Lvideo = mock(LicensedVideoImp.class);

        when(Lvideo.getProducerName()).thenReturn("Mohamed");
        when(Lvideo.getSize()).thenReturn(new BigDecimal(100));

        assertTrue(this.model.Hochladen(Lvideo));
        verify(Lvideo, times(3)).getProducerName();
    }

    @Test
    void DELETE_VORHANDEN_PRODUZENT (){
        // this.producers = {Mohamed, Müller, Schmidt}
        assertTrue(this.producersManager.getSize() == 3);
        UploaderBeschreibung producerMock = mock(UploaderBeschreibung.class);
        when(producerMock.getName()).thenReturn("Mohamed");
        assertTrue(this.producersManager.deleteProducer(producerMock.getName()));
        assertTrue(this.producersManager.getSize() == 2);
        verify(producerMock).getName();
    }
}
