package com.infohubmvc.application.views.users;

import com.infohubmvc.application.components.windows.HouseDialog;
import com.infohubmvc.application.components.windows.UsersDialog;
import com.infohubmvc.application.data.entity.House;
import com.infohubmvc.application.data.entity.User;
import com.infohubmvc.application.repositories.UserRepository;
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
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.PermitAll;
import java.util.stream.Collectors;

@PermitAll
@PageTitle("Users")
@Route(value = "users", layout = MainLayout.class)
@RouteAlias(value = "users", layout = MainLayout.class)
@Transactional
public class UserView extends VerticalLayout {

    private UserRepository userRepository;
    private Grid<User> gridUser;
    private TextField searchTextField;
    private Button createUserButton;
    private ListDataProvider<User> dataProvider;

    public UserView() {
        userRepository = WebApplicationUtils.get(UserRepository.class);
        this.setHeightFull();
        this.setWidthFull();
        dataProvider = new ListDataProvider<>(userRepository.findAll());
        configureGrid();
        createUserButton();
        configureSearchTextField();
        add(topHorizontalLayout(), gridUser);
    }

    private HorizontalLayout topHorizontalLayout() {
        HorizontalLayout topHorizontalLayout = new HorizontalLayout();
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.add(searchTextField);
        topHorizontalLayout.setWidthFull();
        topHorizontalLayout.addAndExpand(searchLayout);
        topHorizontalLayout.add(createUserButton);
        topHorizontalLayout.setAlignSelf(Alignment.END, createUserButton);
        return topHorizontalLayout;
    }

    private void configureGrid() {
        gridUser = new Grid<>();
        gridUser.addColumn(User::getUsername).setHeader("Username").setAutoWidth(true);
        gridUser.addColumn(User::getFirstName).setHeader("First Name").setAutoWidth(true);
        gridUser.addColumn(User::getLastName).setHeader("Last Name").setAutoWidth(true);
        gridUser.addColumn(User::getEmail).setHeader("Email").setAutoWidth(true);
        gridUser.addColumn(User::getPhoneNumber).setHeader("Phone").setAutoWidth(true);
        gridUser.addColumn(e -> e.getStatus().name()).setHeader("Status").setAutoWidth(true);
        gridUser.addColumn(User::getDateCreated).setHeader("Created Date").setAutoWidth(true);
        gridUser.addColumn(this::generateStatusString).setHeader("Roles").setAutoWidth(true);
        gridUser.addComponentColumn(this::buttonEdit).setHeader("Edit");
        gridUser.addComponentColumn(this::buttonDetails).setHeader("Info");
        gridUser.setDataProvider(dataProvider);
        gridUser.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridUser.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        gridUser.addThemeVariants(GridVariant.LUMO_COMPACT);
    }

    private String generateStatusString(User user) {
        return user.getUserRoles().stream().map(e -> e.getRole().name()).collect(Collectors.joining(","));
    }

    private Component buttonEdit(User user) {
        Button button = new Button();
        button.setIcon(VaadinIcon.EDIT.create());
        button.addClickListener(event -> openUserDialog("Edit user", user, true));
        return button;
    }

    private Component buttonDetails(User user) {
        Button button = new Button();
        button.setIcon(VaadinIcon.INFO.create());
        button.addClickListener(event -> openUserDialog("Information about user", user, false));
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
            boolean containsName = caseInsensitiveContains(s.getFirstName(), event.getValue());
            boolean containsSurname = caseInsensitiveContains(s.getLastName(), event.getValue());
            boolean containsEmail = caseInsensitiveContains(s.getEmail(), event.getValue());
            boolean containsUsername = caseInsensitiveContains(s.getUsername(), event.getValue());
            boolean containsPhone = caseInsensitiveContains(s.getPhoneNumber(), event.getValue());
            return containsName || containsSurname || containsEmail || containsUsername || containsPhone;
        });
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        if (where == null) return false;
        return where.toLowerCase().contains(what.toLowerCase());
    }


    private void createUserButton() {
        createUserButton = new Button("New user");
        createUserButton.setIcon(VaadinIcon.PLUS_CIRCLE_O.create());
        createUserButton.addClickListener(event -> openUserDialog("Create user", new User(), true));
    }

    private void openUserDialog(String headerString, User user, boolean isEditable) {
        UsersDialog userDialog;
        try {
            userDialog = new UsersDialog(headerString, user, isEditable);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        userDialog.open();
    }

}

