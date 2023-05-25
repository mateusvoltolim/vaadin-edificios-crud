package com.mv.application.views;

import com.mv.application.model.Apartamento;
import com.mv.application.model.Edificio;
import com.mv.application.model.Situacao;
import com.mv.application.services.ApartamentoService;
import com.mv.application.services.MoradorService;
import com.mv.application.views.events.EventReceiver;
import com.mv.application.views.events.GridEdificioEventSelection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;

import java.util.List;

public class ApartamentoLayout extends VerticalLayout implements EventReceiver {

    private final ApartamentoService apartamentoService;
    private final Grid<Apartamento> grid;

    private Edificio edificio;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ApartamentoLayout(ApartamentoService apartamentoService, MoradorService moradorService) {
        this.apartamentoService = apartamentoService;
        this.grid = new Grid<>(Apartamento.class, false);
        this.grid.addColumn(Apartamento::getNumero).setHeader("Número");
        this.grid.addColumn(Apartamento::getAndar).setHeader("Andar");
        this.grid.addColumn(Apartamento::getMetragem).setHeader("Metragem");
        this.grid.addColumn(Apartamento::getSituacao).setHeader("Situação");

        this.grid.setItemDetailsRenderer(MoradorDetailsRenderer.create(moradorService));

        var adicionar = new Button("Adicionar", event -> formCadastro().open());
        adicionar.setEnabled(false);

        ComponentUtil.addListener(grid, GridEdificioEventSelection.class,
            (ComponentEventListener) (event) -> {
                var e = (GridEdificioEventSelection) event;

                if ((this.edificio = e.getEdificio()) == null) {
                    grid.setItems(List.of());
                    adicionar.setEnabled(false);
                    return;
                }
                var apartamentos = apartamentoService.getByEdificioId(edificio.getId());
                grid.setItems(apartamentos);
                adicionar.setEnabled(true);
            }
        );

        add(
            new HorizontalLayout(
                new H2("Apartamentos"),
                adicionar
            ),
            this.grid
        );
    }

    @Override
    public Component component() {
        return grid;
    }

    private Dialog formCadastro() {
        var numero = new IntegerField("Número");
        var andar = new IntegerField("Andar");
        var metragem = new NumberField("Metragem");
        var situacao = new ComboBox<Situacao>("Situação");
        situacao.setItems(Situacao.values());
        situacao.setItemLabelGenerator(Situacao::getDescricao);

        var form = new Dialog(
            new H3("Cadastro"),
            new FormLayout(numero, andar, metragem, situacao)
        );
        form.getFooter().add(new Button("Cancelar", event -> form.close()));
        form.getFooter().add(new Button("Salvar", event -> {
            this.apartamentoService.save(new Apartamento(numero.getValue(), andar.getValue(), metragem.getValue(), situacao.getValue().name(), this.edificio));
            form.close();
        }));
        return form;
    }

}
