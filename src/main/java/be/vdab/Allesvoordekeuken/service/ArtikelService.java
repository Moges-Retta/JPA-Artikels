package be.vdab.Allesvoordekeuken.service;

import java.math.BigDecimal;

public interface ArtikelService {
    void verhoogVerkoopPrijs(int id, BigDecimal percentage);
}
