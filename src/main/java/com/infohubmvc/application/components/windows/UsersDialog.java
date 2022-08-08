package com.infohubmvc.application.components.windows;

import com.infohubmvc.application.components.notifications.InfoHubNotification;
import com.infohubmvc.application.data.entity.User;
import com.infohubmvc.application.data.entity.UserStatus;
import com.infohubmvc.application.repositories.UserRepository;
import com.infohubmvc.application.utils.WebApplicationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class UsersDialog extends Dialog {


    private UserRepository userRepository;

    private TextField usernameTextField = new TextField("Username");
    private PasswordField passwordField = new PasswordField("Password");
    private TextField firstNameTextField = new TextField("First name");
    private TextField lastNameTextField = new TextField("Last name");
    private EmailField emailField = new EmailField("Email");
    private TextField phoneTextField = new TextField("Phone");
    private ComboBox<UserStatus> userStatusComboBox = new ComboBox<>("Status");

    private FormLayout formLayout;
    private Binder<User> binder = new Binder<>(User.class);

    private boolean isEditable;

    public UsersDialog(String header, User user, boolean isEditable) throws ValidationException {
        userRepository = WebApplicationUtils.get(UserRepository.class);
        this.isEditable = isEditable;
        setHeaderTitle(header);
        configureComponent(user);
        configureBinder(user);
        configureCloseButton();
        configureSaveButton();
        addComponent();
        add(formLayout);
    }


    private void configureComponent(User user) {
        configureUserStatusComboBox(user);
        configureEmailField();
        configureUsernameTextField();
        configurePasswordField();
        configureFirstNameTextField();
        configureLastNameTextField();
        configurePhoneTextField();
    }

    private void configureUserStatusComboBox(User user) {
        userStatusComboBox.setItems(UserStatus.values());
        userStatusComboBox.setItemLabelGenerator(Enum::name);
        userStatusComboBox.setValue(user.getStatus());
        userStatusComboBox.setEnabled(isEditable);
    }

    private void configureEmailField() {
        emailField.setWidthFull();
       // emailField.setEnabled(isEditable);
    }

    private void configureUsernameTextField() {
     //   usernameTextField.setEnabled(isEditable);
    }

    private void configurePasswordField() {
     //   passwordField.setEnabled(isEditable);
    }

    private void configureFirstNameTextField() {
      //  firstNameTextField.setEnabled(isEditable);
    }

    private void configureLastNameTextField() {
      //  lastNameTextField.setEnabled(isEditable);
    }

    private void configurePhoneTextField() {
     //  phoneTextField.setEnabled(isEditable);
    }

    private void configureCloseButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        getHeader().add(closeButton);
    }

    private void configureSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {
            userRepository.save(binder.getBean());
            close();
            InfoHubNotification.showSuccessful("User " + binder.getBean().getFirstName() + " " + binder.getBean().getLastName() + " successfully saved.");
        });
        saveButton.setVisible(isEditable);
        getFooter().add(saveButton);
    }

    private void addComponent() {
        formLayout = new FormLayout();
        formLayout.setEnabled(isEditable);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(usernameTextField,
                passwordField);
        HorizontalLayout horizontalLayout2 = new HorizontalLayout(firstNameTextField,
                lastNameTextField);
        HorizontalLayout horizontalLayout3 = new HorizontalLayout(phoneTextField,
                userStatusComboBox);
        formLayout.add(new VerticalLayout(
                horizontalLayout1,
                horizontalLayout2,
                emailField,
                horizontalLayout3));
    }


    private void configureBinder(User user) {
        binder.setBean(user);
        binder.forField(usernameTextField).bind(User::getUsername, User::setUsername);
        binder.forField(passwordField).bind(User::getPassword, User::setPassword);
        binder.forField(firstNameTextField).bind(User::getFirstName, User::setFirstName);
        binder.forField(lastNameTextField).bind(User::getLastName, User::setLastName);
        binder.forField(emailField).bind(User::getEmail, User::setEmail);
        binder.forField(phoneTextField).bind(User::getPhoneNumber, User::setPhoneNumber);
        binder.forField(userStatusComboBox).bind(User::getStatus, User::setStatus);
    }
}
