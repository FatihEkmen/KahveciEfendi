package com.fatih.core.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Icecek
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Entity
@Table(name = "ICECEK_SIPARIS")
public class IcecekSiparis {

    @SequenceGenerator(name = "iceceksiparis", allocationSize = 1, sequenceName = "ICECEK_SIPARIS_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "iceceksiparis")
    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ICECEK")
    private Icecek icecek;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Eklenti> eklentiler = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Eklenti> getEklentiler() {
        return eklentiler;
    }

    public void setEklentiler(Set<Eklenti> eklentiler) {
        this.eklentiler = eklentiler;
    }

    public Icecek getIcecek() {
        return icecek;
    }

    public void setIcecek(Icecek icecek) {
        this.icecek = icecek;
    }

    @Override
    public String toString() {
        return icecek.getAdi();
    }

}
