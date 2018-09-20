package com.fatih.core.dao;

import com.fatih.core.domain.Siparis;
import com.fatih.core.enumsabitler.EnumIcecek;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * SiparisDao
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Repository
public class SiparisDao extends BaseDao<Siparis> {

    public SiparisDao() {
        super(Siparis.class);
    }

    public List<Siparis> findSiparisListByIcecekTuruAndTarih(EnumIcecek icecekTuru, Date date){

        Criteria criteria = getCurrentSession().createCriteria(Siparis.class);

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Siparis.class,"siparis");

        detachedCriteria.createAlias("siparis.icecekSiparisler", "icecekSiparis", JoinType.INNER_JOIN);
        detachedCriteria.createAlias("icecekSiparis.icecek", "icecek", JoinType.INNER_JOIN);

        if (icecekTuru != null){
            detachedCriteria.add(Restrictions.eq("icecek.enumIcecekTuru", icecekTuru));
        }

        if (date != null){
            detachedCriteria.add(Restrictions.ge("siparis.siparisTarihi", date));
        }

        detachedCriteria.setProjection(Projections.distinct(Projections.projectionList()
                .add(Projections.property("id"))
        ));

        criteria.add(Restrictions.disjunction().add(Property.forName("id").in(detachedCriteria)));

        return criteria.list();
    }

}
