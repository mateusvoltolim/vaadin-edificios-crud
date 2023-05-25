package com.mv.application.views.events;


import com.mv.application.model.Edificio;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

import java.util.Collection;

public class GridEdificioEventSelection extends ComponentEvent<Component> {

    private final Edificio edificio;
    public GridEdificioEventSelection(Component source, Edificio edificio) {
        super(source, false);
        this.edificio = edificio;
    }

    public Edificio getEdificio() {
        return edificio;
    }
}
