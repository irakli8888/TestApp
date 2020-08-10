package com.example.HaulmontTestApp.backend.services;


import com.example.HaulmontTestApp.backend.models.Patients;
import com.example.HaulmontTestApp.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PatientService {
    private PatientRepository repository;
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patients> findAll() {
        return repository.findAll();
    }

    public List<Patients> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public long count() {
        return repository.count();
    }

    public void delete(Patients contact) {
        repository.delete(contact);
    }

    public void save(Patients contact) {
        if (contact == null) {
            LOGGER.log(Level.SEVERE,
                    "Patient is null. Are you sure you have connected your form to the application?");
            return;
        }
        repository.save(contact);
    }
}