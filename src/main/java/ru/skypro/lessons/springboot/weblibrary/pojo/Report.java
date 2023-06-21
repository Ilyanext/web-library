package ru.skypro.lessons.springboot.weblibrary.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nameDepartment;
    private int numberEmployees;

    private int salaryMax;
    private int salaryMin;
    private int salaryAvg;

    public Report() {
    }

    public Report(Integer id, String nameDepartment, int numberEmployees, int salaryMax, int salaryMin, int salaryAvg) {
        this.id = id;
        this.nameDepartment = nameDepartment;
        this.numberEmployees = numberEmployees;
        this.salaryMax = salaryMax;
        this.salaryMin = salaryMin;
        this.salaryAvg = salaryAvg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public int getNumberEmployees() {
        return numberEmployees;
    }

    public void setNumberEmployees(int numberEmployees) {
        this.numberEmployees = numberEmployees;
    }

    public int getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(int salaryMax) {
        this.salaryMax = salaryMax;
    }

    public int getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(int salaryMin) {
        this.salaryMin = salaryMin;
    }

    public int getSalaryAvg() {
        return salaryAvg;
    }

    public void setSalaryAvg(int salaryAvg) {
        this.salaryAvg = salaryAvg;
    }
}
