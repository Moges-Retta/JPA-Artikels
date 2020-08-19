package be.vdab.Allesvoordekeuken.service;

import be.vdab.Allesvoordekeuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.assertThat;
import javax.persistence.EntityManager;
import java.math.BigDecimal;

@DataJpaTest
@Import(DefaultArtikelService.class)
@ComponentScan(value = "be.vdab.Allesvoordekeuken.repositories",
        resourcePattern = "JpaArtikelRepository.class")
@Sql("/insertArtikel.sql")
public class DefaultArtikelServiceTestIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final EntityManager manager;
    private final ArtikelService service;
    public DefaultArtikelServiceTestIntegrationTest(EntityManager manager, ArtikelService service) {
        this.service = service;
        this.manager = manager;
    }
    public int idVanTestArtikel(){
        var sql = "select id from artikels where naam='test'";
        return super.jdbcTemplate.queryForObject(sql,Integer.class);
    }
    @Test
    void verhoogVerkoopPrijs(){
        var id = idVanTestArtikel();
        service.verhoogVerkoopPrijs(id, BigDecimal.TEN);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id=?",Integer.class,id))
                .isEqualByComparingTo(10);
    }
}
