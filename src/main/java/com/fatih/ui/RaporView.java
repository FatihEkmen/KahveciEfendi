package com.fatih.ui;

import com.fatih.controller.indirim.Indirim;
import com.fatih.core.domain.Eklenti;
import com.fatih.core.domain.Icecek;
import com.fatih.core.domain.IcecekSiparis;
import com.fatih.core.domain.Siparis;
import com.fatih.core.entityservice.SiparisEntityService;
import com.fatih.core.enumsabitler.EnumIcecek;
import com.fatih.dbconnection.Connection;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * EklentiKayitView
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class RaporView extends VerticalLayout implements View {

    @Autowired
    private SiparisEntityService siparisEntityService;

    private Grid<Siparis> siparisGrid;
    private DateField tarihFiltre;
    private ComboBox<EnumIcecek> icecekTuruFiltre;

    public RaporView(VaadinRequest vaadinRequest) {
        setSizeFull();
        Connection.initConnection((HttpServletRequest) vaadinRequest, this);

        VerticalLayout mainLayout = new VerticalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();

        icecekTuruFiltre = new ComboBox<>("İçecek Türü");
        icecekTuruFiltre.setItems(EnumIcecek.values());

        tarihFiltre = new DateField("Tarih");

        Button ara = new Button("Ara");
        ara.addClickListener(clickEvent -> ara());

        Button btnAnaSayfa = new Button("Ana Sayfa");
        btnAnaSayfa.addClickListener(clickEvent -> Page.getCurrent().open("http://localhost:8080/", null));

        siparisGrid = new Grid<>(Siparis.class);
        siparisGrid.setSizeFull();
        siparisGrid.scrollToStart();
        siparisGrid.removeColumn("icecekSiparisler");

        verticalLayout.addComponent(icecekTuruFiltre);
        verticalLayout.addComponent(tarihFiltre);
        verticalLayout.addComponent(ara);
        verticalLayout.addComponent(btnAnaSayfa);
        verticalLayout.addComponent(siparisGrid);

        mainLayout.addComponent(verticalLayout);
        addComponent(mainLayout);
    }

    private void ara() {

        EnumIcecek icecekTuru = icecekTuruFiltre.getValue();
        LocalDate tarih = tarihFiltre.getValue();

        Date date = null;
        if (tarih != null) {
            date = Date.from(tarih.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        List<Siparis> siparisList = siparisEntityService.findSiparisListByIcecekTuruAndTarih(icecekTuru, date);

        siparisGrid.setItems(siparisList);

        siparisGrid.addColumn(siparis -> {

            Set<IcecekSiparis> icecekSiparisSet = siparis.getIcecekSiparisler();

            String icecekler = "";

            for (IcecekSiparis icecekSiparis : icecekSiparisSet) {

                Icecek icecek = icecekSiparis.getIcecek();
                String icecekStr = icecek.getAdi() + " -> ";
                Set<Eklenti> eklentiler = icecekSiparis.getEklentiler();

                for (Eklenti eklenti : eklentiler) {
                    icecekStr = icecekStr + eklenti.getAdi() + " ";
                }

                BigDecimal toplamFiyat = Indirim.getIcecekSiparisFiyati(icecekSiparis);

                icecekler = icecekler + " " + icecekStr + " / " + toplamFiyat + " TL  ";
            }

            return icecekler;
        });

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Eklenti Kayıt Ekranına Hoş Geldiniz.");
    }

}
