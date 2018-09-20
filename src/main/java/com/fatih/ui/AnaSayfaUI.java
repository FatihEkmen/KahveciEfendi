package com.fatih.ui;

import com.fatih.controller.NavigatorOlustur;
import com.fatih.controller.indirim.Indirim;
import com.fatih.core.domain.Eklenti;
import com.fatih.core.domain.Icecek;
import com.fatih.core.domain.IcecekSiparis;
import com.fatih.core.domain.Siparis;
import com.fatih.core.entityservice.EklentiEntityService;
import com.fatih.core.entityservice.IcecekEntityService;
import com.fatih.core.entityservice.SiparisEntityService;
import com.fatih.dbconnection.Connection;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.HasValue;
import com.vaadin.event.selection.MultiSelectionListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class AnaSayfaUI extends UI {

    @Autowired
    IcecekEntityService icecekEntityService;

    @Autowired
    EklentiEntityService eklentiEntityService;

    @Autowired
    SiparisEntityService siparisEntityService;

    private Indirim indirim;

    private TextField urunAdiField;
    private TextField urunFiyatField;
    private ComboBox<Icecek> icecekField;
    private ListSelect<Eklenti> eklentiSelectField;
    private Grid<IcecekSiparis> grid;

    private VerticalLayout mainLayout;
    private Navigator navigator;
    private BigDecimal icecekToplamTutari;
    private NavigatorOlustur navigatorOlusturucu;
    private List<IcecekSiparis> icecekSiparisList;
    private TextField siparisTutariField;
    private TextField indirimTutariField;
    private TextField indirimAciklamaField;
    private TextField toplamOdenecekTutarField;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Connection.initConnection((HttpServletRequest) vaadinRequest, this);
        initNavigator(vaadinRequest);
        initMainLayout();
        setContent(mainLayout);
        Notification.show("KAHVECİ EFENDİ OTOMASYON SİSTEMİ", Notification.Type.HUMANIZED_MESSAGE);
    }

    private void initMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.addComponent(initUstPanel());
        mainLayout.addComponent(initSepetPanel());
        mainLayout.addComponent(initOdemePanel());
    }

    public Panel initUstPanel() {

        Panel ustPanel = new Panel(initUstLayout());
        ustPanel.setCaption("Kahveci Efendi");
        ustPanel.addStyleName(ValoTheme.PANEL_WELL);

        return ustPanel;
    }

    public Panel initSepetPanel() {

        Panel sepetPanel = new Panel(initSepetLayout());
        sepetPanel.setCaption("Sepet");
        sepetPanel.addStyleName(ValoTheme.PANEL_WELL);

        return sepetPanel;
    }

    public Panel initOdemePanel() {

        Panel odemePanel = new Panel(initToplamTutarLayout());
        odemePanel.setCaption("Ödeme Bilgileri");
        odemePanel.addStyleName(ValoTheme.PANEL_WELL);

        return odemePanel;
    }

    private HorizontalLayout initToplamTutarLayout() {

        icecekToplamTutari = new BigDecimal(0);

        HorizontalLayout toplamLayout = new HorizontalLayout();

        siparisTutariField = new TextField();
        siparisTutariField.setCaption("Sipariş Tutarı");
        siparisTutariField.setEnabled(false);
        toplamLayout.addComponent(siparisTutariField);

        indirimTutariField = new TextField();
        indirimTutariField.setCaption("İndirim Tutarı");
        indirimTutariField.setEnabled(false);
        toplamLayout.addComponent(indirimTutariField);

        toplamOdenecekTutarField = new TextField();
        toplamOdenecekTutarField.setCaption("Ödenecek Tutar");
        toplamOdenecekTutarField.setDescription("İndirim Sonrası Ödenecek Toplam Tutar");
        toplamOdenecekTutarField.setEnabled(false);
        toplamLayout.addComponent(toplamOdenecekTutarField);

        indirimAciklamaField = new TextField();
        indirimAciklamaField.setCaption("İndirim Açıklama");
        indirimAciklamaField.setEnabled(false);
        indirimAciklamaField.setWidth("850px");
        toplamLayout.addComponent(indirimAciklamaField);

        return toplamLayout;

    }

    private void initNavigator(VaadinRequest vaadinRequest) {
        navigator = new Navigator(this, this);
        navigatorOlusturucu = new NavigatorOlustur();
        navigatorOlusturucu.initNavigator(vaadinRequest, navigator);
    }

    private VerticalLayout initSepetLayout() {
        VerticalLayout altLayout = new VerticalLayout();
        grid = new Grid<>(IcecekSiparis.class);
        grid.setSizeFull();
        grid.setHeight("200px");

        grid.removeColumn("id");
        grid.removeColumn("eklentiler");

        grid.addColumn(icecekSiparis -> {

            Set<Eklenti> eklentiler = icecekSiparis.getEklentiler();

            String eklentilerS = "";

            for (Eklenti eklenti : eklentiler) {
                eklentilerS = eklentilerS + " " + eklenti.getAdi() + " " + eklenti.getFiyat() + " TL  ";
            }

            return eklentilerS;
        });
        icecekSiparisList = new ArrayList<>();

        Button btnOdemeYap = new Button("Ödeme Yap");
        btnOdemeYap.addClickListener(e -> odemeYap());

        altLayout.addComponents(grid, btnOdemeYap);
        return altLayout;
    }

    private void odemeYap() {
        indirim = new Indirim(icecekSiparisList);

        indirim.enYuksekIndirimiUygula();

        BigDecimal odenecekTutar = indirim.getOdenecekTutar();
        BigDecimal indirimTutari = indirim.enYuksekIndirimiUygula();
        String indirimAciklama = getIndirimAciklama(indirimTutari);
        BigDecimal siparisTutari = indirim.getToplamTutar();

        siparisTutariField.setValue(siparisTutari + " TL");
        indirimTutariField.setValue(indirimTutari + " TL");
        indirimAciklamaField.setValue(indirimAciklama);
        toplamOdenecekTutarField.setValue(odenecekTutar + " TL");

        Siparis siparis = new Siparis();
        siparis.getIcecekSiparisler().addAll(icecekSiparisList);
        siparis.setSiparisTarihi(new Date());
        siparisEntityService.save(siparis);

        Notification.show(siparisTutariField.getValue(), Notification.Type.HUMANIZED_MESSAGE);
    }

    private String getIndirimAciklama(BigDecimal indirimTutari) {
        String indirimYazisi = "";
        if (indirimTutari.compareTo(new BigDecimal(0)) == 1) {
            String uygulanacakIndirim = indirim.getUygulananEnYuksekIndirim();
            indirimYazisi = "(Bu siparişinizden kazandığınız indirim => " + uygulanacakIndirim + " : "
                    + indirimTutari + " TL olarak fiyattan düşecektir.)";
        }
        return indirimYazisi;
    }

    private HorizontalLayout initUstLayout() {
        HorizontalLayout ustLayout = new HorizontalLayout();

        ustLayout.addComponent(initIslemPanel());
        ustLayout.addComponent(initYeniKayitPanel());
        ustLayout.addComponent(initUrunFiyatPanel());

        return ustLayout;
    }

    public Panel initIslemPanel() {

        Panel islemPanel = new Panel(initIslemLayout());
        islemPanel.setCaption("Ödeme Bilgileri");
        islemPanel.addStyleName(ValoTheme.PANEL_WELL);

        return islemPanel;
    }

    public Panel initYeniKayitPanel() {

        Panel yeniKayitPanel = new Panel(initYeniKayitLayout());
        yeniKayitPanel.setCaption("Yeni Kayıt");
        yeniKayitPanel.setSizeFull();
        yeniKayitPanel.addStyleName(ValoTheme.PANEL_WELL);

        return yeniKayitPanel;
    }

    public Panel initUrunFiyatPanel() {

        Panel urunFiyatPanel = new Panel(initUrunFiyatLayout());
        urunFiyatPanel.setCaption("Ürün Fiyat");
        urunFiyatPanel.setSizeFull();
        urunFiyatPanel.addStyleName(ValoTheme.PANEL_WELL);

        return urunFiyatPanel;
    }

    private VerticalLayout initYeniKayitLayout() {
        VerticalLayout yeniKayitLayout = new VerticalLayout();
        yeniKayitLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        Button btnIcecekOlustur = new Button("Yeni İçecek Kaydı");
        btnIcecekOlustur.addClickListener(clickEvent -> navigatorOlusturucu.openIcecekKayitEkrani(navigator));

        Button btnEklentiOlustur = new Button("Yeni Eklenti Kaydı");
        btnEklentiOlustur.addClickListener(clickEvent -> navigatorOlusturucu.openEklentiKayitEkrani(navigator));

        Button btnRaporla = new Button("Rapor");
        btnRaporla.addClickListener(clickEvent -> navigatorOlusturucu.openRaporButton(navigator));

        yeniKayitLayout.addComponents(btnIcecekOlustur);
        yeniKayitLayout.addComponents(btnEklentiOlustur);
        yeniKayitLayout.addComponent(btnRaporla);

        return yeniKayitLayout;
    }

    private VerticalLayout initUrunFiyatLayout() {
        VerticalLayout urunFiyatLayout = new VerticalLayout();
        urunFiyatLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        urunAdiField = new TextField();
        urunAdiField.setCaption("İçecek Adı");
        urunAdiField.setEnabled(false);

        urunFiyatField = new TextField("");
        urunFiyatField.setCaption("Toplam Tutar");
        urunFiyatField.setWidth("400px");
        urunFiyatField.setEnabled(false);

        urunFiyatLayout.addComponents(urunAdiField, urunFiyatField);

        return urunFiyatLayout;
    }

    private VerticalLayout initIslemLayout() {
        VerticalLayout islemLayout = new VerticalLayout();
        HorizontalLayout horiLayout1 = new HorizontalLayout();
        HorizontalLayout horiLayout2 = new HorizontalLayout();
        islemLayout.setWidth("700px");
        islemLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        icecekField = new ComboBox<>();
        icecekField.setItems();
        List<Icecek> icecekList = icecekEntityService.findAll();
        icecekField.setItems(icecekList);
        icecekField.addValueChangeListener((HasValue.ValueChangeListener<Icecek>) event -> {
            Icecek icecek = event.getValue();
            if (icecek != null) {
                urunAdiField.setValue(icecek.toString());
            }
            setUrunFiyatFieldValue();
        });
        icecekField.setCaption("İçecekler");

        eklentiSelectField = new ListSelect<>();
        eklentiSelectField.setCaption("Eklentiler");
        eklentiSelectField.setHeight("120px");
        List<Eklenti> eklentiList = eklentiEntityService.findAll();
        eklentiSelectField.setItems(eklentiList);
        eklentiSelectField.addSelectionListener((MultiSelectionListener<Eklenti>)
                multiSelectionEvent -> setUrunFiyatFieldValue());
        Button btnEkle = new Button();
        btnEkle.setCaption("Ekle");
        btnEkle.addClickListener((Button.ClickListener) clickEvent -> {
            siparisEkle();
        });

        horiLayout1.addComponents(icecekField, eklentiSelectField);
        horiLayout2.addComponents(btnEkle);
        islemLayout.addComponents(horiLayout1, horiLayout2);

        return islemLayout;
    }

    private void siparisEkle() {
        Icecek icecek = icecekField.getValue();

        if (icecek != null) {

            icecekToplamTutari = icecekToplamTutari.add(icecek.getFiyat());
            Set<Eklenti> eklentiler = eklentiSelectField.getValue();
            for (Eklenti eklenti : eklentiler) {
                icecekToplamTutari = icecekToplamTutari.add(eklenti.getFiyat());
            }

            IcecekSiparis icecekSiparis = new IcecekSiparis();
            icecekSiparis.setIcecek(icecek);
            icecekSiparis.getEklentiler().addAll(eklentiler);

            icecekSiparisList.add(icecekSiparis);

            grid.setItems(icecekSiparisList);
            siparisTutariField.setValue("Toplam Tutar : " + icecekToplamTutari + " TL");
            Notification.show(icecekSiparisList.toString() + "Sepetinize Eklendi", Notification.Type.HUMANIZED_MESSAGE);
            alanlariTemizle();
        } else {
            Notification.show("Lütfen içecek seçimi yapınız.", Notification.Type.ERROR_MESSAGE);
            alanlariTemizle();
        }
    }

    public void alanlariTemizle() {
        urunAdiField.setValue("");
        urunFiyatField.setValue("");
        icecekField.setValue(null);
        icecekField.setSelectedItem(null);
        eklentiSelectField.clear();
    }

    /**
     * Ekranin saginda bulunan alana eklentiler ile toplam tutar yazilir.
     */
    private void setUrunFiyatFieldValue() {
        String secilenEklentiler = "";
        BigDecimal icecekFiyati = new BigDecimal(0);
        Icecek icecek = icecekField.getValue();
        if (icecek != null) {
            icecekFiyati = icecek.getFiyat();
        }
        Set<Eklenti> eklentiler = eklentiSelectField.getValue();
        BigDecimal toplamEklentiFiyati = new BigDecimal(0);
        for (Eklenti enumEklenti : eklentiler) {
            toplamEklentiFiyati = toplamEklentiFiyati.add(enumEklenti.getFiyat());
            secilenEklentiler += "   " + enumEklenti.getAdi();
        }
        BigDecimal toplamFiyat = icecekFiyati.add(toplamEklentiFiyati);

        urunFiyatField.setValue(secilenEklentiler + "    =>    Toplam Fiyat : " + toplamFiyat + " TL");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AnaSayfaUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
