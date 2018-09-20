package com.fatih.core.enumsabitler;

/**
 * EnumIcecek
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public enum EnumIcecek {

    FILTRE_KAHVE("Filtre Kahve"),
    LATTE("Latte"),
    MOCHA("Mocha"),
    CAY("Çay"),
    TURK_KAHVESI("Türk Kahvesi");

    EnumIcecek(String adi) {
        this.adi = adi;
    }

    private final String adi;

    public String getAdi() {
        return adi;
    }

    @Override
    public String toString() {
        return adi;
    }

}

