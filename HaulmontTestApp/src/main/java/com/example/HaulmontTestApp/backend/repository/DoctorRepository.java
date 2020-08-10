package com.example.HaulmontTestApp.backend.repository;

import com.example.HaulmontTestApp.backend.models.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctors,Integer> {
    @Query("select c from Doctors c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.surname) like lower(concat('%', :searchTerm, '%'))")
    List<Doctors> search(@Param("searchTerm") String searchTerm);

}
