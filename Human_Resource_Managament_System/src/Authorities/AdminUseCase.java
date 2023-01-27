package Authorities;

import Models.Department;
import Models.Employee;
import Colors.ColorString;
import Exceptions.DepartmentException;
import Exceptions.EmployeeException;
import Dao.Intr;
import Dao.IntrImpl;
import UseCase.Main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminUseCase {

    public static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.println(ColorString.TEXT_GREEN+ "Login Successful"+ColorString.TEXT_RESET);

        System.out.println(ColorString.TEXT_CYAN+"Welcome Admin"+ColorString.TEXT_RESET);
        while(true){
            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"----------------------------------------------------------------------------");
            System.out.println(ColorString.TEXT_BRIGHT_CYAN+"|   Please press 1 to add new Department                                   |");
            System.out.println("|   please press 2 to view all departments                                 |");
            System.out.println("|   Please press 3 to register new employee into the db                    |");
            System.out.println("|   please press 4 to transfer existing employee to another department     |");
            System.out.println("|   Please press 5 to grant/deny leave request permissions                 |");
            System.out.println("|   Please press 6 to get all employees details                            |");
            System.out.println("|   Please press 7 to get all details of particular employee               |");
            System.out.println("|   please press 8 to go back                                              |");
            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"-----------------------------------------------------------------------------");

            int n = sc.nextInt();

            if(n==1){
                Intr dao= new IntrImpl();
                System.out.println("please enter department name : ");
                String name = sc.next();
                System.out.println("please enter department location : ");
                String location  = sc.next();
                try {
                    dao.addNewDepartment(name,location);
                } catch (SQLException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (DepartmentException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                }
            }
            else if(n==2){
                Intr dao = new IntrImpl();
                List<Department> departments = null;
                try {
                    departments = dao.getAllDepartmentDetails();
                } catch (DepartmentException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (SQLException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                }
                System.out.printf(ColorString.TEXT_BRIGHT_CYAN+"-----------------------------------%n");
                System.out.printf("| %-5s | %-10s | %-10s |%n", "DID", "DNAME","LOCATION");
                System.out.printf("-----------------------------------%n"+ColorString.TEXT_RESET);

                for(Department dep:departments){
                    System.out.printf(ColorString.TEXT_BRIGHT_PURPLE+"| %-5s | %-10s | %-10s |%n", dep.getDid(), dep.getDname(),dep.getLocation());

                }
                System.out.printf("-----------------------------------%n"+ColorString.TEXT_RESET);

            }
            else if(n==3){
                System.out.println(ColorString.TEXT_BRIGHT_WHITE+"please enter employee name");
                String name = sc.next();
                System.out.println("please enter employee email");
                String email = sc.next();
                System.out.println("please enter password");
                String password = sc.next();
                System.out.println("please enter department id"+ColorString.TEXT_RESET);
                int deptid = sc.nextInt();

                Intr dao = new IntrImpl();
                try {
                    dao.registerNewEmployee(name,email,password,deptid);
                } catch (EmployeeException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (DepartmentException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (SQLException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                }
            }

            else if(n==4){
                System.out.println(ColorString.TEXT_BRIGHT_WHITE+"please enter employee id");
                int eid = sc.nextInt();
                System.out.println("please enter department id"+ColorString.TEXT_RESET);
                int did = sc.nextInt();

                Intr dao = new IntrImpl();
                try {
                    dao.transferEmployeeToDepartment(eid,did);
                } catch (EmployeeException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (DepartmentException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (SQLException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                }

            }

            else if(n==5){
                Intr dao = new IntrImpl();
                dao.grantLeaveRequest();
            }

            else if(n==6){
                Intr dao = new IntrImpl();
                List<Employee> employees = null;
                try {
                    employees = dao.getAllEmployeeDetails();
                } catch (EmployeeException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (SQLException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                }
                System.out.printf(ColorString.TEXT_BRIGHT_PURPLE+"--------------------------------------------------------------------------------%n");
                System.out.printf("| %-3s | %-10s | %-20s | %-10s | %-6s | %-12s | %n", "EID", "ENAME","EMAIL","PASSWORD","DEPTID","LEAVE_STATUS");
                System.out.printf("--------------------------------------------------------------------------------%n"+ColorString.TEXT_RESET);


                for(Employee emp:employees){
                    System.out.printf(ColorString.TEXT_BRIGHT_CYAN+"| %-3s | %-10s | %-20s | %-10s | %-6s | %-12s | %n",emp.getEid(), emp.getName(), emp.getEmail(), emp.getPassword(), emp.getDeptid(), emp.getEmpLeave() );
                }
                System.out.printf("--------------------------------------------------------------------------------%n"+ColorString.TEXT_RESET);


            }
            else if(n==7){
                Intr dao = new IntrImpl();
                System.out.println("Please enter valid employee id : ");
                int id = sc.nextInt();
                try {
                    System.out.println(dao.getEmployeeDetailsById(id));
                } catch (EmployeeException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (SQLException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                }
            }
            else if(n==8){
                String[] args = {"Hello", "World"};
                try {
                    Main.main(args);
                } catch (EmployeeException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                } catch (SQLException e) {
                    System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                }
                break;
            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_RED+"Please enter a valid number"+ColorString.TEXT_RESET);
            }

        }
    }
}
