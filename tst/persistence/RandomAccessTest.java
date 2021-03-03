package persistence;

import data.mediaDB.MediaContentUploadable;
import data.mediaDB.implementation.konkreteMedien.InteractiveVideoImp;
import org.junit.jupiter.api.Test;

import java.io.*;
import static org.mockito.Mockito.*;

public class RandomAccessTest <T extends MediaContentUploadable> {
    RandomAccess randomAccess;

    @Test void RANDOM_SAVE(){
        this.randomAccess = new RandomAccess();
        this.randomAccess.setFilePath("./test");
        RandomAccessFile randomAccessFile = mock(RandomAccessFile.class);
        InteractiveVideoImp mediaObj = mock(InteractiveVideoImp.class);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream outos = null;
        try {
            outos = new ObjectOutputStream(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outos.writeObject(mediaObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = bos.toByteArray();
        try {
            long l = 0;
            doNothing().when(randomAccessFile).seek(0);
            this.randomAccess.save(randomAccessFile, data);
            verify(randomAccessFile).write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test void RANDOM_LOAD(){
        this.randomAccess = new RandomAccess();
        RandomAccessFile randomAccessFile = mock(RandomAccessFile.class);
        byte[] data = new byte[5024];
        try {
            this.randomAccess.load(randomAccessFile, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            verify(randomAccessFile).read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

