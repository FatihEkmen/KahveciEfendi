package com.fatih.ui;

import com.fatih.core.domain.Icecek;
import com.fatih.core.entityservice.IcecekEntityService;
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

/**
 * IcecekKayitView
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class IcecekKayitView extends VerticalLayout implements View {

    @Autowired
    private IcecekEntityService icecekEntityService;

    private ComboBox<EnumIcecek> icecekTuruField;
    private TextField icecekAdiField;
    private TextField icecekFiyatiField;

    private VerticalLayout mainLayout;

    public IcecekKayitView(VaadinRequest vaadinRequest) {
        setSizeFull();
        Connection.initConnection((HttpServletRequest) vaadinRequest, this);

        mainLayout = new VerticalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        icecekTuruField = new ComboBox<>("İçecek Türü");
        icecekTuruField.setItems(EnumIcecek.values());
        icecekAdiField = new TextField("İçecek Adı");
        icecekFiyatiField = new TextField("İçecek Fiyatı");

        Button btnKaydet = new Button("Kaydet");
        btnKaydet.addClickListener(clickEvent -> kaydet());
        Button btnAnaSayfa = new Button("Ana Sayfa");
        btnAnaSayfa.addClickListener(clickEvent -> Page.getCurrent().open("http://localhost:8080/", null));

        verticalLayout.addComponent(icecekTuruField);
        verticalLayout.addComponent(icecekAdiField);
        verticalLayout.addComponent(icecekFiyatiField);
        verticalLayout.addComponent(btnKaydet);
        verticalLayout.addComponent(btnAnaSayfa);


        mainLayout.addComponent(verticalLayout);
        addComponent(mainLayout);
    }

    private void kaydet() {

        if (alanKontrolleri()) {
            Icecek icecek = new Icecek();
            icecek.setAdi(icecekAdiField.getValue());
            icecek.setFiyat(new BigDecimal(icecekFiyatiField.getValue()));
            icecek.setEnumIcecekTuru(icecekTuruField.getValue());
            icecekEntityService.save(icecek);

            Notification.show("İçecek Kaydı Oluşturulmuştur.", Notification.TYPE_HUMANIZED_MESSAGE);
            alanlariTemizle();
        }

    }

    private void alanlariTemizle() {
        icecekAdiField.setValue("");
        icecekFiyatiField.setValue("");
        icecekTuruField.setValue(null);
    }

    private boolean alanKontrolleri() {
        if (icecekAdiField.getValue() == "" || icecekTuruField.getValue().equals(null) || icecekFiyatiField.getValue() == "") {
            Notification.show("Lütfen Tüm ALanları Doldurunuz", Notification.Type.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("İçecek Kayıt Ekranına Hoş Geldiniz.");
    }
}

