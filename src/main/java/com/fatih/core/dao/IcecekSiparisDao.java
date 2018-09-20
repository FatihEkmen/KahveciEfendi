package com.fatih.core.dao;

import com.fatih.core.domain.IcecekSiparis;
import org.springframework.stereotype.Repository;

/**
 * IcecekDao
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Repository
public class IcecekSiparisDao extends BaseDao<IcecekSiparis> {

    public IcecekSiparisDao() {
        super(IcecekSiparis.class);
    }
}
