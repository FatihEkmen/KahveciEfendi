package com.fatih.ui;

import com.fatih.core.domain.Eklenti;
import com.fatih.core.entityservice.EklentiEntityService;
import com.fatih.core.enumsabitler.EnumEklenti;
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
 * EklentiKayitView
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class EklentiKayitView extends VerticalLayout implements View {

    @Autowired
    private EklentiEntityService eklentiEntityService;

    private ComboBox<EnumEklenti> eklentiTuruField;
    private TextField eklentiAdiField;
    private TextField eklentiFiyatiField;


    public EklentiKayitView(VaadinRequest vaadinRequest) {
        setSizeFull();
        Connection.initConnection((HttpServletRequest) vaadinRequest, this);

        VerticalLayout mainLayout = new VerticalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        eklentiTuruField = new ComboBox<>("Eklenti Türü");
        eklentiTuruField.setItems(EnumEklenti.values());
        eklentiAdiField = new TextField("Eklenti Adı");
        eklentiFiyatiField = new TextField("Eklenti Fiyatı");

        Button btnKaydet = new Button("Kaydet");
        btnKaydet.addClickListener(clickEvent -> kaydet());
        Button btnAnaSayfa = new Button("Ana Sayfa");
        btnAnaSayfa.addClickListener(clickEvent -> Page.getCurrent().open("http://localhost:8080/", null));


        verticalLayout.addComponent(eklentiTuruField);
        verticalLayout.addComponent(eklentiAdiField);
        verticalLayout.addComponent(eklentiFiyatiField);
        verticalLayout.addComponent(btnKaydet);
        verticalLayout.addComponent(btnAnaSayfa);


        mainLayout.addComponent(verticalLayout);
        addComponent(mainLayout);
    }

    private void kaydet() {

        if (alanKontrolleri()) {
            Eklenti eklenti = new Eklenti();
            eklenti.setAdi(eklentiAdiField.getValue());
            eklenti.setFiyat(new BigDecimal(eklentiFiyatiField.getValue()));
            eklenti.setEnumEklentiTuru(eklentiTuruField.getValue());
            eklentiEntityService.save(eklenti);

            Notification.show("Eklenti Kaydı Oluşturulmuştur.", Notification.TYPE_HUMANIZED_MESSAGE);
            alanlariTemizle();
        }

    }

    private void alanlariTemizle(){
        eklentiAdiField.setValue("");
        eklentiFiyatiField.setValue("");
        eklentiTuruField.setValue(null);
    }

    private boolean alanKontrolleri() {
        if (eklentiAdiField.getValue() == "" || eklentiTuruField.getValue().equals(null) || eklentiFiyatiField.getValue() == "") {
            Notification.show("Lütfen Tüm Alanları Doldurunuz", Notification.Type.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Eklenti Kayıt Ekranına Hoş Geldiniz.");
    }

}
