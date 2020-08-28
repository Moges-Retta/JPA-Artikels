package be.vdab.Allesvoordekeuken.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="artikels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soort")
@NamedEntityGraph(name = Artikel.MET_ARTIKELGROEP,
        attributeNodes = @NamedAttributeNode("artikelGroeps"))
public class Artikel {
    public static final String MET_ARTIKELGROEP = "Artikel.metArtikelGroep";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String naam;
    private BigDecimal aankoopprijs;
    private BigDecimal verkoopprijs;
    @ElementCollection
    @CollectionTable(name = "kortingen",
            joinColumns = @JoinColumn(name = "artikelid"))
    private Set<Korting> kortingen;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "artikelgroepid")
    private ArtikelGroep artikelGroeps;

    public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs,
                   ArtikelGroep artikelGroeps) {
        this.naam = naam;
        this.aankoopprijs = aankoopprijs;
        this.verkoopprijs = verkoopprijs;
        this.kortingen = new LinkedHashSet<>();
        setArtikelGroeps(artikelGroeps);
    }
    protected Artikel(){
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public BigDecimal getAankoopprijs() {
        return aankoopprijs;
    }

    public void setAankoopprijs(BigDecimal aankoopprijs) {
        this.aankoopprijs = aankoopprijs;
    }

    public BigDecimal getVerkoopprijs() {
        return verkoopprijs;
    }

    public void setVerkoopprijs(BigDecimal verkoopprijs) {
        this.verkoopprijs = verkoopprijs;
    }
    public void verhoogVerkoopPrijs(BigDecimal bedrag) {
        if (bedrag.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        verkoopprijs=verkoopprijs.add(bedrag);
    }

    public Set<Korting> getKorting() {
        return Collections.unmodifiableSet(kortingen);
    }
    public boolean addKorting(Korting korting) {
        return kortingen.add(korting);
    }
    public boolean removeBijnaam(Korting korting) {
        return kortingen.remove(korting);
    }

    public ArtikelGroep getArtikelGroeps() {
        return artikelGroeps;
    }

    public void setArtikelGroeps(ArtikelGroep artikelGroeps) {
        if (!artikelGroeps.getArtikels().contains(this)) {
            artikelGroeps.add(this);
        }
        this.artikelGroeps = artikelGroeps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artikel)) return false;
        Artikel artikel = (Artikel) o;
        return naam.equals(artikel.naam) &&
                aankoopprijs.equals(artikel.aankoopprijs) &&
                verkoopprijs.equals(artikel.verkoopprijs) &&
                kortingen.equals(artikel.kortingen) &&
                artikelGroeps.equals(artikel.artikelGroeps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam, aankoopprijs, verkoopprijs, kortingen, artikelGroeps);
    }
}
