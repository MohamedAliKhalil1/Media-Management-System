package data.mediaDB;

import java.math.BigDecimal;

public interface MediaContent extends Content {
    long getBitrat();
    double getLength();
    BigDecimal getSize();
}
