package com.fatih.core.domain;

import com.fatih.core.enumsabitler.EnumEklenti;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Eklenti
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Entity
@Table(name = "EKLENTI")
public class Eklenti {

    @SequenceGenerator(name = "eklenti", allocationSize = 1, sequenceName = "EKLENTI_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "eklenti")
    @Id
    @Column(name = "ID")
    private Long id;

    @Size(max = 100)
    @Column(length = 100, name = "ADI")
    private String adi;

    @Column(precision = 5, scale = 2, name = "FIYAT")
    private BigDecimal fiyat;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private EnumEklenti enumEklentiTuru;


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

    public BigDecimal getFiyat() {
        return fiyat;
    }

    public EnumEklenti getEnumEklentiTuru() {
        return enumEklentiTuru;
    }

    public void setEnumEklentiTuru(EnumEklenti enumEklentiTuru) {
        this.enumEklentiTuru = enumEklentiTuru;
    }

    public void setFiyat(BigDecimal fiyat) {
        this.fiyat = fiyat;
    }

    @Override
    public String toString(){
        return adi == null ? "" : adi + " " + fiyat + " TL";
    }

}
