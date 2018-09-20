package com.fatih;

import com.fatih.controller.indirim.Indirim;
import com.fatih.core.dao.IcecekSiparisDao;
import com.fatih.core.domain.IcecekSiparis;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * IndirimTestCase
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Ignore
public class IndirimTestCase {

    private List<IcecekSiparis> icecekSiparisList = new IcecekSiparisDao().findAll();
    private Indirim indirim = new Indirim(icecekSiparisList);

    @Test
    public void yuzde25Indirim() {
        if (icecekSiparisList != null) {
            BigDecimal toplamFiyat = indirim.getToplamTutar();
            BigDecimal indirimTutari = indirim.yuzde25Indirim().setScale(2, 2);
            consoleCiktisi(toplamFiyat, indirimTutari, "Sepette %25 İndirim");
        } else {
            System.out.println("Lütfen önce sipariş kaydı oluşturunuz.");
        }
    }

    @Test
    public void uctenFazlaIcecekIndirimi() {
        if (icecekSiparisList != null) {
            BigDecimal toplamFiyat = indirim.getToplamTutar();
            BigDecimal indirimTutari = indirim.uctenFazlaIcecekIndirimi().setScale(2, 2);
            consoleCiktisi(toplamFiyat, indirimTutari, "3'ten Fazla Ürüne Özel İndirim");
        } else {
            System.out.println("Lütfen önce sipariş kaydı oluşturunuz.");
        }
    }

    @Test
    public void enYuksekIndirimiUygula() {
        if (icecekSiparisList != null) {
            BigDecimal toplamFiyat = indirim.getToplamTutar();
            BigDecimal indirimTutari = indirim.enYuksekIndirimiUygula().setScale(2, 2);
            consoleCiktisi(toplamFiyat, indirimTutari,
                    "Avantajlı Bulunan ve Uygulanan İndirim => " + indirim.getUygulananEnYuksekIndirim());
        }
    }

    private void consoleCiktisi(BigDecimal iceceklerinToplamFiyati, BigDecimal indirimTutari, String uygulananIndirim) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("İndirimden Önce Toplam Fiyat = " + iceceklerinToplamFiyati + " TL");
        System.out.println(uygulananIndirim + " " + indirimTutari + " TL");
        System.out.println("İndirimden Sonra Toplam Fiyat = " + iceceklerinToplamFiyati.subtract(indirimTutari) + " TL");
        System.out.println("------------------------------------------------------------------");
    }

}
