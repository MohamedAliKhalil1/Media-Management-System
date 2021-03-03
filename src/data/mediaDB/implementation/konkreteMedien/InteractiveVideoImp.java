package data.mediaDB.implementation.konkreteMedien;

import data.mediaDB.InteractiveVideo;
import data.mediaDB.Tag;
import data.mediaDB.implementation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class InteractiveVideoImp implements InteractiveVideo, Serializable {
    private VideoBeschreibung videoBeschreibung;
    private InteractiveBeschreibung interactiveBeschreibung;

    public InteractiveVideoImp() {
    }

    public InteractiveVideoImp(VideoBeschreibung videoBeschreibung, InteractiveBeschreibung interactiveBeschreibung) {
        this.videoBeschreibung = videoBeschreibung;
        this.interactiveBeschreibung = interactiveBeschreibung;
    }

    @Override
    public int getWdth() {
        return videoBeschreibung.getWidth();
    }

    public void setWidth(int width) {
        videoBeschreibung.setWidth(width);
    }

    @Override
    public int getHight() {
        return videoBeschreibung.getHeight();
    }

    public void setHeight(int height) {
        videoBeschreibung.setHeight(height);
    }

    @Override
    public String getEncodng() {
        return videoBeschreibung.getEncoding();
    }

    public void setEncoding(String encoding) {
        videoBeschreibung.setEncoding(encoding);
    }

    @Override
    public long getBitrat() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getBitrate();
    }

    public void setBitrate(long bitrate) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setBitrate(bitrate);
    }

    @Override
    public double getLength() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getLengthInMinutes();
    }

    public void setLengthInMinutes(double lengthInMinutes) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setLengthInMinutes(lengthInMinutes);
    }

    @Override
    public BigDecimal getSize() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().getSizeInMbs();
    }

    public void setSizeInMbs(BigDecimal sizeInMbs) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().setSizeInMbs(sizeInMbs);
    }

    @Override
    public String getAddr() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getAdress();
    }

    public void setAdress(String adress) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAdress(adress);
    }

    @Override
    public Collection<Tag> getTag() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getTags();
    }

    public void setTags(Collection<Tag> tags) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setTags(tags);
    }

    @Override
    public long getAC() {

        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getAccessCount();
    }

    public void setAccessCount(long accessCount) {

        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAccessCount(accessCount);
    }

    @Override
    public Date getDate() {

        return videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().getUploadDate();
    }

    public void setUploadDate(Date uploadDate) {

        videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().setUploadDate(uploadDate);
    }

    @Override
    public String getProducerName() {

        return videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().getName();
    }

    public void setName(String name) {

        videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().setName(name);
    }

    @Override
    public String getType() {
        return interactiveBeschreibung.getType();
    }

    public void setType(String type) {
        interactiveBeschreibung.setType(type);
    }

    public VideoBeschreibung getVideoBeschreibung() {
        return videoBeschreibung;
    }

    public void setVideoBeschreibung(VideoBeschreibung videoBeschreibung) {
        this.videoBeschreibung = videoBeschreibung;
    }

    public InteractiveBeschreibung getInteractiveBeschreibung() {
        return interactiveBeschreibung;
    }

    public void setInteractiveBeschreibung(InteractiveBeschreibung interactiveBeschreibung) {
        this.interactiveBeschreibung = interactiveBeschreibung;
    }

    @Override
    public String toString() {
        return this.videoBeschreibung.toString() + " " + this.interactiveBeschreibung.toString();
    }
}
