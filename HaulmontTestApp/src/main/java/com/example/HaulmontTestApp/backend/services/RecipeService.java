package com.example.HaulmontTestApp.backend.services;

import com.example.HaulmontTestApp.backend.models.Recipes;
import com.example.HaulmontTestApp.backend.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RecipeService {
    private RecipeRepository repository;
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipes> findAll() {
        return repository.findAll();
    }


    public long count() {
        return repository.count();
    }

    public void delete(Recipes recipe) {
        repository.delete(recipe);
    }

    public void save(Recipes contact) {
        if (contact == null) {
            LOGGER.log(Level.SEVERE,
                    "Recipe is null. Are you sure you have connected your form to the application?");
            return;
        }
        repository.save(contact);
    }
}
