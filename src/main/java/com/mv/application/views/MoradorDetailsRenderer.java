package com.mv.application.views;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.mv.application.model.Apartamento;
import com.mv.application.model.Morador;
import com.mv.application.services.MoradorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.stream.Stream;

public class MoradorDetailsRenderer extends FormLayout {

    private final MoradorService moradorService;
    private final TextField nome = new TextField("Nome");
    private final TextField cpf = new TextField("CPF");
    private final TextField telefone = new TextField("Telefone");

    private final Button editar;
    private final Button deletar;

    private Apartamento apartamento;
    private Morador morador;

    private MoradorDetailsRenderer(MoradorService moradorService) {
        this.moradorService = moradorService;
        var title = new H3("Morador");
        title.getStyle().set("font-weight", "bold");

        var header = new HorizontalLayout(title);
        header.getStyle().set("margin-top", "1.5rem");
        header.setAlignItems(FlexComponent.Alignment.BASELINE);

        var salvar = new Button(FontAwesome.Regular.SAVE.create());
        salvar.getStyle().set("padding", "0");
        salvar.getStyle().set("margin", "0");

        editar = new Button(FontAwesome.Regular.EDIT.create());
        editar.getStyle().set("padding", "0");
        editar.getStyle().set("margin", "0");
        editar.addClickListener(event -> {
            Stream.of(nome, cpf, telefone).forEach(f -> f.setReadOnly(false));
            header.replace(editar, salvar);
        });

        deletar = new Button(FontAwesome.Regular.TRASH_CAN.create());
        deletar.addClickListener(event -> {
            moradorService.deleteById(morador.getId());
            Stream.of(nome, cpf, telefone).forEach(f -> {
                f.setValue("");
                f.setReadOnly(true);
            });
            deletar.setVisible(false);
        });

        salvar.addClickListener(event -> {
            Stream.of(nome, cpf, telefone).forEach(f -> f.setReadOnly(true));
            morador = moradorService.save(new Morador(morador == null ? null : morador.getId(), nome.getValue(), cpf.getValue(), telefone.getValue(), apartamento));
            header.replace(salvar, editar);
            deletar.setVisible(true);
        });

        header.add(editar, deletar);

        setColspan(header, 3);
        add(header);
        Stream.of(nome, cpf, telefone).forEach(f -> {
            f.setReadOnly(true);
            add(f);
        });

        setResponsiveSteps(new ResponsiveStep("0", 3));
    }

    public void init(Apartamento apartamento) {
        this.apartamento = apartamento;
        var morador = moradorService.getByApartamentoId(apartamento.getId());
        if ((this.morador = morador) == null) {
            deletar.setVisible(false);
            return;
        }

        deletar.setVisible(true);
        nome.setValue(morador.getNome());
        cpf.setValue(morador.getCpf());
        telefone.setValue(morador.getTelefone());
    }

    public static ComponentRenderer<MoradorDetailsRenderer, Apartamento> create(MoradorService service) {
        return new ComponentRenderer<>(() -> new MoradorDetailsRenderer(service), MoradorDetailsRenderer::init);
    }

}
