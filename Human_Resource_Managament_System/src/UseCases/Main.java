package UseCases;

import Bins.Department;
import Bins.Employee;
import Colors.ColorString;
import Interface.Intr;
import Interface.IntrImpl;
import ManagementUseCase.AdminUseCase;
import ManagementUseCase.EmployeeUseCase;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"-----------------------------------");
            System.out.println(ColorString.TEXT_BRIGHT_CYAN+"|   Press 1 to login as employee  |");
            System.out.println("|   press 2 to login as admin     |");
            System.out.println("|   press 3 to exit               |");
            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"-----------------------------------"+ColorString.TEXT_RESET);

            int n = sc.nextInt();

            if(n==1){
                System.out.println("Enter your valid email :");
                String email = sc.next();
                System.out.println("Enter your password : ");
                String password = sc.next();
                Intr dao = new IntrImpl();
                int id = dao.loginEmployee(email,password);
                if(id>0){
                    EmployeeUseCase.main(id);
                }


            }

            else if(n==2){
                Intr dao = new IntrImpl();
                System.out.println("please enter email");
                String email = sc.next();
                System.out.println("please enter password");
                String password = sc.next();
                if(dao.adminLogin(email,password)==true){
                    AdminUseCase.main();
                }
                else{
                    System.out.println(ColorString.TEXT_RED+"Invalid email or password"+ColorString.TEXT_RESET);
                }
            }
            else if(n==3){
                System.out.println(ColorString.TEXT_BRIGHT_YELLOW+"Thank You for using my application");
                System.out.println("Application closed"+ColorString.TEXT_RESET);
                return;
            }
            else{
                System.out.println(ColorString.TEXT_RED+"please enter a valid number"+ColorString.TEXT_RESET);
            }
        }



    }
}