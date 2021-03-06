package be.vdab.Allesvoordekeuken.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("F")
public class FoodArtrikel extends Artikel{
    private int houdbaarheid;
    public FoodArtrikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int houdbaarheid,
                        ArtikelGroep artikelGroep) {
        super(naam, aankoopprijs, verkoopprijs,artikelGroep);
        this.houdbaarheid = houdbaarheid;
    }
    protected FoodArtrikel() {
    }

    public int getHoudbaarheid() {
        return houdbaarheid;
    }
}
