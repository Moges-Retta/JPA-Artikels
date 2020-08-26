package be.vdab.Allesvoordekeuken.resources;

import be.vdab.Allesvoordekeuken.domain.*;
import be.vdab.Allesvoordekeuken.repositories.JpaArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaArtikelRepository.class)
@Sql({"/insertArtikelgroep.sql","/insertArtikel.sql"}) // order is important
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private final EntityManager manager;
    private static final String ARTIKELS = "artikels";
    private Artikel artikel;
    private ArtikelGroep artikelGroep;

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }
    @BeforeEach
    void beforeEach() {
        artikelGroep = new ArtikelGroep("test");
        artikel = new Artikel("test",BigDecimal.ONE,BigDecimal.TEN,artikelGroep);
    }
    private int idVanTestArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'FoodArtikel'", Integer.class);
    }
    private int idVanTestNonFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'NonFoodArtikel'", Integer.class);
    }
    @Test
    void findById() {
        assertThat(repository.findById(idVanTestArtikel())
                .get().getNaam()).isEqualTo("FoodArtikel");
    }
    @Test
    void findByIdNonFood() {
        assertThat(repository.findById(idVanTestNonFoodArtikel())
                .get().getNaam()).isEqualTo("NonFoodArtikel");
    }
    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void createFood(){
        manager.persist(artikelGroep);// first step before create
        var artikelFood = new FoodArtrikel("Pizza", BigDecimal.ONE,BigDecimal.ONE,7,artikelGroep);
        repository.create(artikelFood);
        manager.flush();
        assertThat(artikelFood.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + artikelFood.getId())).isOne();
    }
    @Test
    void createNonFood(){
        manager.persist(artikelGroep);
        var artikelNonFood = new NonFoodArtikel("De Morgen", BigDecimal.ONE,BigDecimal.ONE,
                38,artikelGroep);
        repository.create(artikelNonFood);
        manager.flush();
        assertThat(artikelNonFood.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + artikelNonFood.getId())).isOne();
    }

    @Test
    void findNaamBevatWoord(){
        assertThat(repository.findNaamBevatWoord("pe"))
                .hasSize(super.countRowsInTableWhere(
                        "artikels",
                        "naam like 'pe'"))
                .extracting(artikel1 -> artikel1.getNaam().toLowerCase())
                .allSatisfy(naam->assertThat(naam).contains("pe"))
                .isSorted();
    }
    @Test
    void algemeneOpslag(){
        assertThat(repository.algemeneOpslag(BigDecimal.TEN))
                .isEqualTo(super.countRowsInTable(ARTIKELS));
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id=?", BigDecimal.class,
                idVanTestArtikel()))
                .isEqualByComparingTo("1100");
    }
    @Test
    void kortinglezen(){
        repository.findById(idVanTestArtikel())
                .get().getKorting()
                .forEach(korting->assertThat(korting.getPercentage()).isEqualTo(5.5));
    }
    @Test
    void artikelLazyLoaded() {
        var artikel = repository.findById(idVanTestArtikel()).get();
        assertThat(artikel.getNaam()).isEqualTo("FoodArtikel");
    }
}
