package com.example.HaulmontTestApp.backend.services;

import com.example.HaulmontTestApp.backend.models.Doctors;
import com.example.HaulmontTestApp.backend.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DoctorService {
    private DoctorRepository repository;
    private static final Logger LOGGER = Logger.getLogger(DoctorService.class.getName());

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public List<Doctors> findAll() {
        return repository.findAll();
    }

    public List<Doctors> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public long count() {
        return repository.count();
    }

    public void delete(Doctors contact) {
        repository.delete(contact);
    }

    public void save(Doctors contact) {
        if (contact == null) {
            LOGGER.log(Level.SEVERE,
                    "Doctor is null. Are you sure you have connected your form to the application?");
            return;
        }
        repository.save(contact);
    }
}
