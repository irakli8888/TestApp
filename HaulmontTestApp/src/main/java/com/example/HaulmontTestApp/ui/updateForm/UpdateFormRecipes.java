package com.example.HaulmontTestApp.ui.updateForm;

import com.example.HaulmontTestApp.backend.models.Doctors;
import com.example.HaulmontTestApp.backend.models.Patients;
import com.example.HaulmontTestApp.backend.models.Priority;
import com.example.HaulmontTestApp.backend.models.Recipes;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UpdateFormRecipes extends FormLayout {
    private Recipes recipes;

    private DatePicker creationDate = new DatePicker("Creation date");
    private DatePicker validity = new DatePicker("Validity");
    private ComboBox<Priority> priority = new ComboBox("Priority");
    private TextField description = new TextField("Description");
    private ComboBox<Doctors> doctors=new ComboBox<>("Doctor");
    private ComboBox<Patients > patients=new ComboBox<>("Patients");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Recipes> binder = new Binder<>(Recipes.class);

    @Autowired
    public UpdateFormRecipes(List<Doctors> doc,List<Patients> pat) {
        doctors.setItems(doc);
        doctors.setItemLabelGenerator(Doctors::getName);
        patients.setItems(pat);
        patients.setItemLabelGenerator(Patients::getFullName);
        binder.forField(creationDate).withConverter(new LocalDateToDateConverter()).bind(Recipes::getDate,Recipes::setDate);
        binder.forField(validity).withConverter(new LocalDateToDateConverter()).bind(Recipes::getValidity,Recipes::setValidity);
        binder.forField(description).bind(Recipes::getDescription,Recipes::setDescription);
        binder.forField(priority).bind(Recipes::getPriority,Recipes::setPriority);
        priority.setItems(Priority.values());
        add(creationDate, validity, priority, description,doctors,patients, createButtonsLayout());
    }

    public void setRecipes(Recipes recipes){
        this.recipes = recipes;
        binder.setBean(recipes);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click->validateAndSave());
        delete.addClickListener(event -> fireEvent(new UpdateFormRecipes.DeleteEvent(this, recipes)));
        close.addClickListener(event -> fireEvent(new UpdateFormRecipes.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.forField(creationDate).withConverter(new LocalDateToDateConverter()).bind(Recipes::getDate,Recipes::setDate);
            binder.forField(validity).withConverter(new LocalDateToDateConverter()).bind(Recipes::getValidity,Recipes::setValidity);
            binder.writeBean(recipes);
            fireEvent(new UpdateFormRecipes.SaveEvent(this, recipes));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<UpdateFormRecipes> {
        private Recipes recipes;


        protected ContactFormEvent(UpdateFormRecipes source, Recipes recipes) {
            super(source, false);
            this.recipes = recipes;
        }

        public Recipes getRecipes() {
            return recipes;
        }
    }

    public static class SaveEvent extends UpdateFormRecipes.ContactFormEvent {
        SaveEvent(UpdateFormRecipes source, Recipes recipes) {
            super(source, recipes);
        }
    }

    public static class DeleteEvent extends UpdateFormRecipes.ContactFormEvent {
        DeleteEvent(UpdateFormRecipes source, Recipes recipes) {
            super(source, recipes);
        }

    }

    public static class CloseEvent extends UpdateFormRecipes.ContactFormEvent {
        CloseEvent(UpdateFormRecipes source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
