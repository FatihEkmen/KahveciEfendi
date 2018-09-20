package com.fatih.controller.factory;

import com.fatih.core.domain.Icecek;

/**
 * IcecekFactory
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class IcecekFactory {

    /**
     *
     * @param icecekClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Icecek icecekGetir(Class icecekClass) throws IllegalAccessException, InstantiationException {
        return (Icecek) icecekClass.newInstance();
    }

}
