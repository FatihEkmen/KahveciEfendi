package com.fatih.core.entityservice;

import com.fatih.core.dao.IcecekSiparisDao;
import com.fatih.core.domain.IcecekSiparis;
import org.springframework.stereotype.Service;

/**
 * IcecekEntityService
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Service
public class IcecekSiparisEntityService extends BaseEntityService<IcecekSiparis, IcecekSiparisDao> {

    protected IcecekSiparisEntityService() {
        super(IcecekSiparisDao.class);
    }
}
