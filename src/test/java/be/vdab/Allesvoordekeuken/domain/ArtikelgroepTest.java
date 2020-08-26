package be.vdab.Allesvoordekeuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ArtikelgroepTest {
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;

    private Artikel artikel1;
    private Artikel artikel2;

    private final static BigDecimal VPRIJS = BigDecimal.valueOf(200);

    @BeforeEach
    void artikel(){
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        artikel1=new Artikel("test",BigDecimal.valueOf(100),VPRIJS,artikelGroep1);
        artikel2=new Artikel("test2",BigDecimal.valueOf(100),VPRIJS,artikelGroep1);

    }
    @Test
    void DegropeVanArtikelisArtikelgroep(){
        assertThat(artikel1.getArtikelGroeps()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel1);
    }
    @Test
    void eenNullDocentToevoegenMislukt()
    {
        assertThatNullPointerException().isThrownBy(() -> artikelGroep1.add(null));
    }
    @Test
    void meerdereArtikelKunnenTotDezelfdeGroepBehoren() {
        assertThat(artikelGroep1.add(artikel1)).isTrue();
        assertThat(artikelGroep1.add(artikel2)).isTrue();
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel1, artikel2);
    }
    @Test
    void artikel1Naargroep2() {
        artikel1.setArtikelGroeps(artikelGroep2);
        assertThat(artikel1.getArtikelGroeps()).isEqualTo(artikelGroep2);
        assertThat(artikelGroep2.getArtikels()).containsOnly(artikel1);
    }
}
