package com.fatih.core.dao;

import com.fatih.core.domain.Eklenti;
import org.springframework.stereotype.Repository;

/**
 * EklentiDao
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Repository
public class EklentiDao extends BaseDao<Eklenti> {

    public EklentiDao() {
        super(Eklenti.class);
    }
}
