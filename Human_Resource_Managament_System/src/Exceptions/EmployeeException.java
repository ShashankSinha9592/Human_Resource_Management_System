package Exceptions;

import Bins.Employee;

public class EmployeeException extends Exception{
    EmployeeException(){}
    EmployeeException(String message){
        super(message);
    }
}
