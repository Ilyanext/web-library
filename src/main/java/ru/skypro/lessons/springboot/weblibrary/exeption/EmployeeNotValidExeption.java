package ru.skypro.lessons.springboot.weblibrary.exeption;

import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;

public class EmployeeNotValidExeption extends RuntimeException{
    private final EmployeeDTO employeeDTO;
    public EmployeeNotValidExeption(EmployeeDTO employeeDTO){
        this.employeeDTO = employeeDTO;
    }
    public EmployeeDTO getEmployeeDTO(){
        return employeeDTO;
    }
}
