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
    @BeforeEach
    void artikel(){
        artikel=new Artikel("test",BigDecimal.valueOf(100),VPRIJS);
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
}
