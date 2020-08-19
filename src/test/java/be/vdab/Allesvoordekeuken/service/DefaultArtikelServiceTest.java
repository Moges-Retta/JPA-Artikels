package be.vdab.Allesvoordekeuken.service;

import be.vdab.Allesvoordekeuken.domain.Artikel;
import be.vdab.Allesvoordekeuken.exceptions.ArtikelNietGevondenException;
import be.vdab.Allesvoordekeuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DefaultArtikelServiceTest {
    @Mock
    private ArtikelRepository repository;
    private ArtikelService service;
    private Artikel artikel;

    @BeforeEach
    void beforeEach(){
        artikel=new Artikel("test", BigDecimal.valueOf(100),BigDecimal.valueOf(100));
        service = new DefaultArtikelService(repository);
    }

    @Test
    void verhoogVerkoopPrijs(){
        when(repository.findById(1)).thenReturn(Optional.of(artikel));
        service.verhoogVerkoopPrijs(1,BigDecimal.TEN);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("110");
    }
    @Test
    void opslagVoorOnbestaandeArtikel() {
        assertThatExceptionOfType(ArtikelNietGevondenException.class)
                .isThrownBy(()->service.verhoogVerkoopPrijs(-1, BigDecimal.TEN));
        verify(repository).findById(-1);
    }
}
