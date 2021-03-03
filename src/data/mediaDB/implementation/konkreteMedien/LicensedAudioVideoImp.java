package data.mediaDB.implementation.konkreteMedien;

import data.mediaDB.LicensedAudioVideo;
import data.mediaDB.Tag;
import data.mediaDB.implementation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class LicensedAudioVideoImp implements LicensedAudioVideo, Serializable {
    private AudioVideoBeschreibung audioVideoBeschreibung;
    private LicensedBeschreibung licensedBeschreibung;

    public LicensedAudioVideoImp() {
    }

    public LicensedAudioVideoImp(AudioVideoBeschreibung audioVideoBeschreibung, LicensedBeschreibung licensedBeschreibung) {
        this.audioVideoBeschreibung = audioVideoBeschreibung;
        this.licensedBeschreibung = licensedBeschreibung;
    }

    @Override
    public int getWdth() {
        return audioVideoBeschreibung.getVideoBeschreibung().getWidth();
    }

    public void setWidth(int width) {
        audioVideoBeschreibung.getVideoBeschreibung().setWidth(width);
    }

    @Override
    public int getHight() {
        return audioVideoBeschreibung.getVideoBeschreibung().getHeight();
    }

    public void setHeight(int height) {
        audioVideoBeschreibung.getVideoBeschreibung().setHeight(height);
    }

    @Override
    public String getEncodng() {
        return audioVideoBeschreibung.getVideoBeschreibung().getEncoding();
    }

    public void setEncoding(String encoding) {
        audioVideoBeschreibung.getVideoBeschreibung().setEncoding(encoding);
    }

    @Override
    public long getBitrat() {
        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getBitrate();
    }

    public void setBitrate(long bitrate) {
        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setBitrate(bitrate);
    }

    @Override
    public double getLength() {
        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getLengthInMinutes();
    }

    public void setLengthInMinutes(double lengthInMinutes) {
        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                setLengthInMinutes(lengthInMinutes);
    }

    @Override
    public BigDecimal getSize() {
        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().getSizeInMbs();
    }

    public void setSizeInMbs(BigDecimal sizeInMbs) {
        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().setSizeInMbs(sizeInMbs);
    }

    @Override
    public String getAddr() {
        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getAdress();
    }

    public void setAdress(String adress) {
        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAdress(adress);
    }

    @Override
    public Collection<Tag> getTag() {
        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getTags();
    }

    public void setTags(Collection<Tag> tags) {
        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setTags(tags);
    }

    @Override
    public long getAC() {

        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getAccessCount();
    }

    public void setAccessCount(long accessCount) {

        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAccessCount(accessCount);
    }

    @Override
    public Date getDate() {
        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().
                getUploadableBeschreibung().getUploadDate();
    }

    public void setUploadDate(Date uploadDate) {

        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().
                getUploadableBeschreibung().setUploadDate(uploadDate);
    }

    @Override
    public String getProducerName() {

        return audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().getName();
    }

    public void setName(String name) {

        audioVideoBeschreibung.getVideoBeschreibung().getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().setName(name);
    }

    @Override
    public int getSamplingRate() {
        return audioVideoBeschreibung.getAudioBeschreibung().getSamplingRate();
    }

    public void setSamplingRate(int samplingRate) {
        audioVideoBeschreibung.getAudioBeschreibung().setSamplingRate(samplingRate);
    }

    @Override
    public String getHoldr() {
        return licensedBeschreibung.getHolder();
    }

    public void setHolder(String holder) {
        licensedBeschreibung.setHolder(holder);
    }

    public AudioVideoBeschreibung getAudioVideoBeschreibung() {
        return audioVideoBeschreibung;
    }

    public void setAudioVideoBeschreibung(AudioVideoBeschreibung audioVideoBeschreibung) {
        this.audioVideoBeschreibung = audioVideoBeschreibung;
    }

    public LicensedBeschreibung getLicensedBeschreibung() {
        return licensedBeschreibung;
    }

    public void setLicensedBeschreibung(LicensedBeschreibung licensedBeschreibung) {
        this.licensedBeschreibung = licensedBeschreibung;
    }

    @Override
    public String toString() {
        return this.audioVideoBeschreibung.toString() + " " + this.licensedBeschreibung.toString();
    }
}
