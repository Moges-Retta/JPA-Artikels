package be.vdab.Allesvoordekeuken.resources;

import be.vdab.Allesvoordekeuken.domain.Artikel;
import be.vdab.Allesvoordekeuken.domain.FoodArtrikel;
import be.vdab.Allesvoordekeuken.domain.NonFoodArtikel;
import be.vdab.Allesvoordekeuken.repositories.JpaArtikelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
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
        var artikelFood = new FoodArtrikel("Pizza", BigDecimal.ONE,BigDecimal.ONE,7);
        repository.create(artikelFood);
        assertThat(artikelFood.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + artikelFood.getId())).isOne();
    }
    @Test
    void createNonFood(){
        var artikelNonFood = new NonFoodArtikel("De Morgen", BigDecimal.ONE,BigDecimal.ONE,38);

        repository.create(artikelNonFood);
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
}
