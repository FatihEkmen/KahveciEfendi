package com.fatih.core.enumsabitler;

/**
 * EnumEklenti
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public enum EnumEklenti {

    SUT("Süt"),
    FINDIK_SURUBU("Fındık Şurubu"),
    CIKOLATA_SOSU("Çikolata Sosu"),
    LIMON("Limon");

    EnumEklenti(String adi) {
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

