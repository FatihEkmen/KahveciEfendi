package com.fatih.controller.indirim;

import java.math.BigDecimal;

/**
 * IndirimTurleri
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public interface IndirimTurleri {

    BigDecimal yuzde25Indirim();

    BigDecimal uctenFazlaIcecekIndirimi();

    BigDecimal enYuksekIndirimiUygula();

}