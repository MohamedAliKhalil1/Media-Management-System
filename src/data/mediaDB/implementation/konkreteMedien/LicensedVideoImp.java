package data.mediaDB.implementation.konkreteMedien;

import data.mediaDB.LicensedVideo;
import data.mediaDB.Tag;
import data.mediaDB.implementation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class LicensedVideoImp implements LicensedVideo, Serializable {
    private VideoBeschreibung videoBeschreibung;
    private LicensedBeschreibung licensedBeschreibung;

    public LicensedVideoImp() {
    }

    public LicensedVideoImp(VideoBeschreibung videoBeschreibung, LicensedBeschreibung licensedBeschreibung) {
        this.videoBeschreibung = videoBeschreibung;
        this.licensedBeschreibung = licensedBeschreibung;
    }

    public void setWidth(int width) {
        videoBeschreibung.setWidth(width);
    }

    @Override
    public int getWdth() {
        System.out.println(videoBeschreibung);
        return videoBeschreibung.getWidth();
    }

    public void setHeight(int height) {
        videoBeschreibung.setHeight(height);
    }

    @Override
    public int getHight() {
        return videoBeschreibung.getHeight();
    }

    public void setEncoding(String encoding) {
        videoBeschreibung.setEncoding(encoding);
    }

    @Override
    public String getEncodng() {
        return videoBeschreibung.getEncoding();
    }

    public void setBitrate(long bitrate) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setBitrate(bitrate);
    }

    @Override
    public long getBitrat() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getBitrate();
    }

    public void setLengthInMinutes(double lengthInMinutes) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setLengthInMinutes(lengthInMinutes);
    }

    @Override
    public double getLength() {

        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getLengthInMinutes();
    }

    public void setSizeInMbs(BigDecimal sizeInMbs) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setSizeInMbs(sizeInMbs);
    }

    @Override
    public BigDecimal getSize() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().getSizeInMbs();
    }

    public void setAdress(String adress) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAdress(adress);
    }

    @Override
    public String getAddr() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getAdress();
    }

    public void setTags(Collection<Tag> tags) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setTags(tags);
    }

    @Override
    public Collection<Tag> getTag() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getTags();
    }

    public void setAccessCount(long accessCount) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAccessCount(accessCount);
    }

    @Override
    public long getAC() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().getContentBeschreibung().getAccessCount();
    }

    public void setUploadDate(Date uploadDate) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                setUploadDate(uploadDate);
    }

    @Override
    public Date getDate() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().getUploadDate();
    }

    public void setName(String name) {
        videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().setName(name);
    }

    @Override
    public String getProducerName() {
        return videoBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().getName();
    }

    public void setHolder(String holder) {
        licensedBeschreibung.setHolder(holder);
    }

    @Override
    public String getHoldr() {
        return licensedBeschreibung.getHolder();
    }

    public VideoBeschreibung getVideoBeschreibung() {
        return videoBeschreibung;
    }

    public void setVideoBeschreibung(VideoBeschreibung videoBeschreibung) {
        this.videoBeschreibung = videoBeschreibung;
    }

    public LicensedBeschreibung getLicensedBeschreibung() {
        return licensedBeschreibung;
    }

    public void setLicensedBeschreibung(LicensedBeschreibung licensedBeschreibung) {
        this.licensedBeschreibung = licensedBeschreibung;
    }

    @Override
    public String toString() {
        return this.videoBeschreibung.toString() + " " + this.licensedBeschreibung.toString();
    }
}
