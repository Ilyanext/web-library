package ru.skypro.lessons.springboot.weblibrary.pojo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Position(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    @OneToMany(mappedBy = "position")
    private List<Employee> employee;
    public Position() {

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
}