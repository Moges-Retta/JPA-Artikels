package be.vdab.Allesvoordekeuken.service;

import be.vdab.Allesvoordekeuken.exceptions.ArtikelNietGevondenException;
import be.vdab.Allesvoordekeuken.repositories.ArtikelRepository;
import be.vdab.Allesvoordekeuken.repositories.JpaArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
@Service
@Transactional
public class DefaultArtikelService implements ArtikelService{
    private final ArtikelRepository repository;

    public DefaultArtikelService(ArtikelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void verhoogVerkoopPrijs(int id, BigDecimal percentage) {
        repository.findById(id)
                .orElseThrow(ArtikelNietGevondenException::new)
                .verhoogVerkoopPrijs(percentage);
    }
}
