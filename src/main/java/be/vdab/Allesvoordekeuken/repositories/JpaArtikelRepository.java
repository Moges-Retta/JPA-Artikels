package be.vdab.Allesvoordekeuken.repositories;

import be.vdab.Allesvoordekeuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaArtikelRepository implements ArtikelRepository{
    private final EntityManager manager;

    public JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Artikel> findById(Integer id) {
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }

    @Override
    public void create(Artikel artikel) {
        manager.persist(artikel);
    }

    @Override
    public List<Artikel> findNaamBevatWoord(String word) {
        return manager.createNamedQuery("Artikel.findNaamBevatWoord")
                .setParameter("name",word).getResultList();

                /*.createQuery(
                "select d from Artikel d where d.naam like :word order by d.naam",
                Artikel.class).setParameter("word",word).getResultList();*/
    }
}
