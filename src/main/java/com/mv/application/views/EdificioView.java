package com.mv.application.views;

import com.mv.application.services.ApartamentoService;
import com.mv.application.services.EdificioService;
import com.mv.application.services.MoradorService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class EdificioView extends VerticalLayout {

    public EdificioView(EdificioService edificioService, ApartamentoService apartamentoService, MoradorService moradorService) {
        var apartamentoLayout = new ApartamentoLayout(apartamentoService, moradorService);
        var edificioLayout = new EdificioLayout(edificioService)
            .addReceiverToGridSelection(apartamentoLayout.component());

        var splitLayout = new SplitLayout(edificioLayout, apartamentoLayout);
        splitLayout.setSizeFull();
        splitLayout.setSplitterPosition(40);
        add(splitLayout);
        setSizeFull();
    }

}
