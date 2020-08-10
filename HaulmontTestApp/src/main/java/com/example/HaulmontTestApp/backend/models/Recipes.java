package com.example.HaulmontTestApp.backend.models;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;


//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name="id")
//    private Doctors doctor;
//
//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name="id")
//    private Patients patient;

    @Column(name="creation_date")
    private Date date;

    private Date validity;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "priority")
    private Priority priority;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Doctors getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(Doctors doctor) {
//        this.doctor = doctor;
//    }
//
//    public Patients getPatient() {
//        return patient;
//    }
//
//    public void setPatient(Patients patient) {
//        this.patient = patient;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
