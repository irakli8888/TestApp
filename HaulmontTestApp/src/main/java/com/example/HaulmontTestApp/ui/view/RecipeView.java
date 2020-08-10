package com.example.HaulmontTestApp.ui.view;

import com.example.HaulmontTestApp.ui.updateForm.UpdateFormRecipes;
import com.example.HaulmontTestApp.backend.models.Recipes;
import com.example.HaulmontTestApp.backend.services.DoctorService;
import com.example.HaulmontTestApp.backend.services.PatientService;
import com.example.HaulmontTestApp.backend.services.RecipeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Recipes")
@Route(value="recipes",layout = MainLayout.class)

public class RecipeView extends VerticalLayout {
    private RecipeService recipeService;
    private Grid<Recipes> recipesGrid;
    private UpdateFormRecipes form;

    public RecipeView(RecipeService recipeService,DoctorService doctorService,PatientService patientService){
        this.recipeService = recipeService;
        this.recipesGrid=new Grid<>(Recipes.class);
        getToolBar();
        configureGrid();
        form = new UpdateFormRecipes(doctorService.findAll(),patientService.findAll());
        form.addListener(UpdateFormRecipes.SaveEvent.class, this::saveContact);
        form.addListener(UpdateFormRecipes.DeleteEvent.class, this::deleteContact);
        form.addListener(UpdateFormRecipes.CloseEvent.class, e->closeEditor());
        add(getToolBar(), recipesGrid, form);
        updateList();
        closeEditor();
    }
    
    private HorizontalLayout getToolBar() {
        Button addContactButton=new Button("Add recipes", click->addContact());
        HorizontalLayout toolbar=new HorizontalLayout(addContactButton);
        return toolbar;
    }

    private void addContact() {
        recipesGrid.asSingleSelect().clear();
        editContact(new Recipes());
    }

    private void updateList(){
        recipesGrid.setItems(recipeService.findAll());
    }

    private void configureGrid() {
        recipesGrid.setColumns("date", "validity", "priority", "description");
        recipesGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        recipesGrid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    public void editContact(Recipes recipes) {
        if (recipes == null) {
            closeEditor();
        } else {
            form.setRecipes(recipes);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setRecipes(null);
        form.setVisible(false);
    }

    private void saveContact(UpdateFormRecipes.SaveEvent event) {
        recipeService.save(event.getRecipes());
        updateList();
    }

    private void deleteContact(UpdateFormRecipes.DeleteEvent event) {
        recipeService.delete(event.getRecipes());
        updateList();

    }
}
