package be.vdab.Allesvoordekeuken.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "artikelgroepen")
public class ArtikelGroep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String naam;

    @OneToMany(mappedBy = "artikelGroeps")
    //@JoinColumn(name = "artikelgroepid")
    private Set<Artikel> artikels;

    public ArtikelGroep(String naam) {
        this.naam = naam;
        this.artikels= new LinkedHashSet<>();
    }
    protected ArtikelGroep(){}

    public String getNaam() {
        return naam;
    }

    public Set<Artikel> getArtikels() {
        return Collections.unmodifiableSet(artikels);
    }

    public boolean add(Artikel artikel) {
        var toegevoegd = artikels.add(artikel);
        var oudeArtikelgroep = artikel.getArtikelGroeps();
        if (oudeArtikelgroep != null && oudeArtikelgroep != this) {
            oudeArtikelgroep.artikels.remove(artikel);
        }
        if (this != oudeArtikelgroep) {
            artikel.setArtikelGroeps(this);
        }
        return toegevoegd;
    }
}
