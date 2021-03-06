package be.vdab.Allesvoordekeuken.repositories;

import be.vdab.Allesvoordekeuken.domain.Artikel;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(Integer id);
    void create(Artikel artikel);
    List<Artikel> findNaamBevatWoord(String word);
    int algemeneOpslag(BigDecimal percentage);

}
