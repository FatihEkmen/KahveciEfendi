package com.fatih.controller;

import com.fatih.ui.EklentiKayitView;
import com.fatih.ui.IcecekKayitView;
import com.fatih.ui.RaporView;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;

/**
 * NavigatorOlustur
 *
 * Istedigimiz ekran üzerinden asagidaki view'lerin bulundugu ekranlara erisim saglayabiliriz
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class NavigatorOlustur {

    public void initNavigator(VaadinRequest vaadinRequest, Navigator navigator) {
        navigator.addView("icecekKayitView", new IcecekKayitView(vaadinRequest));
        navigator.addView("eklentiKayitView", new EklentiKayitView(vaadinRequest));
        navigator.addView("raporView", new RaporView(vaadinRequest));
    }

    public void openIcecekKayitEkrani(Navigator navigator) {
        navigator.navigateTo("icecekKayitView");
    }

    public void openEklentiKayitEkrani(Navigator navigator) {
        navigator.navigateTo("eklentiKayitView");
    }

    public void openRaporButton(Navigator navigator) {
        navigator.navigateTo("raporView");
    }
}
