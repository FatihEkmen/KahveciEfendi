///*
// * Copyright 2017 Universal Bilgi Teknolojileri.
// *
// * UKL 1.1 lisansı ile lisanslanmıştır. Bu dosyanın lisans koşullarına uygun
// * olmayan şekilde kullanımı yasaklanmıştır. Lisansın bir kopyasını aşağıdaki
// * linkten edinebilirsiniz.
// *
// * http://www.uni-yaz.com/lisans/ukl_1_1.pdf
// *
// * Yasalar aksini söylemediği veya yazılı bir sözleşme ile aksi belirtilmediği sürece,
// * bu yazılım mevcut hali ile hiç bir garanti vermeden veya herhangi bir şart ileri
// * sürmeden dağıtılır. Bu yazılımın edinim izinleri ve limitler konusunda lisans
// * sözleşmesine bakınız.
// *
// */
//package com.fatih.ui;
//
//import com.fatih.core.domain.Eklenti;
//import com.fatih.core.domain.Icecek;
//import com.fatih.core.domain.Siparis;
//import com.fatih.core.entityservice.EklentiEntityService;
//import com.fatih.core.entityservice.IcecekEntityService;
//import com.vaadin.ui.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Set;
//
///**
// * IslemPage
// *
// * @author M.Fatih Ekmen
// * @since 1.0.0
// */
//public class IslemPage extends VerticalLayout {
//
//    private IcecekEntityService icecekEntityService;
//
//    private EklentiEntityService eklentiEntityService;
//
//    private ComboBox<Icecek> icecekField;
//    private ListSelect<Eklenti> eklentiSelectField;
//    private String urunAdi;
//    private BigDecimal icecekToplamTutari;
//    private Set<Siparis> siparisList;
//
//    public IslemPage() {
//        super();
//        eklentiEntityService = ((AnaSayfaUI) UI.getCurrent()).getApplicationContext().getBean(EklentiEntityService.class);
//        icecekEntityService = ((AnaSayfaUI) UI.getCurrent()).getApplicationContext().getBean(IcecekEntityService.class);
//
//        init();
//    }
//
//    private void init() {
//            VerticalLayout islemLayout = new VerticalLayout();
//            HorizontalLayout horiLayout1 = new HorizontalLayout();
//            HorizontalLayout horiLayout2 = new HorizontalLayout();
//            islemLayout.setWidth("700px");
//            islemLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
//
//            icecekField = new ComboBox<>();
//            icecekField.setItems();
//            List<Icecek> icecekList = icecekEntityService.findAll();
//            icecekField.setItems(icecekList);
//            icecekField.setCaption("İçecekler");
//
//            eklentiSelectField = new ListSelect<>();
//            eklentiSelectField.setCaption("Eklentiler");
//            eklentiSelectField.setHeight("120px");
//            List<Eklenti> eklentiList = eklentiEntityService.findAll();
//            eklentiSelectField.setItems(eklentiList);
//            Button btnEkle = new Button();
//            btnEkle.setCaption("Ekle");
//            btnEkle.addClickListener((Button.ClickListener) clickEvent -> {
//                siparisEkle();
//            });
//
//            horiLayout1.addComponents(icecekField, eklentiSelectField);
//            horiLayout2.addComponents(btnEkle);
//            islemLayout.addComponents(horiLayout1, horiLayout2);
//
//            addComponent(islemLayout);
//    }
//
//    private void siparisEkle() {
//        Icecek icecek = icecekField.getValue();
//
//        if (icecek != null) {
//            icecekToplamTutari = icecekToplamTutari.add(icecek.getFiyat());
//            Set<Eklenti> eklentiler = eklentiSelectField.getValue();
//            for (Eklenti eklenti : eklentiler) {
//                icecekToplamTutari = icecekToplamTutari.add(eklenti.getFiyat());
//            }
//            icecek.getEklentiler().addAll(eklentiler);
//            Siparis siparis = new Siparis();
//            siparis.getIcecekler().add(icecek);
//            siparisList.add(siparis);
//            Notification.show(siparis.toString() + "Sepetinize Eklendi", Notification.Type.HUMANIZED_MESSAGE);
//            alanlariTemizle();
//        } else {
//            Notification.show("Lütfen içecek seçimi yapınız.", Notification.Type.ERROR_MESSAGE);
//            alanlariTemizle();
//        }
//    }
//
//    public void alanlariTemizle() {
//        icecekField.setValue(null);
//        eklentiSelectField.clear();
//    }
//
//    public ComboBox<Icecek> getIcecekField() {
//        return icecekField;
//    }
//
//    public ListSelect<Eklenti> getEklentiSelectField() {
//        return eklentiSelectField;
//    }
//
//    public Set<Siparis> getSiparisList() {
//        return siparisList;
//    }
//}
