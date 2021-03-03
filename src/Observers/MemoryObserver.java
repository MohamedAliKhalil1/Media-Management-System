package Observers;

import geschäftslogik.MediaVerwalter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MemoryObserver implements PropertyChangeListener {

    private MediaVerwalter subject;
    private BigDecimal neueZustand;
    private BigDecimal maxTillWarning;
    private BigDecimal memorySize;
    private BigDecimal RestSizePercent;

    public void setSubject(MediaVerwalter subject) {
        this.subject = subject;
    }

    public void setMaxTillWarning(BigDecimal maxTillWarning) {
        this.maxTillWarning = maxTillWarning;
    }

    public void setMemorySize(BigDecimal memorySize) {
        this.memorySize = memorySize;
    }

    public void setRestSizePercent(BigDecimal restSizePercent) {
        RestSizePercent = restSizePercent;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.neueZustand = this.subject.getRestSizeOfMemory();
        this.RestSizePercent = this.PercentCalc();
        if (this.RestSizePercent.intValue() <= 100 - this.maxTillWarning.intValue()) {
            System.out.println("MemoryPlatz beobachter -> Verbrauchter Speicher : " + (100 - this.RestSizePercent.intValue()) + " %");
        }
    }
    private BigDecimal PercentCalc(){
        return new BigDecimal(this.neueZustand.multiply(new BigDecimal(100))
                        .divide(this.memorySize, 2, RoundingMode.CEILING).intValue()
        );
    }
}