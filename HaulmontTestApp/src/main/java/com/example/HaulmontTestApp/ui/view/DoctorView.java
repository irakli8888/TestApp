package com.example.HaulmontTestApp.ui.view;

//import com.example.HaulmontTestApp.components.Editor;
import com.example.HaulmontTestApp.ui.updateForm.UpdateFormDoctor;
import com.example.HaulmontTestApp.backend.models.Doctors;
import com.example.HaulmontTestApp.backend.services.DoctorService;
import com.example.HaulmontTestApp.backend.services.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value="", layout = MainLayout.class)

@PageTitle("Haulmont Med App")
public class DoctorView extends VerticalLayout {
    //текстовое поле для фильра доктора
    private TextField filterText = new TextField();
    //сервисы доктора для получения банных из бд
    private DoctorService doctorService;
    //сетка для отображения списка докторов
    private Grid<Doctors>doctorGrid;
    //форма для записи докторов
    private UpdateFormDoctor form;

    public DoctorView(DoctorService doctorService){
        this.doctorService = doctorService;
     //   this.docEditor = docEditor;
        this.doctorGrid=new Grid<>(Doctors.class);
        //задаем конфигурации фильтра
        getToolBar();
        //конфигурации сетки
        configureGrid();

        form = new UpdateFormDoctor();
        form.addListener(UpdateFormDoctor.SaveEvent.class, this::saveContact);
        form.addListener(UpdateFormDoctor.DeleteEvent.class, this::deleteContact);
        form.addListener(UpdateFormDoctor.CloseEvent.class, e->closeEditor());
        //добавляем фильтр и сетку с формой
        add(getToolBar(), doctorGrid, form);
        //загружаем данные в сетку
        updateList();
        closeEditor();
    }

    //конфигурации фильтра
    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton=new Button("Add doctor",click->addContact());
        HorizontalLayout toolbar=new HorizontalLayout(filterText,addContactButton);
        return toolbar;
    }

    private void addContact() {
        doctorGrid.asSingleSelect().clear();
        editContact(new Doctors());
    }

    //загрузка данных из бд
    private void updateList(){
        doctorGrid.setItems(doctorService.findAll(filterText.getValue()));
    }

    //конфигурация сетки доктора
    private void configureGrid() {
        doctorGrid.setColumns("surname", "name", "patronymic", "specialization");
        doctorGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        doctorGrid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    public void editContact(Doctors doctors) {
        if (doctors == null) {
            closeEditor();
        } else {
            form.setDoctors(doctors);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setDoctors(null);
        form.setVisible(false);
    }

    //редактирование контакта
    private void saveContact(UpdateFormDoctor.SaveEvent event) {
            doctorService.save(event.getDoctors());
            updateList();
    }

    private void deleteContact(UpdateFormDoctor.DeleteEvent event) {
        doctorService.delete(event.getDoctors());
        updateList();

    }
}
