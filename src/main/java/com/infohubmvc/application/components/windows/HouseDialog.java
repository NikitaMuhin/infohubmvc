package com.infohubmvc.application.components.windows;

import com.infohubmvc.application.components.notifications.InfoHubNotification;
import com.infohubmvc.application.converters.DoubleToBigDecimalConverter;
import com.infohubmvc.application.converters.IntegerToLongConverter;
import com.infohubmvc.application.data.entity.Condominium;
import com.infohubmvc.application.data.entity.House;
import com.infohubmvc.application.data.entity.StatusCode;
import com.infohubmvc.application.repositories.CondominiumRepository;
import com.infohubmvc.application.repositories.HouseRepository;
import com.infohubmvc.application.repositories.StatusCodeRepository;
import com.infohubmvc.application.utils.WebApplicationUtils;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class HouseDialog extends Dialog {


    private HouseRepository houseRepository;
    private StatusCodeRepository statusCodeRepository;

    private CondominiumRepository condominiumRepository;
    private ComboBox<Condominium> condominiumComboBox = new ComboBox<>("Condominium");
    private TextField textFieldName = new TextField("Name");
    private TextField textFieldAdres = new TextField("Adresa");
    private IntegerField numberFieldBlocks = new IntegerField("Numar de blocuri");
    private IntegerField numberFieldFloor = new IntegerField("Numar de etaje");
    private IntegerField numberFieldStairs = new IntegerField("Numar de scari");
    private IntegerField numberFieldYear = new IntegerField("Anul darii in exploatare");
    private NumberField numberFieldTotalArea = new NumberField("Suprafata totala");
    private ComboBox<StatusCode> comboBoxStatus = new ComboBox<>("Status");

    private FormLayout formLayout;
    private Binder<House> binder = new Binder<>(House.class);

    private boolean isEditable;

    public HouseDialog(String header, House house, boolean isEditable) throws ValidationException {
        houseRepository = WebApplicationUtils.get(HouseRepository.class);
        statusCodeRepository = WebApplicationUtils.get(StatusCodeRepository.class);
        condominiumRepository = WebApplicationUtils.get(CondominiumRepository.class);
        this.isEditable = isEditable;
        setHeaderTitle(header);
        configureComponent(house);
        configureBinder(house);
        configureCloseButton();
        configureSaveButton();
        addComponent();
        add(formLayout);
    }

    private void configureComponent(House house) {
        configureStatusComboBox(house);
        configureCondominiumComboBox(house);
    }

    private void configureCloseButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        getHeader().add(closeButton);
    }

    private void configureSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {
            houseRepository.save(binder.getBean());
            close();
            InfoHubNotification.showSuccessful("The house with the name " + binder.getBean().getName() + " has been successfully saved.");
        });
        getFooter().add(saveButton);
    }

    private void addComponent() {
        formLayout = new FormLayout();
        formLayout.setEnabled(isEditable);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(condominiumComboBox, textFieldName);
        HorizontalLayout horizontalLayout2 = new HorizontalLayout(textFieldAdres, numberFieldBlocks, numberFieldFloor);
        HorizontalLayout horizontalLayout3 = new HorizontalLayout(numberFieldStairs, numberFieldYear, numberFieldTotalArea);
        formLayout.add(new VerticalLayout(horizontalLayout1, horizontalLayout2, horizontalLayout3, comboBoxStatus));
    }

    private void configureCondominiumComboBox(House house) {
        condominiumComboBox.setItems(condominiumRepository.findAll());
        condominiumComboBox.setItemLabelGenerator(Condominium::getName);
        condominiumComboBox.setValue(house.getCondominium());
    }

    private void configureStatusComboBox(House house) {
        comboBoxStatus.setItems(statusCodeRepository.findAll());
        comboBoxStatus.setItemLabelGenerator(StatusCode::getDescription);
        comboBoxStatus.setValue(house.getStatus());
    }

    private void configureBinder(House house) {
        binder.setBean(house);
        //   binder.forField(condominiumComboBox).bind(House::getCondominium,House::setCondominium);
        binder.forField(textFieldName).bind(House::getName, House::setName);
        binder.forField(textFieldAdres).bind(House::getHouseAddress, House::setHouseAddress);
        binder.forField(numberFieldBlocks).withConverter(new IntegerToLongConverter()).bind(House::getNumberBlocks, House::setNumberBlocks);
        binder.forField(numberFieldFloor).withConverter(new IntegerToLongConverter()).bind(House::getNumberFloors, House::setNumberFloors);
        binder.forField(numberFieldStairs).withConverter(new IntegerToLongConverter()).bind(House::getNumberStairs, House::setNumberStairs);
        binder.forField(numberFieldYear).withConverter(new IntegerToLongConverter()).bind(House::getYearBuilt, House::setYearBuilt);
        binder.forField(numberFieldTotalArea).withConverter(new DoubleToBigDecimalConverter()).bind(House::getTotalArea, House::setTotalArea);
        binder.forField(comboBoxStatus).bind(House::getStatus, House::setStatus);
    }
}
