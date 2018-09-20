/*
 * Copyright 2017 Universal Bilgi Teknolojileri.
 *
 * UKL 1.1 lisansı ile lisanslanmıştır. Bu dosyanın lisans koşullarına uygun
 * olmayan şekilde kullanımı yasaklanmıştır. Lisansın bir kopyasını aşağıdaki
 * linkten edinebilirsiniz.
 *
 * http://www.uni-yaz.com/lisans/ukl_1_1.pdf
 *
 * Yasalar aksini söylemediği veya yazılı bir sözleşme ile aksi belirtilmediği sürece,
 * bu yazılım mevcut hali ile hiç bir garanti vermeden veya herhangi bir şart ileri
 * sürmeden dağıtılır. Bu yazılımın edinim izinleri ve limitler konusunda lisans
 * sözleşmesine bakınız.
 *
 */
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
