package com.fatih.core.entityservice;

import com.fatih.core.dao.SiparisDao;
import com.fatih.core.domain.Siparis;
import com.fatih.core.enumsabitler.EnumIcecek;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * SiparisEntityService
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Service
public class SiparisEntityService extends BaseEntityService<Siparis, SiparisDao> {

    protected SiparisEntityService() {
        super(SiparisDao.class);
    }

    public List<Siparis> findSiparisListByIcecekTuruAndTarih(EnumIcecek icecekTuru, Date date){
        return getDao().findSiparisListByIcecekTuruAndTarih(icecekTuru, date);
    }
}