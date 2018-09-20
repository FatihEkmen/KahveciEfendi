package com.fatih.core.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Siparis
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Entity
@Table(name = "SIPARIS")
public class Siparis {

    @SequenceGenerator(name = "siparis", allocationSize = 1, sequenceName = "SIPARIS_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "siparis")
    @Id
    @Column(name = "ID")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<IcecekSiparis> icecekSiparisler = new HashSet<>();

    @Column(name = "SIPARIS_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date siparisTarihi;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<IcecekSiparis> getIcecekSiparisler() {
        return icecekSiparisler;
    }

    public void setIcecekSiparisler(Set<IcecekSiparis> icecekSiparisler) {
        this.icecekSiparisler = icecekSiparisler;
    }

    public Date getSiparisTarihi() {
        return siparisTarihi;
    }

    public void setSiparisTarihi(Date siparisTarihi) {
        this.siparisTarihi = siparisTarihi;
    }

    @Override
    public String toString() {

        String iceceklerStr = "";
        for (IcecekSiparis icecek: icecekSiparisler) {
            iceceklerStr += icecek.getIcecek() + " ";
        }

        return iceceklerStr;
    }

}
