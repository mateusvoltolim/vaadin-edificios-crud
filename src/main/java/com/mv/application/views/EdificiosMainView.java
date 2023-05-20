package com.mv.application.views;

import com.mv.application.model.Apartamento;
import com.mv.application.model.Edificio;
import com.mv.application.model.Morador;
import com.mv.application.model.Situacao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Edifícios CRUD")
@Route(value = "")
public class EdificiosMainView extends VerticalLayout {

    private static final AtomicInteger id = new AtomicInteger(0);

    private final List<Edificio> edificios = new ArrayList<>(
        List.of(
            new Edificio(id.getAndIncrement(), "Ed. Rosana", "Rua A", List.of(
                new Apartamento(101, 1, 44.0, Situacao.ALUGADO.getDescricao(),
                    new Morador("Mateus", "000.000.000-01", "(99) 9 9999-9999"))
            )),
            new Edificio(id.getAndIncrement(), "Ed. Estrela", "Rua B", List.of(
                new Apartamento(101, 1, 44.0, Situacao.QUITADO.getDescricao(),
                    new Morador("João", "000.000.000-02", "(99) 9 9999-9999"))
            )),
            new Edificio(id.getAndIncrement(), "Ed. Zigma", "Rua 15", List.of(
                new Apartamento(101, 1, 44.0, Situacao.FINANCIADO.getDescricao(),
                    new Morador("Maria", "000.000.000-03", "(99) 9 9999-9999"))
            ))
        )
    );

    public EdificiosMainView() {
        var edificiosGrid = new Grid<>(Edificio.class, false);
        edificiosGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        edificiosGrid.addColumn(Edificio::nome).setHeader("Nome");
        edificiosGrid.addColumn(Edificio::endereco).setHeader("Endereço");
        edificiosGrid.setItems(edificios);

        var nomeEdificioCad = new TextField("nome");
        var enderecoEdificioCad = new TextField("endereco");

        var formEdificioCadastro = new Dialog(new FormLayout(nomeEdificioCad, enderecoEdificioCad));
        formEdificioCadastro.getFooter().add(new Button("Cancelar", event -> formEdificioCadastro.close()));
        formEdificioCadastro.getFooter().add(new Button("Salvar", event -> {
            edificios.add(new Edificio(id.getAndIncrement(), nomeEdificioCad.getValue(), enderecoEdificioCad.getValue(), new ArrayList<>()));
            edificiosGrid.getDataProvider().refreshAll();
            formEdificioCadastro.close();
        }));

        var adicionarEdificio = new Button("Adicionar", event -> {
            formEdificioCadastro.open();
            nomeEdificioCad.setValue("");
            enderecoEdificioCad.setValue("");
        });

        var nomeEdificioEdit = new TextField("nome");
        var enderecoEdificioEdit = new TextField("endereco");

        var formEdificioEdicao = new Dialog(new FormLayout(nomeEdificioEdit, enderecoEdificioEdit));
        formEdificioEdicao.getFooter().add(new Button("Cancelar", event -> formEdificioEdicao.close()));
        formEdificioEdicao.getFooter().add(new Button("Salvar", event -> {
            edificiosGrid.getSelectedItems().stream().findFirst().ifPresent(e -> {
                var idx = edificios.indexOf(e);
                edificios.remove(e);
                edificios.add(idx, new Edificio(e.id(), nomeEdificioEdit.getValue(), enderecoEdificioEdit.getValue(), e.apartamentos()));
                edificiosGrid.getDataProvider().refreshAll();
                formEdificioEdicao.close();
            });
        }));

        var editarEdificio = new Button("Editar", event -> {
            formEdificioEdicao.open();
            edificiosGrid.getSelectedItems().stream().findFirst().ifPresent(e -> {
                nomeEdificioEdit.setValue(e.nome());
                enderecoEdificioEdit.setValue(e.endereco());
            });
        });
        editarEdificio.setVisible(false);

        var edificiosLayout = new VerticalLayout(
            new HorizontalLayout(new H2("Edifícios"), adicionarEdificio, editarEdificio),
            edificiosGrid
        );

        var apartamentosLayout = new VerticalLayout(
            new H2("Apartamentos")
        );
        apartamentosLayout.setVisible(false);

        var apartGrid = new Grid<>(Apartamento.class, false);
        apartGrid.addColumn(Apartamento::numero).setHeader("Número");
        apartGrid.addColumn(Apartamento::andar).setHeader("Andar");
        apartGrid.addColumn(Apartamento::metragem).setHeader("Metragem");
        apartGrid.addColumn(Apartamento::situacao).setHeader("Situação");

        edificiosGrid.addSelectionListener(selection -> {
            editarEdificio.setEnabled(selection.getAllSelectedItems().size() == 1);
            if (selection.getFirstSelectedItem().isPresent()) {
                var aps = selection.getAllSelectedItems()
                    .stream()
                    .map(Edificio::apartamentos)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
                apartGrid.setItemDetailsRenderer(createMoradorDetailsComponentRenderer());
                apartGrid.setItems(aps);
                apartamentosLayout.add(apartGrid);
                apartamentosLayout.setVisible(true);
                editarEdificio.setVisible(true);
            } else {
                apartamentosLayout.setVisible(false);
                editarEdificio.setVisible(false);
            }
        });

        var splitLayout = new SplitLayout(edificiosLayout, apartamentosLayout);
        splitLayout.setSizeFull();
        splitLayout.setSplitterPosition(30);
        add(
            splitLayout
        );
    }

    private static ComponentRenderer<MoradorDetails, Apartamento> createMoradorDetailsComponentRenderer() {
        return new ComponentRenderer<>(MoradorDetails::new, MoradorDetails::init);
    }

    private static class MoradorDetails extends FormLayout {
        private final TextField nome = new TextField("Nome");
        private final TextField cpf = new TextField("CPF");
        private final TextField telefone = new TextField("Telefone");

        public MoradorDetails() {
            var title = new Span("Morador");
            title.getStyle().set("font-weight", "bold");
            setColspan(title, 3);
            add(title);
            Stream.of(nome, cpf, telefone).forEach(f -> {
                f.setReadOnly(true);
                add(f);
            });
            setResponsiveSteps(new ResponsiveStep("0", 3));
        }

        public void init(Apartamento apartamento) {
            var morador = apartamento.morador();
            nome.setValue(morador.nome());
            cpf.setValue(morador.cpf());
            telefone.setValue(morador.telefone());
        }
    }
}

