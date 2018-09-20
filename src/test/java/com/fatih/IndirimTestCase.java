package com.fatih;

import com.fatih.controller.indirim.Indirim;
import com.fatih.core.dao.EklentiDao;
import com.fatih.core.dao.IcecekDao;
import com.fatih.core.domain.Eklenti;
import com.fatih.core.domain.Icecek;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * IndirimTestCase
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Ignore
public class IndirimTestCase {

    @Test
    public void yuzde25Indirim() {
        List<Icecek> icecekList = new IcecekDao().findAll();
        List<Eklenti> eklentiList = new EklentiDao().findAll();
        BigDecimal iceceklerinToplamFiyati = BigDecimal.ZERO;
        BigDecimal eklentilerinToplamFiyati = BigDecimal.ZERO;
        BigDecimal toplamFiyat = BigDecimal.ZERO;
        for (Icecek icecek : icecekList) {
            iceceklerinToplamFiyati = iceceklerinToplamFiyati.add(icecek.getFiyat());
        }
        for (Eklenti eklenti : eklentiList) {
            eklentilerinToplamFiyati = eklentilerinToplamFiyati.add(eklenti.getFiyat());
        }
        toplamFiyat = iceceklerinToplamFiyati.add(eklentilerinToplamFiyati);
        BigDecimal indirimTutari = new Indirim(icecekList, eklentiList).yuzde25Indirim().setScale(2, 2);
        consoleCiktisi(toplamFiyat, indirimTutari, "Sepette %25 İndirim");
    }

    @Test
    public void uctenFazlaIcecekIndirimi() {
        List<Icecek> icecekList = new IcecekDao().findAll();

        BigDecimal iceceklerinToplamFiyati = BigDecimal.ZERO;
        for (Icecek icecek : icecekList) {
            iceceklerinToplamFiyati = iceceklerinToplamFiyati.add(icecek.getFiyat());
            Set<Eklenti> eklentiler = icecek.getEklentiler();
            for (Eklenti eklenti : eklentiler) {
                iceceklerinToplamFiyati = iceceklerinToplamFiyati.add(eklenti.getFiyat());
            }
        }
        BigDecimal indirimTutari = new Indirim(icecekList, null).uctenFazlaIcecekIndirimi().setScale(2, 2);
        consoleCiktisi(iceceklerinToplamFiyati, indirimTutari, "3'ten Fazla Ürüne Özel İndirim");
    }

    @Test
    public void enYuksekIndirimiUygula() {
        List<Icecek> icecekList = new IcecekDao().findAll();
        List<Eklenti> eklentiList = new EklentiDao().findAll();

        BigDecimal iceceklerinToplamFiyati = BigDecimal.ZERO;
        BigDecimal eklentilerinToplamFiyati = BigDecimal.ZERO;
        BigDecimal toplamFiyat = BigDecimal.ZERO;
        for (Icecek icecek : icecekList) {
            iceceklerinToplamFiyati = iceceklerinToplamFiyati.add(icecek.getFiyat());
        }
        for (Eklenti eklenti : eklentiList) {
            eklentilerinToplamFiyati = eklentilerinToplamFiyati.add(eklenti.getFiyat());
        }
        toplamFiyat = iceceklerinToplamFiyati.add(eklentilerinToplamFiyati);
        Indirim indirim = new Indirim(icecekList, eklentiList);
        BigDecimal enYuksekIndirimiUygula = indirim.enYuksekIndirimiUygula().setScale(2, 2);

        consoleCiktisi(toplamFiyat, toplamFiyat.subtract(enYuksekIndirimiUygula),
                "Avantajlı Bulunan ve Uygulanan İndirim => " + indirim.getUygulananEnYuksekIndirim());

    }

    private void consoleCiktisi(BigDecimal iceceklerinToplamFiyati, BigDecimal indirimTutari, String uygulananIndirim) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("İndirimden Önce Toplam Fiyat = " + iceceklerinToplamFiyati + " TL");
        System.out.println(uygulananIndirim + " " + indirimTutari + " TL");
        System.out.println("İndirimden Sonra Toplam Fiyat = " + iceceklerinToplamFiyati.subtract(indirimTutari) + " TL");
        System.out.println("------------------------------------------------------------------");
    }

}
