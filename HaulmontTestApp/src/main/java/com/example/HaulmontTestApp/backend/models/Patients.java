package com.example.HaulmontTestApp.backend.models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String patronymic;

    @Column(name="phone_number")
    private Integer phoneNumber;

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,mappedBy = "patients")
//    private List<Recipes> recipes;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "doctors_has_patients",
            joinColumns = {@JoinColumn(name = "patients_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctors_id")})
    private List<Doctors>doctors=new LinkedList<>();

    public List<Doctors> getDoctors() {
        return doctors;
    }

    public String getDoctorsToString(){
        if(!doctors.isEmpty()) {
            return doctors.stream().map(s -> s.getSurname() + " " + s.getName() + " " + s.getPatronymic() + " "
                    + "|" + s.getSpecialization() + "|").collect(Collectors.toList()).toString();
        }return "";
    }

    public void setDoctors(List<Doctors> doctors) {
        this.doctors = doctors;
    }
//        public List<Recipes> getRecipes() {
//        return recipes;
//    }
//
//    public void setRecipes(List<Recipes> recipes) {
//        this.recipes = recipes;
//    }
public String getFullName(){
    return getSurname()+" "+getName()+" "+getPatronymic();
}


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

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
