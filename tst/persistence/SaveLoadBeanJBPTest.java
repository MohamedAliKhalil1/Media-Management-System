package persistence;
import persistence.dataToSave.MediaProduzentenData;
import data.mediaDB.MediaContentUploadable;
import org.junit.jupiter.api.Test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class SaveLoadBeanJBPTest<T extends MediaContentUploadable> {
    SaveLoadBeanJBP saveLoadBeanJBP;

    @Test void JBP_SAVE_TEST() {
        try {
            this.saveLoadBeanJBP = new SaveLoadBeanJBP("./jbpTest.xml");
            XMLEncoder xmlEncoder = mock(XMLEncoder.class);
            MediaProduzentenData mediaProduzentenData = mock(MediaProduzentenData.class);
            this.saveLoadBeanJBP.save(xmlEncoder, mediaProduzentenData);
            verify(xmlEncoder).writeObject(mediaProduzentenData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test void JBP_LOAD_TEST(){
        try {
            this.saveLoadBeanJBP = new SaveLoadBeanJBP("./jbpTest.xml");
            XMLDecoder xmlDecoder = mock(XMLDecoder.class);
            MediaProduzentenData mediaProduzentenData = this.saveLoadBeanJBP.load(xmlDecoder);
            verify(xmlDecoder).readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

