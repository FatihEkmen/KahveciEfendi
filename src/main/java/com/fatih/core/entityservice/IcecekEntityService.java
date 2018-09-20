package com.fatih.core.entityservice;

import com.fatih.core.dao.IcecekDao;
import com.fatih.core.domain.Icecek;
import org.springframework.stereotype.Service;

/**
 * IcecekEntityService
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Service
public class IcecekEntityService extends BaseEntityService<Icecek, IcecekDao> {

    protected IcecekEntityService() {
        super(IcecekDao.class);
    }
}
