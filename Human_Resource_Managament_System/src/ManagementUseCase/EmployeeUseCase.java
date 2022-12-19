package ManagementUseCase;

import Bins.Employee;
import Colors.ColorString;
import Interface.Intr;
import Interface.IntrImpl;
import UseCases.Main;

import java.util.Scanner;

public class EmployeeUseCase {
    public static void main(int id) {
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"---------------------------------------------");
            System.out.println(ColorString.TEXT_BRIGHT_CYAN+"|   press 1 to view your details            |");
            System.out.println("|   Press 2 to change your password         |");
            System.out.println("|   Press 3 to change your email            |");
            System.out.println("|   press 4 to request for leave            |");
            System.out.println("|   press 5 to check your leave approvals   |");
            System.out.println("|   press 6 to go back                      |"+ColorString.TEXT_RESET);
            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"---------------------------------------------");
            int n = sc.nextInt();

            if(n==1){

                Intr dao = new IntrImpl();
                System.out.println(dao.getEmployeeDetailsById(id));
            }
            else if(n==2){
                Intr dao = new IntrImpl();

                System.out.println("Please enter your new password");
                String password = sc.next();
                dao.changePassword(id,password);
            }
            else if(n==3){
                System.out.println("Enter new email : ");
                String email = sc.next();
                Intr dao = new IntrImpl();
                dao.changeEmail(id, email);
            }
            else if(n==4){
                Intr dao = new IntrImpl();
                dao.requestForLeave(id);
            }
            else if(n==5){
                Intr dao = new IntrImpl();
                Employee emp = dao.getEmployeeDetailsById(id);
                if(emp.getEmpLeave().equals("pending")){
                    System.out.println(ColorString.TEXT_BRIGHT_CYAN+"Pending"+ColorString.TEXT_RESET);
                }
                else if(emp.getEmpLeave().equals("Accepted")){
                    System.out.println(ColorString.TEXT_BRIGHT_GREEN+emp.getEmpLeave()+ColorString.TEXT_RESET);
                }
                else{
                    System.out.println(ColorString.TEXT_BRIGHT_RED+emp.getEmpLeave()+ColorString.TEXT_RESET);
                }
            }
            else if(n==6){
                String[] args = {"Hello", "World"};
                Main.main(args);
                break;
            }
            else{
                System.out.println("please enter valid number");
            }
        }


    }
}
