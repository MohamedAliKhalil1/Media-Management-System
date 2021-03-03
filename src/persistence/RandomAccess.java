package persistence;

import data.mediaDB.MediaContentUploadable;

import java.io.*;

public class RandomAccess {
    private ByteArrayOutputStream byteArrayOutputStream;
    private ByteArrayInputStream byteArrayInputStream;
    private ObjectOutputStream objectOutputStream;
    private String filePath;
    private byte[] buffer;
    private byte[] ref;

    public RandomAccess(){this.buffer = new byte[5024];}
    public RandomAccess(String filePath) {
        this.filePath = filePath;
        this.buffer = new byte[5024];
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private <T extends MediaContentUploadable> void initWrite(T mediaObj) throws IOException {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        this.objectOutputStream.writeObject(mediaObj);
        this.ref = this.byteArrayOutputStream.toByteArray();
        for (byte i : this.buffer)
            i = 0;
    }

    public <T extends MediaContentUploadable> void saveMedia(T mediaObj , String filePath) throws IOException {
        this.initWrite(mediaObj);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")){
            for (int i = 0; i < this.ref.length; i++)
                this.buffer[i] = this.ref[i];
            randomAccessFile.seek(randomAccessFile.length());
            this.save(randomAccessFile, this.buffer);
        }catch (FileNotFoundException e) { } catch (IOException e) { }
    }

    public <T extends MediaContentUploadable> void saveMedia(T mediaObj) throws IOException {
        this.saveMedia(mediaObj, this.filePath);
    }

    public <T extends MediaContentUploadable> T semiRandomAccessLoad(String filename, String adress) throws IOException, ClassNotFoundException {
        try (RandomAccessFile ras=new RandomAccessFile(filename,"r")){
            ras.seek(0);
            while (ras.getFilePointer()<=ras.length()-this.buffer.length) {
                this.load(ras, this.buffer);
                this.byteArrayInputStream = new ByteArrayInputStream(this.buffer);
                ObjectInput in = new ObjectInputStream(this.byteArrayInputStream);
                T media =  (T)in.readObject();
                if (media.getAddr().equals(adress))
                    return media;
            }
        }
        return null;
    }

    public <T extends MediaContentUploadable> T semiRandomAccessLoad(String adress) throws IOException, ClassNotFoundException {
        return this.semiRandomAccessLoad(this.filePath, adress);
    }

    public void save(RandomAccessFile randomAccessFile, byte[] bytes) throws IOException {
        randomAccessFile.write(bytes);
    }

    public void load(RandomAccessFile randomAccessFile, byte[] bytes) throws IOException {
        randomAccessFile.read(bytes);
    }

}
