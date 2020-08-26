package be.vdab.Allesvoordekeuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ArtikelTest {
    private final static BigDecimal VPRIJS = BigDecimal.valueOf(200);
    private Artikel artikel;
    private ArtikelGroep artikelGroep;
    private ArtikelGroep artikelGroep1;
    private Artikel artikel1;

    @BeforeEach
    void artikel(){
        artikelGroep = new ArtikelGroep("test");
        artikelGroep1 = new ArtikelGroep("test");
        artikel=new Artikel("test",BigDecimal.valueOf(100),VPRIJS,artikelGroep);
        artikel1=new Artikel("test",BigDecimal.valueOf(100),VPRIJS,artikelGroep1);

    }
    @Test
    void verhoogVerkoopPrijs(){
        artikel.verhoogVerkoopPrijs(BigDecimal.TEN);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("210");
    }
    @Test
    void verhoogVerkoopPrijsIllegal(){
        assertThatIllegalArgumentException()
                .isThrownBy(() -> artikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }
    @Test
    void verhoogVerkoopPrijsNull(){
        assertThatNullPointerException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(null));
    }
    @Test
    void DegropeVanArtikelisArtikelgroep(){
        assertThat(artikel1.getArtikelGroeps()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel1);
    }
    @Test
    void eenNullCampusInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(()->artikel.setArtikelGroeps(null));
    }
    @Test
    void artikel1Naargroep2() {
        artikel1.setArtikelGroeps(artikelGroep);
        assertThat(artikel1.getArtikelGroeps()).isEqualTo(artikelGroep);
        assertThat(artikelGroep.getArtikels()).containsOnly(artikel1);
    }
}
