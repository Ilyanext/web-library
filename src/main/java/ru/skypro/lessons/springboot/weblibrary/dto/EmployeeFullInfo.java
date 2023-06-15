package ru.skypro.lessons.springboot.weblibrary.dto;

public class EmployeeFullInfo {
    private int id;
    private String name;
    // Зарплата сотрудника
    private Integer salary;
    // Название должности сотрудника
    private String positionName;


    // Конструктор класса EmployeeFullInfo
    public EmployeeFullInfo(int id, String name, Integer salary, String positionName) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.positionName = positionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
