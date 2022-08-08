package com.infohubmvc.application.views.condominium;

import com.infohubmvc.application.components.windows.CondominiumDialog;
import com.infohubmvc.application.data.entity.Condominium;
import com.infohubmvc.application.data.entity.House;
import com.infohubmvc.application.repositories.CondominiumRepository;
import com.infohubmvc.application.utils.WebApplicationUtils;
import com.infohubmvc.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.PermitAll;
import java.util.stream.Collectors;

@PermitAll
@PageTitle("Condominiums")
@Route(value = "condominiums", layout = MainLayout.class)
@RouteAlias(value = "condominiums", layout = MainLayout.class)
@Transactional
public class CondominiumView extends VerticalLayout {

    private CondominiumRepository condominiumRepository;
    private Grid<Condominium> gridCondominium;
    private TextField searchTextField;
    private Button createCondominiumButton;
    private ListDataProvider<Condominium> dataProvider;

    public CondominiumView() {
        condominiumRepository = WebApplicationUtils.get(CondominiumRepository.class);
        this.setHeightFull();
        this.setWidthFull();
        dataProvider = new ListDataProvider<>(condominiumRepository.findAll());
        configureGrid();
        createCondominiumButton();
        configureSearchTextField();
        add(topHorizontalLayout(), gridCondominium);
    }

    private HorizontalLayout topHorizontalLayout() {
        HorizontalLayout topHorizontalLayout = new HorizontalLayout();
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.add(searchTextField);
        topHorizontalLayout.setWidthFull();
        topHorizontalLayout.addAndExpand(searchLayout);
        topHorizontalLayout.add(createCondominiumButton);
        topHorizontalLayout.setAlignSelf(Alignment.END, createCondominiumButton);
        return topHorizontalLayout;
    }

    private void configureGrid() {
        gridCondominium = new Grid<>();
        gridCondominium.addColumn(Condominium::getName).setHeader("Name").setAutoWidth(true);
        gridCondominium.addColumn(Condominium::getRegistrationNumber).setHeader("Registration Number").setAutoWidth(true);
        gridCondominium.addColumn(Condominium::getOfficeAddress).setHeader("Office address").setAutoWidth(true);
        gridCondominium.addColumn(Condominium::getInfohubContractNr).setHeader("Contract NR").setAutoWidth(true);
        gridCondominium.addColumn(Condominium::getStatus).setHeader("Status").setAutoWidth(true);
        gridCondominium.addColumn(Condominium::getDateCreated).setHeader("Created Date").setAutoWidth(true);
        gridCondominium.addColumn(e -> e.getUser().getFirstName() + " " + e.getUser().getLastName()).setHeader("User").setAutoWidth(true);
        gridCondominium.addColumn(Condominium::getConsumptionPoints).setHeader("Consumtions points").setAutoWidth(true);
        gridCondominium.addColumn(this::generateHousesString).setHeader("Houses").setAutoWidth(true);
        gridCondominium.addComponentColumn(this::buttonEdit).setHeader("Edit").setAutoWidth(true);
        gridCondominium.addComponentColumn(this::buttonDetails).setHeader("Info").setAutoWidth(true);
        gridCondominium.setDataProvider(dataProvider);
        gridCondominium.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridCondominium.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        gridCondominium.addThemeVariants(GridVariant.LUMO_COMPACT);
    }

    private String generateHousesString(Condominium condominium) {
        return condominium.getHouses().stream().map(House::getName).collect(Collectors.joining(","));
    }

    private Component buttonEdit(Condominium condominium) {
        Button button = new Button();
        button.setIcon(VaadinIcon.EDIT.create());
        button.addClickListener(event -> openCondominiumDialog("Edit", condominium, true));
        return button;
    }

    private Component buttonDetails(Condominium condominium) {
        Button button = new Button();
        button.setIcon(VaadinIcon.INFO.create());
        button.addClickListener(event -> openCondominiumDialog("Information", condominium, false));
        return button;
    }

    private void configureSearchTextField() {
        searchTextField = new TextField();
        searchTextField.setPlaceholder("Search");
        searchTextField.setWidth("30%");
        searchTextField.setClearButtonVisible(true);
        searchTextField.setValueChangeMode(ValueChangeMode.LAZY);
        searchTextField.addValueChangeListener(this::onNameFilterTextChange);
    }

    private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
        dataProvider.setFilter(s -> {
            boolean containsName = caseInsensitiveContains(s.getName(), event.getValue());
            boolean containsRegistrationNumber = caseInsensitiveContains(s.getRegistrationNumber(), event.getValue());
            boolean containsOfficeAddress = caseInsensitiveContains(s.getOfficeAddress(), event.getValue());
            boolean containsInfohubContractNr = caseInsensitiveContains(s.getInfohubContractNr(), event.getValue());
            boolean containsPhone = caseInsensitiveContains(s.getUser().getFirstName() + " " + s.getUser().getUserRoles(), event.getValue());
            return containsName || containsRegistrationNumber || containsOfficeAddress || containsInfohubContractNr || containsPhone;
        });
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        if (where == null) return false;
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private void createCondominiumButton() {
        createCondominiumButton = new Button("New condominium");
        createCondominiumButton.setIcon(VaadinIcon.PLUS_CIRCLE_O.create());
        createCondominiumButton.addClickListener(event -> openCondominiumDialog("Create condominium", new Condominium(), true));
    }

    private void openCondominiumDialog(String headerString, Condominium condominium, boolean isEditable) {
        CondominiumDialog condominiumDialog;
        condominiumDialog = new CondominiumDialog(headerString, condominium, isEditable);
        condominiumDialog.open();
    }
}
