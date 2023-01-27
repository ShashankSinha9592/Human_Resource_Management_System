package Exceptions;

import Bins.Employee;

public class EmployeeException extends Exception{
    EmployeeException(){}
    public EmployeeException(String message){
        super(message);
    }
}
