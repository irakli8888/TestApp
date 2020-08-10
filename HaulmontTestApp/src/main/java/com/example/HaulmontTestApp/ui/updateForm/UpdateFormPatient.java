package com.example.HaulmontTestApp.ui.updateForm;

import com.example.HaulmontTestApp.backend.models.Patients;
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

import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateFormPatient extends FormLayout {

    private Patients patients;

    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField patronymic = new TextField("Patronymic");
    private TextField phoneNumber=new TextField("Phone number");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Patients> binder2 = new Binder<>(Patients.class);

    @Autowired
    public UpdateFormPatient() {
        binder2.forField(name).withValidator(str->validName(str),"not valid")
                .withValidator(str->!str.isEmpty(),"field is empty").
                bind(Patients::getName,Patients::setName);
        binder2.forField(surname).withValidator(str->validName(str),"not valid")
                .withValidator(str->!str.isEmpty(),"field is empty")
                .bind(Patients::getSurname,Patients::setSurname);
        binder2.forField(patronymic).
                withValidator(str->validName(str),"not valid")
                .withValidator(str->!str.isEmpty(),"field is empty")
                .bind(Patients::getPatronymic,Patients::setPatronymic);
        binder2.forField(phoneNumber).withValidator(str->validNumber(str), "not valid")
                .withValidator(str->!str.isEmpty(),"field is empty")
                .withConverter(new StringToIntegerConverter("converting"))
                .bind(Patients::getPhoneNumber,Patients::setPhoneNumber);
        add(name, surname, patronymic,phoneNumber,createButtonsLayout());
    }

    public boolean validNumber(String str){
        Pattern p1=Pattern.compile("((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}");
        Matcher m1=p1.matcher(str);
        if(m1.matches())
            return true;
        return false;
    }

    public boolean validName(String str){
        Pattern p1=Pattern.compile("[A-Za-zА-Я-а-я]*-?[A-Za-zА-Я-а-я]*?");
        Matcher m1=p1.matcher(str);
        if(m1.matches())
            return true;
        return false;
    }

    public void setPatients(Patients patients){
        this.patients = patients;
        binder2.setBean(patients);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click->validateAndSave());
        delete.addClickListener(event -> fireEvent(new UpdateFormPatient.DeleteEvent(this, patients)));
        close.addClickListener(event -> fireEvent(new UpdateFormPatient.CloseEvent(this)));

        binder2.addStatusChangeListener(e -> save.setEnabled(binder2.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder2.writeBean(patients);
            fireEvent(new UpdateFormPatient.SaveEvent(this, patients));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<UpdateFormPatient> {
        private Patients patients;

        protected ContactFormEvent(UpdateFormPatient source, Patients patients) {
            super(source, false);
            this.patients = patients;
        }

        public Patients getPatients() {
            return patients;
        }
    }

    public static class SaveEvent extends UpdateFormPatient.ContactFormEvent {
        SaveEvent(UpdateFormPatient source, Patients patients) {
            super(source, patients);
        }
    }

    public static class DeleteEvent extends UpdateFormPatient.ContactFormEvent {
        DeleteEvent(UpdateFormPatient source, Patients patients) {
            super(source, patients);
        }

    }

    public static class CloseEvent extends UpdateFormPatient.ContactFormEvent {
        CloseEvent(UpdateFormPatient source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
