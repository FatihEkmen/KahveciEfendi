package com.fatih.core.dao;

import com.fatih.core.domain.Icecek;
import org.springframework.stereotype.Repository;

/**
 * IcecekDao
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Repository
public class IcecekDao extends BaseDao<Icecek> {

    public IcecekDao() {
        super(Icecek.class);
    }
}
