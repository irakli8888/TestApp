package com.example.HaulmontTestApp.backend.repository;

import com.example.HaulmontTestApp.backend.models.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patients,Integer> {
    @Query("select c from Patients c " +
            "where lower(c.name) like lower(concat('%', :searchTer, '%')) " +
            "or lower(c.surname) like lower(concat('%', :searchTer, '%'))")
    List<Patients> search(@Param("searchTer") String searchTerm);
}
