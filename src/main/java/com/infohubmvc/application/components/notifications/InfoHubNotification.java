package com.infohubmvc.application.components.notifications;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class InfoHubNotification {

    private static final Notification.Position POSITION = Notification.Position.BOTTOM_END;
    private static final Integer DURATION = 4000;
    private static final String SUCCESS_TYPE = "success";
    private static final String ERROR_TYPE = "error";
    private static Notification notification;
    private static Span label;
    private static Icon icon;

    private InfoHubNotification() {
        throw new IllegalStateException("Utility class");
    }

    public static void showSuccessful(String message) {
        configureIcon(SUCCESS_TYPE);
        configureLabel(message);
        configureNotification(SUCCESS_TYPE);
    }

    public static void showError(String message) {
        configureIcon(ERROR_TYPE);
        configureLabel(message);
        configureNotification(ERROR_TYPE);
    }

    public static void showError(VerticalLayout content, Integer duration) {
        configureIcon(ERROR_TYPE);
        configureNotification(ERROR_TYPE, content, duration);

    }

    private static void configureNotification(String type, VerticalLayout content, Integer duration) {
        notification = new Notification();
        Button closeButton = new Button(VaadinIcon.CLOSE.create());
        closeButton.addClickListener(event -> notification.close());
        notification.setDuration(duration);
        setNotificationThemeByType(type);
        notification.setPosition(POSITION);
        notification.setOpened(true);
        notification.add(closeButton);
        notification.add(content);
        notification.open();
    }

    private static void configureNotification(String type) {
        notification = new Notification();
        setNotificationThemeByType(type);
        notification.setDuration(DURATION);
        notification.setPosition(POSITION);
        notification.setOpened(true);
        notification.add(icon, label);
        notification.open();
    }

    private static void configureLabel(String message) {
        label = new Span();
        label.setText(message);
        label.getStyle().set("font-size", "13pt");
        label.getStyle().set("vertical-align", "middle");
    }

    private static void configureIcon(String type) {
        icon = getIconByType(type);
        icon.setSize("22px");
        icon.setColor("white");
        icon.getStyle().set("margin-right", "10px");
    }

    private static Icon getIconByType(String type) {
        return (type.equals(SUCCESS_TYPE)) ? VaadinIcon.CHECK_CIRCLE.create() : VaadinIcon.EXCLAMATION_CIRCLE.create();
    }

    private static void setNotificationThemeByType(String type){
        if (type.equals(SUCCESS_TYPE)) {
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
        else {
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
