package be.vdab.Allesvoordekeuken.resources;

import be.vdab.Allesvoordekeuken.domain.Artikel;
import be.vdab.Allesvoordekeuken.repositories.JpaArtikelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)

public class JpaArtikelRepositoryTest  extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private static final String ARTIKELS = "artikels";

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }
    private int idVanTestMan() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'test'", Integer.class);
    }
    @Test
    void findById() {
        assertThat(repository.findById(idVanTestMan())
                .get().getNaam()).isEqualTo("test");
    }
    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }
    Artikel artikel = new Artikel("De Morgen", BigDecimal.ONE,BigDecimal.ONE);
    @Test
    void create(){
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }
}
