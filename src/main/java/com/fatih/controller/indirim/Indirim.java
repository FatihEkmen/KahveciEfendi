package com.fatih.controller.indirim;

import com.fatih.core.domain.Eklenti;
import com.fatih.core.domain.Icecek;
import com.fatih.core.domain.IcecekSiparis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Indirim
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class Indirim implements IndirimTurleri {

    private BigDecimal toplamTutar = BigDecimal.ZERO;
    private BigDecimal odenecekTutar = BigDecimal.ZERO;
    private List<Icecek> sepettekiIcecekler = new ArrayList<>();
    private List<Eklenti> sepettekiEklentiler = new ArrayList<>();
    private List<IcecekSiparis> icecekSiparisList = new ArrayList<>();
    private String uygulananEnYuksekIndirim = "";

    public Indirim(List<IcecekSiparis> icecekSiparisList) {

        this.icecekSiparisList = icecekSiparisList;

        for (IcecekSiparis icecekSiparis : icecekSiparisList) {
            Icecek icecek = icecekSiparis.getIcecek();

            Set<Eklenti> eklentiler = icecekSiparis.getEklentiler();
            sepettekiIcecekler.add(icecek);
            sepettekiEklentiler.addAll(eklentiler);

            BigDecimal eklentiFiyati = BigDecimal.ZERO;
            for (Eklenti eklenti : eklentiler) {
                eklentiFiyati = eklentiFiyati.add(eklenti.getFiyat());
            }

            BigDecimal eachIcecekSiparisFiyati = icecek.getFiyat().add(eklentiFiyati);

            toplamTutar = toplamTutar.add(eachIcecekSiparisFiyati);
            odenecekTutar = toplamTutar;
        }

    }

    public Indirim(List<Icecek> sepettekiIcecekler, List<Eklenti> sepettekiEklentiler) {
//        icecekSiparisList = new HashSet<>();
//        toplamTutar = new BigDecimal(0);
//        this.sepettekiIcecekler = icecekSiparisList.iterator().next().getIcecekler();
//        this.sepettekiEklentiler = icecekSiparisList.iterator().next().getIcecekler().iterator().next().getEklentiler();
//        for (Icecek icecek : sepettekiIcecekler) {
//            toplamTutar = toplamTutar.add(icecek.getFiyat());
//        }
//        if (sepettekiEklentiler != null) {
//            for (Eklenti eklenti : sepettekiEklentiler) {
//                toplamTutar = toplamTutar.add(eklenti.getFiyat());
//            }
//        }
    }

    @Override
    public BigDecimal yuzde25Indirim() {
        if (toplamTutar.compareTo(new BigDecimal(12)) >= 0) {
            return toplamTutar.multiply(new BigDecimal("0.25"));
        }
        return new BigDecimal(0);
    }

    @Override
    public BigDecimal uctenFazlaIcecekIndirimi() {
        if (sepettekiIcecekler.size() >= 3) {
            BigDecimal enUcuzIcecekFiyati = getIcecekSiparisFiyati(icecekSiparisList.get(0));

            for (IcecekSiparis icecekSiparis : icecekSiparisList) {

                BigDecimal eachIcecekFiyati = getIcecekSiparisFiyati(icecekSiparis);

                if (eachIcecekFiyati.compareTo(enUcuzIcecekFiyati) < 0) {
                    enUcuzIcecekFiyati = eachIcecekFiyati;
                }
            }
            return enUcuzIcecekFiyati;
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal getIcecekSiparisFiyati(IcecekSiparis icecekSiparis) {

        BigDecimal icecekFiyati = icecekSiparis.getIcecek().getFiyat();
        Set<Eklenti> eklentiler = icecekSiparis.getEklentiler();

        for (Eklenti eklenti : eklentiler) {
            icecekFiyati = icecekFiyati.add(eklenti.getFiyat());
        }

        return icecekFiyati;
    }

    /**
     * Yapılan en yüksek indirimi döndürür.
     *
     * @return
     */
    @Override
    public BigDecimal enYuksekIndirimiUygula() {
        BigDecimal uctenFazlaIcecekIndirimi = uctenFazlaIcecekIndirimi();
        BigDecimal yuzde25Indirim = yuzde25Indirim();
        if (uctenFazlaIcecekIndirimi.compareTo(yuzde25Indirim) == 1) {
            uygulananEnYuksekIndirim = "3'ten Fazla Ürüne Özel İndirim";
            odenecekTutar = toplamTutar.subtract(uctenFazlaIcecekIndirimi);
            return uctenFazlaIcecekIndirimi;
        } else {
            uygulananEnYuksekIndirim = "Sepette %25 İndirim";
            odenecekTutar = toplamTutar.subtract(yuzde25Indirim);
            return yuzde25Indirim;
        }
    }

    public BigDecimal getToplamTutar() {
        return toplamTutar;
    }

    public String getUygulananEnYuksekIndirim() {
        return uygulananEnYuksekIndirim;
    }

    public BigDecimal getOdenecekTutar() {
        return odenecekTutar;
    }
}
