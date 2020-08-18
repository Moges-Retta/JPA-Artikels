package be.vdab.Allesvoordekeuken.repositories;

import be.vdab.Allesvoordekeuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
}