package com.fatih.controller.factory;

import com.vaadin.ui.Layout;

/**
 * LayoutFactory
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class LayoutFactory {

    /**
     * Vertical ve Horizontal Layout'lar buradan olusturulabilir
     *
     * @param layoutClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Layout createLayout(Class layoutClass) throws IllegalAccessException, InstantiationException {
        return (Layout) layoutClass.newInstance();
    }

}
