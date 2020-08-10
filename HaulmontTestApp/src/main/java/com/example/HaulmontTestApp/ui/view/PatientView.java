package com.example.HaulmontTestApp.ui.view;



import com.example.HaulmontTestApp.ui.updateForm.UpdateFormPatient;
import com.example.HaulmontTestApp.backend.models.Patients;
import com.example.HaulmontTestApp.backend.services.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Patients")
@Route(value="patients",layout = MainLayout.class)

public class PatientView extends VerticalLayout {

    private TextField filterText = new TextField();
    private PatientService patientService;
    private Grid<Patients> patientGrid;
    private UpdateFormPatient form;

    public PatientView(PatientService patientService) {
        this.patientService = patientService;
        this.patientGrid=new Grid<>(Patients.class);
        getToolBar();
        configureGrid();
        form = new UpdateFormPatient();
        form.addListener(UpdateFormPatient.SaveEvent.class, this::saveContact);
        form.addListener(UpdateFormPatient.DeleteEvent.class, this::deleteContact);
        form.addListener(UpdateFormPatient.CloseEvent.class,e->closeEditor());
        add(getToolBar(), patientGrid, form);
        updateList();

    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton=new Button("Add patient", click->addContact());
        HorizontalLayout toolbar=new HorizontalLayout(filterText,addContactButton);
        return toolbar;
    }

    private void addContact() {
        patientGrid.asSingleSelect().clear();
        editContact(new Patients());
    }

    private void updateList(){
        patientGrid.setItems(patientService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        patientGrid.setColumns("surname", "name", "patronymic", "phoneNumber");
        patientGrid.addColumn(Patients::getDoctorsToString).setHeader("Doctors");
        patientGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        patientGrid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    public void editContact(Patients patients) {
        if (patients == null) {
            closeEditor();
        } else {
            form.setPatients(patients);
            form.setVisible(true);
        }
    }

    private void closeEditor() {
        form.setPatients(null);
        form.setVisible(false);
    }

    private void saveContact(UpdateFormPatient.SaveEvent event) {
        patientService.save(event.getPatients());
        updateList();
    }

    private void deleteContact(UpdateFormPatient.DeleteEvent event) {
        patientService.delete(event.getPatients());
        updateList();

    }

}
