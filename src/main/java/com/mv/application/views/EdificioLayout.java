package com.mv.application.views;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.mv.application.model.Edificio;
import com.mv.application.services.EdificioService;
import com.mv.application.views.events.GridEdificioEventSelection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class EdificioLayout extends VerticalLayout {

    private final EdificioService service;
    private final Grid<Edificio> grid;

    public EdificioLayout(EdificioService edificioService) {
        this.service = edificioService;
        this.grid = new Grid<>(Edificio.class, false);
        this.grid.setItems(edificioService.all());

        var nomeColumn = this.grid.addColumn(Edificio::getNome).setHeader("Nome");
        var enderecoColumn = this.grid.addColumn(Edificio::getEndereco).setHeader("Endereço");


        createEditorColumn();
        createBinder(nomeColumn, enderecoColumn);

        add(
            new HorizontalLayout(
                new H2("Edifícios"),
                new Button("Adicionar", event -> formCadastro().open())
            ),
            this.grid
        );
    }

    public EdificioLayout addReceiverToGridSelection(Component component) {
        this.grid.addSelectionListener(selection ->
            ComponentUtil.fireEvent(component,
                new GridEdificioEventSelection(component, selection.getFirstSelectedItem().orElse(null))
            )
        );
        return this;
    }

    private void createBinder(Grid.Column<Edificio> nomeColumn, Grid.Column<Edificio> enderecoColumn) {
        var editor = this.grid.getEditor();
        var binder = new Binder<>(Edificio.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        var nome = new TextField();
        nome.setWidthFull();
        binder.forField(nome).bind(Edificio::getNome, Edificio::setNome);
        nomeColumn.setEditorComponent(nome);

        var endereco = new TextField();
        endereco.setWidthFull();
        binder.forField(endereco).bind(Edificio::getEndereco, Edificio::setEndereco);
        enderecoColumn.setEditorComponent(endereco);
    }

    private void createEditorColumn() {
        var editor = this.grid.getEditor();

        var editComumn = this.grid.addComponentColumn(edificio ->
            new Button(FontAwesome.Regular.EDIT.create(), event -> {
                if (editor.isOpen()) {
                    editor.cancel();
                }
                editor.editItem(edificio);
            })
        );
        editComumn.setTextAlign(ColumnTextAlign.END);
        editComumn.setEditorComponent(createEditorComponent(editor));

        this.grid.getEditor().addSaveListener(event -> {
            this.service.save(event.getItem());
        });
    }

    private HorizontalLayout createEditorComponent(Editor<Edificio> editor) {
        var fechar = new Button(FontAwesome.Solid.X.create(), e -> editor.cancel());
        var salvar = new Button(FontAwesome.Regular.SAVE.create(), event -> editor.save());
        fechar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        var acoes = new HorizontalLayout(salvar, fechar);
        acoes.setSpacing(false);
        acoes.setPadding(false);
        acoes.setMargin(false);
        acoes.setJustifyContentMode(JustifyContentMode.END);
        return acoes;
    }

    private Dialog formCadastro() {
        var nome = new TextField("Nome");
        var endereco = new TextField("Endereço");
        var form = new Dialog(
            new H3("Cadastro"),
            new FormLayout(nome, endereco)
        );
        form.getFooter().add(new Button("Cancelar", event -> form.close()));
        form.getFooter().add(new Button("Salvar", event -> {
            this.service.save(new Edificio(nome.getValue(), endereco.getValue()));
            form.close();
        }));
        return form;
    }

}
