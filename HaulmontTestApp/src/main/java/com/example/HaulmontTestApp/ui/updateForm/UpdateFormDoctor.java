package com.example.HaulmontTestApp.ui.updateForm;

import com.example.HaulmontTestApp.backend.models.Doctors;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateFormDoctor extends FormLayout {
    private Doctors doctors;

    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Doctors> binder = new Binder<>(Doctors.class);

    @Autowired
    public UpdateFormDoctor() {
        binder.forField(name).withValidator(str->validName(str),"not valid")
                .withValidator(str->!str.isEmpty(),"field is empty")
                .bind(Doctors::getName,Doctors::setName);
        binder.forField(surname).withValidator(str->validName(str),"not valid")
                .withValidator(str->!str.isEmpty(),"field is empty")
                .bind(Doctors::getSurname,Doctors::setSurname);
        binder.forField(patronymic).withValidator(str->validName(str),"not valid")
                .withValidator(str->!str.isEmpty(),"field is empty")
                .bind(Doctors::getPatronymic,Doctors::setPatronymic);
        binder.forField(specialization).withValidator(str->!str.isEmpty(),"field is empty")
                .bind(Doctors::getSpecialization,Doctors::setSpecialization);
        add(name, surname, patronymic, specialization, createButtonsLayout());
    }

    public boolean validName(String str){
        Pattern p1=Pattern.compile("[A-Za-zА-Я-а-я]*-?[A-Za-zА-Я-а-я]*?");
        Matcher m1=p1.matcher(str);
        if(m1.matches())
            return true;
        return false;
    }

    public void setDoctors(Doctors doctors){
        this.doctors = doctors;
        binder.setBean(doctors);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click->validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, doctors)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(doctors);
            fireEvent(new SaveEvent(this, doctors));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<UpdateFormDoctor> {

        private Doctors doctors;

        protected ContactFormEvent(UpdateFormDoctor source, Doctors doctors) {
            super(source, false);
            this.doctors = doctors;
        }

        public Doctors getDoctors() {
            return doctors;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(UpdateFormDoctor source, Doctors doctors) {
            super(source, doctors);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(UpdateFormDoctor source, Doctors doctors) {
            super(source, doctors);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(UpdateFormDoctor source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
