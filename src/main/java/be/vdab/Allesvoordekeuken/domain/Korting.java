package be.vdab.Allesvoordekeuken.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Korting {
    private int vanafaantal;
    private double percentage;

    public Korting(int vanafaantal, float percentage) {
        this.vanafaantal = vanafaantal;
        this.percentage = percentage;
    }
    protected Korting(){}

    public int getVanafaantal() {
        return vanafaantal;
    }

    public double getPercentage() {
        return percentage;
    }
    @Override
    public boolean equals(Object object) {
        if (object instanceof Korting){
            var anderKorting = (Korting) object;
            return vanafaantal==(anderKorting.vanafaantal);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode((vanafaantal));
    }
}
