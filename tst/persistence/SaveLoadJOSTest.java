package persistence;
import persistence.dataToSave.MediaProduzentenData;
import data.mediaDB.MediaContentUploadable;

import java.io.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveLoadJOSTest<T extends MediaContentUploadable> {

    @org.junit.jupiter.api.Test
    <T extends MediaContentUploadable> void serialize() {
        ObjectOutput oos=mock(ObjectOutput.class);
        SaveLoadJOS s = new SaveLoadJOS("path.ser");
        try {
            s.serialize(oos,mock(MediaProduzentenData.class));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        try {
            verify(oos).writeObject(any());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @org.junit.jupiter.api.Test
    void deserialize() {
        ObjectInput objectInput=mock(ObjectInput.class);
        try {
            SaveLoadJOS s = new SaveLoadJOS("path.ser");
            MediaProduzentenData item = s.deserialize(objectInput);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        try {
            verify(objectInput).readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}

