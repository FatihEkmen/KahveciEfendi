package com.fatih.core.entityservice;

import com.fatih.core.dao.EklentiDao;
import com.fatih.core.domain.Eklenti;
import org.springframework.stereotype.Service;

/**
 * EklentiEntityService
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Service
public class EklentiEntityService extends BaseEntityService<Eklenti, EklentiDao> {

    protected EklentiEntityService() {
        super(EklentiDao.class);
    }
}