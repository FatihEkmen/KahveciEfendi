package com.fatih.core.domain;

import com.fatih.core.enumsabitler.EnumIcecek;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Icecek
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Entity
@Table(name = "ICECEK")
public class Icecek {

    @SequenceGenerator(name = "icecek", allocationSize = 1, sequenceName = "ICECEK_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "icecek")
    @Id
    @Column(name = "ID")
    private Long id;

    @Size(max = 100)
    @Column(length = 100, name = "ADI")
    private String adi;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Eklenti> eklentiler = new HashSet<>();

    @Column(precision = 5, scale = 2, name = "FIYAT")
    private BigDecimal fiyat;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private EnumIcecek enumIcecekTuru;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public Set<Eklenti> getEklentiler() {
        return eklentiler;
    }

    public void setEklentiler(Set<Eklenti> eklentiler) {
        this.eklentiler = eklentiler;
    }

    public BigDecimal getFiyat() {
        return fiyat;
    }

    public void setFiyat(BigDecimal fiyat) {
        this.fiyat = fiyat;
    }

    public EnumIcecek getEnumIcecekTuru() {
        return enumIcecekTuru;
    }

    public void setEnumIcecekTuru(EnumIcecek enumIcecekTuru) {
        this.enumIcecekTuru = enumIcecekTuru;
    }

    @Override
    public String toString() {
        return adi == null ? "" : adi + " " + fiyat + " TL";
    }

    public String getEklentileri(){
        String eklentilerStr = "";
        for (Eklenti eklenti : eklentiler) {
            eklentilerStr += eklenti.getAdi() + " " + eklenti.getFiyat() + "TL  ";
        }

        return "Eklentiler :\n" + eklentilerStr;
    }

}
