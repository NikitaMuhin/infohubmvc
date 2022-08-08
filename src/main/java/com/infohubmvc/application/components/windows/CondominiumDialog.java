package com.infohubmvc.application.components.windows;

import com.infohubmvc.application.components.notifications.InfoHubNotification;
import com.infohubmvc.application.data.entity.Condominium;
import com.infohubmvc.application.data.entity.CondominiumStatus;
import com.infohubmvc.application.data.entity.User;
import com.infohubmvc.application.repositories.CondominiumRepository;
import com.infohubmvc.application.utils.WebApplicationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;


public class CondominiumDialog extends Dialog {

    private TextField nameTextField = new TextField("Name");
    private TextField registrationNumberTextField = new TextField("Registration Number");
    private TextField addressTextField = new TextField("Address");
    private TextField contractNumber = new TextField("Contract Number");
    private ComboBox<CondominiumStatus> statusComboBox = new ComboBox<>("Status");
    private FormLayout formLayout;
    private Binder<Condominium> binder = new Binder<>(Condominium.class);

    @Autowired
    private CondominiumRepository condominiumRepository;

    private boolean isEditable;

    public CondominiumDialog(String header, Condominium condominium, boolean isEditable) {
        condominiumRepository = WebApplicationUtils.get(CondominiumRepository.class);
        this.isEditable = isEditable;
        setHeaderTitle(header);
        configureComponent(condominium);
        configureBinder(condominium);
        configureCloseButton();
        configureSaveButton();
        addComponent();
        add(formLayout);
    }

    private void addComponent() {
        formLayout = new FormLayout();
        formLayout.setEnabled(isEditable);
        formLayout.add(new VerticalLayout(
                nameTextField,
                registrationNumberTextField,
                addressTextField,
                contractNumber,
                statusComboBox));
    }

    private void configureComponent(Condominium condominium) {
        configureStatusComboBox(condominium);
    }

    private void configureStatusComboBox(Condominium condominium) {
        statusComboBox.setItems(CondominiumStatus.values());
        statusComboBox.setItemLabelGenerator(Enum::name);
        statusComboBox.setValue(condominium.getStatus());
    }

    private void configureBinder(Condominium condominium) {
        binder.setBean(condominium);
        binder.forField(nameTextField).bind(Condominium::getName, Condominium::setName);
        binder.forField(registrationNumberTextField).bind(Condominium::getRegistrationNumber, Condominium::setRegistrationNumber);
        binder.forField(addressTextField).bind(Condominium::getOfficeAddress, Condominium::setOfficeAddress);
        binder.forField(contractNumber).bind(Condominium::getInfohubContractNr, Condominium::setInfohubContractNr);
        binder.forField(statusComboBox).bind(Condominium::getStatus, Condominium::setStatus);
    }

    private void configureCloseButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        getHeader().add(closeButton);
    }

    private void configureSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {
            condominiumRepository.save(binder.getBean());
            close();
            InfoHubNotification.showSuccessful("Condominium witch name " + binder.getBean().getName() + " successfully saved.");
        });
        saveButton.setVisible(isEditable);
        getFooter().add(saveButton);
    }

}
