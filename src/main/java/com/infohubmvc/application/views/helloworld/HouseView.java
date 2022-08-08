package com.infohubmvc.application.views.helloworld;

import com.infohubmvc.application.components.windows.HouseDialog;
import com.infohubmvc.application.data.entity.House;
import com.infohubmvc.application.repositories.HouseRepository;
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
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.PermitAll;

@PermitAll
@PageTitle("Houses")
@Route(value = "house", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Transactional
public class HouseView extends VerticalLayout {

    private Grid<House> gridHouse;
    private TextField searchTextField;

    private Button createHouseButton;
    private ListDataProvider<House> dataProvider;

    public HouseView(HouseRepository houseRepository) {
        this.setHeightFull();
        this.setWidthFull();
        dataProvider = new ListDataProvider<>(houseRepository.findAll());
        configureGrid();
        configureSearchTextField();
        createHouseButton();
        add(topHorizontalLayout(), gridHouse);
    }

    private HorizontalLayout topHorizontalLayout() {
        HorizontalLayout topHorizontalLayout = new HorizontalLayout();
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.add(searchTextField);
        topHorizontalLayout.setWidthFull();
        topHorizontalLayout.addAndExpand(searchLayout);
        topHorizontalLayout.add(createHouseButton);
        topHorizontalLayout.setAlignSelf(Alignment.END, createHouseButton);
        return topHorizontalLayout;
    }

    private void configureGrid() {
        gridHouse = new Grid<>();
        gridHouse.addColumn(e -> e.getCondominium().getName()).setHeader("Condominium").setAutoWidth(true);
        gridHouse.addColumn(House::getName).setHeader("Name").setAutoWidth(true);
        gridHouse.addColumn(House::getHouseAddress).setHeader("Address").setAutoWidth(true);
        gridHouse.addColumn(House::getNumberBlocks).setHeader("Block Nr").setAutoWidth(true);
        gridHouse.addColumn(House::getNumberFloors).setHeader("Floors").setAutoWidth(true);
        gridHouse.addColumn(House::getNumberStairs).setHeader("Stairs").setAutoWidth(true);
        gridHouse.addColumn(House::getTotalArea).setHeader("Total Area").setAutoWidth(true);
        gridHouse.addColumn(House::getYearBuilt).setHeader("Year").setAutoWidth(true);
        gridHouse.addColumn(House::getHasLift).setHeader("Has Lift").setAutoWidth(true);
        gridHouse.addColumn(e -> e.getProperty().size()).setHeader("Properties").setAutoWidth(true);
        gridHouse.addColumn(e -> e.getConsumptionPoints().size()).setHeader("Consumption Points").setAutoWidth(true);
        gridHouse.addComponentColumn(this::buttonEdit).setHeader("Edit").setAutoWidth(true);
        gridHouse.addComponentColumn(this::buttonDetails).setHeader("Info").setAutoWidth(true);
        gridHouse.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridHouse.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        gridHouse.addThemeVariants(GridVariant.LUMO_COMPACT);
        gridHouse.setItems(dataProvider);
        gridHouse.setWidthFull();
        gridHouse.setHeightFull();
    }

    private Component buttonEdit(House house) {
        Button button = new Button();
        button.setIcon(VaadinIcon.EDIT.create());
        button.addClickListener(event -> openHouseDialog("Edit", house, true));
        return button;
    }

    private Component buttonDetails(House house) {
        Button button = new Button();
        button.setIcon(VaadinIcon.INFO.create());
        button.addClickListener(e -> openHouseDialog("Information", house, false));
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
            boolean containsCondominium = caseInsensitiveContains(s.getCondominium().getName(), event.getValue());
            boolean containsName = caseInsensitiveContains(s.getName(), event.getValue());
            boolean containsHouseAddress = caseInsensitiveContains(s.getHouseAddress(), event.getValue());
            return containsCondominium || containsName || containsHouseAddress;
        });
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        if (where == null) return false;
        return where.toLowerCase().contains(what.toLowerCase());
    }


    private void createHouseButton() {
        createHouseButton = new Button("New house");
        createHouseButton.setIcon(VaadinIcon.PLUS_CIRCLE_O.create());
        createHouseButton.addClickListener(event -> openHouseDialog("Create house", new House(), true));
    }

    private void openHouseDialog(String headerString, House house, boolean isEditable) {
        HouseDialog newHouseDialog;
        try {
            newHouseDialog = new HouseDialog(headerString,house, isEditable);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        newHouseDialog.open();
    }

}
