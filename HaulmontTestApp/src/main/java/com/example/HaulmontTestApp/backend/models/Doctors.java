package com.example.HaulmontTestApp.backend.models;

import javax.persistence.*;
import java.util.List;
@Entity
@Table
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String patronymic;

    private String specialization;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "doctors")
//    private List<Recipes> recipes;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "doctors_has_patients",
            joinColumns = {@JoinColumn(name = "doctors_id")},
            inverseJoinColumns = {@JoinColumn(name = "patients_id")})
    private List<Patients>patients;

    public List<Patients> getPatients() {
        return patients;
    }

    public void setPatients(List<Patients> patients) {
        this.patients = patients;
    }

//    public List<Recipes> getRecipes() {
//        return recipes;
//    }
//
//    public void setRecipes(List<Recipes> recipes) {
//        this.recipes = recipes;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
